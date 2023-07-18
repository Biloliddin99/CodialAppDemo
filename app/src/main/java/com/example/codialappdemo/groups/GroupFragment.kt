package com.example.codialappdemo.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.adapters.ViewPagerAdapter
import com.example.codialappdemo.databinding.FragmentGroupBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Courses
import com.example.codialappdemo.models.Mentors
import com.example.codialappdemo.utils.Constants
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class GroupFragment : Fragment() {

    private lateinit var myDbHelper: MyDbHelper
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var courses: Courses
    private val binding by lazy { FragmentGroupBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        courses = arguments?.getSerializable("key") as Courses
        Constants.CURRENT_COURSE = courses
        myDbHelper = MyDbHelper(binding.root.context)
        val mentorsList = ArrayList<Mentors>()

        val dbMentorsList = myDbHelper.getAllMentors()
        dbMentorsList.forEach {
            if (it.courseId?.id == courses.id) {
                mentorsList.add(it)
            }
        }

        myDbHelper = MyDbHelper(binding.root.context)
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding.apply {
            tvLabel.text = courses.name
            btnAdd.visibility = View.INVISIBLE
            myViewPager.adapter = viewPagerAdapter

            TabLayoutMediator(myTabLayout, myViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Ochilgan\nguruhlar"
                    1 -> tab.text = "Ochilayotgan\nguruhlar"
                }

            }.attach()

            myTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab == myTabLayout.getTabAt(0)) {
                        btnAdd.visibility = View.INVISIBLE
                    } else {
                        btnAdd.visibility = View.VISIBLE
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })

            btnAdd.setOnClickListener {
                if (mentorsList.isEmpty()) {
                    Toast.makeText(context, "Mentor yo'q", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(R.id.addGroupFragment, bundleOf("key" to courses))
                }
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }


        return binding.root
    }

}