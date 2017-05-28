package de.beosign.beotracker.jpa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
