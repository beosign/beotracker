package de.beosign.beotracker.jpa;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    protected int id;

    @Version
    protected int version;

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

}
