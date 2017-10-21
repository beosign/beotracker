package de.beosign.beotracker.tag;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TagHandlers are called twice on AJAX: During RESTORE_VIEW and RENDER_RESPONSE.
 * It is possible that the condition changes between those phases; this causes the component tree to change.
 * <p>
 * Working example: A button adds an item to a list with ajax and updates the if-tag, which tests for even entries in the list.
 * <ul>
 * <li>During phase 1, the nr of entries is let's say even; an outputText is added with the text "size is even".</li>
 * <li>During phase 5, an item is added to the list.</li>
 * <li>During phase 6, the IfTagHandler is called again, now evaluating to "odd", in which case a different control is added.</li>
 * </ul>
 * Result: Only the "odd" control is rendered.
 * </p>
 * <p>
 * This is just a copy of Mojarra's implementation of the IfHandler and does not provide any additional functionality.
 * </p>
 * 
 * @author florian
 */
public class IfTagHandler extends TagHandler {
    private static final Logger log = LoggerFactory.getLogger(IfTagHandler.class);

    private TagAttribute test;
    private TagAttribute var;

    public IfTagHandler(TagConfig config) {
        super(config);
        this.test = this.getRequiredAttribute("test");
        this.var = this.getAttribute("var");
    }

    @Override
    public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
        log.debug("[" + ctx.getFacesContext().getCurrentPhaseId() + "] IfTagHandler called with parent " + parent.getId());
        boolean condition = this.test.getBoolean(ctx);
        if (this.var != null) {
            ctx.setAttribute(var.getValue(ctx), condition);
        }
        if (condition) {
            this.nextHandler.apply(ctx, parent);
        }

    }

}
