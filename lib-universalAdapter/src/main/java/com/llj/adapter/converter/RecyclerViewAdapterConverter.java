package com.llj.adapter.converter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.llj.adapter.CommonConverter;
import com.llj.adapter.UniversalAdapter;
import com.llj.adapter.ViewHolder;
import com.llj.adapter.listener.FooterClickListener;
import com.llj.adapter.listener.FooterLongClickListener;
import com.llj.adapter.listener.HeaderClickListener;
import com.llj.adapter.listener.HeaderFooterListenerAdapter;
import com.llj.adapter.listener.HeaderLongClickListener;
import com.llj.adapter.listener.ItemClickedListener;
import com.llj.adapter.listener.ItemDoubleClickedListener;
import com.llj.adapter.listener.ItemLongClickedListener;
import com.llj.adapter.observable.ListObserver;
import com.llj.adapter.observable.ListObserverListener;
import com.llj.adapter.util.ThreadingUtils;

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/2/10.
 */

public class RecyclerViewAdapterConverter<Item, Holder extends ViewHolder> extends RecyclerView.Adapter implements HeaderFooterListenerAdapter<Item, Holder>, CommonConverter<Item, Holder> {
    public interface RecyclerItemClickListener<Holder extends ViewHolder> {

        /**
         * Called when an item in the {@link RecyclerView} is clicked.
         *
         * @param viewHolder The view holder of the clicked item.
         * @param parent     The recycler view which contained the clicked item.
         * @param position   The position in the adapter of the clicked item.
         * @param x          x position
         * @param y          y position
         */
        void onItemClick(Holder viewHolder, RecyclerView parent, int position, float x, float y);
    }


    private UniversalAdapter<Item, Holder>         mUniversalAdapter;
    private RecyclerViewListObserverListener<Item> mObserverListener;//更新监听器
    private RecyclerItemClickListener<Holder>      mRecyclerItemClickListener;

    RecyclerViewAdapterConverter(@NonNull UniversalAdapter<Item, Holder> universalAdapter, RecyclerView recyclerView) {
        mObserverListener = new RecyclerViewListObserverListener<>(this);
        universalAdapter.checkIfBoundAndSet();

        setAdapter(universalAdapter);
        recyclerView.setAdapter(this);

        recyclerView.addOnItemTouchListener(internalOnItemTouchListener);
        universalAdapter.notifyDataSetChanged();
    }

