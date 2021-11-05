package com.example.hopskipdrivecodingchallenge.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hopskipdrivecodingchallenge.R
import com.example.hopskipdrivecodingchallenge.RidesViewModel
import com.example.hopskipdrivecodingchallenge.adapters.WaypointListAdapter
import com.example.hopskipdrivecodingchallenge.data.Ride
import com.example.hopskipdrivecodingchallenge.databinding.FragmentRideDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

const val MARKERS_WIDTH = 50
const val MARKERS_HEIGHT = 50

class RideDetailsFragment: Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentRideDetailBinding
    private lateinit var model: RidesViewModel
    lateinit var ride: Ride
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        model = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        binding = FragmentRideDetailBinding.inflate(layoutInflater)

        requireActivity().title = "Ride Details"

        ride = model.selectedRide.value!!
        val adapter = WaypointListAdapter()
        adapter.submitList(ride.orderedWaypoints)

        binding.ride = ride
        if (!ride.inSeries) {
            binding.seriesText.visibility = View.INVISIBLE
        }
        binding.waypointList.adapter = adapter
        binding.waypointList.layoutManager = LinearLayoutManager(context)

        val mapFragment = childFragmentManager.fragments[0] as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.cancelRideButton.setOnClickListener {
            model.unselectRide()
        }

        model.selectedRide.observe(viewLifecycleOwner, {
            if (it == null) {
                findNavController().navigate(
                    RideDetailsFragmentDirections
                        .actionRideDetailsFragmentToRideListFragment()
                )
            }
        })

        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {
        val ride = model.selectedRide.value!!
        map.apply {
            val start = ride.orderedWaypoints[0].location
            val end = ride.orderedWaypoints[1].location
            val startPosition = LatLng(start.lat, start.lng)
            val endPosition = LatLng(end.lat, end.lng)

            val greenCircle = BitmapFactory.decodeResource(resources, R.drawable.green_circle)
            val greenCircleScaled = Bitmap
                .createScaledBitmap(greenCircle, MARKERS_WIDTH, MARKERS_HEIGHT, false)
            val redCircle = BitmapFactory.decodeResource(resources, R.drawable.red_circle)
            val redCircleScaled = Bitmap
                .createScaledBitmap(redCircle, MARKERS_WIDTH, MARKERS_HEIGHT, false)

            addMarker(
                MarkerOptions()
                    .position(startPosition)
                    .icon(BitmapDescriptorFactory.fromBitmap(greenCircleScaled))
            )
            addMarker(
                MarkerOptions()
                    .position(endPosition)
                    .icon(BitmapDescriptorFactory.fromBitmap(redCircleScaled))
            )

            val builder = LatLngBounds.builder()
            builder.include(startPosition)
            builder.include(endPosition)
            moveCamera(
                CameraUpdateFactory.newLatLngBounds(builder.build(), 100)
            )
        }
    }
}