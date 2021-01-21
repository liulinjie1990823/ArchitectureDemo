package com.llj.lib.tracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks;

/**
 * describe  Fragment生命周期
 *
 * @author liulinjie
 * @date 12/21/20 4:50 PM
 */
public class TrackerFragmentLifeCycle extends FragmentLifecycleCallbacks {

  @Override
  public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
    TrackerApp.trackFragmentResume(f);
  }

  @Override
  public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
    TrackerApp.trackFragmentPause(f);
  }
}
