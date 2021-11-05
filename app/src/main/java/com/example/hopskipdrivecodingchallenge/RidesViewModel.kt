package com.example.hopskipdrivecodingchallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.hopskipdrivecodingchallenge.data.Ride
import com.example.hopskipdrivecodingchallenge.data.RidesApi
import com.example.hopskipdrivecodingchallenge.data.RidesDay

class RidesViewModel : ViewModel() {

    private val _rides = liveData {
        emit(RidesApi.ridesApiService.getRides().rides)
    }
    val rides: LiveData<List<Ride>>
        get() = _rides

    fun getRidesDays(rides: List<Ride>): List<RidesDay> {
        return RidesDay.splitByDay(rides)
    }

    private val _selectedRide = MutableLiveData<Ride?>()
    val selectedRide: LiveData<Ride?>
        get() = _selectedRide

    fun selectRide(ride: Ride) {
        _selectedRide.value = ride
    }

    fun unselectRide() {
        _selectedRide.value = null
    }
}

