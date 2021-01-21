package com.llj.architecturedemo.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import butterknife.BindView
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.MainMvcBaseFragment
import com.llj.architecturedemo.R
import com.llj.architecturedemo.R2
import com.llj.lib.scrollable.ScrollableHelper

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/16
 */
class ItemFragment : MainMvcBaseFragment<ViewBinding>(), ScrollableHelper.ScrollableContainer {
  override fun getScrollableView(): View {
    return mRecyclerView
  }

  companion object {
    fun getInstance(): androidx.fragment.app.Fragment {
      return ItemFragment()
    }
  }

  @BindView(R2.id.recyclerView)
  lateinit var mRecyclerView: androidx.recyclerview.widget.RecyclerView

  override fun layoutId(): Int {
    return R.layout.fragment_item
  }

  override fun initViews(savedInstanceState: Bundle?) {
    super.initViews(savedInstanceState)
    val arrayList = arrayListOf<Data?>()

    for (i in 0 until 100) {
      arrayList.add(Data("text$i"))
    }
    UniversalBind.Builder(mRecyclerView, MyAdapter(arrayList))
        .setLinearLayoutManager()
        .build()
        .getAdapter()
  }

  override fun initData() {
  }


  inner class MyAdapter(list: MutableList<Data?>) : ListBasedAdapter<Data?, ViewHolderHelper>(list) {
    init {
      addItemLayout(R.layout.item_home_fragment)
    }


    override fun onBindViewHolder(holder: ViewHolderHelper, item: Data?, position: Int) {
      if (item == null) {
        return
      }
      val textView = holder.getView<TextView>(R.id.tv_text)
      setText(textView, item.text)
    }
  }

  inner class Data(var text: String)
}