package de.beosign.beotracker.component.dynaform;

import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class DynaFormRowBuilder {
    private DynaFormRow dynaFormRow;
    private DynaFormModel dynaFormModel;

    public DynaFormRowBuilder(DynaFormModel dynaFormModel) {
        this.dynaFormModel = dynaFormModel;
        dynaFormRow = dynaFormModel.createRegularRow();
    }

    public static DynaFormRowBuilder create(DynaFormModel dynaFormModel) {
        return new DynaFormRowBuilder(dynaFormModel);
    }

    public static DynaFormRow createWithLabelAndControl(DynaFormModel dynaFormModel, DynaFormProperty dynaFormProperty) {
        DynaFormRowBuilder builder = DynaFormRowBuilder.create(dynaFormModel)
                .addLabel(dynaFormProperty.getLabel())
                .addControl(dynaFormProperty);

        ((DynaFormLabel) builder.dynaFormRow.getElements().get(0)).setForControl((DynaFormControl) builder.dynaFormRow.getElements().get(1));

        return builder.build();
    }

    public DynaFormRowBuilder addLabel(String label) {
        dynaFormRow.addLabel(label);
        return this;
    }

    public DynaFormRowBuilder addControl(DynaFormProperty dynaFormProperty) {
        dynaFormRow.addControl(dynaFormProperty, dynaFormProperty.getType());
        return this;
    }

    public DynaFormRow build() {
        return dynaFormRow;
    }

}
