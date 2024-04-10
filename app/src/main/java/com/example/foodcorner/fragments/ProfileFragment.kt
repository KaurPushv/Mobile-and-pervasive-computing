package com.example.foodcorner.fragments

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodcorner.LoginActivity
import com.example.foodcorner.Models.User
import com.example.foodcorner.SignupActivity
import com.example.foodcorner.adapters.ViewPagerAdapter
import com.example.foodcorner.databinding.FragmentProfileBinding
import com.example.foodcorner.utils.USER_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Setting click listeners
        binding.editProfile.setOnClickListener {
            val intent = Intent(activity, SignupActivity::class.java)
            intent.putExtra("MODE", 1)
            intent.putExtra("email", binding.bio.text.toString())
            intent.putExtra("name", binding.name.text.toString())
            activity?.startActivity(intent)
            activity?.finish()
        }

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            // After signing out, navigate back to the login page
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragments(MyPostFragment(), "My Post")
        viewPagerAdapter.addFragments(MyReelsFragment(), "My Videos")
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Load user data
        FirebaseFirestore.getInstance().collection(USER_NODE).document(FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnSuccessListener {
                val user = it.toObject<User>()
                if (user != null) {
                    binding.name.text = user.name
                    binding.bio.text = user.email
                    if (!user.image.isNullOrEmpty()) {
                        Picasso.get().load(user.image).into(binding.profileImage)
                    }
                }
            }
    }
}
