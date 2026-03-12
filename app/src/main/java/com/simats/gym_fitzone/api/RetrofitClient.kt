package com.simats.gym_fitzone.api

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object RetrofitClient {

    // This URL will be automatically updated by `update_ip.py` (called from START_BACKEND.bat)
    // to your current Wi-Fi network address, ensuring seamless connection across different devices.
    private const val STATIC_TUNNEL_URL = "http://10.196.130.112:5000/"
    private var _activeBaseUrl = STATIC_TUNNEL_URL
    
    val currentUrl: String get() = _activeBaseUrl

    var onUrlUpdated: ((String) -> Unit)? = null

    private var _apiService: ApiService? = null

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Bypass-Tunnel-Reminder", "true")
                .addHeader("X-App-Name", "GymFitZone")
                .build()
            chain.proceed(request)
        }
        .build()

    val apiService: ApiService
        get() = synchronized(this) {
            if (_apiService == null) {
                _apiService = createApiService(_activeBaseUrl)
            }
            _apiService!!
        }

    /**
     * Called by ServiceDiscoveryManager.
     * We VERIFY the URL first before switching.
     */
    fun updateBaseUrl(newUrl: String) {
        val formattedUrl = if (newUrl.endsWith("/")) newUrl else "$newUrl/"
        if (_activeBaseUrl == formattedUrl) return

        Log.d("RetrofitClient", "Handshaking with newly discovered server: $formattedUrl")
        
        // Use a coroutine to test the connection without blocking
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val tempService = createApiService(formattedUrl)
                val response = tempService.ping()
                
                // If the app name matches, it's our server!
                if (response["app"] == "gym-fitzone") {
                    Log.d("RetrofitClient", "HANDSHAKE SUCCESS! Switching to $formattedUrl")
                    synchronized(this@RetrofitClient) {
                        _activeBaseUrl = formattedUrl
                        _apiService = createApiService(formattedUrl)
                    }
                    withContext(Dispatchers.Main) {
                        onUrlUpdated?.invoke(formattedUrl)
                    }
                } else {
                    Log.w("RetrofitClient", "Handshake failed: Unknown app at $formattedUrl")
                }
            } catch (e: Exception) {
                Log.e("RetrofitClient", "Handshake failed for $formattedUrl: ${e.message}")
            }
        }
    }

    private fun createApiService(url: String): ApiService {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
