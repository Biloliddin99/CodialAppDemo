package com.example.codialappdemo.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.codialappdemo.models.Courses
import com.example.codialappdemo.models.Groups
import com.example.codialappdemo.models.Mentors
import com.example.codialappdemo.models.Students
import com.example.codialappdemo.utils.Constants.COURSE_ABOUT
import com.example.codialappdemo.utils.Constants.COURSE_ID
import com.example.codialappdemo.utils.Constants.COURSE_NAME
import com.example.codialappdemo.utils.Constants.COURSE_TABLE
import com.example.codialappdemo.utils.Constants.DB_NAME
import com.example.codialappdemo.utils.Constants.DB_VERSION
import com.example.codialappdemo.utils.Constants.GROUP_COURSE_ID
import com.example.codialappdemo.utils.Constants.GROUP_DATE
import com.example.codialappdemo.utils.Constants.GROUP_ID
import com.example.codialappdemo.utils.Constants.GROUP_MENTOR_ID
import com.example.codialappdemo.utils.Constants.GROUP_NAME
import com.example.codialappdemo.utils.Constants.GROUP_OPEN
import com.example.codialappdemo.utils.Constants.GROUP_TABLE
import com.example.codialappdemo.utils.Constants.GROUP_TIME
import com.example.codialappdemo.utils.Constants.MENTOR_COURSE_ID
import com.example.codialappdemo.utils.Constants.MENTOR_ID
import com.example.codialappdemo.utils.Constants.MENTOR_LASTNAME
import com.example.codialappdemo.utils.Constants.MENTOR_NAME
import com.example.codialappdemo.utils.Constants.MENTOR_NUMBER
import com.example.codialappdemo.utils.Constants.MENTOR_TABLE
import com.example.codialappdemo.utils.Constants.STUDENT_DATE
import com.example.codialappdemo.utils.Constants.STUDENT_GROUP_ID
import com.example.codialappdemo.utils.Constants.STUDENT_ID
import com.example.codialappdemo.utils.Constants.STUDENT_LASTNAME
import com.example.codialappdemo.utils.Constants.STUDENT_NAME
import com.example.codialappdemo.utils.Constants.STUDENT_TABLE

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    MyDbInterface {
    override fun onCreate(p0: SQLiteDatabase?) {
        val courseQuery =
            "create table $COURSE_TABLE ($COURSE_ID integer not null primary key autoincrement unique, $COURSE_NAME text not null, $COURSE_ABOUT text not null)"

        val mentorQuery =
            "create table $MENTOR_TABLE ($MENTOR_ID integer not null primary key autoincrement unique, $MENTOR_NAME text not null, $MENTOR_LASTNAME text not null, $MENTOR_NUMBER text not null, $MENTOR_COURSE_ID integer not null, foreign key ($MENTOR_COURSE_ID) references $COURSE_TABLE($COURSE_ID))"

        val groupQuery =
            "create table $GROUP_TABLE ($GROUP_ID integer not null primary key autoincrement unique, $GROUP_NAME text not null, $GROUP_TIME text not null, $GROUP_DATE text not null, $GROUP_OPEN integer not null, $GROUP_COURSE_ID integer not null, $GROUP_MENTOR_ID integer not null, foreign key ($GROUP_COURSE_ID) references $COURSE_TABLE ($COURSE_ID),foreign key ($GROUP_MENTOR_ID) references $MENTOR_TABLE ($MENTOR_ID))"

        val studentQuery =
            "create table $STUDENT_TABLE ($STUDENT_ID integer not null primary key autoincrement unique, $STUDENT_NAME text not null, $STUDENT_LASTNAME text not null, $STUDENT_DATE text not null, $STUDENT_GROUP_ID integer not null, foreign key ($STUDENT_GROUP_ID) references $GROUP_TABLE($GROUP_ID))"

        p0?.execSQL(courseQuery)
        p0?.execSQL(mentorQuery)
        p0?.execSQL(groupQuery)
        p0?.execSQL(studentQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun addCourses(courses: Courses) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COURSE_NAME, courses.name)
        contentValues.put(COURSE_ABOUT, courses.about)
        database.insert(COURSE_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllCourses(): ArrayList<Courses> {
        val database = this.readableDatabase
        val courseList = ArrayList<Courses>()
        val cursor = database.rawQuery("select * from $COURSE_TABLE", null)
        if (cursor.moveToFirst()) {
            do {
                courseList.add(
                    Courses(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                    )
                )
            } while (cursor.moveToNext())
        }
        return courseList
    }

    override fun deleteCourses(courses: Courses) {
        val database = this.writableDatabase
        database.delete(COURSE_TABLE, "$COURSE_ID=?", arrayOf(courses.id.toString()))
        database.close()
    }

    override fun addMentors(mentors: Mentors) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTOR_NAME, mentors.name)
        contentValues.put(MENTOR_LASTNAME, mentors.lastName)
        contentValues.put(MENTOR_NUMBER, mentors.number)
        contentValues.put(MENTOR_COURSE_ID, mentors.courseId?.id)
        database.insert(MENTOR_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllMentors(): ArrayList<Mentors> {
        val database = this.readableDatabase
        val mentorList = ArrayList<Mentors>()
        val cursor = database.rawQuery("select * from $MENTOR_TABLE", null)
        if (cursor.moveToFirst()) {
            do {
                mentorList.add(
                    Mentors(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        getCourseById(cursor.getInt(4))
                    )
                )
            } while (cursor.moveToNext())
        }
        return mentorList
    }

    override fun editMentors(mentors: Mentors) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTOR_NAME, mentors.name)
        contentValues.put(MENTOR_LASTNAME, mentors.lastName)
        contentValues.put(MENTOR_NUMBER, mentors.number)
        contentValues.put(MENTOR_COURSE_ID, mentors.courseId?.id)
        database.update(MENTOR_TABLE, contentValues, "$MENTOR_ID=?", arrayOf(mentors.id.toString()))
    }

    override fun deleteMentor(mentors: Mentors) {
        val database = this.writableDatabase
        database.delete(MENTOR_TABLE, "$MENTOR_ID=?", arrayOf(mentors.id.toString()))
        database.close()
    }

    override fun addGroups(groups: Groups) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUP_NAME, groups.name)
        contentValues.put(GROUP_COURSE_ID, groups.courseId?.id)
        contentValues.put(GROUP_MENTOR_ID, groups.mentorId?.id)
        contentValues.put(GROUP_TIME, groups.time)
        contentValues.put(GROUP_DATE, groups.date)
        contentValues.put(GROUP_OPEN, groups.open)
        database.insert(GROUP_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllGroups(): ArrayList<Groups> {
        val database = this.readableDatabase
        val groupList = ArrayList<Groups>()
        val cursor = database.rawQuery("select * from $GROUP_TABLE", null)
        if (cursor.moveToFirst()) {
            do {
                groupList.add(
                    Groups(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        getCourseById(cursor.getInt(5)),
                        getMentorsById(cursor.getInt(6))
                    )

                )
            } while (cursor.moveToNext())
        }
        return groupList
    }

    override fun editGroups(groups: Groups) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUP_NAME, groups.name)
        contentValues.put(GROUP_TIME, groups.time)
        contentValues.put(GROUP_OPEN, groups.open)
        contentValues.put(GROUP_COURSE_ID, groups.courseId?.id)
        contentValues.put(GROUP_MENTOR_ID, groups.mentorId?.id)
        database.update(GROUP_TABLE, contentValues, "$GROUP_ID=?", arrayOf(groups.id.toString()))
    }

    override fun deleteGroups(groups: Groups) {
        val database = this.writableDatabase
        database.delete(GROUP_TABLE, "$GROUP_ID=?", arrayOf(groups.id.toString()))
        database.close()
    }

    override fun addStudents(students: Students) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_NAME, students.firstName)
        contentValues.put(STUDENT_LASTNAME, students.lastName)
        contentValues.put(STUDENT_DATE, students.date)
        contentValues.put(STUDENT_GROUP_ID, students.groupId?.id)
        database.insert(STUDENT_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllStudents(): ArrayList<Students> {
        val database = this.readableDatabase
        val studentList = ArrayList<Students>()
        val cursor = database.rawQuery("select * from $STUDENT_TABLE", null)
        if (cursor.moveToFirst()) {
            do {
                studentList.add(
                    Students(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        getGroupsById(cursor.getInt(4))
                    )
                )
            } while (cursor.moveToNext())
        }
        return studentList
    }

    override fun editStudents(students: Students) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_NAME, students.firstName)
        contentValues.put(STUDENT_LASTNAME, students.firstName)
        contentValues.put(STUDENT_DATE, students.date)
        contentValues.put(STUDENT_GROUP_ID, students.groupId?.id)
        database.update(
            STUDENT_TABLE,
            contentValues,
            "$STUDENT_ID=?",
            arrayOf(students.id.toString())
        )
    }

    override fun deleteStudents(students: Students) {
        val database = this.writableDatabase
        database.delete(STUDENT_TABLE, "$STUDENT_ID=?", arrayOf(students.id.toString()))
    }

    override fun getCourseById(id: Int): Courses {
        val database = this.readableDatabase
        val cursor = database.query(
            COURSE_TABLE,
            arrayOf(
                COURSE_ID,
                COURSE_NAME,
                COURSE_ABOUT
            ),
            "$COURSE_ID =?",
            arrayOf(id.toString()),
            null, null, null
        )
        if (cursor != null && cursor.moveToFirst()) {
            return Courses(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
            )
        }

        return Courses(
            0,
            cursor.getString(1),
            cursor.getString(2)

        )
    }

    override fun getMentorsById(id: Int): Mentors {
        val database = this.readableDatabase
        val cursor = database.query(
            MENTOR_TABLE,
            arrayOf(
                MENTOR_ID,
                MENTOR_NAME,
                MENTOR_LASTNAME,
                MENTOR_NUMBER,
                MENTOR_COURSE_ID,
            ),
            "$MENTOR_ID =?",
            arrayOf(id.toString()),
            null, null, null
        )
        if (cursor != null && cursor.moveToFirst()) {
            return Mentors(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                getCourseById(cursor.getInt(4))
            )
        }

        return Mentors(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            getCourseById(cursor.getInt(4))
        )
    }

    override fun getGroupsById(id: Int): Groups {
        val database = this.readableDatabase
        val cursor = database.query(
            GROUP_TABLE,
            arrayOf(
                GROUP_ID,
                GROUP_NAME,
                GROUP_TIME,
                GROUP_DATE,
                GROUP_OPEN,
                GROUP_COURSE_ID,
                GROUP_MENTOR_ID
            ),
            "$GROUP_ID =?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()

        if (cursor != null && cursor.moveToFirst()) {
            return Groups(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                getCourseById(cursor.getInt(5)),
                getMentorsById(cursor.getInt(6))
            )
        }

        return Groups(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getInt(4),
            getCourseById(cursor.getInt(5)),
            getMentorsById(cursor.getInt(6))
        )
    }

}