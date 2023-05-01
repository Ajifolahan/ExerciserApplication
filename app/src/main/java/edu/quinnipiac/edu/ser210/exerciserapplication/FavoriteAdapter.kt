//@Authors: Camryn Keller and Momoreoluwa Ayinde
package edu.quinnipiac.edu.ser210.exerciserapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import edu.quinnipiac.edu.ser210.exerciserapplication.databinding.ListItemBinding

 //[ListAdapter] implementation for the FavoriteFragment.

class FavoriteAdapter(private val onItemClicked: (Workout) -> Unit) :
    ListAdapter<Workout, FavoriteAdapter.ItemViewHolder>(DiffCallback) {

    //inflates ListItem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
           ListItemBinding.inflate(
               LayoutInflater.from(
                   parent.context
               )
           )
        )
    }

    //Binds item to position
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    //Binds title and picture to card
    class ItemViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(workout: Workout) {
            Glide.with(itemView.context).load(workout.imageURL)
                .apply(RequestOptions().centerCrop())
                .into(binding.itemImage)
            binding.itemTitle.text = workout.title
        }
    }



    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Workout>(){
            override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}
