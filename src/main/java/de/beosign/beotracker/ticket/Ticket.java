package de.beosign.beotracker.ticket;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

import de.beosign.beotracker.jpa.AbstractEntity;

@Entity
public class Ticket extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private String summary;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

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
        return "Ticket [summary=" + summary + ", status=" + status + ", id=" + id + ", version=" + version + "]";
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

}
