// Generated code from Butter Knife. Do not modify!
package com.zeonic.icity.location_picker.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LocationListActivity$$ViewBinder<T extends com.zeonic.icity.location_picker.ui.LocationListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'listview'");
    target.listview = finder.castView(view, 2131558524, "field 'listview'");
  }

  @Override public void unbind(T target) {
    target.listview = null;
  }
}
