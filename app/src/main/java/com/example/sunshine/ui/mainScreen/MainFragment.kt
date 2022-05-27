package com.example.sunshine.ui.mainScreen

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.sunshine.R
import com.example.sunshine.adapters.CurrentDataAdapter
import com.example.sunshine.adapters.HourlyDataAdapter
import com.example.sunshine.databinding.FragmentMainBinding
import com.example.sunshine.models.Forecast
import com.example.sunshine.utils.DateUtil
import com.example.sunshine.viewModels.MainViewModal

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(),CurrentDataAdapter.ModelInstance {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModal:MainViewModal
    private lateinit var adapter:CurrentDataAdapter
    private lateinit var binding:FragmentMainBinding
    private lateinit var hourlyDataAdapter: HourlyDataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Log.i("from main fraggg","created")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i("from main fraggg","initialized")

        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_main,null,false)
        viewModal=ViewModelProvider(requireActivity()).get(MainViewModal::class.java)
        binding.addLocationIcon.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
        hourlyDataAdapter= HourlyDataAdapter(listOf(),requireContext(),"")
        setOberservers(layoutInflater)
        setup()
        setCurrentAdapter()
//        setAdapter()
        return binding.root
    }

    private fun setOberservers(layoutInflater: LayoutInflater) {
//        viewModal.hourlyForecast.observe(viewLifecycleOwner, Observer {
//            adapter.list?.clear()
//            adapter.list?.addAll(it)
//            adapter.notifyDataSetChanged()
//        })
        binding.currentDataViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.i("main","scrolled")
                viewModal.setIndex(position)
            }
        })
        viewModal.forecast.observe(viewLifecycleOwner, Observer {
            Log.i("from main","size:--- ${viewModal.dailyForecast.value!!.size}")
            if(!viewModal.loading.value!!){
               adapter.list=it
                adapter.notifyDataSetChanged()
                if(arguments!=null && arguments?.containsKey("forecast")!!) {
                    binding.currentDataViewPager.setCurrentItem(it.size - 1, false)
                    arguments?.remove("forecast")
                }
                togglePlaceholder()
                for(i in it.indices){
                    val cv=addIndicator()
                }
            }
        })

        viewModal.currentIndex.observe(viewLifecycleOwner, Observer {
            Log.i("from main frag","currentindex: $it")
            if(it>=0){hourlyDataAdapter.list=viewModal.forecast.value!![it].hourly
            hourlyDataAdapter.notifyDataSetChanged()
            for(i in viewModal.forecast.value!!.indices){
                val cv=binding.pagerIndicatorContainer.getChildAt(i)
                if(i==it)setActive(cv)
                else setInActive(cv)
            }}
        })
    }

    private fun setCurrentAdapter(){
        adapter = CurrentDataAdapter(viewModal.forecast.value!!,requireContext(),hourlyDataAdapter)
        binding.currentDataViewPager.adapter=adapter
    }


    private fun setup(){
        if(arguments!=null && arguments?.containsKey("forecast")!!){
            Log.i("from main frag","setup called")
            val forecast = arguments?.getSerializable("forecast") as Forecast
            viewModal.addForecast(forecast)
        }
    }

    private fun togglePlaceholder(){
        binding.apply {
            if(viewModal.forecast.value!!.size>0){
                addLocationPlaceholder.visibility=View.GONE
                mainContainer.visibility=View.VISIBLE
            }
        }
    }

    private fun addIndicator():View{
        val childView= LayoutInflater.from(context).inflate(R.layout.pager_indicator,null,false)
        Log.i("from main frag","indic 2:-- ${childView.height}")
        binding.pagerIndicatorContainer.addView(childView)
        return childView
    }

    private fun setActive(childView:View){
        val iv=childView.findViewById<ImageView>(R.id.indicator_img)
        iv.setColorFilter(R.color.active_pager_color,PorterDuff.Mode.SRC_ATOP)
    }

    private fun setInActive(childView:View){
        Log.i("from main frag","inactive getting called")
        val iv=childView.findViewById<ImageView>(R.id.indicator_img)
        iv.setColorFilter(Color.WHITE,PorterDuff.Mode.SRC_ATOP)
    }

    override fun onStop() {
        super.onStop()
        Log.i("from main frag","fragment stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("from main frag","fragment destroyed")
    }


    override fun getViewModelInstance(): MainViewModal {
        Log.i("from main frag","interface: ${viewModal.dailyForecast.value}")
        return viewModal
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}