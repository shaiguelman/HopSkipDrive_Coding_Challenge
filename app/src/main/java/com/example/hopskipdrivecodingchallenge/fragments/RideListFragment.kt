package com.example.hopskipdrivecodingchallenge.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hopskipdrivecodingchallenge.RidesViewModel
import com.example.hopskipdrivecodingchallenge.adapters.DayListAdapter
import com.example.hopskipdrivecodingchallenge.databinding.FragmentRideListBinding

class RideListFragment : Fragment() {

    private lateinit var binding: FragmentRideListBinding
    private lateinit var model: RidesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        model = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        binding = FragmentRideListBinding.inflate(layoutInflater)

        requireActivity().title = "My Rides"

        val adapter = DayListAdapter(model)
        binding.dayList.adapter = adapter
        binding.dayList.layoutManager = LinearLayoutManager(context)

        model.rides.observe(viewLifecycleOwner, {
            val ridesDays = model.getRidesDays(it)
            adapter.submitList(ridesDays)
        })
        model.selectedRide.observe(viewLifecycleOwner, {
            if (it != null) {
                findNavController().navigate(
                    RideListFragmentDirections
                        .actionRideListFragmentToRideDetailsFragment()
                )
            }
        })

        return binding.root
    }
}