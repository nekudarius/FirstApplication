package com.vanhan.firstapplication.base.util.listener;

/** Abstract class representing a callback for item click events */
public abstract class OnItemClickCallback<Item> {

    /** Abstract method to be implemented by subclasses
     * Invoked when an item is clicked
     * @param data: The data associated with the clicked item
     * @param index: The position or index of the clicked item
     */
    public abstract void onItemClicked(Item data, int index);
}