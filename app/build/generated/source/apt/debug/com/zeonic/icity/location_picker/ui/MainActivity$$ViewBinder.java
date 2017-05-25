// Generated code from Butter Knife. Do not modify!
package com.zeonic.icity.location_picker.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.zeonic.icity.location_picker.ui.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'listView'");
    target.listView = finder.castView(view, 2131558524, "field 'listView'");
    view = finder.findRequiredView(source, 2131558523, "field 'latText'");
    target.latText = finder.castView(view, 2131558523, "field 'latText'");
    view = finder.findRequiredView(source, 2131558522, "field 'lngText'");
    target.lngText = finder.castView(view, 2131558522, "field 'lngText'");
    view = finder.findRequiredView(source, 2131558519, "field 'remarkText'");
    target.remarkText = finder.castView(view, 2131558519, "field 'remarkText'");
    view = finder.findRequiredView(source, 2131558521, "field 'numPicker'");
    target.numPicker = finder.castView(view, 2131558521, "field 'numPicker'");
  }

  @Override public void unbind(T target) {
    target.listView = null;
    target.latText = null;
    target.lngText = null;
    target.remarkText = null;
    target.numPicker = null;
  }
}
