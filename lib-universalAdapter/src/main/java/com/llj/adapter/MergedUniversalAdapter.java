package com.llj.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.llj.adapter.observable.ListObserver;
import com.llj.adapter.observable.ListObserverListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Merges adapters together into one large {@link UniversalAdapter}.
 */
public class MergedUniversalAdapter extends UniversalAdapter {

    // region Constants

    private final List<ListPiece> listPieces = new ArrayList<>();

    // endregion Constants

    // region Inherited Methods

    @Override
    public void notifyDataSetChanged() {
        onGenericChange();
        recalculateStartPositions();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, Object o, int position) {
        ListPiece piece = getPieceAt(position);
        int adjusted = piece.getAdjustedItemPosition(position);
        piece.adapter.bindViewHolder(viewHolder, adjusted);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int itemType) {
        ViewHolder viewHolder = null;
        for (ListPiece piece : listPieces) {

            if (piece.hasViewType(itemType)) {
                viewHolder = piece.adapter.createViewHolder(parent, itemType);
                break;
            }
        }
        if (viewHolder == null) {
            throw new IllegalStateException("ViewHolder returned a null for itemType " + itemType);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewTypeCount() {
        int count = 0;
        for (ListPiece listPiece : listPieces) {
            count += listPiece.adapter.getInternalItemViewTypeCount();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        ListPiece piece = getPieceAt(position);
        return piece != null ? piece.getAdjustedItemViewType(position) : 0;
    }

    @Override
    public int getCount() {
        int count = 0;
        for (ListPiece piece : listPieces) {
            count += piece.getCount();
        }
        return count;
    }

    @Override
    public boolean isEnabled(int position) {
        ListPiece piece = getPieceAt(position);
        return piece.isEnabled(position);
    }

    @Override
    public long getItemId(int position) {
        ListPiece piece = getPieceAt(position);
        return piece.getItemId(position);
    }

    @Override
    public Object get(int position) {
        ListPiece piece = getPieceAt(position);
        return piece != null ? piece.getAdjustedItem(position) : null;
    }

    // endregion Inherited Methods

    // region Instance Methods

    /**
     * Adds an adapter at the end of this {@link MergedUniversalAdapter}.
     *
     * @param adapter The adapter to add to this adapter
     */
    public void addAdapter(UniversalAdapter adapter) {
        int count = getCount();
        addAdapter(listPieces.size(), adapter);
        onItemRangeInserted(count, listPieces.get(listPieces.size() - 1).getCount());
    }

    /**
     * Appends a varying amount of adapters at the end of this {@link MergedUniversalAdapter}
     *
     * @param adapters The adapters to append.
     */
    public void addAdapters(UniversalAdapter... adapters) {
        int count = getCount();
        int totalCount = 0;
        for (UniversalAdapter adapter : adapters) {
            addAdapter(listPieces.size(), adapter);
            totalCount += listPieces.get(listPieces.size() - 1).getCount();
        }
        onItemRangeInserted(count, totalCount);
    }

    /**
     * Adds an adapter to this merged adapter based on the position of adapters.
     *
     * @param position The 0-based index position within adapters to add. If this is the 3rd adapter, the position is 2.
     * @param adapter  The adapter to add.
     */
    @SuppressWarnings("unchecked")
    void addAdapter(int position, UniversalAdapter adapter) {
        int count = getCount();

        // create reference piece
        ListPiece piece = new ListPiece(adapter, this);
        listPieces.add(position, piece);

        // set the starting point for it
        piece.setStartPosition(count);

        // know what kind of item types the piece contains for faster item view type.
        piece.initializeItemViewTypes();
    }

    /**
     * Retrieves the adapter that is for the specified position within the whole merged adapter.
     *
     * @param position The position of item in the {@link MergedUniversalAdapter}
     *
     * @return The adapter that displays the specified position.
     */
    public ListPiece getPieceAt(int position) {
        for (ListPiece piece : listPieces) {
            if (piece.isPositionWithinAdapter(position)) {
                return piece;
            }
        }
        return null;
    }

    /**
     * @param adapterIndex The index of adapters added to this adapter. 0 is for the first adapter added, 1 is for second. etc.
     *
     * @return The specified adapter from the adapterIndex.
     */
    public UniversalAdapter getAdapter(int adapterIndex) {
        return listPieces.get(adapterIndex).adapter;
    }

    /**
     * When data changes we need to update the starting positions of all adapters.
     */
    private void recalculateStartPositions() {
        int currentStartIndex = 0;
        for (ListPiece listPiece : listPieces) {
            listPiece.setStartPosition(currentStartIndex);
            currentStartIndex += listPiece.getCount();
        }
    }

    // endregion Instance Methods

    // region Anonymous Classes

    /**
     * Whenever a singular {@link ListPiece} changes, we refresh the adapter and notify content
     * changed.
     */
    private final ListObserverListener cascadingListObserver = new ListObserverListener() {

        @Override
        public void onItemRangeChanged(ListObserver observer, int start, int count,
            @Nullable Object payload) {
            MergedUniversalAdapter.this.onItemRangeChanged(start, count, payload);
        }

        @Override
        public void onItemRangeChanged(ListObserver listObserver, int start, int count) {
            MergedUniversalAdapter.this.onItemRangeChanged(start, count);
        }

        @Override
        public void onItemRangeInserted(ListObserver listObserver, int start, int count) {
            recalculateStartPositions();
            MergedUniversalAdapter.this.onItemRangeInserted(start, count);
        }

        @Override
        public void onItemRangeRemoved(ListObserver listObserver, int start, int count) {
            recalculateStartPositions();
            MergedUniversalAdapter.this.onItemRangeRemoved(start, count);
        }

        @Override
        public void onGenericChange(ListObserver listObserver) {
            recalculateStartPositions();
            MergedUniversalAdapter.this.onGenericChange();
        }
    };

    // endregion Anonymous Classes

    // region Inner Classes

    /**
     * Struct that keeps track of each {@link UniversalAdapter} in this merged adapter.
     */
    private static class ListPiece {

        final Set<Integer> itemViewTypes = new HashSet<>();

        final UniversalAdapter adapter;

        final ForwardingChangeListener forwardingChangeListener;

        /**
         * Position it starts at
         */
        int startPosition;

        @SuppressWarnings("unchecked")
        ListPiece(UniversalAdapter adapter, MergedUniversalAdapter mergedUniversalAdapter) {
            this.adapter = adapter;
            forwardingChangeListener = new ForwardingChangeListener(this, mergedUniversalAdapter.cascadingListObserver);
        }

        // region Instance Methods

        boolean isEnabled(int position) {
            return adapter.internalIsEnabled(getAdjustedItemPosition(position));
        }

        long getItemId(int position) {
            return adapter.getItemId(getAdjustedItemPosition(position));
        }

        public boolean hasViewType(int itemType) {
            return itemViewTypes.contains(itemType);
        }

        void setStartPosition(int position) {
            startPosition = position;
        }

        /**
         * Tracks the item view types of each adapter.
         */
        void initializeItemViewTypes() {
            synchronized (itemViewTypes) {
                itemViewTypes.clear();
                for (int i = 0; i < getCount(); i++) {
                    itemViewTypes.add(adapter.getInternalItemViewType(i));
                }
            }
        }

        boolean isPositionWithinAdapter(int position) {
            return position >= startPosition && position < (startPosition + getCount());
        }

        int getCount() {
            return adapter.getInternalCount();
        }

        Object getAdjustedItem(int position) {
            return adapter.get(getAdjustedItemPosition(position));
        }

        int getAdjustedItemViewType(int position) {
            return adapter.getInternalItemViewType(getAdjustedItemPosition(position));
        }

        int getAdjustedItemPosition(int position) {
            return position - startPosition;
        }

        // endregion Instance Methods

    }

    /**
     * Forwards internal adapter changes to the merged adapter.
     */
    @SuppressWarnings("unchecked")
    private static class ForwardingChangeListener implements ListObserverListener {

        private final ListPiece listPiece;

        private final ListObserverListener listObserverListener;

        private ForwardingChangeListener(ListPiece listPiece, ListObserverListener listObserverListener) {
            this.listPiece = listPiece;
            this.listObserverListener = listObserverListener;
            listPiece.adapter.getListObserver().addListener(this);
        }

        @Override
        public void onItemRangeChanged(ListObserver observer, int start, int count,
            @Nullable Object payload) {
            listPiece.initializeItemViewTypes();
            listObserverListener
                .onItemRangeChanged(observer, listPiece.startPosition + start, count, payload);
        }

        @Override
        public void onItemRangeChanged(ListObserver observer, int start, int count) {
            listPiece.initializeItemViewTypes();
            listObserverListener
                .onItemRangeChanged(observer, listPiece.startPosition + start, count);
        }

        @Override
        public void onItemRangeInserted(ListObserver observer, int start, int count) {
            listPiece.initializeItemViewTypes();
            listObserverListener
                .onItemRangeInserted(observer, listPiece.startPosition + start, count);
        }

        @Override
        public void onItemRangeRemoved(ListObserver observer, int start, int count) {
            listPiece.initializeItemViewTypes();
            listObserverListener
                .onItemRangeRemoved(observer, listPiece.startPosition + start, count);
        }

        @Override
        public void onGenericChange(ListObserver observer) {
            listPiece.initializeItemViewTypes();
            listObserverListener.onGenericChange(observer);
        }
    }

    // endregion Inner Classes
}
