package com.example.codialappdemo.groups

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.databinding.FragmentAddGroupBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Courses
import com.example.codialappdemo.models.Groups
import com.example.codialappdemo.models.Mentors
import com.example.codialappdemo.utils.Constants


class AddGroupFragment : Fragment() {

    private lateinit var myDbHelper: MyDbHelper
    private lateinit var courses: Courses
    private lateinit var mentorList: ArrayList<Mentors>
    private lateinit var mentorNameList: ArrayList<String>
    private val binding by lazy { FragmentAddGroupBinding.inflate(layoutInflater) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        courses = arguments?.getSerializable("key") as Courses
        myDbHelper = MyDbHelper(binding.root.context)
        mentorList = ArrayList()
        mentorNameList = ArrayList()
        val dbMentorList = myDbHelper.getAllMentors()
        dbMentorList.forEach {
            if (it.courseId?.id == courses.id) {
                mentorList.add(it)
                mentorNameList.add("${it.name} ${it.lastName}")
            }
        }

        val adapter = ArrayAdapter(
            binding.root.context,
            androidx.transition.R.layout.support_simple_spinner_dropdown_item,
            mentorNameList
        )
        binding.apply {
            spinnerMentor.setAdapter(adapter)
            spinnerTime.setAdapter(
                ArrayAdapter(
                    binding.root.context,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    Constants.TIMES
                )
            )

            spinnerDay.setAdapter(
                ArrayAdapter(
                    binding.root.context,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    Constants.DAYS
                )
            )
            var mentorPosition = 0
            var dayPosition = 0
            var timePosition = 0
            var mentorState = false
            var dayState = false
            var timeState = false

            spinnerMentor.setOnItemClickListener { adapterView, view, i, l ->
                mentorPosition = i
                mentorState = true
            }
            spinnerDay.setOnItemClickListener { adapterView, view, i, l ->
                dayPosition = i
                dayState = true
            }
            spinnerTime.setOnItemClickListener { adapterView, view, i, l ->
                timePosition = i
                timeState = true
            }

            btnSave.setOnClickListener {
                val name = edtName.text.toString().trim()
                if (mentorState && dayState && timeState && name.isNotEmpty()) {
                    val group = Groups(
                        name,
                        timePosition.toString(),
                        dayPosition.toString(),
                        1,
                        courses,
                        mentorList[mentorPosition]
                    )
                    myDbHelper.addGroups(group)
                    Toast.makeText(context, "Saqlandi", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Ma'lumotlarni kiriting", Toast.LENGTH_SHORT).show()
                }
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.coordinateLayout.setOnTouchListener { p0, p1 ->
            binding.apply {
                edtName.clearFocus()
                spinnerMentor.clearFocus()
                spinnerDay.clearFocus()
                spinnerTime.clearFocus()
            }
            true
        }

        return binding.root
    }

}