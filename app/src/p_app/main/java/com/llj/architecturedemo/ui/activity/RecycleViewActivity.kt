package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.XViewHolder
import com.llj.adapter.util.ViewHolderHelperWrap
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.architecturedemo.databinding.ActivityRecycleViewBinding
import com.llj.architecturedemo.databinding.ItemRecycleViewBinding
import com.llj.architecturedemo.vo.DataVo
import com.llj.component.service.arouter.CRouterClassName
import timber.log.Timber


/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020/5/16 7:24 PM
 */
@Route(path = CRouterClassName.APP_RECYCLE_VIEW_ACTIVITY)
class RecycleViewActivity : AppMvcBaseActivity<ActivityRecycleViewBinding>() {

  override fun initViews(savedInstanceState: Bundle?) {
    mViewBinder.recyclerView.layoutManager = LinearLayoutManager(mContext)
    mViewBinder.recyclerView.adapter = ItemAdapter()

    val adapter = UniversalBind.Builder(mViewBinder.recyclerView2, ItemAdapter2())
        .setLinearLayoutManager()
        .build().getAdapter()
  }

  override fun initData() {
  }

  inner class ItemAdapter2 : ListBasedAdapter<DataVo?, ViewHolderHelperWrap<ItemRecycleViewBinding>> {

    constructor() : super() {
    }

    override fun onCreateViewBinding(viewType: Int): ViewBinding? {
      return ItemRecycleViewBinding.inflate(layoutInflater, mViewBinder.recyclerView2, false)
    }

    override fun getCount(): Int {
      return 100
    }

    private var currentTimeMillis: Long? = null
    override fun bindViewHolder(holder: XViewHolder, position: Int) {
      Timber.tag(mTagLog).i("ItemAdapter2 onBindViewHolder:%s,view:%s,position:%d", holder.hashCode(), holder.itemView.hashCode(), position)
      currentTimeMillis = System.currentTimeMillis()
      super.bindViewHolder(holder, position)
    }

    override fun onBindViewHolder(holder: ViewHolderHelperWrap<ItemRecycleViewBinding>, item: DataVo?, position: Int) {
      val viewBinder = holder.mViewBinder

      viewBinder.ivHeader.setBackgroundColor(getCompatColor(mContext, R.color.darkorange))
      viewBinder.tvNumber.text = "$position"
      viewBinder.tvTitle.text = "主标题"
      viewBinder.tvSubTitle.text = "子标题"

      holder.itemView.setOnClickListener(View.OnClickListener {
      })
      val spend = System.currentTimeMillis() - currentTimeMillis!!

      Timber.tag(mTagLog).i("ItemAdapter2 onBindViewHolder:%s,view:%s,position:%d,spend:%d",
          holder.hashCode(), holder.itemView.hashCode(), position, spend)
    }
  }

  inner class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    private var currentTimeMillis: Long? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
      val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_view, parent, false)
      val itemViewHolder = ItemViewHolder(view)

      itemViewHolder.itemView.setOnClickListener(View.OnClickListener {

        Timber.tag(mTagLog).i("ItemAdapter setOnClickListener:%s,view:%s", itemViewHolder.hashCode(), it.hashCode())
        Timber.tag(mTagLog).i("ItemAdapter position:%s", itemViewHolder.adapterPosition)

      })

      Timber.tag(mTagLog).i("ItemAdapter onCreateViewHolder:%s,view:%s", itemViewHolder.hashCode(), view.hashCode())
      return itemViewHolder
    }

    override fun getItemCount(): Int {
      return 100
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
      Timber.tag(mTagLog).i("ItemAdapter onBindViewHolder:%s,view:%s,position:%d", holder.hashCode(), holder.itemView.hashCode(), position)

      currentTimeMillis = System.currentTimeMillis()
      holder.image.setBackgroundColor(getCompatColor(mContext, R.color.darkorange))
      holder.number.text = "$position"
      holder.title.text = "主标题"
      holder.subTitle.text = "子标题"
      val spend = System.currentTimeMillis() - currentTimeMillis!!

      Timber.tag(mTagLog).i("ItemAdapter onBindViewHolder:%s,view:%s,position:%d,spend:%d",
          holder.hashCode(), holder.itemView.hashCode(), position, spend)
    }
  }

  class ItemViewHolder : RecyclerView.ViewHolder {
    val image: ImageView
    val number: TextView
    val title: TextView
    val subTitle: TextView

    constructor(itemView: View) : super(itemView) {
      image = itemView.findViewById(R.id.iv_header)
      number = itemView.findViewById(R.id.tv_number)
      title = itemView.findViewById(R.id.tv_title)
      subTitle = itemView.findViewById(R.id.tv_sub_title)

    }
  }

}