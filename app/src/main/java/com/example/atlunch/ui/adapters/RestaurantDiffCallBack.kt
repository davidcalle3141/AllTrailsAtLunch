package com.example.atlunch.ui.adapters

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.atlunch.data.models.Restaurant

class RestaurantDiffCallBack( var oldList: List<Restaurant>, var newList: List<Restaurant>) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].place_id == newList[newItemPosition].place_id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isFavorite == newList[newItemPosition].isFavorite
    }
    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }

}