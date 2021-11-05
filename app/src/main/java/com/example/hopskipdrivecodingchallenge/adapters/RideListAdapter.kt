package com.example.hopskipdrivecodingchallenge.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hopskipdrivecodingchallenge.RidesViewModel
import com.example.hopskipdrivecodingchallenge.data.Ride
import com.example.hopskipdrivecodingchallenge.databinding.RideListItemBinding

class RideListAdapter(private val model: RidesViewModel) :
    ListAdapter<Ride, RideListAdapter.RideViewHolder>(RideDiffCallback) {

    class RideViewHolder private constructor(
        private val binding: RideListItemBinding,
        private val model: RidesViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ride: Ride) {
            binding.ride = ride
            binding.rideCard.setOnClickListener {
                model.selectRide(ride)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, model: RidesViewModel): RideViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RideListItemBinding.inflate(layoutInflater, parent, false)

                return RideViewHolder(binding, model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        return RideViewHolder.from(parent, model)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        val ride = getItem(position)
        holder.bind(ride)
    }
}

object RideDiffCallback : DiffUtil.ItemCallback<Ride>() {
    override fun areItemsTheSame(oldItem: Ride, newItem: Ride): Boolean {
        return oldItem.tripId == newItem.tripId
    }

    override fun areContentsTheSame(oldItem: Ride, newItem: Ride): Boolean {
        return oldItem == newItem
    }
}