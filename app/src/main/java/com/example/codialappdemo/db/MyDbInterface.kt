package com.example.codialappdemo.db

import com.example.codialappdemo.models.Courses
import com.example.codialappdemo.models.Groups
import com.example.codialappdemo.models.Mentors
import com.example.codialappdemo.models.Students

interface MyDbInterface {

    fun addCourses(courses: Courses)
    fun getAllCourses():ArrayList<Courses>
    fun deleteCourses(courses: Courses)

    fun addMentors(mentors: Mentors)
    fun getAllMentors():ArrayList<Mentors>
    fun editMentors(mentors: Mentors)
    fun deleteMentor(mentors: Mentors)

    fun addGroups(groups: Groups)
    fun getAllGroups():ArrayList<Groups>
    fun editGroups(groups: Groups)
    fun deleteGroups(groups: Groups)

    fun addStudents(students: Students)
    fun getAllStudents():ArrayList<Students>
    fun editStudents(students: Students)
    fun deleteStudents(students: Students)

    fun getCourseById(id:Int):Courses
    fun getMentorsById(id: Int):Mentors
    fun getGroupsById(id: Int):Groups
}