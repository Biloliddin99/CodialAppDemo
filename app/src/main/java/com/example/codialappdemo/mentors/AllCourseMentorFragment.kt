package com.example.codialappdemo.mentors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.adapters.CourseAdapter
import com.example.codialappdemo.adapters.MentorAdapter
import com.example.codialappdemo.adapters.RvCourseClick
import com.example.codialappdemo.adapters.RvMentorClick
import com.example.codialappdemo.databinding.FragmentAllCourseMentorBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Courses
import com.example.codialappdemo.models.Mentors


class AllCourseMentorFragment : Fragment(), RvCourseClick {

    private lateinit var courseAdapter: CourseAdapter
    private lateinit var myDbHelper: MyDbHelper
    private val binding by lazy { FragmentAllCourseMentorBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        myDbHelper = MyDbHelper(binding.root.context)
        courseAdapter = CourseAdapter(myDbHelper.getAllCourses(), this)
        binding.myRv.adapter = courseAdapter
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }


        return binding.root
    }

    override fun itemClick(courses: Courses, position: Int) {
        findNavController().navigate(R.id.allMentorsFragment, bundleOf("key" to courses,"gfgf" to courses))
    }

}