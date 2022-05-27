package com.example.sunshine.adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sunshine.R

import com.example.sunshine.databinding.SearchResultRowBinding

import com.example.sunshine.models.SearchResult


class SearchDataAdapter(var list: List<SearchResult.Feature>?,private val context:Context,val listener:SearchItemClickListener):RecyclerView.Adapter<SearchDataAdapter.ViewHolder>() {

    class ViewHolder(val binding: SearchResultRowBinding):RecyclerView.ViewHolder(binding.root)

    interface SearchItemClickListener{
        fun onSearchItemClicked(position: Int,item:SearchResult.Feature)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: SearchResult.Feature=list!![position]
        holder.binding.apply{
          resultP1.text=data.text
            resultP2.text=data.place_name
            root.setOnClickListener {
                listener.onSearchItemClicked(position,data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SearchResultRowBinding= DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.search_result_row,parent,false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }
}