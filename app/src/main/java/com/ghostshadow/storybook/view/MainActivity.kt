package com.ghostshadow.storybook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ghostshadow.storybook.R
import com.ghostshadow.storybook.service.constants.Constants
import com.ghostshadow.storybook.service.model.StoryModel
import com.ghostshadow.storybook.view.adapter.StoryAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Constants.getStoryList()

        setAdapterRecyclerView(data)


    }

    private fun setAdapterRecyclerView(data: ArrayList<StoryModel>){
        val recyclerView = findViewById<RecyclerView>(R.id.rvStoryList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = StoryAdapter(data)
        recyclerView.adapter = adapter

        adapter.setOnClickListener(object : StoryAdapter.OnClickListener{
            override fun onClick(position: Int) {
                val intent = Intent(this@MainActivity, StoryActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)
            }

        })

    }

}