package com.llj.lib.tracker;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.llj.lib.tracker.model.TrackerEvent;
import java.util.List;
import timber.log.Timber;

/**
 * describe 动作事件上报
 *
 * @author liulinjie
 * @date 12/21/20 4:48 PM
 */
public class TrackerAction {

  private static final String TAG = "TrackerAction";


  public void trackEvent(View view, @TrackerEvent.ActionType String actionType) {
    Object object = calPageObject(view);
    if (object == null) {
      return;
    }
    if (!(object instanceof ITracker)) {
      return;
    }

    ITracker tracker = (ITracker) object;
    if (Tracker.filterPage(tracker.getPageName())) {
      return;
    }
    Timber.tag(TAG).i("trackEvent：%s", tracker.getPageName());
    Tracker.report(new TrackerEvent(TrackerEvent.TYPE_ACTION, actionType, tracker));
  }


  public void trackViewOnClick(View view) {
    trackEvent(view, TrackerEvent.ACTION_SHOW);
  }


  public Object calPageObject(View view) {
    Rect viewRect = new Rect();
    view.getGlobalVisibleRect(viewRect);

    Context context = Tracker.getTrueContext(view);
    if (context instanceof FragmentActivity) {
      FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
      List<Fragment> fragments = fragmentManager.getFragments();
      for (Fragment fragment : fragments) {
        if (fragment == null) {
          continue;
        }
        Fragment fragmentTemp = findFragment(viewRect, fragment);
        if (fragmentTemp != null) {
          //找到对应view所在的fragment
          return getAcceptFragment(fragmentTemp);
        }
      }
    }
    //如果没有找到对应的fragment则是当前的context
    return context;
  }

  //遍历向子找到准确的fragment
  private Fragment findFragment(Rect viewRect, Fragment fragment) {

    if (fragment.getView() != null
        && fragment.isVisible()
        && (fragment.getUserVisibleHint() || fragment.isResumed())) {

      Rect fragmentRect = new Rect();
      fragment.getView().getGlobalVisibleRect(fragmentRect);

      Fragment trueFragment;
      if (fragmentRect.contains(viewRect)) {
        //说明是在当前fragment下,但是也有可能是在子fragment中，需要继续遍历直到 List<Fragment>为0
        FragmentManager fragmentManager2 = fragment.getChildFragmentManager();
        List<Fragment> fragments2 = fragmentManager2.getFragments();
        if (fragments2.size() != 0) {
          for (Fragment fragment1 : fragments2) {
            if (fragment1 == null) {
              continue;
            }
            trueFragment = findFragment(viewRect, fragment1);
            if (trueFragment != null) {
              return trueFragment;
            }
          }
        }
        //子fragment中不存在，就确定是当前fragment
        return fragment;
      } else {
        //如果不在当前fragment中就往父类回溯，直到最大的fragment
        return null;
      }
    }
    return null;
  }

  private Fragment getContainParentFragment(Fragment fragment, Rect fragmentRect, Rect viewRect) {
    Fragment parentFragment = fragment.getParentFragment();
    if (parentFragment != null && parentFragment.getView() != null) {
      parentFragment.getView().getGlobalVisibleRect(fragmentRect);
      if (fragmentRect.contains(viewRect)) {
        return parentFragment;
      } else {
        return getContainParentFragment(parentFragment, fragmentRect, viewRect);
      }
    }
    return fragment;
  }

  //获取正确的fragment，如果当前是忽略的就使用父fragment
  private Object getAcceptFragment(Fragment fragment) {
    if ((fragment instanceof ITracker) && ((ITracker) fragment).ignoreAction()) {
      Fragment fragmentTemp = fragment.getParentFragment();
      if (fragmentTemp == null) {
        return fragment.getContext();
      }
      return getAcceptFragment(fragmentTemp);
    }
    return fragment;
  }


}
