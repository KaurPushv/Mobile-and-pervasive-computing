package com.example.foodcorner.Post

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.foodcorner.HomeActivity
import com.example.foodcorner.Models.Post
import com.example.foodcorner.Models.User
import com.example.foodcorner.databinding.ActivityPostBinding
import com.example.foodcorner.utils.POST
import com.example.foodcorner.utils.POST_FOLDER
import com.example.foodcorner.utils.USER_NODE
import com.example.foodcorner.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    private var imageUrl: String? = null

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) { url ->
                url?.let {
                    binding.selectImage.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                Firebase.firestore.collection(USER_NODE).document(currentUser.uid)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val user = documentSnapshot.toObject<User>()
                        user?.let {
                            if (imageUrl != null) {
                                val post = Post(
                                    postUrl = imageUrl!!,
                                    ingredients = binding.ingredients.editText?.text.toString(),
                                    uid = currentUser.uid,
                                    time = System.currentTimeMillis().toString()
                                )

                                Firebase.firestore.collection(POST).add(post)
                                    .addOnSuccessListener {
                                        startActivity(
                                            Intent(
                                                this@PostActivity,
                                                HomeActivity::class.java
                                            )
                                        )
                                        finish()
                                    }
                                    .addOnFailureListener { exception ->
                                        // Handle failure
                                    }
                            } else {
                                // Handle case when imageUrl is null
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Handle failure
                    }
            } else {
                // Handle case when currentUser is null
            }
        }
    }
}