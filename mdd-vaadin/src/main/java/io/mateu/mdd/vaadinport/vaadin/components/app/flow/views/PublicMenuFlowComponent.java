package io.mateu.mdd.vaadinport.vaadin.components.app.flow.views;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.mateu.mdd.core.MDD;
import io.mateu.mdd.vaadinport.vaadin.MyUI;

public class PublicMenuFlowComponent extends VerticalLayout {

    @Override
    public String toString() {
        return "Public areas for " + MDD.getApp().getName();
    }


    public PublicMenuFlowComponent() {

        addStyleName("publicmenuflowcomponent");


        MDD.getApp().getAreas().stream().filter(a -> a.isPublicAccess()).forEach(a -> {

            Button b;
            addComponent(b = new Button(a.getName(), a.getIcon()));
            b.addClickListener(e -> MyUI.get().getNavegador().goTo(a));
            b.addStyleName(ValoTheme.BUTTON_QUIET);


        });


        addComponentsAndExpand(new Label(""));
    }

}
