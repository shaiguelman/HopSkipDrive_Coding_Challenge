package com.example.hopskipdrivecodingchallenge.data

import java.time.LocalDate

class RidesDay private constructor(val date: LocalDate, val rides: List<Ride>) {

    companion object {
        fun splitByDay(rides: List<Ride>): List<RidesDay> {
            val mapsByDate = rides.groupBy {
                it.startsAt.toLocalDate()
            }
            return mapsByDate.map {
                RidesDay(it.key, it.value)
            }
        }
    }
}