package com.example.codialappdemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialappdemo.databinding.ItemRvBinding
import com.example.codialappdemo.databinding.ItemRvGroupBinding
import com.example.codialappdemo.models.Groups
import com.example.codialappdemo.models.Students

class GroupAdapter(
    val list: ArrayList<Groups>,
    val dbStudentList: ArrayList<Students>,
    val context: Context,
    val rvGroupClick: RvGroupClick
) :
    RecyclerView.Adapter<GroupAdapter.Vh>() {

    inner class Vh(private val itemRvGroupBinding: ItemRvGroupBinding) :
        RecyclerView.ViewHolder(itemRvGroupBinding.root) {

        private var count = 0
        fun onBind(groups: Groups, position: Int) {
            itemRvGroupBinding.tvName.text = groups.name

            count = 0
            dbStudentList.forEach {
                if (it.groupId?.id == groups.id) {
                    count++
                }
            }
            itemRvGroupBinding.tvCountStudent.text = "O'quvchilar soni $count ta"

            itemRvGroupBinding.cardEdit.setOnClickListener {
                rvGroupClick.btnEditClick(groups, position)
            }
            itemRvGroupBinding.cardDelete.setOnClickListener {
                rvGroupClick.btnDeleteClick(groups, position)
            }
            itemRvGroupBinding.cardShow.setOnClickListener {
                rvGroupClick.btnViewClick(groups, position)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }
}

interface RvGroupClick {
    fun btnEditClick(group: Groups, position: Int)
    fun btnViewClick(group: Groups, position: Int)
    fun btnDeleteClick(group: Groups, position: Int)
}