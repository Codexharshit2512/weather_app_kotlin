package com.example.sunshine.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunshine.R
import com.example.sunshine.databinding.HourlyDataColumnBinding
import com.example.sunshine.models.Forecast
import com.example.sunshine.network.ApiClient
import java.text.SimpleDateFormat
import java.util.*

class HourlyDataAdapter(var list:List<Forecast.Hourly>?,var context: Context,var locTimeZone:String?):RecyclerView.Adapter<HourlyDataAdapter.ViewHolder>() {

    inner class ViewHolder(val binding:HourlyDataColumnBinding):RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: HourlyDataAdapter.ViewHolder, position: Int) {
        val data=list!![position]
        holder.binding.apply {
            hourlyDataTime.text=getTime(data.dt)
            val temp=data.temp.toInt()
            hourlyDataTemp.text=context.getString(R.string.standard_temperature,temp.toString())
            val url=getUrl(data.weather[0].icon)
            hourlyDataWspeed.text=context.getString(R.string.standard_wind_speed,data.wind_speed.toInt().toString())
            Glide.with(context)
                .load(url)
                .dontAnimate()
                .into(hourlyDataIcon)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:HourlyDataColumnBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.hourly_data_column
            ,parent,false
            )
        return ViewHolder(binding)
    }

    private fun getTime(unix:Long):String{
        val date=Date(unix*1000L)
        val sdf=SimpleDateFormat("h:mm")
        sdf.timeZone= TimeZone.getTimeZone(locTimeZone)
        return sdf.format(date)
    }

    fun updateList(newList:List<Forecast.Hourly>?){
        this.list=newList
        notifyDataSetChanged()
    }

    fun setTimeZone(tz:String){
        locTimeZone=tz
    }

    companion object{
        fun getUrl(icon:String):String{
            val base= ApiClient.getIconUrl()
            val url = "$base$icon.png"
            return url
        }
    }


}