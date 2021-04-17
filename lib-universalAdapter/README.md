# lib-universalAdapter

## 功能介绍
1. 适用于多种布局的适配器，切换布局控件可以实现适配器零修改，支持RecyclerView，ViewPager2，ViewPager，AbsListView，ViewGroup等等。
2. 使用MergedUniversalAdapter来组合多个UniversalAdapter进行刷新

## 相关类
### UniversalConverterFactory
根据布局类型创建各自的适配器。
```kotlin
  fun <Item, Holder : XViewHolder> createGeneric(adapter: UniversalAdapter<Item, Holder>, viewGroup: ViewGroup)
      : UniversalConverter<Item, Holder> {
    return when (viewGroup) {
      is RecyclerView -> {
        //RecyclerViewAdapterConverter
        create(adapter, viewGroup)
      }
      is ViewPager2 -> {
        //ViewPager2AdapterConverter
        create(adapter, viewGroup)
      }
      is ViewPager -> {
        //PagerAdapterConverter
        create(adapter, viewGroup)
      }
      is AbsListView -> {
        //BaseAdapterConverter
        createAdapterView(adapter, viewGroup)
      }
      else -> {
        //ViewGroupAdapterConverter
        create(adapter, viewGroup)
      }
    }
  }
```

### UniversalAdapter
相关代码的具体实现类


## 使用方法
### 单ViewBinding模式
```kotlin
class CardItemAdapter(private val mNoteId: Long, private val mIsVideoDetail: Boolean, private val mTab: MarryHomeVo.TabItem?) :
    ListBasedAdapter<NoteItemVo.NoteCardVo?,
        ViewHolderHelperWrap<MarryItemNoteCardBinding>>(), IUiHandler, ITracker {

  constructor(mNoteId: Long, mTab: MarryHomeVo.TabItem?) : this(mNoteId, false, mTab)

  override fun onCreateViewBinding(parent: ViewGroup, viewType: Int): ViewBinding? {
    return MarryItemNoteCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
  }

  override fun getItemId(position: Int): Long {
    return get(position)?.hashCode()?.toLong() ?: 0
  }

  override fun hasStableIds(): Boolean {
    return true
  }

  override fun onBindViewHolder(holder: ViewHolderHelperWrap<MarryItemNoteCardBinding>, item: NoteItemVo.NoteCardVo?, position: Int) {
    item ?: return
    val viewBinder = holder.mViewBinder

    
  }
}
```
如果只有一种ViewBinding，可以使用ListBasedAdapter<NoteItemVo.NoteCardVo?,ViewHolderHelperWrap<MarryItemNoteCardBinding>>设置



