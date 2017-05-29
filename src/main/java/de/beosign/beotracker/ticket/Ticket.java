package de.beosign.beotracker.ticket;

import javax.persistence.Entity;

import de.beosign.beotracker.jpa.AbstractEntity;

@Entity
public class Ticket extends AbstractEntity {
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
