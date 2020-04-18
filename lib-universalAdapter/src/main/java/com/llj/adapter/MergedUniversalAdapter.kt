package com.llj.adapter

import android.view.ViewGroup
import com.llj.adapter.observable.ListObserver
import com.llj.adapter.observable.ListObserverListener
import java.util.*
import kotlin.collections.ArrayList

/**
 * Merges adapters together into one large [UniversalAdapter].
 */
class MergedUniversalAdapter : UniversalAdapter<Any?, XViewHolder>() {
    // region Constants
    private val listPieces = ArrayList<ListPiece>()

    // endregion Constants
    // region Inherited Methods
    override fun notifyDataSetChanged() {
        onGenericChange()
        recalculateStartPositions()
    }

    override fun onBindViewHolder(viewHolder: XViewHolder, item: Any?, position: Int) {
        val piece = getPieceAt(position)
        val adjusted = piece!!.getAdjustedItemPosition(position)
        piece.adapter.bindViewHolder(viewHolder, adjusted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): XViewHolder {
        var viewHolder: XViewHolder? = null
        for (piece in listPieces) {
            if (piece.hasViewType(itemType)) {
                viewHolder = piece.adapter.createViewHolder(parent, itemType)
                break
            }
        }
        checkNotNull(viewHolder) { "ViewHolder returned a null for itemType $itemType" }
        return viewHolder
    }

    override fun getItemViewTypeCount(): Int {
        var count = 0
        for (listPiece in listPieces) {
            count += listPiece.adapter.internalItemViewTypeCount
        }
        return count
    }

    override fun getItemViewType(position: Int): Int {
        val piece = getPieceAt(position)
        return piece?.getAdjustedItemViewType(position) ?: 0
    }

    override fun getCount(): Int {
        var count = 0
        for (piece in listPieces) {
            count += piece.count
        }
        return count
    }

    override fun isEnabled(position: Int): Boolean {
        val piece = getPieceAt(position)
        return piece!!.isEnabled(position)
    }

    override fun getItemId(position: Int): Long {
        val piece = getPieceAt(position)
        return piece!!.getItemId(position)
    }

    override fun get(position: Int): Any? {
        val piece = getPieceAt(position)
        return piece?.getAdjustedItem(position)
    }

    // endregion Inherited Methods
    // region Instance Methods
    /**
     * Adds an adapter at the end of this [MergedUniversalAdapter].
     *
     * @param adapter The adapter to add to this adapter
     */
    fun addAdapter(adapter: UniversalAdapter<out Any?, out XViewHolder>) {
        val count = getCount()
        addAdapter(listPieces.size, adapter)
        onItemRangeInserted(count, listPieces[listPieces.size - 1].count)
    }

    /**
     * Appends a varying amount of adapters at the end of this [MergedUniversalAdapter]
     *
     * @param adapters The adapters to append.
     */
    fun addAdapters(vararg adapters: UniversalAdapter<out Any?, out XViewHolder>) {
        val count = getCount()
        var totalCount = 0
        for (adapter in adapters) {
            addAdapter(listPieces.size, adapter)
            totalCount += listPieces[listPieces.size - 1].count
        }
        onItemRangeInserted(count, totalCount)
    }

    /**
     * Adds an adapter to this merged adapter based on the position of adapters.
     *
     * @param position The 0-based index position within adapters to add. If this is the 3rd adapter, the position is 2.
     * @param adapter  The adapter to add.
     */
    fun addAdapter(position: Int, adapter: UniversalAdapter<out Any?, out XViewHolder>) {
        val count = getCount()

        // create reference piece
        val piece = ListPiece(adapter, this)
        listPieces.add(position, piece)

        // set the starting point for it
        piece.startPosition = count

        // know what kind of item types the piece contains for faster item view type.
        piece.initializeItemViewTypes()
    }

    /**
     * Retrieves the adapter that is for the specified position within the whole merged adapter.
     *
     * @param position The position of item in the [MergedUniversalAdapter]
     *
     * @return The adapter that displays the specified position.
     */
    fun getPieceAt(position: Int): ListPiece? {
        for (piece in listPieces) {
            if (piece.isPositionWithinAdapter(position)) {
                return piece
            }
        }
        return null
    }

    /**
     * @param adapterIndex The index of adapters added to this adapter. 0 is for the first adapter added, 1 is for second. etc.
     *
     * @return The specified adapter from the adapterIndex.
     */
    fun getAdapter(adapterIndex: Int): UniversalAdapter<*, *> {
        return listPieces[adapterIndex].adapter
    }

    /**
     * When data changes we need to update the starting positions of all adapters.
     */
    private fun recalculateStartPositions() {
        var currentStartIndex = 0
        for (listPiece in listPieces) {
            listPiece.startPosition = currentStartIndex
            currentStartIndex += listPiece.count
        }
    }
    // endregion Instance Methods
    // region Anonymous Classes
    /**
     * Whenever a singular [ListPiece] changes, we refresh the adapter and notify content
     * changed.
     */
    private val cascadingListObserver: ListObserverListener<Any?> = object : ListObserverListener<Any?> {
        override fun onItemRangeChanged(observer: ListObserver<Any?>, startPosition: Int, count: Int,
                                        payload: Any?) {
            this@MergedUniversalAdapter.onItemRangeChanged(startPosition, count, payload)
        }

        override fun onItemRangeChanged(observer: ListObserver<Any?>, startPosition: Int, count: Int) {
            this@MergedUniversalAdapter.onItemRangeChanged(startPosition, count)
        }

        override fun onItemRangeInserted(observer: ListObserver<Any?>, startPosition: Int, count: Int) {
            recalculateStartPositions()
            this@MergedUniversalAdapter.onItemRangeInserted(startPosition, count)
        }

        override fun onItemRangeRemoved(observer: ListObserver<Any?>, startPosition: Int, count: Int) {
            recalculateStartPositions()
            this@MergedUniversalAdapter.onItemRangeRemoved(startPosition, count)
        }

        override fun onGenericChange(observer: ListObserver<Any?>) {
            recalculateStartPositions()
            this@MergedUniversalAdapter.onGenericChange()
        }
    }
    // endregion Anonymous Classes
    // region Inner Classes

    override fun addListener(listener: ListObserverListener<Any?>) {
        super.addListener(listener)
    }

    class ListPiece {

        val adapter: UniversalAdapter<out Any?, out XViewHolder>

        private val itemViewTypes: MutableSet<Int>

        private val forwardingChangeListener: ForwardingChangeListener


        var startPosition = 0

        internal constructor(adapter: UniversalAdapter<out Any?, out XViewHolder>, mergedUniversalAdapter: MergedUniversalAdapter) {
            this.adapter = adapter
            this.itemViewTypes = HashSet()
            forwardingChangeListener = ForwardingChangeListener(this, mergedUniversalAdapter.cascadingListObserver)
        }


        // region Instance Methods
        fun isEnabled(position: Int): Boolean {
            return adapter.internalIsEnabled(getAdjustedItemPosition(position))
        }

        fun getItemId(position: Int): Long {
            return adapter.getItemId(getAdjustedItemPosition(position))
        }

        fun hasViewType(itemType: Int): Boolean {
            return itemViewTypes.contains(itemType)
        }

        /**
         * Tracks the item view types of each adapter.
         */
        fun initializeItemViewTypes() {
            synchronized(itemViewTypes) {
                itemViewTypes.clear()
                for (i in 0 until count) {
                    itemViewTypes.add(adapter.getInternalItemViewType(i))
                }
            }
        }

        fun isPositionWithinAdapter(position: Int): Boolean {
            return position >= startPosition && position < startPosition + count
        }

        val count: Int
            get() = adapter.internalCount

        fun getAdjustedItem(position: Int): Any? {
            return adapter[getAdjustedItemPosition(position)]
        }

        fun getAdjustedItemViewType(position: Int): Int {
            return adapter.getInternalItemViewType(getAdjustedItemPosition(position))
        }

        fun getAdjustedItemPosition(position: Int): Int {
            return position - startPosition
        } // endregion Instance Methods

    }

    /**
     * Forwards internal adapter changes to the merged adapter.
     */
    class ForwardingChangeListener : ListObserverListener<Any?> {

        private val listPiece: ListPiece
        private val listObserverListener: ListObserverListener<Any?>

        constructor(listPiece: ListPiece, listObserverListener: ListObserverListener<Any?>) {
            this.listPiece = listPiece
            this.listObserverListener = listObserverListener
            listPiece.adapter.mListObserver.addListener(listObserverListener);
        }

        override fun onItemRangeChanged(observer: ListObserver<Any?>, startPosition: Int, count: Int, payload: Any?) {
            listPiece.initializeItemViewTypes()
            listObserverListener
                    .onItemRangeChanged(observer, listPiece.startPosition + startPosition, count, payload)
        }

        override fun onItemRangeChanged(observer: ListObserver<Any?>, startPosition: Int, count: Int) {
            listPiece.initializeItemViewTypes()
            listObserverListener
                    .onItemRangeChanged(observer, listPiece.startPosition + startPosition, count)
        }

        override fun onItemRangeInserted(observer: ListObserver<Any?>, startPosition: Int, count: Int) {
            listPiece.initializeItemViewTypes()
            listObserverListener
                    .onItemRangeInserted(observer, listPiece.startPosition + startPosition, count)
        }

        override fun onItemRangeRemoved(observer: ListObserver<Any?>, startPosition: Int, count: Int) {
            listPiece.initializeItemViewTypes()
            listObserverListener
                    .onItemRangeRemoved(observer, listPiece.startPosition + startPosition, count)
        }

        override fun onGenericChange(observer: ListObserver<Any?>) {
            listPiece.initializeItemViewTypes()
            listObserverListener.onGenericChange(observer)
        }

    } // endregion Inner Classes
}