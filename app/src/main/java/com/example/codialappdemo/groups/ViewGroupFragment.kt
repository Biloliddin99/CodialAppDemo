package com.example.codialappdemo.groups

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.adapters.RvStudentClick
import com.example.codialappdemo.adapters.StudentAdapter
import com.example.codialappdemo.databinding.FragmentViewGroupBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Groups
import com.example.codialappdemo.models.Students
import com.example.codialappdemo.utils.Constants


class ViewGroupFragment : Fragment(), RvStudentClick {

    private lateinit var myDbHelper: MyDbHelper
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var list: ArrayList<Students>
    private lateinit var group: Groups
    private val binding = FragmentViewGroupBinding.inflate(layoutInflater)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        group = arguments?.getSerializable("key") as Groups
        myDbHelper = MyDbHelper(binding.root.context)
        val dbStudentsList = myDbHelper.getAllStudents()
        list = ArrayList()

        dbStudentsList.forEach {
            if (it.groupId?.id == group.id) {
                list.add(it)
            }
        }
        studentAdapter = StudentAdapter(binding.root.context, list, this)

        binding.apply {
            myRv.adapter = studentAdapter
            tvLabel.text = group.name.toString()
            tvName.text = group.name.toString()
            tvCountStudents.text = "O'quvchilar soni:${list.size}"
            if (group.open == 0) {
                btnStartGroup.visibility = View.GONE
            }
            tvTime.text = "Vaqti ${Constants.TIMES[group.time!!.toInt()]}"

            btnStartGroup.setOnClickListener {
                group.open = 0
                myDbHelper.editGroups(group)
                btnStartGroup.visibility = View.GONE
                Toast.makeText(context, "Guuruhga dars boshlandi", Toast.LENGTH_SHORT).show()
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnAdd.setOnClickListener {
                findNavController().navigate(
                    R.id.addStudentsGroupFragment,
                    bundleOf("isEdit" to false, "group" to group)
                )
            }
        }


        return binding.root
    }

    override fun btnEditClick(positionMentor: Int, students: Students) {
        findNavController().navigate(
            R.id.addStudentsGroupFragment,
            bundleOf("isEdit" to true, "student" to students)
        )
    }

    override fun btnDeleteClick(positionMentor: Int, students: Students) {
        val dialog: AlertDialog.Builder =
            AlertDialog.Builder(binding.root.context, R.style.MyMenuDialogTheme)
                .setMessage("Ushbu o'quvchini o'chirmoqchimisiz?")
                .setNegativeButton("Yo'q", null)
                .setPositiveButton(
                    "Xa"
                )
                { _, _ ->
                    binding.apply {
                        studentAdapter.list.removeAt(positionMentor)
                        myRv.adapter?.notifyItemRemoved(positionMentor)
                        myRv.adapter?.notifyItemChanged(0, studentAdapter.list.size)
                    }
                    myDbHelper.deleteStudents(students)
                }
        dialog.show()
    }

}