package com.example.foodcorner.fragments

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodcorner.Models.User
import com.example.foodcorner.R
import com.example.foodcorner.adapters.SearchAdapter
import com.example.foodcorner.databinding.FragmentSearchBinding
import com.example.foodcorner.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: SearchAdapter
    var userList= ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rv.layoutManager=LinearLayoutManager(requireContext())
        adapter= SearchAdapter(requireContext(),userList)
        binding.rv.adapter=adapter

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener{
            var tempList=ArrayList<User>()
            userList.clear()
            for (i in it.documents){
                if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                }else {
                    var user: User = i.toObject<User>()!!

                    tempList.add(user)
                }
            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        binding.searchButton.setOnClickListener() {
            var text = binding.searchView.text.toString()

            Firebase.firestore.collection(USER_NODE).whereEqualTo("name", text).get().addOnSuccessListener{

                    var tempList=ArrayList<User>()
                    userList.clear()
                    if(it.isEmpty){

                    }else{
                        for (i in it.documents){
                            if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                            }else {
                                var user: User = i.toObject<User>()!!

                                tempList.add(user)
                            }
                        }
                        userList.addAll(tempList)
                        adapter.notifyDataSetChanged()
                    }


            }

        }

        return binding.root
    }

    companion object {
    }
}