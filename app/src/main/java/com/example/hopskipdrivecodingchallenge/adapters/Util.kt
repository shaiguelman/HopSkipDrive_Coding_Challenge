package com.example.hopskipdrivecodingchallenge.adapters

import com.example.hopskipdrivecodingchallenge.data.Passenger
import com.example.hopskipdrivecodingchallenge.data.Ride
import java.util.*

fun centsToPriceString(cents: Int): String {
    val price = cents / 100.0
    return String.format("%.2f", price)
}

fun getUniquePassengers(ride: Ride): Set<Passenger> {
    val allRiders = ride.orderedWaypoints.fold(setOf<Passenger>()) { set, waypoint ->
        set.plus(waypoint.passengers)
    }
    return allRiders
}