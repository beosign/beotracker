package de.beosign.beotracker.status;

import java.util.List;

public interface StatefulEntity<T> {
    List<T> getTransitions();
}
