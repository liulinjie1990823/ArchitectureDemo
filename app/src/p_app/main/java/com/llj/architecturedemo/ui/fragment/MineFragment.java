package com.llj.architecturedemo.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.GenericDraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.llj.adapter.ListBasedAdapter;
import com.llj.adapter.MergedUniversalAdapter;
import com.llj.adapter.UniversalAdapter;
import com.llj.adapter.UniversalBind;
import com.llj.adapter.converter.UniversalConverterFactory;
import com.llj.adapter.util.ViewHolderHelper;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.analysis.AppAction;
import com.llj.architecturedemo.ui.model.ExpoInfoVo;
import com.llj.architecturedemo.ui.model.PersonalCenterCountVo;
import com.llj.architecturedemo.ui.model.PersonalCenterVo;
import com.llj.architecturedemo.ui.presenter.PersonalCenterPresenter;
import com.llj.architecturedemo.ui.view.IMineView;
import com.llj.component.service.ComponentMvpBaseFragment;
import com.llj.component.service.ComponentApplication;
import com.llj.component.service.arouter.CRouter;
import com.llj.component.service.refreshLayout.JHSmartRefreshLayout;
import com.llj.component.service.vo.UserInfoVo;
import com.llj.lib.base.help.DisplayHelper;
import com.llj.lib.base.widget.LoadingDialog;
import com.llj.lib.image.loader.FrescoImageLoader;
import com.llj.lib.image.loader.ICustomImageLoader;
import com.llj.lib.net.response.BaseResponse;
import com.llj.lib.statusbar.LightStatusBarCompat;
import com.llj.lib.utils.AViewUtils;
import com.llj.lib.utils.helper.Utils;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.internal.DebouncingOnClickListener;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/5
 */
public class MineFragment extends ComponentMvpBaseFragment<PersonalCenterPresenter> implements IMineView {

