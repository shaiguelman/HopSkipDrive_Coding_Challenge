package com.example.hopskipdrivecodingchallenge.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hopskipdrivecodingchallenge.RidesViewModel
import com.example.hopskipdrivecodingchallenge.databinding.DayListItemBinding
import com.example.hopskipdrivecodingchallenge.data.RidesDay

class DayListAdapter(private val model: RidesViewModel)
    : ListAdapter<RidesDay, DayListAdapter.DayViewHolder>(RidesDayDiffCallback) {

    class DayViewHolder private constructor(
        private val context: Context,
        private val binding: DayListItemBinding,
        private val model: RidesViewModel)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(ridesDay: RidesDay) {
            val rideAdapter = RideListAdapter(model)
            rideAdapter.submitList(ridesDay.rides)

            binding.ridesDay = ridesDay
            binding.ridesList.adapter = rideAdapter
            binding.ridesList.layoutManager = LinearLayoutManager(context)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, model: RidesViewModel): DayViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DayListItemBinding.inflate(layoutInflater, parent, false)

                return DayViewHolder(parent.context, binding, model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder.from(parent, model)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val ridesDay = getItem(position)
        holder.bind(ridesDay)
    }
}

object RidesDayDiffCallback : DiffUtil.ItemCallback<RidesDay>() {
    override fun areItemsTheSame(oldItem: RidesDay, newItem: RidesDay): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: RidesDay, newItem: RidesDay): Boolean {
        return oldItem.rides == newItem.rides
    }
}