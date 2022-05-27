package com.example.sunshine.ui.detailsScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.sunshine.R
import com.example.sunshine.adapters.HourlyDataAdapter
import com.example.sunshine.databinding.FragmentJumbotronDetailsBinding
import com.example.sunshine.models.Forecast
import com.example.sunshine.utils.DateUtil
import com.example.sunshine.viewModels.DetailsViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JumbotronDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class JumbotronDetails : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentJumbotronDetailsBinding
    private lateinit var viewModel:DetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_jumbotron_details,container,false)
        val parentContext = parentFragment?.context
        viewModel=ViewModelProviders.of(requireParentFragment()).get(DetailsViewModel::class.java)
        setObservers()
        return binding.root
    }

    fun setObservers(){
        viewModel.data.observe(viewLifecycleOwner, Observer {
           if(it!=null)
            setUI(it)
        })
    }

    fun setUI(data:Forecast.Daily){
        binding.apply {
            detailsDate.text=DateUtil.getShortLocaleDate(data.dt)
            val url=HourlyDataAdapter.getUrl(data.weather[0].icon)
            Glide.with(requireContext())
                .load(url)
                .dontAnimate()
                .into(detailsDescIcon)
            detailsDesc.text=data.weather[0].description
            detailsMaxTemp.text=getString(R.string.standard_temperature,data.temp.max.toInt().toString())
            detailsMinTemp.text=getString(R.string.standard_temperature,data.temp.min.toInt().toString())
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JumbotronDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JumbotronDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}