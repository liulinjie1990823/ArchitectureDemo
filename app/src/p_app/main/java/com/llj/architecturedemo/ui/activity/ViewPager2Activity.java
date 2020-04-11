package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.facebook.drawee.view.SimpleDraweeView;
import com.llj.adapter.util.ViewHolderHelper;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.databinding.ActivityViewpager2Binding;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.help.DisplayHelper;
import com.llj.lib.image.loader.ImageLoader;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/**
 * describe ViewPager2
 *
 * @author liulinjie
 * @date 2020/3/22 1:39 PM
 */
@Route(path = CRouter.APP_VIEWPAGER2_ACTIVITY)
public class ViewPager2Activity extends AppMvcBaseActivity {

  private ActivityViewpager2Binding mBinding;

  private int mPicWidth;
  private int mPicHeight;

  private ImageLoader mImageLoader;

  @Override
  public int layoutId() {
    return R.layout.activity_viewpager2;
  }

  @Nullable
  @Override
  public View layoutView() {
    mBinding = ActivityViewpager2Binding.inflate(getLayoutInflater());
    return mBinding.getRoot();
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {

    mImageLoader = ImageLoader.getInstance();
    ((ViewGroup) mBinding.vpPicture.getChildAt(0)).setClipToPadding(false);
    //画面的宽度，按比例计算
    mPicWidth = (int) (DisplayHelper.SCREEN_WIDTH * 348 / 750F);
    mPicHeight = (int) (DisplayHelper.SCREEN_WIDTH * 348 * 16 / (750 * 9f));

    int left = (int) ((DisplayHelper.SCREEN_WIDTH - mPicWidth) / 2f);
    int top = dip2px(mContext, 20);
    mBinding.vpPicture.getChildAt(0).setPadding(left, top, left, top);

    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mBinding.vpPicture
        .getLayoutParams();
    layoutParams.width = -1;
    layoutParams.height =
        mPicHeight + mBinding.vpPicture.getPaddingTop() + mBinding.vpPicture.getPaddingBottom();

    mBinding.vpPicture.setOffscreenPageLimit(3);
    ViewPager2.PageTransformer pageTransformer = new ViewPager2.PageTransformer() {
      private static final float MIN_SCALE = 0.9f;
      private static final float MIN_ALPHA = 0.65f;
      //final float transformPos = (float) (child.getLeft() - scrollX) / getClientWidth();
      private float mCenterPosition = mBinding.vpPicture.getPaddingLeft() / (mPicWidth * 1f);

      private final int mMarginPx = 60;

      @Override
      public void transformPage(@NonNull View page, float position) {
        Timber.tag(mTagLog).e("position:" + position);
        final float scaleFactor =
            MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(mCenterPosition - position));
        final float alphaFactor =
            MIN_ALPHA + (1 - MIN_ALPHA) * (1 - Math.abs(mCenterPosition - position));

        //View view = page.findViewById(R.id.v_cover);

        page.setScaleX(scaleFactor);
        page.setScaleY(scaleFactor);

        //page.setAlpha(1 - alphaFactor);

        float offset = mMarginPx * position;
        page.setTranslationX(offset);
      }
    };

    //mBinding.vpPicture.setPageTransformer(new MarginPageTransformer(dip2px(mContext, 23)));
    mBinding.vpPicture.setPageTransformer(pageTransformer);
    //mBinding.vpPicture.getViewTreeObserver().addOnGlobalLayoutListener(
    //    new OnGlobalLayoutListener() {
    //      @Override
    //      public void onGlobalLayout() {
    //        Timber.tag(mTagLog).i("onGlobalLayout:");
    //      }
    //    });
    //mBinding.vpPicture.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
    //  @Override
    //  public boolean onPreDraw() {
    //    Timber.tag(mTagLog).i("onPreDraw:");
    //    return false;
    //  }
    //});
    //mBinding.vpPicture.getViewTreeObserver().addOnDrawListener(new OnDrawListener() {
    //  @Override
    //  public void onDraw() {
    //    Timber.tag(mTagLog).i("onDraw:");
    //  }
    //});
    mBinding.vpPicture.setAdapter(new RecyclerView.Adapter<ViewHolderHelper>() {

      @NonNull
      @Override
      public ViewHolderHelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolderHelper.createViewHolder(parent, R.layout.mv_item_ae_make_pic);
      }

      @Override
      public void onBindViewHolder(@NonNull ViewHolderHelper holder, int position) {
        SimpleDraweeView simpleDraweeView = holder.getView(R.id.sdv_image);
        String url = "https://img.hbhcdn.com/dmp/other/1584979200/jh-img-orig-ga_1242381413835505664_1079_1918_225146.jpg";
        mImageLoader.loadImage(simpleDraweeView, url, mPicWidth, mPicHeight);
      }

      @Override
      public int getItemCount() {
        return 3;
      }
    });

    //mPicAdapter = new Builder<String, ViewHolderHelper, PicAdapter>(
    //    mBinding.vpPicture, new PicAdapter())
    //    .build().getAdapter();
    //mPicAdapter.clear();
    //
    //ArrayList<String> strings = new ArrayList<>();
    //for (int i = 0; i < 5; i++) {
    //  strings.add("" + i);
    //}
    //mPicAdapter.addAll(strings);
  }

  @Override
  public void initData() {

  }


}