    public void setRecyclerItemClickListener(RecyclerItemClickListener<Holder> recyclerItemClickListener) {
        this.mRecyclerItemClickListener = recyclerItemClickListener;
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void setAdapter(@NonNull UniversalAdapter<Item, Holder> universalAdapter) {
        //先移除之前的监听
        if (getAdapter() != null) {
            getAdapter().getListObserver().removeListener(mObserverListener);
        }

        this.mUniversalAdapter = universalAdapter;
        //设置新的监听
        universalAdapter.getListObserver().addListener(mObserverListener);
        setHasStableIds(universalAdapter.hasStableIds());
    }

    @NonNull
    @Override
    public UniversalAdapter<Item, Holder> getAdapter() {
        return mUniversalAdapter;
    }

    @Override
    public void cleanup() {
        getAdapter().getListObserver().removeListener(mObserverListener);
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getAdapter().createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        getAdapter().bindViewHolder((ViewHolder) holder, position);
    }

    @Override
    public long getItemId(int position) {
        return getAdapter().getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return getAdapter().getInternalItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return getAdapter().getInternalCount();
    }


    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void setFooterClickListener(FooterClickListener footerClickListener) {
        getAdapter().setFooterClickListener(footerClickListener);
    }

    @Override
    public void setFooterLongClickListener(FooterLongClickListener footerLongClickListener) {
        getAdapter().setFooterLongClickListener(footerLongClickListener);
    }

    @Override
    public void setHeaderClickListener(HeaderClickListener headerClickListener) {
        getAdapter().setHeaderClickListener(headerClickListener);
    }

    @Override
    public void setHeaderLongClickListener(HeaderLongClickListener headerLongClickListener) {
        getAdapter().setHeaderLongClickListener(headerLongClickListener);
    }

    @Override
    public void setItemClickedListener(ItemClickedListener<Item, Holder> listener) {
        getAdapter().setItemClickedListener(listener);
    }

    @Override
    public void setItemDoubleClickedListener(ItemDoubleClickedListener<Item, Holder> listener) {
        getAdapter().setItemDoubleClickedListener(listener);
    }

    @Override
    public void setItemLongClickedListener(ItemLongClickedListener<Item, Holder> longClickedListener) {
        getAdapter().setItemLongClickedListener(longClickedListener);
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    /**
     * adapter更新封装
     *
     * @param <Item>
     */
    static class RecyclerViewListObserverListener<Item> implements ListObserverListener<Item> {

        private RecyclerView.Adapter<?> adapter;

        RecyclerViewListObserverListener(RecyclerView.Adapter<?> adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onItemRangeChanged(ListObserver<Item> observer, final int startPosition, final int itemCount) {
            ThreadingUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyItemRangeChanged(startPosition, itemCount);
                }
            });
        }

        @Override
        public void onItemRangeInserted(ListObserver<Item> observer, final int startPosition, final int itemCount) {
            ThreadingUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyItemRangeInserted(startPosition, itemCount);
                }
            });
        }

        @Override
        public void onItemRangeRemoved(ListObserver<Item> observer, final int startPosition, final int itemCount) {
            ThreadingUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyItemRangeRemoved(startPosition, itemCount);
                }
            });
        }

        @Override
        public void onGenericChange(ListObserver<Item> observer) {
            ThreadingUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }

    }


    private final RecyclerViewItemClickListener internalOnItemTouchListener = new RecyclerViewItemClickListener() {
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            //TODO
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onItemClick(ViewHolder viewHolder, RecyclerView parent, int position, float x, float y) {
            if (getAdapter().internalIsEnabled(position)) {
                if (mRecyclerItemClickListener != null) {
                    mRecyclerItemClickListener.onItemClick((Holder) viewHolder, parent, position, x, y);
                }

                getAdapter().onItemClicked(position, viewHolder);
            }
        }

        @Override
        public void onItemDoubleClick(ViewHolder viewHolder, RecyclerView parent, int position, float x, float y) {
            getAdapter().onItemDoubleClicked(position, viewHolder);
        }

        @Override
        public void onItemLongClick(ViewHolder viewHolder, RecyclerView parent, int position, float x, float y) {
            getAdapter().onItemLongClicked(position, viewHolder);
        }
    };

    public abstract class RecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;

        @Override
        public boolean onInterceptTouchEvent(final RecyclerView view, MotionEvent e) {
            if (gestureDetector == null) {
                gestureDetector = new GestureDetector(view.getContext(), new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return !getAdapter().isSupportDoubleClick();
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (getAdapter().isSupportDoubleClick()) {
                            View childView = view.findChildViewUnder(e.getX(), e.getY());
                            if (childView != null) {
                                int position = view.getChildAdapterPosition(childView);
                                ViewHolder viewHolder = (ViewHolder) view.getChildViewHolder(childView);
                                onItemClick(viewHolder, view, position, e.getX(), e.getY());
                            }
                        }
                        return super.onSingleTapConfirmed(e);
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        View childView = view.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null) {
                            int position = view.getChildAdapterPosition(childView);
                            ViewHolder viewHolder = (ViewHolder) view.getChildViewHolder(childView);
                            onItemDoubleClick(viewHolder, view, position, e.getX(), e.getY());
                        }
                        return super.onDoubleTap(e);
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = view.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null) {
                            int position = view.getChildAdapterPosition(childView);
                            ViewHolder viewHolder = (ViewHolder) view.getChildViewHolder(childView);
                            onItemLongClick(viewHolder, view, position, e.getX(), e.getY());
                        }
                    }
                });
                if (getAdapter().isSupportLongClick())
                    gestureDetector.setIsLongpressEnabled(true);
            }
            if (gestureDetector.onTouchEvent(e)) {
                View childView = view.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) {
                    int position = view.getChildAdapterPosition(childView);
                    ViewHolder viewHolder = (ViewHolder) view.getChildViewHolder(childView);
                    onItemClick(viewHolder, view, position, e.getX(), e.getY());
                }
            }
//            if (gestureDetector == null) {
//                gestureDetector = new GestureDetector(view.getContext(), new GestureDetector.SimpleOnGestureListener() {
//                    @Override
//                    public boolean onSingleTapUp(MotionEvent e) {
//                        return true;
//                    }
//
//                    @Override
//                    public void onLongPress(MotionEvent e) {
//                        View childView = view.findChildViewUnder(e.getX(), e.getY());
//                        if (childView != null) {
//                            int position = view.getChildAdapterPosition(childView);
//                            ViewHolder viewHolder = (ViewHolder) view.getChildViewHolder(childView);
//                            onItemLongClick(viewHolder, view, position, e.getX(), e.getY());
//                        }
//                    }
//                });
//                gestureDetector.setIsLongpressEnabled(true);
//            }
//
//            if (gestureDetector.onTouchEvent(e)) {
//                View childView = view.findChildViewUnder(e.getX(), e.getY());
//                if (childView != null) {
//                    int position = view.getChildAdapterPosition(childView);
//                    ViewHolder viewHolder = (ViewHolder) view.getChildViewHolder(childView);
//                    onItemClick(viewHolder, view, position, e.getX(), e.getY());
//                }
//            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        /**
         * Called when an item in the {@link RecyclerView} is clicked.
         *
         * @param viewHolder The view holder of the clicked item.
         * @param parent     The recycler view which contained the clicked item.
         * @param position   The position in the adapter of the clicked item.
         * @param x          x position
         * @param y          y position
         */
        public abstract void onItemClick(ViewHolder viewHolder, RecyclerView parent, int position, float x, float y);

        public abstract void onItemDoubleClick(ViewHolder viewHolder, RecyclerView parent, int position, float x, float y);

        /**
         * Called when an item in the {@link RecyclerView} is long clicked.
         *
         * @param viewHolder The view holder of the clicked item.
         * @param parent     The recycler view which contained the clicked item.
         * @param position   The position in the adapter of the clicked item.
         * @param x          x position
         * @param y          y position
         */
        public abstract void onItemLongClick(ViewHolder viewHolder, RecyclerView parent, int position, float x, float y);
    }
}
