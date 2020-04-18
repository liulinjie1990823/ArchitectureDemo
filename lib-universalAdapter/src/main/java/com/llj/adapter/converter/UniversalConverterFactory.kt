package com.llj.adapter.converter

import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.llj.adapter.UniversalAdapter
import com.llj.adapter.UniversalConverter
import com.llj.adapter.XViewHolder

/**
 * PROJECT:CommonAdapter
 * DESCRIBE:
 * Created by llj on 2017/2/11.
 */
object UniversalConverterFactory {

    @JvmStatic
    fun <Item, Holder : XViewHolder> createGeneric(adapter: UniversalAdapter<Item, Holder>,
                                                   viewGroup: ViewGroup)
            : UniversalConverter<Item, Holder> {
        return when (viewGroup) {
            is RecyclerView -> {
                create(adapter, viewGroup)
            }
            is AbsListView -> {
                createAdapterView(adapter, viewGroup)
            }
            is ViewPager -> {
                create(adapter, viewGroup)
            }
            else -> {
                create(adapter, viewGroup)
            }
        }
    }

    /**
     * Creates a [ViewGroupAdapterConverter] that can populate the given [ViewGroup].
     *
     * @param adapter   The adapter to create the converter for.
     * @param viewGroup The view group to create the converter for.
     * @param <Item>    The type of data being bound to the views.
     * @param <Holder>  The type of view holder being used for views.
     * @return A [ViewGroupAdapterConverter] created to bind the given adapter to
     * the given view group.
    </Holder></Item> */
    fun <Item, Holder : XViewHolder> create(adapter: UniversalAdapter<Item, Holder>, viewGroup: ViewGroup)
            : ViewGroupAdapterConverter<Item, Holder> {
        return ViewGroupAdapterConverter(adapter, viewGroup)
    }

    /**
     * Creates a [RecyclerViewAdapterConverter] that can populate the given [RecyclerView].
     *
     * @param adapter      The adapter to create the converter for.
     * @param recyclerView The recycler view to create the converter for.
     * @param <Item>       The type of data being bound to the views.
     * @param <Holder>     The type of view holder being used for the views.
     * @return A [RecyclerViewAdapterConverter] created to bind the given
     * adapter to the given recycler view.
    </Holder></Item> */
    fun <Item, Holder : XViewHolder> create(adapter: UniversalAdapter<Item, Holder>, recyclerView: RecyclerView)
            : RecyclerViewAdapterConverter<Item, Holder> {
        return RecyclerViewAdapterConverter(adapter, recyclerView)
    }

    /**
     * Creates a [PagerAdapterConverter] that can populate the given [ViewPager].
     *
     * @param adapter   The adapter to create the converter for.
     * @param viewPager The view pager to create the converter for.
     * @param <Item>    The type of data being bound to the views.
     * @param <Holder>  The type of view holder being used for the views.
     * @return A [PagerAdapterConverter] created to bind the given
     * adapter to the given view pager.
    </Holder></Item> */
    fun <Item, Holder : XViewHolder> create(adapter: UniversalAdapter<Item, Holder>, viewPager: ViewPager)
            : PagerAdapterConverter<Item, Holder> {
        return PagerAdapterConverter(adapter, viewPager)
    }

    /**
     * Creates a [BaseAdapterConverter] that can populate the given [AdapterView].
     *
     * @param adapter     The adapter to create the converter for.
     * @param adapterView The adapter view to create the converter for.
     * @param <Item>      The type of data being bound to the views.
     * @param <Holder>    The type of view holder being used for the views.
     * @return A [BaseAdapterConverter] created to bind the given adapter
     * to the given adapter view.
    </Holder></Item> */
    fun <Item, Holder : XViewHolder> createAdapterView(adapter: UniversalAdapter<Item, Holder>, adapterView: AbsListView)
            : BaseAdapterConverter<Item, Holder> {
        return BaseAdapterConverter(adapter, adapterView)
    }
}