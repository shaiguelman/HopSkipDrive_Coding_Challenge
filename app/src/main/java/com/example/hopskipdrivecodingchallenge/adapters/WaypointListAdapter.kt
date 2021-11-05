package com.example.hopskipdrivecodingchallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hopskipdrivecodingchallenge.data.Waypoint
import com.example.hopskipdrivecodingchallenge.databinding.WaypointListItemBinding

class WaypointListAdapter() :
    ListAdapter<Waypoint, WaypointListAdapter.WaypointViewHolder>(WaypointDiffUtil) {

    class WaypointViewHolder(
        private val binding: WaypointListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(waypoint: Waypoint) {
            binding.waypoint = waypoint
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): WaypointViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    WaypointListItemBinding.inflate(layoutInflater, parent, false)

                return WaypointViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaypointViewHolder {
        return WaypointViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WaypointViewHolder, position: Int) {
        val waypoint = getItem(position)
        holder.bind(waypoint)
    }
}

object WaypointDiffUtil : DiffUtil.ItemCallback<Waypoint>() {
    override fun areItemsTheSame(oldItem: Waypoint, newItem: Waypoint): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Waypoint, newItem: Waypoint): Boolean {
        return oldItem == newItem
    }
}