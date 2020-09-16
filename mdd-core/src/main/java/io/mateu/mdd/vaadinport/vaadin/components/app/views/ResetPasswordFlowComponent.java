package io.mateu.mdd.vaadinport.vaadin.components.app.views;

import com.google.common.base.Strings;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.mateu.mdd.core.MDD;
import io.mateu.mdd.core.app.BaseMDDApp;
import io.mateu.mdd.core.interfaces.GeneralRepository;
import io.mateu.mdd.core.interfaces.UserPrincipal;
import io.mateu.mdd.util.Helper;
import io.mateu.mdd.util.JPAHelper;
import io.mateu.mdd.vaadinport.vaadin.MDDUI;

public class ResetPasswordFlowComponent extends VerticalLayout {

    private final String key;
    private PasswordField passwordRepeated;
    private PasswordField password;

    @Override
    public String toString() {
        return "Reset password";
    }


    public ResetPasswordFlowComponent(String key) {

        this.key = key;

        addStyleName("logincomponent");


        if (!MDD.isMobile()) setSizeFull();


        try {
            UserPrincipal u = Helper.getImpl(GeneralRepository.class).findUserByPasswordResetKey(key);

            Label t;
            //izda.addComponent(t = new Label("Sign in " + MDD.getApp().getName()));
            //t.addStyleName(ValoTheme.LABEL_H3);

            addComponent(password = new PasswordField("New password"));
            addComponent(passwordRepeated = new PasswordField("Repeat new password"));

            HorizontalLayout hl;
            addComponent(hl = new HorizontalLayout());
            Button b;
            hl.addComponent(b = new Button("Update password and sign in", e -> login()));
            //b.setDescription("Click ENTER to update password and sign in");
            b.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            b.addStyleName(ValoTheme.BUTTON_PRIMARY);

            if (!MDD.isMobile()) addComponentsAndExpand(new Label(""));



            if (password != null) password.focus();

        } catch (Throwable e) {
            setError(e.getMessage());
        }




    }

    private void setError(String msg) {
        Label l;
        addComponent(l = new Label(msg));
        l.addStyleName(ValoTheme.LABEL_H2);
        l.addStyleName(ValoTheme.NOTIFICATION_ERROR);
    }

    private void login() {
        try {
            String[] login = {""};

            try {

                GeneralRepository repo = Helper.getImpl(GeneralRepository.class);

                UserPrincipal u = repo.findUserByPasswordResetKey(key);

                if (!Strings.isNullOrEmpty(password.getValue()) && password.getValue().equals(passwordRepeated.getValue())) {

                    repo.setPassword(key, password.getValue());

                    login[0] = u.getLogin();

                } else {
                    throw new Exception("Both password fields must match. Please try again");
                }
            } catch (Throwable e) {
                setError(e.getMessage());
            }

            ((BaseMDDApp)MDD.getApp()).authenticate(login[0], password.getValue());
            MDDUI.get().getNavegador().goTo("welcome");

        } catch (Throwable throwable) {
            MDD.alert(throwable);
        }
    }


}
