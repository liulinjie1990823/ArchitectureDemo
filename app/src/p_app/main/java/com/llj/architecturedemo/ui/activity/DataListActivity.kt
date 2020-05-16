package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.architecturedemo.databinding.ActivityDataListBinding
import com.llj.architecturedemo.vo.DataVo

/**
 * describe 列表基类
 *
 * @author liulinjie
 * @date 2020/4/19 2:41 PM
 */

abstract class DataListActivity : AppMvcBaseActivity<ActivityDataListBinding>() {

  private lateinit var mAdapter: ItemAdapter

  override fun initViews(savedInstanceState: Bundle?) {
    mAdapter = UniversalBind.Builder(mViewBinder!!.recyclerView, ItemAdapter())
        .setLinearLayoutManager()
        .build().getAdapter()
  }

  override fun initData() {
    val data = ArrayList<DataVo?>()
    getData(data)
    mAdapter.addAll(data)
//    mViewBinder!!.recyclerView.postDelayed(Runnable {
//      val data = ArrayList<DataVo?>()
//      getData(data)
//      mAdapter.addAll(data)
//    },0)
  }

  abstract fun getData(data: ArrayList<DataVo?>)

  abstract fun onClick(view: View, item: DataVo)

  inner class ItemAdapter : ListBasedAdapter<DataVo?, ViewHolderHelper> {

    constructor() : super() {
      addItemLayout(R.layout.item_canvas)
    }

    override fun onBindViewHolder(viewHolder: ViewHolderHelper, item: DataVo?, position: Int) {
      if (item == null) {
        return
      }
      val name = viewHolder.getView<TextView>(R.id.tv_text)
      setText(name, item.name)
      viewHolder.itemView.setOnClickListener(View.OnClickListener {
        onClick(it, item)
      })
    }
  }
}