package com.llj.lib.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/3/14.
 */

public class RecycleViewUtils {
    /**
     * Get center child in X Axes
     *
     * @param recyclerView recyclerView
     *
     * @return
     */
    public static View getCenterXChild(@NonNull RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildInCenterX(recyclerView, child)) {
                    return child;
                }
            }
        }
        return null;
    }

    /**
     * Get position of center child in X Axes
     *
     * @param recyclerView recyclerView
     *
     * @return
     */
    public static int getCenterXChildPosition(@NonNull RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildInCenterX(recyclerView, child)) {
                    return recyclerView.getChildAdapterPosition(child);
                }
            }
        }
        return childCount;
    }

    /**
     * Get center child in Y Axes
     *
     * @param recyclerView recyclerView
     *
     * @return
     */
    public static View getCenterYChild(@NonNull RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildInCenterY(recyclerView, child)) {
                    return child;
                }
            }
        }
        return null;
    }

    /**
     * Get position of center child in Y Axes
     *
     * @param recyclerView recyclerView
     *
     * @return
     */
    public static int getCenterYChildPosition(@NonNull RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildInCenterY(recyclerView, child)) {
                    return recyclerView.getChildAdapterPosition(child);
                }
            }
        }
        return childCount;
    }


    /**
     * Judge if the RecyclerView slides to the top
     *
     * @param recyclerView recyclerView
     *
     * @return
     */
    public static boolean isTop(@NonNull RecyclerView recyclerView) {
        int firstVisibleItemPosition = -1;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] viewsIds = staggeredGridLayoutManager.findFirstVisibleItemPositions(null);
            firstVisibleItemPosition = viewsIds[0];
        }

        View childAt = recyclerView.getChildAt(0);
        return childAt == null || (firstVisibleItemPosition == 0 && layoutManager.getDecoratedTop(childAt) == 0);
    }

    /**
     * Judge if the RecyclerView slides to the bottom
     *
     * @param recyclerView recyclerView
     *
     * @return
     */
    public static boolean isBottom(@NonNull RecyclerView recyclerView) {
        int lastVisibleItemPosition = -1;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            return false;
        }
        if (layoutManager instanceof GridLayoutManager) {

            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] viewsIds = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
            lastVisibleItemPosition = viewsIds[0];
        }

        View childAt = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
        return childAt == null || (lastVisibleItemPosition == (layoutManager.getItemCount() - 1) && layoutManager.getDecoratedBottom(childAt) == recyclerView.getMeasuredHeight());
    }

    /**
     * Precise locate
     * <p>
     * if you want to keep position when page redisplay.
     * you should save position and offset in onScrollStateChanged when newState is SCROLL_STATE_IDLE.
     *
     * @param recyclerView recyclerView
     * @param position     position to locate
     * @param offset       offset
     */
    public static void scrollToPositionWithOffset(@NonNull RecyclerView recyclerView, int position, int offset) {
        if (recyclerView == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.scrollToPositionWithOffset(position, offset);
        } else if (layoutManager instanceof LinearLayoutManager) {

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            linearLayoutManager.scrollToPositionWithOffset(position, offset);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            staggeredGridLayoutManager.scrollToPositionWithOffset(position, offset);
        }
    }

    /**
     * general locate
     *
     * @param recyclerView recyclerView
     * @param position     position to locate
     */
    public static void scrollToPositionWithOffset(@NonNull RecyclerView recyclerView, int position) {
        if (recyclerView == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.scrollToPosition(position);
        } else if (layoutManager instanceof LinearLayoutManager) {

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            linearLayoutManager.scrollToPosition(position);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            staggeredGridLayoutManager.scrollToPosition(position);
        }
    }

    public static int findFirstVisibleItemPosition(@NonNull RecyclerView recyclerView) {
        if (recyclerView == null) {
            return -1;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            return gridLayoutManager.findFirstVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            return linearLayoutManager.findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] viewsIds = staggeredGridLayoutManager.findFirstVisibleItemPositions(null);
            if (viewsIds.length > 0) {
                return viewsIds[0];
            }
        }
        return -1;
    }


    public static int getChildAdapterPosition(@NonNull RecyclerView recyclerView, @NonNull View child) {
        return recyclerView.getChildAdapterPosition(child);
    }

    public static int getChildLayoutPosition(@NonNull RecyclerView recyclerView, @NonNull View child) {
        return recyclerView.getChildLayoutPosition(child);
    }


    @Nullable
    public static View findViewByPosition(@NonNull RecyclerView recyclerView, int position) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            return null;
        }
        return layoutManager.findViewByPosition(position);
    }

    @Nullable
    public static RecyclerView.ViewHolder findViewHolderForAdapterPosition(@NonNull RecyclerView recyclerView, int position) {
        return recyclerView.findViewHolderForAdapterPosition(position);
    }

    @Nullable
    public static RecyclerView.ViewHolder findViewHolderForLayoutPosition(@NonNull RecyclerView recyclerView, int position) {
        return recyclerView.findViewHolderForLayoutPosition(position);
    }


    private static boolean isChildInCenterX(RecyclerView recyclerView, View view) {
        int childCount = recyclerView.getChildCount();
        int[] lvLocationOnScreen = new int[2];
        int[] vLocationOnScreen = new int[2];
        recyclerView.getLocationOnScreen(lvLocationOnScreen);
        int middleX = lvLocationOnScreen[0] + recyclerView.getWidth() / 2;
        if (childCount > 0) {
            view.getLocationOnScreen(vLocationOnScreen);
            if (vLocationOnScreen[0] <= middleX && vLocationOnScreen[0] + view.getWidth() >= middleX) {
                return true;
            }
        }
        return false;
    }

    private static boolean isChildInCenterY(RecyclerView recyclerView, View view) {
        int childCount = recyclerView.getChildCount();
        int[] lvLocationOnScreen = new int[2];
        int[] vLocationOnScreen = new int[2];
        recyclerView.getLocationOnScreen(lvLocationOnScreen);
        int middleY = lvLocationOnScreen[1] + recyclerView.getHeight() / 2;
        if (childCount > 0) {
            view.getLocationOnScreen(vLocationOnScreen);
            if (vLocationOnScreen[1] <= middleY && vLocationOnScreen[1] + view.getHeight() >= middleY) {
                return true;
            }
        }
        return false;
    }
}
