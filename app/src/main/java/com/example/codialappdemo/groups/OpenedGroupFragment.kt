package com.example.codialappdemo.groups

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.support.v4.os.IResultReceiver._Parcel
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.adapters.GroupAdapter
import com.example.codialappdemo.adapters.RvGroupClick
import com.example.codialappdemo.databinding.EditGroupDialogBinding
import com.example.codialappdemo.databinding.FragmentOpenedGroupBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Groups
import com.example.codialappdemo.models.Mentors
import com.example.codialappdemo.models.Students
import com.example.codialappdemo.utils.Constants


class OpenedGroupFragment : Fragment(), RvGroupClick {

    private lateinit var groupAdapter: GroupAdapter
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var dbStudentList: ArrayList<Students>
    private val mentorList = ArrayList<Mentors>()
    private val binding by lazy { FragmentOpenedGroupBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myDbHelper = MyDbHelper(binding.root.context)
        val allGroupList = myDbHelper.getAllGroups()
        dbStudentList = myDbHelper.getAllStudents()
        val openGroupList = ArrayList<Groups>()
        allGroupList.forEach {
            if (it.open == 0 && it.courseId?.id == Constants.CURRENT_COURSE.id) {
                openGroupList.add(it)
            }
        }

        groupAdapter = GroupAdapter(openGroupList, dbStudentList, binding.root.context, this)
        binding.myRv.adapter = groupAdapter

        return binding.root
    }


    override fun btnEditClick(group: Groups, position: Int) {
        val name = group.name
        val editGroupDialogBinding = EditGroupDialogBinding.inflate(layoutInflater)
        editGroupDialogBinding.apply {
            edtName.setText(name)
            val mentorsNameList = ArrayList<String>()
            val dbMentorsList = myDbHelper.getAllMentors()
            mentorList.clear()
            dbMentorsList.forEach {
                if (it.courseId?.id == Constants.CURRENT_COURSE.id) {
                    mentorList.add(it)
                    mentorsNameList.add("${it.name} ${it.lastName}")
                }
            }
            val adapter = ArrayAdapter<String>(
                binding.root.context,
                androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                mentorsNameList
            )
            spinnerMentor.adapter = adapter
            spinnerMentor.setSelection(dbMentorsList.indexOf(group.mentorId))

            spinnerTime.adapter = ArrayAdapter<String>(
                binding.root.context,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                Constants.TIMES
            )

            spinnerDays.adapter = ArrayAdapter<String>(
                binding.root.context,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                Constants.DAYS
            )

        }
        val dialog = AlertDialog.Builder(
            binding.root.context,
            R.style.MyMenuDialogTheme
        )
            .setView(editGroupDialogBinding.root)
            .setPositiveButton(
                "O'zgartirish"
            ) { _, _ ->
                editGroupDialogBinding.apply {
                    val editedGroup = Groups(
                        edtName.text.toString().trim(),
                        spinnerTime.selectedItemPosition.toString(),
                        spinnerDays.selectedItemPosition.toString(),
                        1,
                        group.courseId,
                        mentorList[spinnerMentor.selectedItemPosition]
                    )

                    myDbHelper.editGroups(editedGroup)
                    groupAdapter.list[position] = editedGroup
                    groupAdapter.notifyItemChanged(position)
                }
            }
            .setNegativeButton("Yopish", null)
            .create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFB00"))

    }

    override fun btnViewClick(group: Groups, position: Int) {
        findNavController().navigate(
            R.id.viewGroupFragment,
            bundleOf("key" to group)
        )

    }

    override fun btnDeleteClick(group: Groups, position: Int) {
        val dialog = AlertDialog.Builder(
            binding.root.context,
            R.style.MyMenuDialogTheme
        )
            .setMessage("${group.name} ni o'chirmoqchimisiz?")
            .setNegativeButton("Yo'q", null)
            .setPositiveButton(
                "Xa"
            ) { _, _ ->
                binding.apply {
                    groupAdapter.list.removeAt(position)
                    myRv.adapter?.notifyItemRemoved(position)
                    myRv.adapter?.notifyItemChanged(0, groupAdapter.list.size)
                }
                myDbHelper.deleteGroups(group)
                dbStudentList.forEach {
                    if (it.groupId?.id == group.id) {
                        myDbHelper.deleteStudents(it)
                    }
                }
            }
        dialog.show()
    }


}