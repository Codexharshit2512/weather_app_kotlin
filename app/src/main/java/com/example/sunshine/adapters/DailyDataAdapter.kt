package com.example.sunshine.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunshine.R
import com.example.sunshine.databinding.DailyDataRowBinding
import com.example.sunshine.models.Forecast
import com.example.sunshine.models.SearchResult
import com.example.sunshine.network.ApiClient
import com.example.sunshine.utils.DateUtil
import java.util.Date

class DailyDataAdapter(var list:MutableList<Forecast.Daily>?,var context: Context,val listener:DailyItemClickListener):RecyclerView.Adapter<DailyDataAdapter.ViewHolder>() {


    interface DailyItemClickListener{
        fun onDailyItemClicked(position: Int,item: Forecast.Daily)
    }

    class ViewHolder(val binding:DailyDataRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data:Forecast.Daily=list!![position]
        holder.binding.apply{
            val date=  DateUtil.getDay(data.dt)
            dailyDataDay.text= date
            val mat:String="${data.temp.max.toInt()}"
            val mit:String = "${data.temp.min.toInt()}"
            val maxTemp=context.getString(R.string.without_sign_temp,mat)
            val minTemp=context.getString(R.string.without_sign_temp,mit)
            dailyDataTemp.text= "$maxTemp/$minTemp"
            dailyDataWspeed.text=context.getString(R.string.standard_wind_speed,data.wind_speed.toInt().toString())
            val url =getUrl(data.weather[0].icon)
            Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .dontAnimate()
                .into(dailyDataIcon)
        }
        holder.binding.root.setOnClickListener {
            listener.onDailyItemClicked(position,data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:DailyDataRowBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),
           R.layout.daily_data_row,parent,false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    fun getUrl(icon:String):String{
        val base=ApiClient.getIconUrl()
        val url = "$base$icon.png"
        return url
    }

}