package com.llj.lib.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;
import com.llj.lib.base.mvp.IBasePresenter;
import com.llj.lib.utils.AFragmentUtils;

/**
 * ArchitectureDemo describe: author llj date 2018/5/24
 */
public abstract class BaseTabActivity<V extends ViewBinding, P extends IBasePresenter> extends
    MvpBaseActivity<V, P> {

  public static final String TAB_INDEX = "index";

  private FragmentManager mFragmentManager;
  public  String          mHideItem = TAB_INDEX;
  public  String          mShowItem = TAB_INDEX;//因为xml中第一个tab的tag从0开始
  private Fragment        mCurrentFragment;//当前显示的fragment

  private OnBackPressedListener mOnBackPressedListener;//返回监听
  private OnItemReClickListener mOnItemReClickListener;//tab重复点击的监听


  @CallSuper
  @Override
  public void getIntentData(@NonNull Intent intent) {
    mShowItem = intent.getStringExtra("mShowItem");
    if (mShowItem == null) {
      mShowItem = TAB_INDEX;
    }
  }

  @CallSuper
  @Override
  public void initViews(Bundle savedInstanceState) {
    mFragmentManager = getSupportFragmentManager();
    // 从savedInstanceState获取到保存的mCurrentItem
    if (savedInstanceState != null) {
      mHideItem = savedInstanceState.getString("mHideItem", TAB_INDEX);
      mShowItem = savedInstanceState.getString("mShowItem", TAB_INDEX);
    }
    // 第一次进入默认显示第1页
    performSelectItem(mHideItem, mShowItem, true);
  }


  /**
   * 有可能被意外销毁之前调用，主动销毁不调用 1.按home回到主页
   */
  @CallSuper
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("mHideItem", mHideItem);
    outState.putString("mShowItem", mShowItem);
  }

  /**
   * 隐藏当前显示的fragment,显示将要显示的fragment
   *
   * @param hideItem 需要隐藏的fragment
   * @param showItem 需要显示的fragment
   * @param isOnCreate 是否是第一次从OnCreate中启动,点击都是flase
   */
  public void performSelectItem(String hideItem, String showItem, boolean isOnCreate) {
    // 获得将要隐藏页的tag
    String currentTag = AFragmentUtils.makeFragmentTag(hideItem);
    // 隐藏当前的的fragment
    FragmentTransaction transaction = mFragmentManager.beginTransaction();
    // 如果被杀后再进来，全部的fragment都会被呈现显示状态，所以都隐藏一边
    if (isOnCreate) {
      for (Fragment fragment : mFragmentManager.getFragments()) {
        transaction.hide(fragment);
      }
    } else {
      // 正常按钮点击进入
      Fragment lastFragment = mFragmentManager.findFragmentByTag(currentTag);
      if (lastFragment != null) {
        transaction.hide(lastFragment);
      }
    }

    // 获得将要显示页的tag
    String toTag = AFragmentUtils.makeFragmentTag(showItem);
    // find要显示的Fragment
    mCurrentFragment = mFragmentManager.findFragmentByTag(toTag);
    if (mCurrentFragment != null) {
      // 已经存在则显示
      transaction.show(mCurrentFragment);
    } else {
      // 不存在则添加新的fragment
      mCurrentFragment = makeFragment(showItem);
      if (mCurrentFragment != null) {
        transaction.add(getFragmentId(), mCurrentFragment, toTag);
      }
    }

    // 保存当前显示fragment的item
    mHideItem = hideItem;
    mShowItem = showItem;

    // 选择image图片
    changeSelectImage(showItem, hideItem);

    transaction.commitAllowingStateLoss();
  }

  /**
   * 获得当前显示的fragment
   *
   * @return
   */
  public Fragment getCurrentFragment() {
    return mCurrentFragment;
  }


  /**
   * fragment添加的布局id
   *
   * @return
   */
  public abstract int getFragmentId();

  /**
   * 创建对应的Fragment,从0开始
   *
   * @param showItem
   * @return
   */
  public abstract Fragment makeFragment(String showItem);

  /**
   * 改变对应选中图片或者文字
   *
   * @param showItem
   */
  public abstract void changeSelectImage(String showItem, String hideItem);

  /**
   * 根据view的点击时间触发,并通过tag判断 包含可能重复点击的回调
   *
   * @param view
   */
  public void selectItemFromTagByClick(View view) {
    String arg1 = view.getTag().toString();
    if (mShowItem != null && !mShowItem.equals(arg1)) {
      //tab不是重复点击
      performSelectItem(mShowItem, arg1, false);
    } else {
      //tab重复点击
      if (mOnItemReClickListener != null) {
        mOnItemReClickListener.OnItemReClick(arg1);
      }
    }
  }

  /**
   * 监听返回键,先相应mOnBackPressedListener方法的回调 fragment中的方法可以实现该方法来控制返回键
   */
  @CallSuper
  @Override
  public void onBackPressed() {
    if (mOnBackPressedListener != null) {
      boolean isHandler = mOnBackPressedListener.onBackPressedHandler(getCurrentFragment());
      //如果fragment中没有处理,则调用exit;
      if (!isHandler)
      //activity的返回键监听
      {
        exit();
      }
    } else {
      //activity的返回键监听
      exit();
    }
  }


  /**
   * 退出程序
   */
  private long mExitTime;

  protected void exit() {
    if (System.currentTimeMillis() - mExitTime > 2000) {
      mExitTime = System.currentTimeMillis();
      Toast.makeText(this, "再按一次退出程序！", Toast.LENGTH_SHORT).show();
    } else {
      backToLauncher(true);
    }
  }


  /**
   * @param onBackPressedListener
   */
  public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
    mOnBackPressedListener = onBackPressedListener;
  }

  /**
   * 设置tab的点击事件
   *
   * @param onItemReClickListener
   */
  public void setOnItemReClickListener(OnItemReClickListener onItemReClickListener) {
    mOnItemReClickListener = onItemReClickListener;
  }

  /**
   * 如果fragment需要监听返回键,可以实现这个接口
   */
  public interface OnBackPressedListener {

    boolean onBackPressedHandler(Fragment fragment);
  }

  /**
   * 如果tab重复点击(即已经显示的页面再通过手去点击)
   */
  public interface OnItemReClickListener {

    void OnItemReClick(String item);
  }

}
