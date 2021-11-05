package com.example.hopskipdrivecodingchallenge.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class Passenger(
    val id: Int,
    @Json(name = "booster_seat")
    val boosterSeat: Boolean,
    @Json(name = "first_name")
    val firstName: String
)

@JsonClass(generateAdapter = true)
data class Location(
    val address: String,
    val lat: Double,
    val lng: Double
)

@JsonClass(generateAdapter = true)
data class Waypoint(
    val id: Int,
    val anchor: Boolean,
    val passengers: List<Passenger>,
    val location: Location
)

@JsonClass(generateAdapter = true)
data class Ride(
    @Json(name = "trip_id")
    val tripId: Int,
    @Json(name = "in_series")
    val inSeries: Boolean,
    @Json(name = "starts_at")
    val startsAt: LocalDateTime,
    @Json(name = "ends_at")
    val endsAt: LocalDateTime,
    @Json(name = "estimated_earnings_cents")
    val estimatedEarningsCents: Int,
    @Json(name = "estimated_ride_minutes")
    val estimatedRideMinutes: Int,
    @Json(name = "estimated_ride_miles")
    val estimatedRideMiles: Double,
    @Json(name = "ordered_waypoints")
    val orderedWaypoints: List<Waypoint>
)

data class Rides(val rides: List<Ride>)