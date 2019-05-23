package com.llj.component.service.refreshLayout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

/**
 * dmp_hunbohui
 * describe:
 * @author liulj
 * date 2018/1/22
 */

public class JHSmartRefreshLayout extends SmartRefreshLayout {
    public JHSmartRefreshLayout(Context context) {
        super(context);
        initViews();
    }

    public JHSmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public JHSmartRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        ClassicsFooter classicsFooter = new ClassicsFooter(getContext());
        classicsFooter.setPrimaryColor(Color.WHITE);

        setRefreshHeader(new JHRefreshHeader(getContext()))//设置刷新头
//                .setRefreshFooter(new JHRefreshFooter(getContext()))
                .setRefreshFooter(classicsFooter)
                .setDragRate(1f)//设置阻尼效果
                .setHeaderMaxDragRate(2f)//设置最大拖动高度和header高度的比例
                .setReboundDuration(500)//设置释放后的回弹时间
                .setEnableRefresh(true)
                .setEnableLoadMore(true)
                .setEnableAutoLoadMore(true)//设置自动加载更多，不用手指拖动触发
                .setEnableLoadMoreWhenContentNotFull(true)
                .setEnableOverScrollDrag(false)//是否启用越界拖动（仿苹果效果）
                .setEnableOverScrollBounce(false)//是否启用越界滑动以及回弹（仿苹果效果）
        ;
    }
}
