package com.example.sunshine.ui.mainScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunshine.R
import com.example.sunshine.adapters.DailyDataAdapter
import com.example.sunshine.databinding.FragmentDailyDataBinding
import com.example.sunshine.models.Forecast
import com.example.sunshine.viewModels.MainViewModal

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DailyData.newInstance] factory method to
 * create an instance of this fragment.
 */
class DailyData : Fragment(),DailyDataAdapter.DailyItemClickListener {
    // TODO: Rename and change types of parameters
    private val TAG = "Daily Data fargment"
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel:MainViewModal
    private lateinit var binding:FragmentDailyDataBinding
    private lateinit var dataAdapter:DailyDataAdapter
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
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_daily_data,container,false)
        viewModel=ViewModelProvider(requireActivity()).get(MainViewModal::class.java)
        dataAdapter= DailyDataAdapter(viewModel.dailyForecast.value,requireContext(),this)
        binding.apply {
         dailyDataRecylerView.layoutManager=LinearLayoutManager(requireContext())
            dailyDataRecylerView.adapter=dataAdapter
        }
        setObservers()
        return binding.root
    }
    private fun setObservers() {
       viewModel.dailyForecast.observe(viewLifecycleOwner, Observer {
           Log.i(TAG,"data changed ${it}")
           dataAdapter.apply {
               list?.clear()
               list?.addAll(it)
               notifyDataSetChanged()
           }
       })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DailyData.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DailyData().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDailyItemClicked(position: Int, item: Forecast.Daily) {
        val bundle=Bundle()
        bundle.putSerializable("data",item)
        findNavController().navigate(R.id.action_mainFragment_to_detailScreenFragment,bundle)
    }
}