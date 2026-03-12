package com.simats.gym_fitzone.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.util.Log
import java.net.Inet4Address
import java.net.NetworkInterface

object NetworkUtils {

    fun getLocalIpAddress(): String {
        try {
            // Method 1: Try WiFi manager first (most reliable)
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (networkInterface in interfaces) {
                val addresses = networkInterface.inetAddresses
                for (address in addresses) {
                    if (!address.isLoopbackAddress && address is Inet4Address) {
                        val hostAddress = address.hostAddress
                        if (hostAddress != null && !hostAddress.startsWith("127.") && !hostAddress.startsWith("169.254")) {
                            Log.d("NetworkUtils", "Found IP: $hostAddress")
                            return hostAddress
                        }
                    }
                }
            }
            
            // Method 2: Fallback to common local IPs
            val commonIPs = listOf("192.168.1.1", "192.168.0.1", "10.0.0.1", "172.16.0.1")
            for (ip in commonIPs) {
                if (isReachable(ip)) {
                    Log.d("NetworkUtils", "Using fallback IP: $ip")
                    return ip
                }
            }
            
        } catch (e: Exception) {
            Log.e("NetworkUtils", "Error getting IP: ${e.message}")
        }
        
        // Final fallback
        Log.d("NetworkUtils", "Using default fallback IP")
        return "192.168.1.1"
    }
    
    private fun isReachable(ip: String): Boolean {
        return try {
            val address = java.net.InetAddress.getByName(ip)
            address.isReachable(1000)
        } catch (e: Exception) {
            false
        }
    }
    
    fun getBaseUrl(): String {
        val ip = getLocalIpAddress()
        return "http://$ip:5000/"
    }
}