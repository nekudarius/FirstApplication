package com.vanhan.firstapplication.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.vanhan.firstapplication.base.util.Constants;
import com.vanhan.firstapplication.base.util.listener.OnItemClickCallback;
import com.vanhan.firstapplication.databinding.ItemProgressBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseAdapter is an abstract class serving as the base for RecyclerView adapters in Android applications.
 *
 * @param <VB> The type of ViewBinding used in the adapter.
 * @param <DATA> The type of data items to be displayed in the RecyclerView.
 */
public abstract class BaseAdapter<VB extends ViewBinding, DATA> extends RecyclerView.Adapter<BaseAdapter<VB, DATA>.MyViewHolder> {

    protected ArrayList<DATA> dataList = new ArrayList<>(); // ArrayList to store the data items to be displayed in the RecyclerView.
    public Context context; // Context of the application.
    public FragmentManager fragmentManager; // FragmentManager used for certain operations.
    public boolean isLoadMore = false; // Flag indicating whether the adapter is in "load more" mode.

    public OnItemClickCallback<DATA> onItemClickCallback; // Callback for item click events.

    /**
     * Constructor for creating an instance of the adapter with a given context.
     *
     * @param context The context of the application.
     */
    public BaseAdapter(Context context) {
        this.context = context;
    }

    /**
     * Constructor for creating an instance of the adapter with a given context and fragmentManager.
     *
     * @param context The context of the application.
     * @param fragmentManager The FragmentManager used for certain operations.
     */
    public BaseAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    /**
     * Sets the data list for the adapter and notifies the adapter of the data change.
     *
     * @param initData The list of data items to be displayed.
     */
    public void setDataList(List<DATA> initData) {
        this.dataList.clear();
        this.dataList.addAll(initData);
        notifyDataSetChanged();
    }

    /**
     * Sets the callback for item click events.
     *
     * @param onItemClickCallback The callback to be set.
     */
    public void setOnItemClickCallback(OnItemClickCallback<DATA> onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == Constants.VIEW_TYPE_DATA) {
            VB binding = getView(viewGroup);
            return new DataViewHolder(binding);
        } else {
            ItemProgressBinding binding = ItemProgressBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
            return new ProgressViewHolder(binding.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        if (viewHolder.getClass().isAssignableFrom(DataViewHolder.class)) {
            bind(viewHolder.getViewBinding(), dataList.get(position), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) != null) {
            return Constants.VIEW_TYPE_DATA;
        } else {
            return Constants.VIEW_TYPE_LOADING;
        }
    }

    class DataViewHolder extends MyViewHolder {
        public DataViewHolder(VB itemView) {
            super(itemView);
        }
    }

    class ProgressViewHolder extends MyViewHolder {
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * Initializes "load more" mode and adds a "load more" indicator to the dataList.
     * @param isLoadMore True to enable "load more" mode, false otherwise.
     */
    public void initLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        if (isLoadMore) {
            dataList.add(null);
            notifyItemInserted(dataList.size() - 1);
        }
    }

    /**
     * Abstract method to be implemented by subclasses to obtain the ViewBinding object based on its type.
     *
     * @param viewGroup The parent view group.
     * @return The ViewBinding object.
     */
    public abstract VB getView(ViewGroup viewGroup);

    @Override
    public int getItemCount() {
        return dataList.size() == 0 ? 0 : dataList.size();
    }

    /**
     * Abstract method to be implemented by subclasses to bind data to the ViewBinding object.
     *
     * @param binding The ViewBinding object.
     * @param item The data item to be bound.
     * @param index The position or index of the item in the list.
     */
    public abstract void bind(VB binding, DATA item, int index);

    /**
     * ViewHolder class serving as the base for DataViewHolder and ProgressViewHolder.
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        private VB getViewHolder; // The ViewBinding object for the ViewHolder.

        /**
         * Constructor for MyViewHolder with a ViewBinding object.
         *
         * @param view The ViewBinding object.
         */
        public MyViewHolder(VB view) {
            super(view.getRoot());
            getViewHolder = view;
        }

        /**
         * Constructor for MyViewHolder with a regular View.
         *
         * @param view The regular View object.
         */
        public MyViewHolder(View view) {
            super(view);
        }

        /**
         * Gets the ViewBinding object.
         * @return The ViewBinding object.
         */
        public VB getViewBinding() {
            return getViewHolder;
        }

    }
}
