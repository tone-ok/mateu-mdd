package io.mateu.mdd.core.interfaces;

import io.mateu.mdd.shared.data.MDDBinder;

public interface DoBeforeRemoveFromCollection {

    void onRemove(MDDBinder binder) throws Throwable;

}
