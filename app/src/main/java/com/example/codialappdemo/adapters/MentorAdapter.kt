package com.example.codialappdemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialappdemo.databinding.ItemRvBinding
import com.example.codialappdemo.models.Mentors


class MentorAdapter(val context: Context, val list: ArrayList<Mentors>, val rvMentorClick: RvMentorClick) :
    RecyclerView.Adapter<MentorAdapter.Vh>() {

    inner class Vh(private val itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(mentors: Mentors, position: Int) {
            itemRvBinding.tvName.text = mentors.name

            itemRvBinding.root.setOnClickListener {
                rvMentorClick.btnEditClick(position)
            }
            itemRvBinding.root.setOnClickListener {
                rvMentorClick.btnDeleteClick(position,mentors)
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

interface RvMentorClick {
    fun btnEditClick(positionMentor: Int)
    fun btnDeleteClick(positionMentor: Int, mentor: Mentors)
}