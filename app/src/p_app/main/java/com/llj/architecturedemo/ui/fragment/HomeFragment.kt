package com.llj.architecturedemo.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import butterknife.BindView
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.architecturedemo.flutter.PageRouter
import com.llj.architecturedemo.ui.model.BabyHomeModuleItemVo
import com.llj.component.service.MiddleMvcBaseFragment
import com.llj.component.service.arouter.CRouter
import com.llj.lib.scrollable.ScrollableHelper
import com.llj.lib.webview.CWebViewActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/15
 */
class HomeFragment : MiddleMvcBaseFragment<ViewBinding>(), ScrollableHelper.ScrollableContainer {
  override fun getScrollableView(): View {
    return mRecyclerView
  }

  @BindView(R.id.recyclerView)
  lateinit var mRecyclerView: androidx.recyclerview.widget.RecyclerView

  @BindView(R.id.tv_update)
  lateinit var mUpdate: TextView

  companion object {
    fun getInstance(data: BabyHomeModuleItemVo, position: Int): HomeFragment {
      val bundle = Bundle()
      bundle.putInt("position", position)
      bundle.putSerializable("data", data)
      val dataFragment = HomeFragment()
      dataFragment.arguments = bundle
      return dataFragment
    }
  }


  override fun layoutId(): Int {
    return R.layout.fragment_home
  }

  override fun initViews(savedInstanceState: Bundle?) {
    super.initViews(savedInstanceState)

    val arrayList = arrayListOf<Data?>()

    val cRouterClassName = Class.forName("com.llj.component.service.arouter.CRouterClassName")
    val declaredFields = cRouterClassName.declaredFields

    for (field in declaredFields) {
      arrayList.add(Data(field.get(null) as String))
    }
    arrayList.add(Data("CWebViewActivity", "CWebViewActivity"))
    arrayList.add(Data("NativePageActivity", PageRouter.NATIVE_PAGE_URL))

    UniversalBind.Builder(mRecyclerView, MyAdapter(arrayList))
        .setLinearLayoutManager()
        .build()
        .getAdapter()


    mUpdate.setOnClickListener {
      mRecyclerView.adapter?.notifyDataSetChanged()
    }

  }

  override fun initData() {

  }

  private inner class MyAdapter : ListBasedAdapter<Data, ViewHolderHelper> {

    constructor(list: MutableList<Data?>) : super(list) {
      addItemLayout(R.layout.item_home_fragment)
    }

    override fun onBindViewHolder(viewHolder: ViewHolderHelper, item: Data?, position: Int) {
      if (item == null) {
        return
      }
      Log.e("onBindViewHolder", position.toString())

      val textView = viewHolder.getView<TextView>(R.id.tv_text)
      setText(textView, position.toString() + "  " + item.text)

      viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
          when (item.text) {
            "CWebViewActivity" -> CWebViewActivity.start(mContext, "http://m.reallycar.cn/ocert")
            "FirstActivity" -> {
              val intent = Intent()
              intent.setClassName("com.llj.architecturedemo", "com.llj.architecturedemo.ui.activity.FirstActivity")
              mContext.startActivity(intent)
            }
            "SecondActivity" -> {
              val intent = Intent()
              intent.setClassName("com.llj.architecturedemo", "com.llj.architecturedemo.ui.activity.SecondActivity")
              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
              mContext.startActivity(intent)
            }
            "NativePageActivity" -> {
              PageRouter.openPageByUrl(mContext, PageRouter.NATIVE_PAGE_URL, null)
            }
            else -> CRouter.start(item.path)
          }
        }
      })
    }
  }

  private inner class Data() {
    var text: String? = null;
    var path: String? = null;

    constructor(path: String) : this() {
      this.path = path
      if (!isEmpty(path)) {
        this.text = path.substring(path.lastIndexOf("/") + 1)
      }
    }

    constructor(text: String, path: String) : this(path) {
      this.text = text
    }

  }
}