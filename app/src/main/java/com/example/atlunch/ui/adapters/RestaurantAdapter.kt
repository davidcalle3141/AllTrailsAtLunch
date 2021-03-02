package com.example.atlunch.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.atlunch.R
import com.example.atlunch.data.api.getPlacesPhotoUrl
import com.example.atlunch.data.models.Restaurant


class RestaurantAdapter(val onFavoriteClick: (id: String, position: Int) -> Unit) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    private var searchResults: List<Restaurant> = ArrayList()

    fun setData(newList: List<Restaurant>) {
        val diffCallBack = RestaurantDiffCallBack(searchResults, newList)
        val diffResults = DiffUtil.calculateDiff(diffCallBack)
        searchResults = newList
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_list_item, parent, false)
        return RestaurantViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val item = searchResults[position]
        holder.bind(item)
        holder.getRestaurantFavoriteHolder().setOnClickListener {
            onFavoriteClick(item.place_id, position)
        }
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val restaurantImage: ImageView =
            itemView.findViewById(R.id.restaurant_list_item_image)
        private val restaurantName: TextView =
            itemView.findViewById(R.id.restaurant_list_item_name_tv)
        private val restaurantRating: RatingBar =
            itemView.findViewById(R.id.restaurant_list_item_rating_bar)
        private val restaurantPrice: TextView =
            itemView.findViewById(R.id.restaurant_list_item_price_tv)
        private val restaurantOpenHours: TextView =
            itemView.findViewById(R.id.restaurant_list_item_hours_tv)
        private val restaurantFavoriteSelected: ImageView =
            itemView.findViewById(R.id.restaurant_list_item_favorite_selected)
        private val restaurantFavoriteUnSelected: ImageView =
            itemView.findViewById(R.id.restaurant_list_item_favorite_unselected)
        private val restaurantFavoriteHolder: FrameLayout =
            itemView.findViewById(R.id.restaurant_list_item_favorite_holder)

        fun getRestaurantFavoriteHolder() = restaurantFavoriteHolder


        fun bind(restaurant: Restaurant) {
            with(restaurant) {
                restaurantName.text = name
                restaurantRating.rating = rating?.toFloat() ?: 0f
                restaurantPrice.text = getPriceLevel()
                restaurantOpenHours.text =
                    if (opening_hours?.open_now == true) "Open Now" else "closed "
                if (!photos.isNullOrEmpty()) Glide.with(itemView)
                    .load(getPlacesPhotoUrl(photos.first().photo_reference, itemView.context))
                    .into(restaurantImage)
                setIsFavorite(restaurant.getFavorite())
            }

        }


        private fun setIsFavorite(isFavorite: Boolean) {
            restaurantFavoriteSelected.visibility = if (isFavorite) View.VISIBLE else View.INVISIBLE
            restaurantFavoriteUnSelected.visibility =
                if (isFavorite) View.INVISIBLE else View.VISIBLE
        }
    }
}