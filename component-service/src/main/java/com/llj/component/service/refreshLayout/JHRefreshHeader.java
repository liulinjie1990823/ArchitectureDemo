package com.llj.component.service.refreshLayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.llj.component.service.R;
import com.llj.component.service.R2;
import com.llj.lib.image.loader.FrescoUtils;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import butterknife.BindView;


/**
 * Created by zhouyao on 17-12-18.
 */
public class JHRefreshHeader extends FrameLayout implements RefreshHeader {

    @BindView(R2.id.iv_loading) SimpleDraweeView mIvLoading;

    public JHRefreshHeader(Context context) {
        super(context);
        initViews();
    }

    public JHRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public JHRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    protected void initViews() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.common_refresh_header, this);
        FrescoUtils.setController(R.drawable.service_ic_refresh, 100, 100,
                false, null, 0, 0,
                0, 0, true, mIvLoading, null);
    }


    /**
     * 手指拖动下拉（会连续多次调用，添加isDragging并取代之前的onPulling、onReleasing）
     *
     * @param isDragging    true 手指正在拖动 false 回弹动画
     * @param percent       下拉的百分比 值 = offset/footerHeight (0 - percent - (footerHeight+maxDragHeight) / footerHeight )
     * @param offset        下拉的像素偏移量  0 - offset - (footerHeight+maxDragHeight)
     * @param height        高度 HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
    }


    /**
     * 释放时刻（调用一次，将会触发加载）
     *
     * @param layout       RefreshLayout
     * @param headerHeight HeaderHeight
     * @param extendHeight extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onReleased(RefreshLayout layout, int headerHeight, int extendHeight) {
    }

    /**
     * 动画结束
     *
     * @param layout  RefreshLayout
     * @param success 数据是否成功刷新或加载
     *
     * @return 完成动画所需时间 如果返回 Integer.MAX_VALUE 将取消本次完成事件，继续保持原有状态
     */
    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        return 10;
    }

    /**
     * 状态改变事件 {@link RefreshState}
     *
     * @param refreshLayout RefreshLayout
     * @param oldState      改变之前的状态
     * @param newState      改变之后的状态
     */
    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case PullDownToRefresh:
                break;
        }
    }

    /**
     * 获取实体视图
     */
    @NonNull
    @Override
    public View getView() {
        return this;
    }

    /**
     * 获取变换方式 {@link SpinnerStyle} 必须返回 非空
     */
    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    /**
     * 设置主题颜色
     *
     * @param colors 对应Xml中配置的 srlPrimaryColor srlAccentColor
     */
    @Override
    public void setPrimaryColors(int... colors) {

    }

    /**
     * 尺寸定义完成 （如果高度不改变（代码修改：setHeader），只调用一次, 在RefreshLayout#onMeasure中调用）
     *
     * @param kernel       RefreshKernel
     * @param height       HeaderHeight or FooterHeight
     * @param extendHeight extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    /**
     * 水平方向的拖动
     *
     * @param percentX  下拉时，手指水平坐标对屏幕的占比（0 - percentX - 1）
     * @param offsetX   下拉时，手指水平坐标对屏幕的偏移（0 - offsetX - LayoutWidth）
     * @param offsetMax
     */
    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    /**
     * 开始动画
     *
     * @param layout       RefreshLayout
     * @param height       HeaderHeight or FooterHeight
     * @param extendHeight extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onStartAnimator(@NonNull RefreshLayout layout, int height, int extendHeight) {

    }


    /**
     * 是否支持水平方向的拖动（将会影响到onHorizontalDrag的调用）
     *
     * @return 水平拖动需要消耗更多的时间和资源，所以如果不支持请返回false
     */
    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }


}
