package com.example.sunshine.ui.searchScreen

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunshine.R
import com.example.sunshine.adapters.SearchDataAdapter
import com.example.sunshine.databinding.FragmentSearchBinding
import com.example.sunshine.models.Forecast
import com.example.sunshine.models.SearchResult
import com.example.sunshine.viewModels.SearchViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment(),SearchDataAdapter.SearchItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
   private lateinit var binding:FragmentSearchBinding
   private lateinit var viewModel:SearchViewModel
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
        Log.i("from search","thread:- ${Thread.currentThread()}")
        binding= DataBindingUtil.inflate(layoutInflater,R.layout.fragment_search,container,false)
        viewModel=ViewModelProvider(this).get(SearchViewModel::class.java)
        Log.i("from search frag",binding.toString())
        setInputObserver()
        setRecyclerView()
        setOtherObservers()
        setInputFocus()
        return binding.root
    }

    private fun setInputFocus() {
      binding.apply {
          if(searchInput.requestFocus()){
              val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
              imm.showSoftInput(searchInput as View,InputMethodManager.SHOW_IMPLICIT)
          }
      }
    }

    private fun setOtherObservers() {
       viewModel.loading.observe(viewLifecycleOwner, Observer {
           if(it){
               binding.apply {
                   mainContainer.visibility=View.GONE
                   loaderContainer.visibility=View.VISIBLE
               }
           }
           else{
               binding.mainContainer.visibility=View.VISIBLE
           }
       })

        binding.searchScreenToolbar.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                findNavController().popBackStack()
            }
        })
    }

    private fun setInputObserver() {
        binding.searchInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val str = p0.toString()
                viewModel.searchQuery.value=str
                if(str.isEmpty()){
                    binding.apply {
                        searchScreenPlaceholder.visibility=View.VISIBLE
                        searchResultRecyclerView.visibility=View.GONE
                    }
                }
                else{
                    binding.apply {
                        searchScreenPlaceholder.visibility=View.GONE
                        searchResultRecyclerView.visibility=View.VISIBLE
                    }
                }
            }
        })

        viewModel.searchQuery.observe(viewLifecycleOwner, Observer {
            viewModel.fetchSearchResults()
        })
    }

    private fun setRecyclerView(){
        val layoutManager=LinearLayoutManager(requireContext())
        binding.searchResultRecyclerView.layoutManager=layoutManager
        val adapter:SearchDataAdapter= SearchDataAdapter(viewModel.results.value,requireContext(),this)
        binding.searchResultRecyclerView.adapter=adapter
        binding.searchResultRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(),layoutManager.orientation)
        )
        viewModel.results.observe(viewLifecycleOwner, Observer {
           binding.searchResultRecyclerView.apply {
               adapter.list=it
               adapter.notifyDataSetChanged()
           }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ab = (requireActivity() as AppCompatActivity).supportActionBar
        Log.i("from search frag","ab: - ${ab}")
        super.onViewCreated(view, savedInstanceState)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSearchItemClicked(position: Int, item: SearchResult.Feature) {
        if(item!=null){
            val lat = item.center[1]
            val lon=item.center[0]
            hideKeyboard()
            viewModel.fetchLocationForecast(lat.toString(),lon.toString(),findNavController(),item)
        }
    }

    fun hideKeyboard(){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

}