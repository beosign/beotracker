package de.beosign.beotracker.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logging Filter for Wildfly for monitoring the nummber of sql statements during <b>development</b>. In order to make it work, create the following entries in
 * standalone.xml:
 * <br/>
 * <br/>
 * 1. Create a logging handler. The path must match the init param of the filter, see below.
 * 
 * <pre>
 *    &lt;file-handler name="SQL" autoflush="true"&gt;
 *        &lt;formatter&gt;
 *            &lt;named-formatter name="PATTERN"/&gt;
 *        &lt;/formatter&gt;
 *        &lt;file relative-to="jboss.server.log.dir" path="sql.log"/&gt;
 *        &lt;append value="false"/&gt;
 *    &lt;/file-handler&gt;
 * </pre>
 * 
 * <br/>
 * 2. Create a logger and associate the above handler with it
 * 
 * <pre>
 *    &lt;logger category="org.hibernate.SQL"&gt;
 *        &lt;level name="DEBUG"/&gt;
 *        &lt;handlers&gt;
 *           &lt;handler name="SQL"/&gt;
 *        &lt;/handlers&gt;
 *    &lt;/logger&gt;
 * </pre>
 * 
 * In addition, be sure that the path to the logfile is correct. To change the path to fit your needs, add the following to the web.xml file:
 * 
 * <pre>
 *     &lt;filter&gt;
 *       &lt;filter-name&gt;WildflySqlLoggingFilter&lt;/filter-name&gt;
 *       &lt;init-param&gt;
 *           &lt;param-name&gt;logfile&lt;/param-name&gt;
 *           &lt;param-value&gt;C:/myPath/wildfly/standalone/log/sql.log&lt;/param-value&gt;
 *       &lt;/init-param>
 *   &lt;/filter&gt;
 * </pre>
 * <p>
 * <b>Restrictions:</b>
 * <ul>
 * <li>Only works for a single user; if multiple users interact with the system at the same time (no concurrent users supported)</li>
 * </ul>
 * 
 * @author florian
 */
@WebFilter(
        filterName = "WildflySqlLoggingFilter",
        urlPatterns = { "*.xhtml" },
        initParams = { @WebInitParam(name = "logfile", value = "C:/Program Files/Application Server/wildfly-10.0.0.Final/standalone/log/sql.log") })
public class WildflySqlLoggingFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(WildflySqlLoggingFilter.class);

    private String logfileName;

    public WildflySqlLoggingFilter() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        logfileName = config.getInitParameter("logfile");
        log.info("Using the following logfile for extracting sql statement information: {}", logfileName);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        if (isFacesRequest((HttpServletRequest) req)) {
            // clear logfile
            try {
                File file = new File(logfileName);
                try (FileWriter fw = new FileWriter(file)) {
                    fw.write("" + System.lineSeparator()); // Needed because sometimes \0 are added to first line
                    fw.flush();
                }
            } catch (IOException e1) {
                log.error("Error cleaning logfile {}", logfileName, e1);
            }
        }

        chain.doFilter(req, resp);

        if (isFacesRequest((HttpServletRequest) req)) {
            File file = new File(logfileName);
            // evaluate entries in logfile
            Map<String, List<String>> groupedStrings = Files.lines(file.toPath())
                    .map(line -> {
                        if (line.indexOf("select") >= 0) {
                            return line.substring(line.indexOf("select"));
                        }
                        if (line.indexOf("insert") >= 0) {
                            return line.substring(line.indexOf("insert"));
                        }
                        if (line.indexOf("delete") >= 0) {
                            return line.substring(line.indexOf("delete"));
                        }
                        if (line.indexOf("update") >= 0) {
                            return line.substring(line.indexOf("update"));
                        }
                        return "";
                    })
                    .filter(line -> line != null && !line.isEmpty())
                    .collect(Collectors.groupingBy(line -> line.toString().split("\\s")[0]));

            groupedStrings.entrySet().forEach(entry -> {
                HttpServletRequest r = (HttpServletRequest) req;
                String event = "GET";
                String source = "<null>";
                if (r.getParameter("javax.faces.partial.ajax") != null) {
                    event = r.getParameter("javax.faces.partial.event");
                    source = r.getParameter("javax.faces.source");
                }
                log.trace(r.getRequestURL().toString() + "(" + source + "," + event + "): " + entry.getKey() + ": " + entry.getValue().size());
            });
        }

    }

    /**
     * Checks if an xhtml (facelet) is requested (and not jsut a resource like css or js).
     * 
     * @param request request
     */
    public static boolean isFacesRequest(HttpServletRequest request) {
        return request.getRequestURL().toString().endsWith(".xhtml") && !request.getRequestURL().toString().contains("javax.faces.resource");
    }

}
