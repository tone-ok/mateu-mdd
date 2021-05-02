package org.vaadin.example.addon;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import io.mateu.mdd.Mateu;
import org.vaadin.example.addon.model.Persona;

@Route("form")
public class FormView extends Div {

    public FormView() {
        add(Mateu.createFormComponent(new Persona()));
    }
}