    @NotNull
    @Override
    public <Data> AutoDisposeConverter<BaseResponse<Data>> bindRequestLifecycle() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle()));
    }

    public static final int REQUEST_GET_QR_CODE = 1;//请求扫描二维码

    @BindView(R.id.scrollView) NestedScrollView mScrollView;

    @BindViews({R.id.iv_scanning,
            R.id.iv_qr_code,
            R.id.iv_message,
            R.id.iv_setting,}) List<ImageView> mIvWhite;

    @BindViews({R.id.iv_scanning1,
            R.id.iv_qr_code1,
            R.id.iv_message1,
            R.id.iv_setting1,}) List<ImageView> mIvBlack;

    @BindView(R.id.v_status_bar)    View        mVStatusBar;
    @BindView(R.id.v_white_back)    View        mOverlayView;
    @BindView(R.id.fl_toolbar_root) FrameLayout mFlToolbarRoot;

    @BindView(R.id.iv_header)         SimpleDraweeView mIvHeader;
    @BindView(R.id.rl_login_wrap)     RelativeLayout   mRlLoginWrap;
    @BindView(R.id.tv_login)          TextView         mTvLogin;
    @BindView(R.id.iv_member_tag)     TextView         mIvMemberTag;
    @BindView(R.id.tv_diamond_points) TextView         mTvDiamondPoints;
    @BindView(R.id.tv_sign_in)        Button           mTvSignIn;
    @BindView(R.id.tv_sign_tag)       TextView         mTvSignTag;

    @BindView(R.id.ll_header_menu)   LinearLayoutCompat mLiHeaderMenu;
    @BindView(R.id.li_mine_top_menu) LinearLayout       mLiMineTopMenu;

    @BindView(R.id.cv_image1) CardView         mCvImage1;
    @BindView(R.id.iv_add1)   SimpleDraweeView mIvAdd1;

    @BindView(R.id.ll_tools) LinearLayout mLiTools;

    @BindView(R.id.refresh_layout) JHSmartRefreshLayout mRefreshLayout;
    @BindView(R.id.parallax)       ImageView            mParallax;
    @BindView(R.id.iv_top_bg)      ImageView            mIvTopBg;

    private ICustomImageLoader<GenericDraweeView> mImageLoad = FrescoImageLoader.getInstance(Utils.getApp());


    private String mH5Link;

    @Override
    public int layoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!isHidden() && hasInLoading()) {
            showLoadingDialog();
        }
        if (!hidden) {
            //获取相关数量接口
            mPresenter.getPersonalCenterCount(false);
            LightStatusBarCompat.setLightStatusBar(((Activity) mContext).getWindow(), !(mScrollView.getScrollY() == 0));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden() && hasInLoading()) {
            showLoadingDialog();
        }
        handleUserInfo(ComponentApplication.mUserInfoVo);

    }

    private void handleUserInfo(UserInfoVo userInfoVo) {
        if (isLogin()) {
            //已经登录
            //头像
            mImageLoad.loadImage(userInfoVo.getAvatar(), dip2px(mContext, 65), dip2px(mContext, 65), mIvHeader);
            //昵称
            mTvLogin.setText(userInfoVo.getUname());
            //会员
            if (isEmpty(userInfoVo.getUlevel_name())) {
                mIvMemberTag.setVisibility(View.GONE);
            } else {
                mIvMemberTag.setVisibility(View.VISIBLE);
                setText(mIvMemberTag, userInfoVo.getUlevel_name());
            }
            //积分
            if (isEmpty(userInfoVo.getPutong_score())) {
                mTvDiamondPoints.setVisibility(View.GONE);
            } else {
                mTvDiamondPoints.setVisibility(View.VISIBLE);
                setText(mTvDiamondPoints, getString(R.string.score_num, nullToEmpty(userInfoVo.getPutong_score())));
            }
        } else {
            //未登录
            //头像
            mImageLoad.loadImage(0, dip2px(mContext, 65), dip2px(mContext, 65), mIvHeader);
            mTvLogin.setText(R.string.click_to_login);
            mIvMemberTag.setVisibility(View.GONE);
            mTvDiamondPoints.setVisibility(View.GONE);

            //清空数量
            handleBadgeView(-1, -1, -1);
        }
    }


    private int   mOffset     = 0;
    private float scrollYTemp = 0;

    private boolean mIsStatusTextBlack;

    @Override
    protected boolean statusBarTextColorBlack() {
        return false;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        mFlToolbarRoot.setBackgroundColor(Color.parseColor("#ffffff"));
        mFlToolbarRoot.getBackground().mutate().setAlpha(0);
        //获取用户信息

        mIsLoadingMushUp = true;
        mIsLoadingMushUpNum = true;
        ((LoadingDialog) getLoadingDialog()).setOnCustomerCancelListener(dialog -> {
            mIsLoadingMushUp = false;
            mIsLoadingMushUpNum = false;
        });
        showLoadingDialog();

        mPresenter.getPersonalCenterInfo(false);
        mPresenter.getPersonalCenterCount(false);


        int height = (int) ((DisplayHelper.SCREEN_WIDTH - dip2px(mContext, 54)) * 12 / 64f);
        mCvImage1.getLayoutParams().height = height;

        final float flexibleRange = dip2px(mContext, 44);

        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int i2, int i3) {
                mParallax.setTranslationY(-scrollY);
                mIvTopBg.setTranslationY(-scrollY);
                if (scrollY > 0) {
                    if (!mIsStatusTextBlack) {
                        LightStatusBarCompat.setLightStatusBar(((Activity) mContext).getWindow(), true);
                        mIsStatusTextBlack = true;
                    }
                } else {
                    mIsStatusTextBlack = false;
                    LightStatusBarCompat.setLightStatusBar(((Activity) mContext).getWindow(), false);
                }

                scrollYTemp = scrollY > flexibleRange ? flexibleRange : scrollY;
                float percent = scrollYTemp / flexibleRange;

                if (percent > 0) {
                    if (percent >= 1) {
                        mFlToolbarRoot.getBackground().mutate().setAlpha(255);
                        for (ImageView imageView : mIvWhite) {
                            imageView.setAlpha(0f);
                        }
                        for (ImageView imageView : mIvBlack) {
                            imageView.setAlpha(1f);
                        }
                    } else {
                        //上层的title
                        mFlToolbarRoot.getBackground().mutate().setAlpha((int) (getMiddle(percent, 0, 1) * 255));
                        for (ImageView imageView : mIvWhite) {
                            imageView.setAlpha(1 - (getMiddle(percent, 0, 1)));
                        }
                        for (ImageView imageView : mIvBlack) {
                            imageView.setAlpha(getMiddle(percent, 0, 1));
                        }
                    }
                } else {
                    //初始状态
                    for (ImageView imageView : mIvWhite) {
                        imageView.setAlpha(1f);
                    }
                    for (ImageView imageView : mIvBlack) {
                        imageView.setAlpha(0f);
                    }
                    mFlToolbarRoot.getBackground().mutate().setAlpha(0);
                }
            }

        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getPersonalCenterInfo(false);
                mPresenter.getPersonalCenterCount(false);
            }
        }).setOnMultiPurposeListener(new SimpleMultiPurposeListener() {

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                if (isDragging) {
                    if (offset > 0) {
                        mOffset = offset / 2;
                        mParallax.setTranslationY(mOffset - scrollYTemp);
                    }
                } else {
                    if (offset > 0) {
                        mOffset = offset / 2;
                        mParallax.setTranslationY(mOffset - scrollYTemp);
                    }
                }
            }
        }).setEnableLoadMore(false).setEnableOverScrollDrag(true).setEnableOverScrollBounce(true);

        handleTopMenu();
    }


    private void handleTopMenu() {
        List<MenuVo> topMenuVos = new ArrayList<>();
        topMenuVos.add(new MenuVo(R.drawable.ic_my_cash_coupon, R.string.cash_coupon, CRouter.APP_MINE_MY_COUPON_ACTIVITY, AppAction.MINE_CASH));
        topMenuVos.add(new MenuVo(R.drawable.ic_order, R.string.order, CRouter.ORDER_LIST, AppAction.MINE_ORDER));
        topMenuVos.add(new MenuVo(R.drawable.ic_comment, R.string.comment, CRouter.MY_COMMENT_LIST, AppAction.MINE_EVALUATE));
        topMenuVos.add(new MenuVo(R.drawable.ic_my_wallet, R.string.my_wallet, CRouter.APP_MINE_MY_WALLET_ACTIVITY, AppAction.MINE_WALLET));
        TopMenuAdapter topMenuAdapter = new TopMenuAdapter(topMenuVos);
        UniversalConverterFactory.createGeneric(topMenuAdapter, mLiMineTopMenu);
    }


    class MenuVo {
        public int count;
        int    imageId;
        int    textId;
        String path;
        String action;//埋点动作名称


        public MenuVo(int imageId, int textId, String path, String action) {
            this.imageId = imageId;
            this.textId = textId;
            this.path = path;
            this.action = action;
        }
    }

    @Override
    public void initData() {

    }

    boolean mIsLoadingMushUp;
    boolean mIsLoadingMushUpNum;

    public boolean hasInLoading() {
        return mIsLoadingMushUp || mIsLoadingMushUpNum;
    }

    public boolean isAllLoadFinish() {
        return (!mIsLoadingMushUp) && (!mIsLoadingMushUpNum);
    }

