package com.example.codialappdemo.courses

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.databinding.FragmentCourseInfoBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Courses


class CourseInfoFragment : Fragment() {

    private lateinit var myDbHelper: MyDbHelper
    private val binding by lazy { FragmentCourseInfoBinding.inflate(layoutInflater) }
    @android.support.annotation.RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    @android.support.annotation.RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val courses = arguments?.getSerializable("courses") as Courses
        myDbHelper = MyDbHelper(binding.root.context)

        binding.tvCourseName.text = courses.name
        binding.tvCourseAbout.text = courses.about

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(binding.root.context, R.style.MyMenuDialogTheme)
                .setMessage("Kursni o'chirmoqchimisiz")
                .setNegativeButton("Yo'q", null)
                .setPositiveButton(
                    "Xa"
                ) { _, _ ->
                    val coursesList = myDbHelper.getAllCourses()
                    val groupsList = myDbHelper.getAllGroups()
                    val mentorsList = myDbHelper.getAllMentors()
                    val studentsList = myDbHelper.getAllStudents()
                    coursesList.forEach { mycourse ->

                        if (mycourse.id == courses.id) {
                            mentorsList.forEach { mentorIt ->
                                if (mycourse.id == mentorIt.courseId?.id) {
                                    groupsList.forEach { groupIt ->
                                        if (groupIt.mentorId?.id == mentorIt.id) {
                                            studentsList.forEach { studentsIt ->
                                                if (studentsIt.groupId?.id == groupIt.id) {
                                                    myDbHelper.deleteStudents(studentsIt)
                                                }
                                            }
                                            myDbHelper.deleteGroups(groupIt)
                                        }
                                    }
                                    myDbHelper.deleteMentor(mentorIt)
                                }
                            }
                            myDbHelper.deleteCourses(mycourse)
                        }
                    }
                    Toast.makeText(context, "O'chirildi", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()

                }
            dialog.show()
        }

        return binding.root
    }


}