package com.example.sunshine.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunshine.R
import com.example.sunshine.databinding.ViewpagerItemBinding
import com.example.sunshine.models.Forecast
import com.example.sunshine.ui.mainScreen.MainFragment
import com.example.sunshine.utils.DateUtil
import com.example.sunshine.viewModels.MainViewModal

class CurrentDataAdapter(var list: MutableList<Forecast>,val context: Context,val hourlyDataAdapter: HourlyDataAdapter):RecyclerView.Adapter<CurrentDataAdapter.ViewHolder>() {

    interface ModelInstance{
       fun getViewModelInstance():MainViewModal
    }

    inner class ViewHolder(val binding:ViewpagerItemBinding,var adapter: HourlyDataAdapter?):RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ViewpagerItemBinding>(LayoutInflater.from(context),R.layout.viewpager_item,parent,false)
        val adapter:HourlyDataAdapter=hourlyDataAdapter
        return ViewHolder(binding,adapter)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Log.i("from curr data adapter","adapter pos:- ${position}")
        Log.i("from curr data adapter","list size: ${list.size}")
        val data=list[position]
        setAdapter(holder,data)
        setData(data,holder.binding)
    }

    private fun setData(curr:Forecast,binding:ViewpagerItemBinding){
        binding.apply {
             currentLocationName.text=curr.place_name.toString()
                curr.current.apply {
                    currentLocationDate.text= DateUtil.getLocaleDate(dt)
                    setImage(weather[0].icon,binding)
                    currentLocationTemp.text=context.getString(R.string.standard_temperature,temp.toInt().toString())
//                    val viewModal=provider.getViewModelInstance()
                    val feelsLike=context.getString(R.string.current_weather_short_info,
                        weather[0].description,
                        curr.daily[0].temp.max.toInt(),
                        curr.daily[0].temp.min.toInt(),
                        feels_like.toInt()
                    )
//                    viewModal.dailyForecast.value!![0].temp.max.toInt(),
//                    viewModal.dailyForecast.value!![0].temp.min.toInt(),
                    currentLocationFeelsLike.text=feelsLike
                }

        }
    }

    private fun setImage(icon:String,binding:ViewpagerItemBinding){
        val url=HourlyDataAdapter.getUrl(icon)
        Glide.with(context)
            .load(url)
            .dontAnimate()
            .into(binding.currentLocationIcon)
    }

    private fun setAdapter(holder:ViewHolder,data:Forecast){
//        holder.adapter = HourlyDataAdapter(data.hourly,context,"")
        holder.binding.apply {
            hourlyDataRecyclerview.layoutManager=
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            hourlyDataRecyclerview.adapter=holder.adapter
        }
    }


}