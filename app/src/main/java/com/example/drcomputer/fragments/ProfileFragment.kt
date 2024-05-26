package com.example.drcomputer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.drcomputer.R
import com.example.drcomputer.activities.AuthActivity
import com.example.drcomputer.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

// TODO: Finish build profile fragment !!
class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var profileViewModel : ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_profile, container, false)
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        val userNameText: TextView = view.findViewById(R.id.userNameProfile)
        val emailText: TextView = view.findViewById(R.id.emailProfile)
        val btnLogOut: Button = view.findViewById(R.id.btn_logout)

        profileViewModel.getUserByUid(uid){ userEntity ->
            if(userEntity!=null){
                userNameText.text= userEntity.userName
                emailText.text= userEntity.email
            }
        }
        val editProfileBtn=view.findViewById<Button>(R.id.btn_edit)
        editProfileBtn.setOnClickListener{
            findNavController().navigate(R.id.action_myProfileFragment_to_editProfile)
        }
        val myPostsBtn=view.findViewById<Button>(R.id.btn_myPost)
        myPostsBtn.setOnClickListener{
            findNavController().navigate(R.id.action_myProfileFragment_to_myPosts)
        }
        btnLogOut.setOnClickListener{
            auth.signOut()
            val submitActivityIntent = Intent(context, AuthActivity::class.java)
            startActivity(submitActivityIntent)
        }
        return view
    }

}