//    @Override
//    public void onReceive(BaseResponse baseResponse) {
//        super.onReceive(baseResponse);
//        if (baseResponse.getCmd() == EventConstants.EVENT_LOGIN_SUCCESS
//                || baseResponse.getCmd() == EventConstants.EVENT_SELECTCITY_SUCCESS
//                || baseResponse.getCmd() == EventConstants.EVENT_LOGIN_OUT) {
//            mIsLoadingMushUp = true;
//            mIsLoadingMushUpNum = true;
//            resetView();
//            mPersonalCenterPresenter.getHomePageInfo(this);
//
//            if (baseResponse.getCmd() == EventConstants.EVENT_LOGIN_OUT) {
//                if (mHeaderMenuAdapter != null) {
//                    mHeaderMenuAdapter.notifyDataSetChanged();
//                }
//            }
//            mPersonalCenterPresenter.getMashupNum(this);
//        }
//    }

    private void resetView() {
        mCvImage1.setVisibility(View.GONE);
        mTvSignIn.setVisibility(View.GONE);
        mTvSignTag.setVisibility(View.GONE);
    }

//    private UserQrCodeDialog mUserQrCodeDialog;
//
//    private void showQrCodeDialog(String qrCode) {
//        if (isEmpty(qrCode)) {
//            return;
//        }
//        if (mUserQrCodeDialog == null) {
//            mUserQrCodeDialog = new UserQrCodeDialog(mContext);
//        }
//        mUserQrCodeDialog.setQrCode(qrCode);
//        mUserQrCodeDialog.show();
//    }

    @OnClick({
            R.id.iv_scanning,
            R.id.iv_qr_code,
            R.id.iv_message,
            R.id.iv_setting,
            R.id.iv_header,
            R.id.tv_login,
            R.id.iv_member_tag,
            R.id.tv_diamond_points,
            R.id.tv_sign_in
    })
    public void onClick(View view) {
        switch (view.getId()) {
            //扫描
            case R.id.iv_scanning:
                //埋点
//                AnalysisUtils.getInstance().sendEvent(view, Category.TAP, AppAction.MINE_SCAN);
//                PermissionUtils.checkPermission(this, Manifest.permission.CAMERA);
                break;
            //二维码
            case R.id.iv_qr_code:
                //埋点
//                AnalysisUtils.getInstance().sendEvent(view, Category.TAP, AppAction.MINE_CODE);
                if (checkLogin()) {
                    mPresenter.getQrCode(false);
                }
                break;
            //消息
            case R.id.iv_message:
                //埋点
//                AnalysisUtils.getInstance().sendEvent(view, Category.TAP, AppAction.MINE_NEWS);
                if (checkLogin()) {
                    CRouter.start(CRouter.MESSAGE_MESSAGE_CENTER);
                }
                break;
            //设置
            case R.id.iv_setting:
                //埋点
//                AnalysisUtils.getInstance().sendEvent(view, Category.TAP, AppAction.MINE_SETTING);
                CRouter.start(CRouter.APP_MINE_SETTING_ACTIVITY);
                break;
            //我的资料界面
            case R.id.iv_header:
                if (checkLogin()) {
                    CRouter.start(CRouter.APP_MINE_MY_PROFILE_ACTIVITY);
                }
                break;
            //登录
            case R.id.tv_login:
                if (checkLogin()) {
                    CRouter.start(CRouter.APP_MINE_MY_PROFILE_ACTIVITY);
                }
                break;
            //会员
            case R.id.iv_member_tag:
                //埋点
//                AnalysisUtils.getInstance().sendEvent(view, Category.TAP, AppAction.MINE_LEVEL);
                if (checkLogin()) {
                    CRouter.start(CRouter.APP_MINE_MEMBER_CENTER_ACTIVITY);
                }
                break;
            //查看积分
            case R.id.tv_diamond_points:
                //埋点
//                AnalysisUtils.getInstance().sendEvent(view, Category.TAP, AppAction.MINE_INTEGRAL);
                break;
            //签到
            case R.id.tv_sign_in:
                if (mTvSignIn.getTag() != null) {
                    //去签到
//                    WebViewConfig.builder().setWebUrl(mTvSignIn.getTag().toString()).start((Activity) mContext);
                    //埋点
//                    AnalysisUtils.getInstance().sendEvent(mTvSignIn, Category.TAP, AppAction.SIGN_IN_MINE, mTvSignIn.getTag().toString());
                }
                break;
        }
    }


    private void handleSign(PersonalCenterVo homePageVo) {
        if (homePageVo.getActivity() == null) {
            return;
        }
        PersonalCenterVo.ActivityVo activityVo = homePageVo.getActivity();
        mTvSignIn.setTag(activityVo.getHref());

        if (activityVo.is_start() && !isEmpty(activityVo.getHref())) {
            mTvSignIn.setVisibility(View.VISIBLE);
        } else {
            mTvSignIn.setVisibility(View.GONE);
        }

        if (activityVo.is_start() && !isEmpty(activityVo.getCorner()) && AViewUtils.isVisible(mTvSignIn)) {
            mTvSignTag.setVisibility(View.VISIBLE);
            mTvSignTag.setText(activityVo.getCorner());
        } else {
            mTvSignTag.setVisibility(View.GONE);
        }
    }

    private void handleAdds(PersonalCenterVo homePageVo) {
        mCvImage1.setVisibility(View.GONE);

        if (isEmpty(homePageVo.getAds())) {
            return;
        }

        List<PersonalCenterVo.AdVo> ads = homePageVo.getAds();

        for (final PersonalCenterVo.AdVo ad : ads) {
            if (ad == null) {
                continue;
            }
            if (PersonalCenterVo.AdVo.POSITION_TOP.equals(ad.getPosition()) && !isEmpty(ad.getImage())) {
                mCvImage1.setVisibility(View.VISIBLE);

                mImageLoad.loadImage(ad.getImage(), DisplayHelper.SCREEN_WIDTH, DisplayHelper.SCREEN_WIDTH, mIvAdd1);
                mCvImage1.setOnClickListener(new DebouncingOnClickListener() {
                    @Override
                    public void doClick(View v) {
                        //统计
//                        AnalysisUtils.getInstance().sendEvent(mCvImage1, Category.TAP, AppAction.CMS, AbStringUtils.nullOrString(ad.getPosition_id()), ad.getLink());
//                        CiwHelper.startActivity((BaseActivity) mContext, ad.getLink());
                    }
                });
            }
        }
    }

    private PersonalCenterVo.AdVo getAdData(PersonalCenterVo homePageVo, String position) {

        if (isEmpty(homePageVo.getAds())) {
            return null;
        }

        List<PersonalCenterVo.AdVo> ads = homePageVo.getAds();

        for (final PersonalCenterVo.AdVo ad : ads) {
            if (ad == null) {
                continue;
            }
            if (position.equals(ad.getPosition()) && !isEmpty(ad.getImage())) {
                return ad;
            }
        }
        return null;
    }

    private HeaderMenuAdapter mHeaderMenuAdapter;

    private void handleHeaderMenu(PersonalCenterCountVo homePageVo) {
        if (homePageVo == null) {
            return;
        }
        if (mHeaderMenuAdapter == null) {
            mHeaderMenuAdapter = new HeaderMenuAdapter(null);
            UniversalConverterFactory.createGeneric(mHeaderMenuAdapter, mLiHeaderMenu);
        }
        if (isEmpty(homePageVo.getHeader_lists())) {
            mLiHeaderMenu.setVisibility(View.GONE);
        } else {
            mLiHeaderMenu.setVisibility(View.VISIBLE);
        }
        mHeaderMenuAdapter.clear();
        if (!isEmpty(homePageVo.getHeader_lists())) {
            mHeaderMenuAdapter.addAll(homePageVo.getHeader_lists());
        }
    }

    private Badge mCardBadge;
    private Badge mCommentBadge;
    private Badge mNoticeBadge;

    private void handleBadgeView(int cardCount, int commentCount, int noticeNum) {
        String cardCountString = "";
        String commentCountString = "";
        if (cardCount > 99) {
            cardCountString = "99+";
        } else {
            cardCountString = cardCount + "";
        }
        if (commentCount > 99) {
            commentCountString = "99+";
        } else {
            commentCountString = commentCount + "";
        }
        if (cardCount > 0) {
            if (mCardBadge == null) {
                mCardBadge = new QBadgeView(mContext)
                        .bindTarget(((ViewGroup) mLiMineTopMenu.getChildAt(0)).getChildAt(0))
                        .setBadgePadding(5, false)
                        .setExactMode(true)
                        .setBadgeTextSize(12, true)
                        .setShowShadow(false)
                        .setBadgeTextColor(ContextCompat.getColor(mContext, R.color.color_FF6363))
                        .setBadgeBackgroundColor(ContextCompat.getColor(mContext, R.color.white))
                        .stroke(ContextCompat.getColor(mContext, R.color.color_FF6363), 1, true)
                        .setBadgeGravity(Gravity.END | Gravity.TOP);
            }
            mCardBadge.setBadgeText(cardCountString);
        } else {
            if (mCardBadge != null)
                mCardBadge.hide(false);
        }
        if (commentCount > 0) {
            if (mCommentBadge == null) {
                mCommentBadge = new QBadgeView(mContext)
                        .bindTarget(((ViewGroup) mLiMineTopMenu.getChildAt(3)).getChildAt(0))
                        .setBadgePadding(5, false)
                        .setExactMode(true)
                        .setBadgeTextSize(12, true)
                        .setShowShadow(false)
                        .setBadgeTextColor(ContextCompat.getColor(mContext, R.color.color_FF6363))
                        .setBadgeBackgroundColor(ContextCompat.getColor(mContext, R.color.white))
                        .stroke(ContextCompat.getColor(mContext, R.color.color_FF6363), 1, true)
                        .setBadgeGravity(Gravity.END | Gravity.TOP);
            }
            mCommentBadge.setBadgeText(commentCountString);
        } else {
            if (mCommentBadge != null)
                mCommentBadge.hide(false);
        }
        if (noticeNum > 0) {
            if (mNoticeBadge == null) {
                mNoticeBadge = new QBadgeView(mContext)
                        .bindTarget(mIvWhite.get(2))
                        .setBadgePadding(12, false)
                        .setExactMode(true)
                        .setBadgeTextSize(0, true)
                        .setShowShadow(false)
                        .setBadgeTextColor(ContextCompat.getColor(mContext, R.color.color_FF6363))
                        .setBadgeBackgroundColor(ContextCompat.getColor(mContext, R.color.color_FF6363))
                        .stroke(ContextCompat.getColor(mContext, R.color.color_FF6363), 1, true)
                        .setBadgeGravity(Gravity.END | Gravity.TOP);
            }
            mNoticeBadge.setBadgeText("1");
        } else {
            if (mNoticeBadge != null)
                mNoticeBadge.hide(false);
        }
    }

