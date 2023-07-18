package com.example.codialappdemo.mentors

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.adapters.MentorAdapter
import com.example.codialappdemo.adapters.RvMentorClick
import com.example.codialappdemo.databinding.DialogMentorEditBinding
import com.example.codialappdemo.databinding.FragmentAllMentorsBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Courses
import com.example.codialappdemo.models.Groups
import com.example.codialappdemo.models.Mentors
import com.example.codialappdemo.models.Students


class AllMentorsFragment : Fragment(), RvMentorClick {

    private lateinit var mentorAdapter: MentorAdapter
    private lateinit var    courses: Courses
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var mentorList: ArrayList<Mentors>
    private lateinit var dbGroupList: ArrayList<Groups>
    private lateinit var dbStudentList: ArrayList<Students>
    private lateinit var binding: FragmentAllMentorsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMentorsBinding.inflate(layoutInflater)

        courses = arguments?.getSerializable("key") as Courses
        myDbHelper = MyDbHelper(binding.root.context)
        mentorList = ArrayList()
        val dbMentorList = myDbHelper.getAllMentors()
        dbMentorList.forEach {
            if (it.courseId?.id == courses.id) {
                mentorList.add(it)
            }
        }
        dbGroupList = myDbHelper.getAllGroups()
        dbStudentList = myDbHelper.getAllStudents()

        mentorAdapter = MentorAdapter(binding.root.context, mentorList, this)

        binding.apply {
            tvLabel.text = courses.name

            myRv.adapter = mentorAdapter

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnAdd.setOnClickListener {
                findNavController().navigate(R.id.addMentorsFragment, bundleOf("key" to courses))
            }

        }

        return binding.root
    }

    override fun btnEditClick(positionMentor: Int) {
        val mentor = mentorList[positionMentor]
        val lastName = mentor.lastName
        val name = mentor.name
        val number = mentor.number
        Toast.makeText(context, "${mentor.id}", Toast.LENGTH_SHORT).show()
        val alertDialogLayoutBinding = DialogMentorEditBinding.inflate(layoutInflater)
        alertDialogLayoutBinding.apply {
            edtSurname.setText(lastName)
            edtName.setText(name)
            edtNumber.setText(number)
        }
        val dialog = AlertDialog.Builder(binding.root.context, R.style.MyMenuDialogTheme)
            .setView(alertDialogLayoutBinding.root)
            .setPositiveButton(
                "O'zgartirish"
            ) { _, _ ->
                alertDialogLayoutBinding.apply {
                    mentor.lastName = edtSurname.text.toString().trim()
                    mentor.name = edtName.text.toString().trim()
                    mentor.number = edtNumber.toString().trim()
                    myDbHelper.editMentors(mentor)
                    mentorAdapter.list[positionMentor] = mentor
                    mentorAdapter.notifyItemChanged(positionMentor)
                }
            }
            .setNegativeButton("Yopish", null)
            .create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFB800"))

    }

    override fun btnDeleteClick(positionMentor: Int, mentor: Mentors) {

        val dialog = AlertDialog.Builder(binding.root.context, R.style.MyMenuDialogTheme)
            .setMessage("Ushbu mentorni o'chirmoqchimisiz?")
            .setNegativeButton("Yo'q", null)
            .setPositiveButton(
                "Xa"
            ) { _, _ ->
                binding.apply {
                    mentorAdapter.list.removeAt(positionMentor)
                    myRv.adapter?.notifyItemRemoved(positionMentor)
                    myRv.adapter?.notifyItemRangeChanged(0, mentorAdapter.list.size)
                }
                myDbHelper.deleteMentor(mentor)

                dbGroupList.forEach { groups ->
                    if (groups.mentorId?.id == mentor.id) {
                        dbStudentList.forEach {
                            if (it.groupId?.id == groups.id) {
                                myDbHelper.deleteStudents(it)
                            }
                        }
                        myDbHelper.deleteGroups(groups)
                    }
                }
            }
        dialog.show()
    }

}