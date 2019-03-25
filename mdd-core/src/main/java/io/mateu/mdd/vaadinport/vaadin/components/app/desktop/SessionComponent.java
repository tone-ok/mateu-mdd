package io.mateu.mdd.vaadinport.vaadin.components.app.desktop;

import com.google.common.base.Strings;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.mateu.mdd.core.MDD;
import io.mateu.mdd.core.data.UserData;
import io.mateu.mdd.vaadinport.vaadin.MDDUI;

public class SessionComponent extends VerticalLayout {

    private final Image foto;
    private final Button signingButton;
    //private final Label greeting;

    public SessionComponent() {

        addStyleName("sesion");

        addComponent(foto = new Image("", new ClassResource("/images/profile.jpg")));
        foto.setVisible(false);
        foto.addStyleName("foto");
        //foto.setDescription("Click to change your profile");
        foto.addClickListener(e -> MDDUI.get().getNavegador().goTo("private/profile"));

        //addComponent(greeting = new Label(""));
        //greeting.addStyleName("greeting");


        addComponent(signingButton = new Button("Login"));
        signingButton.setPrimaryStyleName(ValoTheme.BUTTON_LINK);
        signingButton.addStyleName("signingbutton");
        signingButton.addClickListener(e -> {
            if (MDD.getUserData() != null) {
                MDDUI.get().getNavegador().goTo("bye");
            } else {
                MDDUI.get().getNavegador().goTo("login");
            }
        });

        if (!MDD.getApp().isAuthenticationNeeded() && !MDD.getApp().hasPrivateContent()) signingButton.setVisible(false);



    }

    public void update() {
        UserData ud = MDD.getUserData();
        foto.setSource((ud != null && !Strings.isNullOrEmpty(ud.getPhoto()))?new ExternalResource(ud.getPhoto()):new ClassResource("/images/profile.jpg"));
        foto.setVisible(ud != null);

        if (MDD.getUserData() != null) {
            //greeting.setValue("Hi " + MDD.getUserData().getName());
            foto.setCaption("Hi " + MDD.getUserData().getName());
            signingButton.setCaption("Logout");
        } else {
            //greeting.setValue("");
            signingButton.setCaption("Login");
        }

    }
}
