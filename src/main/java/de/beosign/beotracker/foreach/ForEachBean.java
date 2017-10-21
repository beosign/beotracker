package de.beosign.beotracker.foreach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ForEachBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Integer> items = new ArrayList<>();

    @PostConstruct
    private void init() {
        items.add(1);
        items.add(2);
    }

    public void addItem() {
        items.add(items.size() + 1);
    }

    public void removeItem() {
        items.remove(items.size() - 1);
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }
}
