package com.example.codialappdemo.groups

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.adapters.GroupAdapter
import com.example.codialappdemo.adapters.RvGroupClick
import com.example.codialappdemo.databinding.FragmentOpeningGroupBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Groups
import com.example.codialappdemo.models.Mentors
import com.example.codialappdemo.models.Students
import com.example.codialappdemo.utils.Constants

class OpeningGroupFragment : Fragment(), RvGroupClick {

    private lateinit var groupAdapter: GroupAdapter
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var dbStudentList: ArrayList<Students>
    private val mentorsList = ArrayList<Mentors>()
    private val binding by lazy { FragmentOpeningGroupBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myDbHelper = MyDbHelper(binding.root.context)
        dbStudentList = myDbHelper.getAllStudents()
        val allGroupList = myDbHelper.getAllGroups()
        val openingList = ArrayList<Groups>()
        allGroupList.forEach {
            if (it.open == 1 && it.courseId?.id == Constants.CURRENT_COURSE.id) {
                openingList.add(it)
            }
        }

        groupAdapter =
            GroupAdapter(openingList, myDbHelper.getAllStudents(), binding.root.context, this)

        binding.myRv.adapter = groupAdapter

        return binding.root
    }

    override fun btnEditClick(group: Groups, position: Int) {

    }

    override fun btnViewClick(group: Groups, position: Int) {
        findNavController().navigate(R.id.viewGroupFragment, bundleOf("key" to group))
    }

    override fun btnDeleteClick(group: Groups, position: Int) {
        val dialog: AlertDialog.Builder =
            AlertDialog.Builder(requireContext(), R.style.MyMenuDialogTheme)
                .setMessage("${group.name}ni o'chirmoqchimisiz?")
                .setNegativeButton("Yo'q", null)
                .setPositiveButton(
                    "Xa"
                ) { _, _ ->
                    binding.apply {
                        groupAdapter.list.removeAt(position)
                        myRv.adapter?.notifyItemRemoved(position)
                        myRv.adapter?.notifyItemRangeChanged(0, groupAdapter.list.size)
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