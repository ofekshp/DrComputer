package com.example.drcomputer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

// TODO: Finish build profile fragment !!
class myProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_profile, container, false)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        val userNameText: TextView = view.findViewById(R.id.userNameProfile)
        val emailText: TextView = view.findViewById(R.id.emailProfile)
        userNameText.text= currentUser.email
        emailText.text= currentUser.email
        val editProfileBtn=view.findViewById<Button>(R.id.btn_edit)
        editProfileBtn.setOnClickListener{
            findNavController().navigate(R.id.action_myProfileFragment_to_editProfile)
        }
//        profileViewModel.getUserByUid(uid){ userEntity ->
//            if(userEntity!=null){
//                Glide.with(requireContext())
//                    .load(userEntity.profileImg)
//                    .into(imageView)
//
//                NameTextView.text= userEntity.name
//                EmailTextView.text= userEntity.email
//            }
//        }
        return view
    }

}