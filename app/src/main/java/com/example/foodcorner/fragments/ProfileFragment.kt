package com.example.foodcorner.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodcorner.Models.User
import com.example.foodcorner.R
import com.example.foodcorner.SignupActivity
import com.example.foodcorner.adapters.ViewPagerAdapter
import com.example.foodcorner.databinding.FragmentProfileBinding
import com.example.foodcorner.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater, container, false)

        binding.editProfile.setOnClickListener{
            val intent=Intent(activity,SignupActivity::class.java)
            intent.putExtra("MODE", 1)
            intent.putExtra("email", binding.bio.text.toString()) // Pass the current email
            intent.putExtra("name", binding.name.text.toString()) // Pass the current name
            activity?.startActivity(intent)
            activity?.finish()
        }
        viewPagerAdapter= ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragments(MyPostFragment(), "My Post")
        viewPagerAdapter.addFragments(MyReelsFragment(), "My Videos")
        binding.viewPager.adapter=viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    companion object {

    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User=it.toObject<User>()!!
                binding.name.text=user.name
                binding.bio.text=user.email
                if (!user.image.isNullOrEmpty()){
                    Picasso.get().load(user.image).into(binding.profileImage)
                }
        }
    }
}