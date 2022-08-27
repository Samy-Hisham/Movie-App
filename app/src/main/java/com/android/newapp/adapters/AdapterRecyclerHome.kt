package com.android.newapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.newapp.data.models.Result
import com.android.newapp.databinding.ItemMovieBinding
import com.android.newapp.helpers.Const
import com.bumptech.glide.Glide

class AdapterRecyclerHome (val onClicks : (Int?) -> Unit): RecyclerView.Adapter<AdapterRecyclerHome.Holder>() {

    var list : ArrayList<Result> ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = list?.get(position)
        holder.binding.apply {
            model?.apply {

                textTitle.text = original_title
                Glide.with(holder.itemView.context)
                    .load(Const.BASE_IMAGE+poster_path)
                    .into(imageMovie)
            }
        }
    }

    override fun getItemCount(): Int = if (list == null) 0 else list!!.size


    inner class Holder (val binding : ItemMovieBinding): RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener {
                onClicks.invoke(list?.get(layoutPosition)?.id)
            }
        }
    }
}