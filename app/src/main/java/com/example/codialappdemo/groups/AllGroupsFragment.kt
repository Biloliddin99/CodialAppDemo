package com.example.codialappdemo.groups


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.R
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.adapters.CourseAdapter
import com.example.codialappdemo.adapters.GroupAdapter
import com.example.codialappdemo.adapters.RvCourseClick
import com.example.codialappdemo.adapters.RvGroupClick
import com.example.codialappdemo.databinding.FragmentAllGroupsBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Courses
import com.example.codialappdemo.models.Groups


class AllGroupsFragment : Fragment(), RvCourseClick {

    private lateinit var courseAdapter: CourseAdapter
    private lateinit var myDbHelper: MyDbHelper
    private val binding by lazy { FragmentAllGroupsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        myDbHelper = MyDbHelper(binding.root.context)
        courseAdapter = CourseAdapter(myDbHelper.getAllCourses(),this)
        binding.itemRv.adapter = courseAdapter

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun itemClick(courses: Courses, position: Int) {
        findNavController().navigate(com.example.codialappdemo.R.id.groupFragment, bundleOf("key" to courses))
    }


}