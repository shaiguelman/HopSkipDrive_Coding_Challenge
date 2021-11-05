package com.example.hopskipdrivecodingchallenge.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.example.hopskipdrivecodingchallenge.R
import com.example.hopskipdrivecodingchallenge.data.Passenger
import com.example.hopskipdrivecodingchallenge.data.Ride
import com.example.hopskipdrivecodingchallenge.data.RidesDay
import com.example.hopskipdrivecodingchallenge.data.Waypoint
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@BindingAdapter("priceText")
fun TextView.setPriceText(ride: Ride?) {
    if (ride != null) {
        val priceString = centsToPriceString(ride.estimatedEarningsCents)
        text = "$$priceString"
    }
}

@BindingAdapter("timeText")
fun TextView.setTimeText(ride: Ride?) {
    if (ride != null){
        val startTime = ride.startsAt.toLocalTime()
        val endTime = ride.endsAt.toLocalTime()
        text = "$startTime - $endTime"
    }
}

@BindingAdapter("ridersText")
fun TextView.setRidersText(ride: Ride?) {
    if (ride != null) {
        val allRiders = getUniquePassengers(ride)
        val totalRiders = allRiders.size
        val totalBoosterSeats = allRiders.count {
            it.boosterSeat
        }
        val riderOrRiders = if (totalRiders == 1) "rider" else "riders"
        val ridersString = "$totalRiders $riderOrRiders"
        if (totalBoosterSeats > 0) {
            val boosterOrBoosters = if (totalBoosterSeats == 1) "booster" else "boosters"
            text = "($ridersString • $totalBoosterSeats $boosterOrBoosters)"
        } else {
            text = "($ridersString)"
        }
    }
}

@BindingAdapter("addressesText")
fun TextView.setAddressesText(ride: Ride) {
    val builder = StringBuilder()
    ride.orderedWaypoints.forEachIndexed { idx, waypoint ->
        val line = "${idx + 1}. ${waypoint.location.address}"
        builder.appendLine(line)
    }
    text = builder
}

@BindingAdapter("dateText")
fun TextView.setDateText(ridesDay: RidesDay) {
    val dayOfWeek = ridesDay.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    val formatter = DateTimeFormatter.ofPattern("MM/dd")
    val date = ridesDay.date.format(formatter)
    text = "$dayOfWeek $date"
}

@BindingAdapter("timeText")
fun TextView.setTimeText(ridesDay: RidesDay) {
    // These calculations assume no rides go past midnight
    val startTime = (ridesDay.rides.minOf { it.startsAt }).toLocalTime()
    val endTime = (ridesDay.rides.maxOf { it.endsAt }).toLocalTime()

    text = " • $startTime - $endTime"
}

@BindingAdapter("priceText")
fun TextView.setPriceText(ridesDay: RidesDay) {
    val totalCents = ridesDay.rides.sumOf { it.estimatedEarningsCents }
    val priceString = centsToPriceString(totalCents)
    text = "$$priceString"
}

@BindingAdapter("dateText")
fun TextView.setDateText(ride: Ride?) {
    if (ride != null) {
        val dayOfWeek = ride.startsAt.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
        val formatter = DateTimeFormatter.ofPattern("MM/dd")
        val date = ride.startsAt.format(formatter)
        text = "$dayOfWeek $date"
    }
}

@BindingAdapter("rideInfoText")
fun TextView.setRideInfoText(ride: Ride?) {
    if (ride != null) {
        text = "Trip Id: ${ride.tripId} • " +
                "${ride.estimatedRideMiles} mi • " +
                "${ride.estimatedRideMinutes} min"
    }
}

@BindingAdapter("icon")
fun ImageView.setIcon(waypoint: Waypoint) {
    if (waypoint.anchor){
        setImageResource(R.drawable.diamond)
    } else {
        setImageResource(R.drawable.circle)
    }
}

@BindingAdapter("waypointTypeText")
fun TextView.setWaypointTypeText(waypoint: Waypoint) {
    text = if (waypoint.anchor) "Pickup" else "Drop-off"
}

@BindingAdapter("addressText")
fun TextView.setAddressText(waypoint: Waypoint) {
    text = waypoint.location.address
}