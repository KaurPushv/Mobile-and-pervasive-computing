package com.example.foodcorner.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodcorner.Models.Post
import com.example.foodcorner.Models.User
import com.example.foodcorner.R
import com.example.foodcorner.databinding.PostRvBinding
import com.example.foodcorner.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class PostAdapter(var context: Context, var postList: ArrayList<Post>) :
    RecyclerView.Adapter<PostAdapter.MyHolder>() {

    inner class MyHolder(var binding: PostRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = PostRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList[position].uid)
                .get()
                .addOnSuccessListener { document ->
                    val user = document.toObject<User>()
                    if (user != null) {
                        Glide.with(context).load(user.image).into(holder.binding.profileImage)
                        holder.binding.name.text = user.name
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle failure
                }

        }catch (e:Exception){

        }

        // Load post image
        Glide.with(context).load(postList[position].postUrl)
            .placeholder(R.drawable.loading)
            .into(holder.binding.postImage)
        try {
            val text = TimeAgo.using(postList[position].time.toLong())
            holder.binding.time.text = text
        }catch (e:Exception){
            holder.binding.time.text = ""
        }

        holder.binding.share.setOnClickListener {
            var i= Intent(android.content.Intent.ACTION_SEND)
            i.type="text/plain"
            i.putExtra(Intent.EXTRA_TEXT,postList[position].postUrl)
            context.startActivity(i)
        }
        holder.binding.caption.text = postList[position].ingredients

        // Set like button image based on post's like status
        if (postList[position].isLiked) {
            holder.binding.like.setImageResource(R.drawable.heart_filled)
        } else {
            holder.binding.like.setImageResource(R.drawable.heart)
        }

        // Toggle like status and update UI on like button click
        holder.binding.like.setOnClickListener {
            postList[position].isLiked = !postList[position].isLiked
            if (postList[position].isLiked) {
                holder.binding.like.setImageResource(R.drawable.heart_filled)
            } else {
                holder.binding.like.setImageResource(R.drawable.heart)
            }
        }
    }
}
