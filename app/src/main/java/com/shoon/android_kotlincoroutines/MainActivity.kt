package com.shoon.android_kotlincoroutines

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
 //   private lateinit var viewAdapter: VideosListAdapter
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

     //   viewAdapter = VideosListAdapter()

 //       characters_list.apply {
    //        setHasFixedSize(true)
        //    layoutManager = LinearLayoutManager(applicationContext)
        //    adapter = viewAdapter
 //       }
    }
}
