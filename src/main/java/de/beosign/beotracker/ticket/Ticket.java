package de.beosign.beotracker.ticket;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import de.beosign.beotracker.jpa.AbstractEntity;
import de.beosign.beotracker.user.User;

@Entity
public class Ticket extends AbstractEntity {
    public static final Ticket NULL_TICKET = new Ticket(-1000);

    private static final long serialVersionUID = 1L;

    private String summary;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @ManyToOne
    private User assignedUser;

    public Ticket() {
    }

    private Ticket(int id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Ticket [summary=" + summary + ", status=" + status + ", assignedUser=" + assignedUser + ", id=" + id + ", version=" + version + "]";
    }

    /**
     * Status values for tickets.
     * 
     * @author florian
     */
    public enum Status {
        /**
         * NEW.
         */
        NEW,

        /**
         * ASSIGNED.
         */
        ASSIGNED,

        /**
         * IN PROGRESS.
         */
        INPROGRESS,

        /**
         * RESOLVED.
         */
        RESOLVED,

        /**
         * CLOSED.
         */
        CLOSED
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

}
