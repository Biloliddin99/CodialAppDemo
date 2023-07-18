package com.example.codialappdemo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialappdemo.databinding.ItemRvMentorBinding
import com.example.codialappdemo.models.Students

class StudentAdapter(
    context: Context,
    val list: ArrayList<Students>,
    val rvStudentClick: RvStudentClick
) : RecyclerView.Adapter<StudentAdapter.Vh>() {

    inner class Vh(private val itemRvMentorBinding: ItemRvMentorBinding) :
        RecyclerView.ViewHolder(itemRvMentorBinding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(students: Students, position: Int) {
            itemRvMentorBinding.tvNameMentor.text = "${students.firstName} ${students.lastName}"

            itemRvMentorBinding.btnEdit.setOnClickListener {
                rvStudentClick.btnEditClick(position, students)
            }
            itemRvMentorBinding.btnDelete.setOnClickListener {
                rvStudentClick.btnDeleteClick(position, students)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvMentorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }
}

interface RvStudentClick {
    fun btnEditClick(positionMentor: Int, students: Students)
    fun btnDeleteClick(positionMentor: Int, students: Students)
}