//    @Override
//    public void onGetRecordUrl(HttpResult<RecordUrlVo> result) {
//        if (result != null && result.getData() != null) {
//            CiwHelper.startActivity(result.getData().getLdurl());
//        }
//    }


    @NotNull
    @Override
    public HashMap<String, Object> getParams1() {
        return new HashMap<>();
    }

    @NotNull
    @Override
    public HashMap<String, Object> getParams2() {
        return new HashMap<>();
    }

    @NotNull
    @Override
    public HashMap<String, Object> getParams3() {
        return new HashMap<>();
    }

    @NotNull
    @Override
    public HashMap<String, Object> getParams4() {
        return new HashMap<>();
    }

    private void handleAds(PersonalCenterVo data, String position, MergedUniversalAdapter adapter) {
        PersonalCenterVo.AdVo adData = getAdData(data, position);
        if (adData != null) {
            AdAdapter adAdapter = new AdAdapter();
            ArrayList<PersonalCenterVo.AdVo> adVos = new ArrayList<>();
            adVos.add(adData);
            adAdapter.setItemsList(adVos);
            adapter.addAdapter(adAdapter);
        }
    }

    @Override
    public void onDataSuccess1(BaseResponse<PersonalCenterVo> result) {
        mRefreshLayout.finishRefresh(true);
        mIsLoadingMushUp = false;
        if (isAllLoadFinish()) {
            dismissLoadingDialog();
        }

        if (result == null || result.getData() == null) {
            return;
        }
        PersonalCenterVo data = result.getData();


        //用户信息
        if (data.getUser() != null) {
            data.getUser().setAccess_token(ComponentApplication.mUserInfoVo.getAccess_token());
            ComponentApplication.Companion.initUserInfo(data.getUser());
            handleUserInfo(ComponentApplication.mUserInfoVo);
        }

//        UserInfoPreference.Companion.getInstance().(data.getServer_tel());

        //签到信息
        handleSign(data);

        //广告位top
        handleAdds(data);

        MergedUniversalAdapter mergedUniversalAdapter = new MergedUniversalAdapter();

        //我的婚博会
        if (data.getNew_menus() != null && data.getNew_menus().size() > 0) {
            ToolsAdapter toolsAdapter = new ToolsAdapter();
            ArrayList<PersonalCenterVo.ToolsVo> list = new ArrayList<>();
            list.add(data.getNew_menus().get(0));
            toolsAdapter.setItemsList(list);
            mergedUniversalAdapter.addAdapter(toolsAdapter);
        }

        //广告位middle
        handleAds(data, PersonalCenterVo.AdVo.POSITION_MIDDLE, mergedUniversalAdapter);

        //小工具
        if (data.getNew_menus() != null && data.getNew_menus().size() > 1) {
            ToolsAdapter toolsAdapter1 = new ToolsAdapter();
            ArrayList<PersonalCenterVo.ToolsVo> list1 = new ArrayList<>();
            list1.add(data.getNew_menus().get(1));
            toolsAdapter1.setItemsList(list1);
            mergedUniversalAdapter.addAdapter(toolsAdapter1);
        }

        //广告位bottom
        handleAds(data, PersonalCenterVo.AdVo.POSITION_BOTTOM, mergedUniversalAdapter);

        //其他小工具
        if (data.getNew_menus() != null && data.getNew_menus().size() > 2) {
            ToolsAdapter toolsAdapter2 = new ToolsAdapter();
            toolsAdapter2.setItemsList(data.getNew_menus().subList(0, 2));
            mergedUniversalAdapter.addAdapter(toolsAdapter2);
        }


        UniversalConverterFactory.createGeneric(mergedUniversalAdapter, mLiTools);

    }

    @Override
    public void onDataSuccess2(BaseResponse<PersonalCenterCountVo> result) {
        mIsLoadingMushUpNum = false;
        if (isAllLoadFinish()) {
            dismissLoadingDialog();
        }

        if (result == null || result.getData() == null) {
            return;
        }
        PersonalCenterCountVo data = result.getData();

        //headerMenu
        handleHeaderMenu(data);
        //购物车评价数量
        handleBadgeView(data.getCart_num(), data.getComment_num(), data.getNotice_num());
    }

    @Override
    public void onDataSuccess3(BaseResponse<String> result) {
        if (result == null) {
            return;
        }
        if (isEmpty(result.getData())) {
            showLongToast("没有二维码");
            return;
        }
//        showQrCodeDialog(result.getData());
    }

    @Override
    public void onDataSuccess4(BaseResponse<ExpoInfoVo> result) {
//        if (result.isOpen()) {
//            mH5Link = result.getH5Link();
//            LatLng latLngStart = new LatLng(UserInfoPreference.getInstance().getLat(), UserInfoPreference.getInstance().getLng());
//            if (result.getLatitude() != null && result.getLongitude() != null) {
//                LatLng latLngEnd = new LatLng(Double.valueOf(result.getLatitude()), Double.valueOf(result.getLongitude()));
//                double distance = LocationHelper.getDistance(latLngStart, latLngEnd);
//                if (distance <= result.getRange()) {
//                    Log.e("distance", "distance=" + distance);
//                    //isRange = true;
//                    if (isLogin()) {
//                        WebViewConfig.builder().setWebUrl(mH5Link).start((Activity) mContext);
//                        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//                            Intent intent = new Intent(getActivity(), PlazaActivity.class);
//                            intent.putExtra("url",mH5Link);
//                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
//                        }*/
//                    } else {
//                        showPlazaDialog();
//                    }
//                } else {
//                    AbToast.show("仅展馆内用户可参与");
//
//                }
//            }
//        } else if (result.isPreheat()) {
//            PreHeatDialog dialog = new PreHeatDialog(mContext);
//            dialog.setBg(result.getPhotoLink());
//            dialog.show();
//        } else {
//            showLongToast("无法识别，请稍后重试");
//        }
    }

    @Override
    public void onDataError(int tag, @NotNull Throwable e) {
        if (tag == PersonalCenterPresenter.GET_PERSONAL_CENTER_INFO) {
            mRefreshLayout.finishRefresh(true);
            mIsLoadingMushUp = false;
            if (isAllLoadFinish()) {
                dismissLoadingDialog();
            }
        } else {
            mIsLoadingMushUpNum = false;
            if (isAllLoadFinish()) {
                dismissLoadingDialog();
            }
        }
    }


    //头部菜单adapter
    public class HeaderMenuAdapter extends ListBasedAdapter<PersonalCenterCountVo.HeaderMenu, ViewHolderHelper> {
        HeaderMenuAdapter(List<PersonalCenterCountVo.HeaderMenu> list) {
            super(list);
            addItemLayout(R.layout.item_mine_header_menu);
        }

        @Override
        protected void onBindViewHolder(ViewHolderHelper viewHolder, final PersonalCenterCountVo.HeaderMenu data, final int position) {
            viewHolder.setText(R.id.tv_num, isLogin() ? data.getShowNum() : "0")
                    .setText(R.id.tv_title, data.getTitle())
                    .setOnItemClickListener(new DebouncingOnClickListener() {
                        @Override
                        public void doClick(View v) {
                            if (data.getType().equals("collect")) {//收藏
                                //埋点
//                                AnalysisUtils.getInstance().sendEvent(v, Category.TAP, AppAction.MINE_COLLECT);
                            } else if (data.getType().equals("activity")) {//活动
                                //埋点
//                                AnalysisUtils.getInstance().sendEvent(v, Category.TAP, AppAction.MINE_ACTIVITY);
                            } else if (data.getType().equals("demand")) {//我的预约
                                //埋点
//                                AnalysisUtils.getInstance().sendEvent(v, Category.TAP, AppAction.MINE_RESERVE);
                            } else {
                                //统计
//                                AnalysisUtils.getInstance().sendEvent(v, Category.TAP, AppAction.CMS, data.getPosition_id(), data.getLink());
                            }

                            if ("yes".equals(data.getNeed_login())) {
                                if (checkLogin()) {
//                                    CiwHelper.startActivity(data.getLink());
                                }
                            } else {
//                                CiwHelper.startActivity(data.getLink());
                            }
                        }
                    });
        }
    }

    //顶部菜单adapter，本地数据
    public class TopMenuAdapter extends ListBasedAdapter<MenuVo, ViewHolderHelper> {

        TopMenuAdapter(List<MenuVo> list) {
            super(list);
            addItemLayout(R.layout.item_mine_top_menu);
        }

        @Override
        protected void onBindViewHolder(ViewHolderHelper viewHolder, final MenuVo item, final int position) {
            viewHolder.setText(R.id.textView, item.textId)
                    .setImageResource(R.id.imageView, item.imageId)
                    .setOnItemClickListener(new DebouncingOnClickListener() {
                        @Override
                        public void doClick(View v) {
                            //必须先登录
                            if (checkLogin()) {
                                CRouter.start(item.path);
                            }
                            //埋点
//                            AnalysisUtils.getInstance().sendEvent(v, Category.TAP, data.action);

                        }
                    });
        }
    }

    //小工具wrapAdapter
    public class ToolsAdapter extends ListBasedAdapter<PersonalCenterVo.ToolsVo, ViewHolderHelper> {

        ToolsAdapter() {
            super(null);
            addItemLayout(new UniversalAdapter.LayoutConfig(R.layout.item_mine_tools, 1));
        }

        @Override
        public int getItemViewType(int position) {
            return 1;
        }

        @Override
        protected void onBindViewHolder(ViewHolderHelper viewHolder, final PersonalCenterVo.ToolsVo item, final int position) {
            viewHolder.setText(R.id.tv_title, item.getTitle());
            RecyclerView recyclerView = viewHolder.getView(R.id.rv_tools_menu);
            recyclerView.setFocusableInTouchMode(false);

            if ("user_expo".equals(item.getMenu_type())) {
                new UniversalBind.Builder<PersonalCenterVo.ToolsMenuVo, ViewHolderHelper, ToolsItemAdapter>(recyclerView, new ToolsItemAdapter(item.getLists()))
                        .setGridLayoutManager(3)
                        .setNestedScrollingEnabled(false)
                        .build();
            } else if ("user_tool".equals(item.getMenu_type())) {
                new UniversalBind.Builder<PersonalCenterVo.ToolsMenuVo, ViewHolderHelper, ToolsItemAdapter>(recyclerView, new ToolsItemAdapter(item.getLists()))
                        .setGridLayoutManager(3)
                        .setNestedScrollingEnabled(false)
                        .build();
            }
        }
    }

    //小工具adapter
    public class ToolsItemAdapter extends ListBasedAdapter<PersonalCenterVo.ToolsMenuVo, ViewHolderHelper> {
        public ToolsItemAdapter(List<PersonalCenterVo.ToolsMenuVo> list) {
            super(list);
            addItemLayout(R.layout.item_mine_tools_menu);
        }

        @Override
        protected void onBindViewHolder(ViewHolderHelper viewHolder, final PersonalCenterVo.ToolsMenuVo item, final int position) {
            SimpleDraweeView icon = viewHolder.getView(R.id.iv_icon);
            viewHolder.setText(R.id.tv_title, item.getTitle());
            viewHolder.setText(R.id.tv_sub_title, item.getSub_title());
            viewHolder.setText(R.id.tv_status, item.getStatus_name());

            mImageLoad.loadImage(item.getImage(), 60, 60, icon);

            viewHolder.itemView.setOnClickListener(new DebouncingOnClickListener() {
                @Override
                public void doClick(View v) {

                    //统计
//                    AnalysisUtils.getInstance().sendEvent(v, Category.TAP, AppAction.CMS, data.getPosition_id(), data.getLink());

                    if ("yes".equals(item.getNeed_login())) {
                        if (checkLogin()) {
//                            CiwHelper.startActivity(item.getLink());
                        }
                    } else {
//                        CiwHelper.startActivity(item.getLink());
                    }
                }
            });

        }
    }

    //广告位置
    public class AdAdapter extends ListBasedAdapter<PersonalCenterVo.AdVo, ViewHolderHelper> {

        AdAdapter() {
            super(null);
            addItemLayout(new UniversalAdapter.LayoutConfig(R.layout.item_mine_ad, 2));
        }

        @Override
        public int getItemViewType(int position) {
            return 2;
        }

        @Override
        protected void onBindViewHolder(ViewHolderHelper viewHolder, final PersonalCenterVo.AdVo item, final int position) {
            SimpleDraweeView simpleDraweeView = viewHolder.getView(R.id.sv_image);

            mImageLoad.loadImage(item.getImage(), 1080, 1080, simpleDraweeView);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}
