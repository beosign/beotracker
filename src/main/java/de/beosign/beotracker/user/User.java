package de.beosign.beotracker.user;

import javax.persistence.Entity;

import de.beosign.beotracker.jpa.AbstractEntity;

@Entity
public class User extends AbstractEntity {
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

}
