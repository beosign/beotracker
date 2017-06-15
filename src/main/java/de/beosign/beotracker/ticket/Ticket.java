package de.beosign.beotracker.ticket;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.beosign.beotracker.jpa.AbstractEntity;
import de.beosign.beotracker.status.StatefulEntity;
import de.beosign.beotracker.ticket.validation.ValidStatus;
import de.beosign.beotracker.user.User;

@Entity
@ValidStatus
public class Ticket extends AbstractEntity implements StatefulEntity<Ticket.Status> {
    public static final Ticket NULL_TICKET = new Ticket(-1000);

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1)
    private String summary;

    @Enumerated
    @NotNull
    private Priority priority;

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

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public List<Status> getTransitions() {
        return Arrays.asList(Status.values());
    }

    @Override
    public String toString() {
        return "Ticket [summary=" + summary + ", priority=" + priority + ", description=" + description + ", status=" + status + ", assignedUser="
                + assignedUser + "]";
    }

    /**
     * Status values for tickets.
     * 
     * @author florian
     */
    public enum Status {
        /**
         * CLOSED.
         */
        CLOSED,

        /**
         * RESOLVED.
         */
        RESOLVED,

        /**
         * IN PROGRESS.
         */
        INPROGRESS,

        /**
         * ASSIGNED.
         */
        ASSIGNED,

        /**
         * NEW.
         */
        NEW;

    }

    /**
     * Priority.
     * 
     * @author florian
     */
    public enum Priority {
        /**
         * VERY HIGH.
         */
        VERY_HIGH,

        /**
         * HIGH.
         */
        HIGH,

        /**
         * MEDIUM.
         */
        MEDIUM,

        /**
         * LOW.
         */
        LOW;
    }

}
