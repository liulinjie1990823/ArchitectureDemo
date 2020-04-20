package com.llj.lib.base;

import android.view.View;
import android.view.ViewGroup;
import androidx.viewbinding.ViewBinding;
import com.llj.lib.base.mvp.IBasePresenter;
import com.llj.lib.base.widget.CommonToolbar;

/**
 * ArchitectureDemo describe: author liulj date 2018/5/24
 */
public abstract class BaseToolbarActivity<V extends ViewBinding, P extends IBasePresenter> extends
    MvpBaseActivity<V, P> {

  public CommonToolbar mCommonToolbar;

  @Override
  public View layoutView() {
    ViewGroup rootView = null;
    if (layoutId() != 0) {
      rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.base_title_layout, null);
      initToolbar(rootView);
      //inflate后面直接加rootView的作用是源码里面直接通过root.generateLayoutParams(attrs)来获得rootView的Linerlayout.LayoutParams
      //这样getLayoutId()产生的view就可以直接设置view.setLayoutParams(params);这里的params就是上面获得的LayoutParams
      //这样就可以避免自己创建LayoutParams,再添加进去
      //true的作用是否需要执行root.addView(view, params);
      getLayoutInflater().inflate(layoutId(), rootView, true);
    }
    return rootView;
  }

  @Override
  public int layoutId() {
    return R.layout.base_title_layout;
  }

  /**
   * 初始化头部栏
   *
   * @param view
   */
  private void initToolbar(View view) {
    mCommonToolbar = view.findViewById(R.id.toolbar);
    mCommonToolbar.setLeftTextOnClickListener(v -> onBackPressed());
  }
}
