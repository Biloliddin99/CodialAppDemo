package com.example.codialappdemo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialappdemo.databinding.ItemRvBinding
import com.example.codialappdemo.models.Courses

class CourseAdapter(var list: ArrayList<Courses>, private val rvItemClick: RvCourseClick) :
    RecyclerView.Adapter<CourseAdapter.Vh>() {

    inner class Vh(private val itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(courses: Courses, position: Int) {
            itemRvBinding.tvName.text = courses.name

            itemRvBinding.root.setOnClickListener {
                rvItemClick.itemClick(courses, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }
}

interface RvCourseClick {
    fun itemClick(courses: Courses, position: Int)
}