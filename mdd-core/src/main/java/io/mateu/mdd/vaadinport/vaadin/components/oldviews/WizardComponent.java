package io.mateu.mdd.vaadinport.vaadin.components.oldviews;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.mateu.mdd.core.CSS;
import io.mateu.mdd.core.MDD;
import io.mateu.mdd.core.interfaces.WizardPage;
import io.mateu.mdd.core.util.Helper;
import io.mateu.mdd.vaadinport.vaadin.MDDUI;
import io.mateu.mdd.vaadinport.vaadin.navigation.MDDViewComponentCreator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WizardComponent extends VerticalLayout {

    private final Button goToPreviousButton;
    private final Button goToNextButton;
    private final VerticalLayout container;
    private final Button okButton;
    private List<WizardPage> stack = new ArrayList<>();
    private WizardPage currentPage = null;
    private EditorViewComponent editorViewComponent;

    public EditorViewComponent getEditorViewComponent() {
        return editorViewComponent;
    }

    public WizardComponent(WizardPage page) throws Exception {

        addStyleName(CSS.NOPADDING);

        setSizeFull();

        Panel panel;
        addComponentsAndExpand(panel = new Panel(container = new VerticalLayout()));
        panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        container.addStyleName(CSS.NOPADDING);

        HorizontalLayout hl;
        addComponent(hl = new HorizontalLayout(goToPreviousButton = new Button(VaadinIcons.ARROW_LEFT), goToNextButton = new Button(VaadinIcons.ARROW_RIGHT), okButton = new Button(VaadinIcons.CHECK)));
        hl.addStyleName(CSS.NOPADDING);

        goToPreviousButton.addClickListener(e -> {
            WizardPage prevPage = stack.remove(0);
            currentPage = null;
            try {
                setPage(prevPage);
            } catch (Exception e1) {
                MDD.alert(e1);
            }
        });


        goToNextButton.addClickListener(e -> {
            if (getEditorViewComponent().validate()) {
                WizardPage nextPage = currentPage.getNext();
                try {
                    setPage(nextPage);
                } catch (Exception e1) {
                    MDD.alert(e1);
                }
            }
        });

        okButton.addClickListener(e -> {
            if (getEditorViewComponent().validate()) {
                try {

                    currentPage.onOk();

                    if (currentPage.backOnOk()) MDDUI.get().getNavegador().goBack();

                } catch (Throwable throwable) {
                    MDD.alert(throwable);
                }
            }
        });

        setPage(page);

        addAttachListener(x -> {
            log.debug("attached!");
            if (false && editorViewComponent != null) {
                editorViewComponent.getBinder().addValueChangeListener(e -> {
                    updateButtons();
                });
            }
            updateButtons();
        });
    }

    private void setPage(WizardPage page) throws Exception {

        editorViewComponent = MDDViewComponentCreator.createEditorViewComponent(page.getClass(), false);
        editorViewComponent.setModel(page);
        editorViewComponent.addStyleName(CSS.NOPADDING);

        editorViewComponent.getBinder().addValueChangeListener(e -> {
            updateButtons();
        });

        container.removeAllComponents();


        container.addComponent(editorViewComponent);

        if (currentPage != null) stack.add(0, currentPage);
        currentPage = page;

        MDD.updateTitle(Helper.capitalize(currentPage.getClass().getSimpleName()));

        updateButtons();
    }

    private void updateButtons() {
        goToPreviousButton.setVisible(stack.size() > 0);
        goToNextButton.setVisible(currentPage.hasNext());
        okButton.setVisible(!currentPage.hasNext());

        boolean valid = currentPage.isValid() && getEditorViewComponent().validate(true);

        goToNextButton.setEnabled(valid);
        okButton.setEnabled(valid);
    }

    @Override
    public String toString() {
        return Helper.capitalize(currentPage.getClass().getSimpleName());
    }

}
