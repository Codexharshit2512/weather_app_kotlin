package com.example.sunshine.utils
import java.text.SimpleDateFormat
import java.util.*
object DateUtil {
    fun getDay(unix: Long):String{
        val pattern= "EEEE"
        val date= Date(unix*1000L)
        val sdf= SimpleDateFormat(pattern)
        return sdf.format(date)
    }

    fun getLocaleDate(unix:Long):String{
        val pattern= "EE,d MMMM HH:mm"
        val date= Date(unix*1000L)
        val sdf= SimpleDateFormat(pattern)
        return sdf.format(date)
    }

    fun getShortLocaleDate(unix:Long):String{
        val pattern= "EEEE, MMMM d"
        val date= Date(unix*1000L)
        val sdf= SimpleDateFormat(pattern)
        return sdf.format(date)
    }

}