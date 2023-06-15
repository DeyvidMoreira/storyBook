package com.ghostshadow.storybook.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ghostshadow.storybook.R
import com.ghostshadow.storybook.service.model.StoryModel

class StoryAdapter(private val dataSet: ArrayList<StoryModel>):
    RecyclerView.Adapter<StoryAdapter.ViewHolder> () {

    private var onClickListener: OnClickListener? = null


    class ViewHolder(v:View): RecyclerView.ViewHolder(v){
        val tvTitle:TextView = v.findViewById(R.id.tvItemListTitle)
        val image: ImageView = v.findViewById(R.id.itemListImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.setText(dataSet[position].title)
        holder.image.setImageResource(dataSet[position].image)
        holder.itemView.setOnClickListener {
            if (onClickListener != null){
                onClickListener!!.onClick(position)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int)
    }

}