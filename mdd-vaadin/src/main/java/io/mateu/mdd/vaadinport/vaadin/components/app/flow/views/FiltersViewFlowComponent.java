package io.mateu.mdd.vaadinport.vaadin.components.app.flow.views;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.mateu.mdd.vaadinport.vaadin.MyUI;
import io.mateu.mdd.vaadinport.vaadin.components.oldviews.ListViewComponent;

public class FiltersViewFlowComponent extends VerticalLayout {

    private final ListViewComponent listViewComponent;

    @Override
    public String toString() {
        return "All filters for " + listViewComponent;
    }

    public FiltersViewFlowComponent(String state, ListViewComponent listViewComponent) {
        this.listViewComponent = listViewComponent;

        addStyleName("filtersflowcomponent");

        Panel p = new Panel(listViewComponent.getFiltersViewComponent());
        p.addStyleName(ValoTheme.PANEL_BORDERLESS);
        addComponentsAndExpand(p);


        Button b;
        addComponent(b = new Button(VaadinIcons.SEARCH));
        b.setDescription("Search");
        b.addStyleName(ValoTheme.BUTTON_QUIET);
        b.addStyleName("buttonlink");

        b.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                MyUI.get().getNavegador().goBack();
            }
        });

        b.setClickShortcut(ShortcutAction.KeyCode.ENTER);



    }

}
