package com.example.foodcorner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcorner.Models.Reel
import com.example.foodcorner.R
import com.example.foodcorner.databinding.FragmentShortBinding
import com.example.foodcorner.databinding.ShortDgBinding
import com.squareup.picasso.Picasso

class ReelAdapter (var context: Context, var reelList: ArrayList<Reel>):RecyclerView.Adapter<ReelAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ShortDgBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ShortDgBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList.get(position).profileLink).placeholder(R.drawable.account).into(holder.binding.profileImage)
        holder.binding.caption.setText(reelList.get(position).ingredients)
        holder.binding.videoView.setVideoPath(reelList.get(position).reelUrl)
        holder.binding.videoView.setOnPreparedListener{
            holder.binding.progressBar.visibility= View.GONE
            holder.binding.videoView.start()
        }
    }
}