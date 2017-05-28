package de.beosign.beotracker.ticket;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.beosign.beotracker.jpa.AbstractEntity;

@Entity
@NamedQueries(//
@NamedQuery(name = Ticket.FIND_BY_ID, query = "SELECT t from Ticket t where t.id = :id")//
)
public class Ticket extends AbstractEntity {
    public static final String FIND_BY_ID = "Ticket.findById";

    private String summary;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Ticket [summary=" + summary + ", id=" + id + ", version=" + version + "]";
    }

}
