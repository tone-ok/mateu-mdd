package io.mateu.mdd.vaadin.components.fieldBuilders.components;

import com.google.common.collect.Lists;
import com.vaadin.data.HasValue;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import io.mateu.mdd.shared.annotations.Html;
import io.mateu.mdd.shared.reflection.FieldInterfaced;
import io.mateu.mdd.vaadin.data.MDDBinder;
import io.mateu.util.Helper;
import io.mateu.util.interfaces.GeneralRepository;
import io.mateu.util.interfaces.Translated;
import io.mateu.util.notification.Notifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LiteralComponent extends Composite implements HasValue<Translated>, Component.Focusable {
    private final AbstractField<String> tf;
    private final ComboBox<String> cb;
    private final MDDBinder binder;
    private Translated literal;
    private Map<UUID, ValueChangeListener> listeners = new HashMap<>();


    public LiteralComponent(FieldInterfaced field, MDDBinder binder) {
        this.binder = binder;

        Collection<String> langs = Lists.newArrayList("es", "en", "de", "fr", "it", "ar", "cz", "ru");

        if (field.isAnnotationPresent(Html.class)) tf = new RichTextArea();
        else if (field.isAnnotationPresent(io.mateu.mdd.shared.annotations.TextArea.class)) tf = new TextArea();
        else tf = new TextField();

        Button b;
        HorizontalLayout hl = new HorizontalLayout(tf, cb = new ComboBox<String>(null, langs), b = new Button("<i class=\"fas fa-language\"></i>", (e) -> {
            Notifier.alert("DeepL is not configured. Please contact your administrator.");
        }));
        b.setCaptionAsHtml(true);
        cb.setWidth("80px");
        cb.setEmptySelectionAllowed(false);
        cb.setValue("es");

        cb.addValueChangeListener(e -> {
            if (literal != null) {
                String v = literal.getEs();
                if ("en".equals(e.getValue())) v = literal.getEn();
                if ("de".equals(e.getValue())) v = literal.getDe();
                if ("fr".equals(e.getValue())) v = literal.getFr();
                if ("it".equals(e.getValue())) v = literal.getIt();
                if ("ar".equals(e.getValue())) v = literal.getAr();
                if ("cz".equals(e.getValue())) v = literal.getCz();
                if ("ru".equals(e.getValue())) v = literal.getRu();
                if (v == null) v = "";
                tf.setValue(v);
            }
        });

        setCompositionRoot(hl);

        if (tf instanceof AbstractTextField) ((AbstractTextField) tf).setValueChangeMode(ValueChangeMode.BLUR);

        tf.addValueChangeListener(e -> {

            if (e.isUserOriginated()) {
                if ("en".equals(cb.getValue())) literal.setEn(e.getValue());
                else if ("de".equals(cb.getValue())) literal.setDe(e.getValue());
                else if ("fr".equals(cb.getValue())) literal.setFr(e.getValue());
                else if ("it".equals(cb.getValue())) literal.setIt(e.getValue());
                else if ("ar".equals(cb.getValue())) literal.setAr(e.getValue());
                else if ("cz".equals(cb.getValue())) literal.setCz(e.getValue());
                else if ("ru".equals(cb.getValue())) literal.setRu(e.getValue());
                else literal.setEs(e.getValue());

            }

            ValueChangeEvent ce = new ValueChangeEvent(this, this, e.isUserOriginated());

            listeners.values().forEach(l -> l.valueChange(ce));

        });

    }

    @Override
    public void setValue(Translated o) {
        literal = o;
        if (literal == null) {
            try {
                literal = Helper.getImpl(GeneralRepository.class).getNewTranslated();
            } catch (Exception e) {
                Notifier.alert(e);
            }
        }

        if (literal != null) {
            String v = literal.getEs();
            if ("en".equals(cb.getValue())) v = literal.getEn();
            if ("de".equals(cb.getValue())) v = literal.getDe();
            if ("fr".equals(cb.getValue())) v = literal.getFr();
            if ("it".equals(cb.getValue())) v = literal.getIt();
            if ("ar".equals(cb.getValue())) v = literal.getAr();
            if ("cz".equals(cb.getValue())) v = literal.getCz();
            if ("ru".equals(cb.getValue())) v = literal.getRu();
            if (v == null) v = "";
            tf.setValue(v);
        }
    }

    @Override
    public Translated getValue() {
        return literal;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean b) {
        tf.setRequiredIndicatorVisible(b);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return tf.isRequiredIndicatorVisible();
    }

    @Override
    public void setReadOnly(boolean b) {
        tf.setReadOnly(b);
    }

    @Override
    public boolean isReadOnly() {
        return tf.isReadOnly();
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<Translated> valueChangeListener) {
        UUID _id = UUID.randomUUID();
        listeners.put(_id, valueChangeListener);
        return new Registration() {

            UUID id = _id;

            @Override
            public void remove() {
                listeners.remove(id);
            }
        };
    }

    @Override
    public void focus() {
        tf.focus();
    }

    @Override
    public int getTabIndex() {
        return tf.getTabIndex();
    }

    @Override
    public void setTabIndex(int i) {
        tf.setTabIndex(i);
    }
}
