package de.beosign.beotracker.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class SettingsController implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Setting<?>> settings;

    @PostConstruct
    private void init() {
        settings = new ArrayList<>();

        Setting<Integer> maxLength = new Setting<>("maxLength", 10);
        Setting<Integer> minLength = new Setting<>("minLength", 3);

        maxLength.getValidationRules().add(s -> s > 0);
        minLength.getValidationRules().add(s -> s > 0);

        settings.add(minLength);
        settings.add(maxLength);
    }

    public void onSave() {
        System.out.println("SAVE");
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    public void setSettings(List<Setting<?>> settings) {
        this.settings = settings;
    }
}
