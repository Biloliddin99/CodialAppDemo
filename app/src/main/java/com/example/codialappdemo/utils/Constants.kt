package com.example.codialappdemo.utils

import com.example.codialappdemo.models.Courses

object Constants {
    const val DB_NAME = "db_codial"
    const val DB_VERSION = 1

    const val COURSE_TABLE = "course_table"
    const val COURSE_ID = "course_id"
    const val COURSE_NAME = "course_name"
    const val COURSE_ABOUT = "course_about"

    const val GROUP_TABLE = "group_table"
    const val GROUP_ID = "group_id"
    const val GROUP_NAME = "group_name"
    const val GROUP_TIME = "group_time"
    const val GROUP_DATE = "group_date"
    const val GROUP_COURSE_ID = "group_course_id"
    const val GROUP_MENTOR_ID = "group_mentor_id"
    const val GROUP_OPEN = "group_open"

    const val MENTOR_TABLE = "mentor_table"
    const val MENTOR_ID = "mentor_id"
    const val MENTOR_LASTNAME = "mentor_last_name"
    const val MENTOR_NAME = "mentor_name"
    const val MENTOR_NUMBER = "mentor_number"
    const val MENTOR_COURSE_ID = "mentor_course_id"

    const val STUDENT_TABLE = "student_table"
    const val STUDENT_ID = "student_id"
    const val STUDENT_NAME = "student_name"
    const val STUDENT_LASTNAME = "student_last_name"
    const val STUDENT_DATE = "student_date"
    const val STUDENT_GROUP_ID = "student_group_id"

    val TIMES = arrayOf("10:00-12:00", "12:00-14:00", "14:00-16:00", "16:00-18:00", "18:00-20:00")
    val DAYS = arrayOf("Juft kunlari", "Toq kunlari")

    var CURRENT_COURSE = Courses("name","description")
}