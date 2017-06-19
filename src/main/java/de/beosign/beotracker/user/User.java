package de.beosign.beotracker.user;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import de.beosign.beotracker.jpa.AbstractEntity;

@Entity
@NamedQuery(name = User.QUERY_FIND_BY_LOGIN, query = "select u from User u where u.loginName = :loginName")
public class User extends AbstractEntity {
    static final String QUERY_FIND_BY_LOGIN = "User.findByLogin";

    private static final long serialVersionUID = 1L;

    private String loginName;

    protected User() {
    }

    public User(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        return "User [loginName=" + loginName + ", id=" + id + ", version=" + version + "]";
    }

    // CHECKSTYLE:OFF
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (loginName == null) {
            if (other.loginName != null)
                return false;
        } else if (!loginName.equals(other.loginName))
            return false;
        return true;
    }

}
