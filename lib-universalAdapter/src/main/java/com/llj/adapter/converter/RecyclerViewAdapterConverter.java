package com.llj.adapter.converter;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.llj.adapter.UniversalAdapter;
import com.llj.adapter.UniversalConverter;
import com.llj.adapter.ViewHolder;
import com.llj.adapter.listener.FooterClickListener;
import com.llj.adapter.listener.FooterListenerAdapter;
import com.llj.adapter.listener.FooterLongClickListener;
import com.llj.adapter.listener.HeaderClickListener;
import com.llj.adapter.listener.HeaderListenerAdapter;
import com.llj.adapter.listener.HeaderLongClickListener;
import com.llj.adapter.listener.ItemClickedListener;
import com.llj.adapter.listener.ItemDoubleClickedListener;
import com.llj.adapter.listener.ItemListenerAdapter;
import com.llj.adapter.listener.ItemLongClickedListener;
import com.llj.adapter.observable.ListObserver;
import com.llj.adapter.observable.ListObserverListener;
import com.llj.adapter.util.ThreadingUtils;
import java.util.List;

/**
 * PROJECT:UniversalAdapter DESCRIBE: Created by llj on 2017/2/10.
 */

public class RecyclerViewAdapterConverter<Item, Holder extends ViewHolder> extends
    RecyclerView.Adapter
    implements HeaderListenerAdapter,
    FooterListenerAdapter,
    ItemListenerAdapter<Item, Holder>, UniversalConverter<Item, Holder> {

  public interface RecyclerItemClickListener<Holder extends ViewHolder> {

    /**
     * Called when an item in the {@link RecyclerView} is clicked.
     *
     * @param viewHolder The view holder of the clicked item.
     * @param parent The recycler view which contained the clicked item.
     * @param position The position in the adapter of the clicked item.
     * @param x x position
     * @param y y position
     */
    void onItemClick(Holder viewHolder, RecyclerView parent, int position, float x, float y);
  }


  private UniversalAdapter<Item, Holder>         mUniversalAdapter;
  private RecyclerViewListObserverListener<Item> mObserverListener;//更新监听器
  private RecyclerItemClickListener<Holder>      mRecyclerItemClickListener;

  RecyclerViewAdapterConverter(@NonNull UniversalAdapter<Item, Holder> universalAdapter,
      RecyclerView recyclerView) {
    mObserverListener = new RecyclerViewListObserverListener<>(this);
    universalAdapter.checkIfBoundAndSet();

    setAdapter(universalAdapter);
    recyclerView.setAdapter(this);

    recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener() {

      @Override
      public void onItemClick(ViewHolder viewHolder, RecyclerView parent, int position, float x,
          float y) {
        if (getAdapter().internalIsEnabled(position)) {
          if (mRecyclerItemClickListener != null) {
            mRecyclerItemClickListener.onItemClick((Holder) viewHolder, parent, position, x, y);
          }
          getAdapter().onItemClicked(position, viewHolder);
        }
      }

      @Override
      public void onItemDoubleClick(ViewHolder viewHolder, RecyclerView parent, int position,
          float x, float y) {
        getAdapter().onItemDoubleClicked(position, viewHolder);
      }

      @Override
      public void onItemLongClick(ViewHolder viewHolder, RecyclerView parent, int position, float x,
          float y) {
        getAdapter().onItemLongClicked(position, viewHolder);
      }
    });
    universalAdapter.notifyDataSetChanged();
  }

  public void setRecyclerItemClickListener(
      RecyclerItemClickListener<Holder> recyclerItemClickListener) {
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
    getAdapter().getListObserver().addListener(mObserverListener);
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

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    return getAdapter().createViewHolder(viewGroup, viewType);
  }


  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    getAdapter().bindViewHolder((ViewHolder) holder, position);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position,
      @NonNull List payloads) {
    if (payloads.isEmpty()) {
      getAdapter().bindViewHolder((ViewHolder) holder, position);
    } else {
      getAdapter().bindViewHolder((ViewHolder) holder, position, payloads);
    }
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
  public void setItemLongClickedListener(
      ItemLongClickedListener<Item, Holder> longClickedListener) {
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
    public void onItemRangeChanged(ListObserver<Item> observer, int start, int count,
        @Nullable Object payload) {
      ThreadingUtils.runOnUIThread(() -> adapter.notifyItemRangeChanged(start, count, payload));
    }

    @Override
    public void onItemRangeChanged(ListObserver<Item> observer, final int start, final int count) {
      ThreadingUtils.runOnUIThread(() -> adapter.notifyItemRangeChanged(start, count));
    }

    @Override
    public void onItemRangeInserted(ListObserver<Item> observer, final int start, final int count) {
      ThreadingUtils.runOnUIThread(() -> adapter.notifyItemRangeInserted(start, count));
    }

    @Override
    public void onItemRangeRemoved(ListObserver<Item> observer, final int start, final int count) {
      ThreadingUtils.runOnUIThread(() -> adapter.notifyItemRangeRemoved(start, count));
    }

    @Override
    public void onGenericChange(ListObserver<Item> observer) {
      ThreadingUtils.runOnUIThread(() -> adapter.notifyDataSetChanged());
    }
  }


  //点击事件实现
  public abstract class RecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent e) {
      if (gestureDetector == null) {
        gestureDetector = new GestureDetector(view.getContext(),
            new GestureDetector.SimpleOnGestureListener() {
              @Override
              public boolean onSingleTapUp(MotionEvent e) {
                //1.如果支持双击，返回false,则gestureDetector.onTouchEvent(e)返回false，不会触发后面的单击代码
                //单击事件在onSingleTapConfirmed中触发
                //2.如果不支持双击，返回true,则gestureDetector.onTouchEvent(e)返回true，直接触发后面的单击代码，
                //这样在onSingleTapUp后就返回true,就触发了单击，单击只需100ms左右，如果放在onSingleTapConfirmed里面执行单击则需要至少300ms的延时判断
                return !getAdapter().isSupportDoubleClick();
              }

              @Override
              public boolean onSingleTapConfirmed(MotionEvent e) {
                //300ms外没有迎来第二次点击
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
                //必须300ms内触发
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
                //长按，超过600ms
                View childView = view.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) {
                  int position = view.getChildAdapterPosition(childView);
                  ViewHolder viewHolder = (ViewHolder) view.getChildViewHolder(childView);
                  onItemLongClick(viewHolder, view, position, e.getX(), e.getY());
                }
              }
            });
        gestureDetector.setIsLongpressEnabled(getAdapter().isSupportLongClick());
      }
      if (gestureDetector.onTouchEvent(e)) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null) {
          int position = view.getChildAdapterPosition(childView);
          ViewHolder viewHolder = (ViewHolder) view.getChildViewHolder(childView);
          onItemClick(viewHolder, view, position, e.getX(), e.getY());
        }
      }
      return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    public abstract void onItemClick(ViewHolder viewHolder, RecyclerView parent, int position,
        float x, float y);

    public abstract void onItemDoubleClick(ViewHolder viewHolder, RecyclerView parent, int position,
        float x, float y);

    public abstract void onItemLongClick(ViewHolder viewHolder, RecyclerView parent, int position,
        float x, float y);
  }
}
