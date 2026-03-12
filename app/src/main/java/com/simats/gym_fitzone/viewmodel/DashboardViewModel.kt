package com.simats.gym_fitzone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.gym_fitzone.api.RetrofitClient
import com.simats.gym_fitzone.models.Gym
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val api = RetrofitClient.apiService

    fun getGyms(onResult: (List<Gym>) -> Unit) {

        viewModelScope.launch {

            try {
                val gyms = api.getGyms()
                onResult(gyms)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}