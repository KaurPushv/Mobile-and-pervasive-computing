package com.example.foodcorner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodcorner.Models.Reel
import com.example.foodcorner.adapters.MyReelAdapter
import com.example.foodcorner.databinding.FragmentMyVideosBinding
import com.example.foodcorner.utils.REEL
import com.example.foodcorner.utils.REELS_FOLDER
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyReelsFragment : Fragment() {
    private lateinit var binding: FragmentMyVideosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyVideosBinding.inflate(inflater, container, false)

        var reelList = ArrayList<Reel>()
        var adapter = MyReelAdapter(requireContext(), reelList)
        binding.rv.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter

        // Fetch videos from the "Reel" folder in Firestore
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL)
            .get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                reelList.clear()
                for (document in querySnapshot.documents) {
                    reelList.add(document.toObject(Reel::class.java)!!)
                }
                adapter.notifyDataSetChanged()
            }

        return binding.root
    }

    companion object {
    }
}