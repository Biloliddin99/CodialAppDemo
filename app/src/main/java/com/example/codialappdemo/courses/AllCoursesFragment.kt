package com.example.codialappdemo.courses

import android.app.AlertDialog
import android.content.DialogInterface.BUTTON_POSITIVE
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.adapters.CourseAdapter
import com.example.codialappdemo.adapters.RvCourseClick
import com.example.codialappdemo.databinding.FragmentAllCoursesBinding
import com.example.codialappdemo.databinding.ItemDialogAllCoursesBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Courses


class AllCoursesFragment : Fragment(), RvCourseClick {

    private lateinit var courseAdapter: CourseAdapter
    private lateinit var myDbHelper: MyDbHelper
    private val binding by lazy { FragmentAllCoursesBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        myDbHelper = MyDbHelper(binding.root.context)
        courseAdapter = CourseAdapter(myDbHelper.getAllCourses(), this)
        binding.itemRv.adapter = courseAdapter

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.btnAdd.setOnClickListener {
            val itemDialogAllCoursesBinding = ItemDialogAllCoursesBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(binding.root.context, R.style.MyMenuDialogTheme)
                .setView(itemDialogAllCoursesBinding.root)
                .setPositiveButton(
                    "Qo'shish"
                ) { _, _ ->
                    val courseName = itemDialogAllCoursesBinding.courseName.text.toString().trim()
                    val aboutCourse = itemDialogAllCoursesBinding.aboutCourse.text.toString().trim()
                    myDbHelper.addCourses(Courses(courseName, aboutCourse))
                    courseAdapter.list = myDbHelper.getAllCourses()
                    courseAdapter.notifyItemInserted(courseAdapter.itemCount - 1)
                }
                .setNegativeButton("Yopish", null)
                .create()
            dialog.show()
            dialog.getButton(BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFB800"))
        }

        return binding.root
    }

    override fun itemClick(courses: Courses, position: Int) {
        findNavController().navigate(R.id.courseInfoFragment, bundleOf("courses" to courses,"position" to position))
    }


}