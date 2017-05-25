// Generated code from Butter Knife. Do not modify!
package com.zeonic.icity.location_picker.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyAdapter$ViewHolder$$ViewBinder<T extends com.zeonic.icity.location_picker.ui.MyAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558543, "field 'latText'");
    target.latText = finder.castView(view, 2131558543, "field 'latText'");
    view = finder.findRequiredView(source, 2131558542, "field 'lngText'");
    target.lngText = finder.castView(view, 2131558542, "field 'lngText'");
    view = finder.findRequiredView(source, 2131558541, "field 'remarkText'");
    target.remarkText = finder.castView(view, 2131558541, "field 'remarkText'");
    view = finder.findRequiredView(source, 2131558540, "field 'levelText'");
    target.levelText = finder.castView(view, 2131558540, "field 'levelText'");
  }

  @Override public void unbind(T target) {
    target.latText = null;
    target.lngText = null;
    target.remarkText = null;
    target.levelText = null;
  }
}
