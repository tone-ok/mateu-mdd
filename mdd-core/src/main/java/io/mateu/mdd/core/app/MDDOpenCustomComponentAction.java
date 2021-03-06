package io.mateu.mdd.core.app;


import com.vaadin.ui.Component;
import io.mateu.mdd.core.interfaces.WizardPage;
import io.mateu.reflection.ReflectionHelper;
import io.mateu.util.notification.Notifier;

import java.lang.reflect.InvocationTargetException;

public class MDDOpenCustomComponentAction extends AbstractAction {

    public final Class viewClass;
    public final Object component;

    public MDDOpenCustomComponentAction(String name, Class viewClass) {
        super(name);
        this.viewClass = viewClass;
        this.component = null;
    }

    public MDDOpenCustomComponentAction(String name, Object component) {
        super(name);
        this.viewClass = component.getClass();
        this.component = component;
    }

    @Override
    public String toString() {
        return "Home";
    }

    public Component getComponent() {
        try {
            return (Component) (component != null?component: ReflectionHelper.newInstance(viewClass));
        } catch (Exception e) {
            Notifier.alert(e);
            return null;
        }
    }
}
