// Generated code from Butter Knife. Do not modify!
package com.zeonic.icity.location_picker.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MapActivity$$ViewBinder<T extends com.zeonic.icity.location_picker.ui.MapActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558525, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131558525, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131558527, "method 'gotoList'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.gotoList(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.toolbar = null;
  }
}
