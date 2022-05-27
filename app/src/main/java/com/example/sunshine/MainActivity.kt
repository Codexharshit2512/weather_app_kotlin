package com.example.sunshine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.sunshine.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("from main","thread:- ${Thread.currentThread()}")
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(binding.root)
//        val bar = supportActionBar
//        Log.i("from main activity","${binding.root}")
//        supportActionBar?.hide()
//        setSupportActionBar(binding.searchToolbar)
    }


//    override fun onResume() {
//        super.onResume()
//        val bar = supportActionBar
//        Log.i("from main activity","$supportActionBar")
//    }
}