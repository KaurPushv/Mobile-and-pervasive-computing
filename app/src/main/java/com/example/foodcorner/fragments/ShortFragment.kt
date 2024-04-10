package com.example.foodcorner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodcorner.Models.Reel
import com.example.foodcorner.R
import com.example.foodcorner.adapters.ReelAdapter
import com.example.foodcorner.databinding.FragmentShortBinding
import com.example.foodcorner.utils.REEL
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class ShortFragment : Fragment() {
     private lateinit var binding: FragmentShortBinding
     lateinit var adapter: ReelAdapter
     var reelList= ArrayList<Reel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentShortBinding.inflate(inflater, container, false)
        adapter = ReelAdapter(requireContext(),reelList)
        binding.viewPager.adapter=adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener() {
            var tempList = ArrayList<Reel>()
            reelList.clear()

            for (i in it.documents){
                var reel =i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }

        return binding.root

    }

    companion object {}
}