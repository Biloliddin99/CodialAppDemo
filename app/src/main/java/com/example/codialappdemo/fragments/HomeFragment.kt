package com.example.codialappdemo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.apply {
            ramCourse.setOnClickListener { findNavController().navigate(R.id.allCoursesFragment) }
            ramGroup.setOnClickListener { findNavController().navigate(R.id.allGroupsFragment) }
            ramMentor.setOnClickListener { findNavController().navigate(R.id.allCourseMentorFragment) }
        }

        return binding.root
    }


}