package com.example.codialappdemo.mentors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.databinding.FragmentAddMentorsBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Courses
import com.example.codialappdemo.models.Mentors


class AddMentorsFragment : Fragment() {

    private lateinit var binding: FragmentAddMentorsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMentorsBinding.inflate(layoutInflater)

        val courses = arguments?.getSerializable("key") as Courses
        val myDbHelper = MyDbHelper(binding.root.context)

        var lastName = ""
        var name = ""
        var number = ""

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSave.setOnClickListener {
                lastName = edtLastname.text.toString().trim()
                name = edtName.text.toString().trim()
                number = edtNumber.text.toString().trim()

                myDbHelper.addMentors(
                    Mentors(
                        name,
                        lastName,
                        number,
                        courses
                    )
                )
                findNavController().popBackStack()
            }

        }

        return binding.root
    }

}