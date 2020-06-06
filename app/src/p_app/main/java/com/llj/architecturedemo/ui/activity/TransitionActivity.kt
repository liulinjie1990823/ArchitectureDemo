package com.llj.architecturedemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityTransitionBinding
import com.llj.component.service.arouter.CRouterClassName

@Route(path = CRouterClassName.APP_TRANSITION_ACTIVITY)
class TransitionActivity : AppMvcBaseActivity<ActivityTransitionBinding>() {
  override fun initViews(savedInstanceState: Bundle?) {
    mViewBinder.tvShare.setOnClickListener {
// Construct an Intent as normal

      // Construct an Intent as normal
      val pair1 = Pair<View, String>(mViewBinder.ivClose, "testImage")
      val pair2 = Pair<View, String>(mViewBinder.tvShare, "testTextView")
      val activityOptionsCompat = ActivityOptionsCompat
          .makeSceneTransitionAnimation(this, pair1, pair2)


      val intent = Intent(this, TransitionShareActivity::class.java)
      ActivityCompat.startActivity(this, intent, activityOptionsCompat.toBundle())
    }
    mViewBinder.tvExplode.setOnClickListener {
      val activityOptionsCompat = ActivityOptionsCompat
          .makeSceneTransitionAnimation(this)

      ARouter.getInstance().build(CRouterClassName.APP_TRANSITION_EXPLODE_ACTIVITY)
          .withOptionsCompat(activityOptionsCompat)
          .navigation(this)
    }
    mViewBinder.tvFade.setOnClickListener {
      val activityOptionsCompat = ActivityOptionsCompat
          .makeSceneTransitionAnimation(this)
      ARouter.getInstance().build(CRouterClassName.APP_TRANSITION_FADE_ACTIVITY)
          .withOptionsCompat(activityOptionsCompat)
          .navigation(this)
    }
    mViewBinder.tvSlide.setOnClickListener {
      val activityOptionsCompat = ActivityOptionsCompat
          .makeSceneTransitionAnimation(this)
      ARouter.getInstance().build(CRouterClassName.APP_TRANSITION_SLIDE_ACTIVITY)
          .withOptionsCompat(activityOptionsCompat)
          .navigation(this)
    }
  }

  override fun initData() {
  }
}