### 多ViewBinding模式
```kotlin
class ComponentAdapter2(private val mContext: Context,
                        private val mBackgroundColor: String,
                        private val mAsAppPageId: Int,
                        private val mPublishVersion: String) : ListBasedAdapterWrap<ComponentVo, ComponentViewHolder<*>>() {

//  private var mData: MutableList<ComponentVo> = mutableListOf()

  companion object {
    var rnViewMap: HashMap<String, View> = HashMap()
  }

  override fun getItemViewType(position: Int): Int = when (getList()[position]!!.type) {
      ComponentTypeEnum.Header.name -> ComponentTypeEnum.Header.ordinal
      ComponentTypeEnum.Image.name -> ComponentTypeEnum.Image.ordinal
      ComponentTypeEnum.ImageGroup.name -> ComponentTypeEnum.ImageGroup.ordinal
      ComponentTypeEnum.ImageSlider.name -> ComponentTypeEnum.ImageSlider.ordinal
      ComponentTypeEnum.Navigator.name -> ComponentTypeEnum.Navigator.ordinal
      ComponentTypeEnum.Spacer.name -> ComponentTypeEnum.Spacer.ordinal
      ComponentTypeEnum.Swiper.name -> ComponentTypeEnum.Swiper.ordinal
    else -> -1
  }


  override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): XViewHolder {
    return when (itemType) {
      //滑动图片
        ComponentTypeEnum.ImageSlider.ordinal -> ImageSliderViewHolder.create(parent)
        ComponentTypeEnum.Swiper.ordinal -> BannerViewHolder.create(parent)
        ComponentTypeEnum.Spacer.ordinal -> SpacerViewHolder.create(parent)
        ComponentTypeEnum.ImageGroup.ordinal -> ImageGroupViewHolder.create(parent)
        ComponentTypeEnum.Header.ordinal -> HeaderViewHolder.create(parent)
        ComponentTypeEnum.Image.ordinal -> ImageViewHolder.create(parent)
        ComponentTypeEnum.Navigator.ordinal -> NavViewHolder.create(parent)
      else -> {
        TabViewHolder.create(parent, mAsAppPageId, mPublishVersion, mBackgroundColor)
      }
    }
  }

  override fun onBindViewHolder(holder: ComponentViewHolder<*>, item: ComponentVo?, position: Int) {
    item?.let {
      holder.setBackgroundColor(mBackgroundColor)
      holder.bindView(it)
    }

  }
}

//基础ViewHolder基类
abstract class ComponentViewHolder<V : ViewBinding>(view: View) : ViewBinderHolder<V>(view) {

  abstract fun bindView(componentVo: ComponentVo)
  var mBackgroundColor: String = ""
  fun setBackgroundColor(backgroundColor: String){
    mBackgroundColor = backgroundColor
  }
}

//具体实现
class ImageSliderViewHolder(private val mViewBinder: PageLayoutImageSliderBinding)
    : ComponentViewHolder<PageLayoutImageSliderBinding>(mViewBinder.root){

    companion object{
        fun create(parent: ViewGroup): ImageSliderViewHolder{
            return ImageSliderViewHolder(PageLayoutImageSliderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun bindView(componentVo: ComponentVo) {
        componentVo.props.let { props ->
            var adapter = ImageSliderAdapter(mContext,mBackgroundColor)
            (mViewBinder.rvImageSlide.layoutParams as RelativeLayout.LayoutParams)
                    .setMargins(AbDisplayUtil.dip2px(props.marginHorizontal.toFloat()),0,
                            AbDisplayUtil.dip2px(props.marginHorizontal.toFloat()),0)
            RecyclerBuild(mViewBinder.rvImageSlide)
                    .setLinerLayout(false)
                    .bindAdapter(adapter,true)
            adapter.setProperties(props)
            adapter.replaceAll(props.items)
        }

    }
}
```
在onBindViewHolder中需要判断不同类型ViewBinder的ViewHolder


### 多布局模式
```java
private class TemplateAdapter extends ListBasedAdapterWrap<MvTemplateVo, ViewHolderHelper> {

    private int padding;//item的padding
    private int itemWidth;//item的宽度

    TemplateAdapter(int spanCount) {

      if (mThemeCollectionTemplate) {
        addItemLayout(
            new LayoutConfig(R.layout.layout_state_no_favorite_template,
                MvTemplateVo.TYPE_NO_DATA));
      }
      addItemLayout(new LayoutConfig(R.layout.mv_item_common_template, MvTemplateVo.TYPE_COMMON));
      padding = dip2px(mContext, 4);
      itemWidth = (int) (
          (BaseLibConfig.UI_WIDTH - padding * 2 * spanCount - mRvMv.getPaddingLeft() * 2) / 2.0);
    }

    @Override
    public int getItemViewType(int position) {
      MvTemplateVo mvTemplateVo = get(position);
      if (mvTemplateVo == null) {
        return 0;
      }
      return mvTemplateVo.itemViewType;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderHelper viewHolder,
        @Nullable MvTemplateVo item, int position) {
      if (item == null) {
        return;
      }
      if (item.itemViewType == MvTemplateVo.TYPE_NO_DATA) {
        //无数据
      

      } else if (item.itemViewType == TYPE_SEE_MORE) {
        //查看更多
        
      } else if (item.itemViewType == MvTemplateVo.TYPE_COMMON) {
        //正常item
        
      }
    }
  }
```
通过addItemLayout设置布局类型，getItemViewType设置具体类型，在onBindViewHolder中根据itemViewType类型来设置不同的数据。



## License
```text
Copyright 2018 liulinjie

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
