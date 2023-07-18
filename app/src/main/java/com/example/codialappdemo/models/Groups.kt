package com.example.codialappdemo.models

class Groups :java.io.Serializable{
    var id:Int? = null
    var name:String? = null
    var time:String? = null
    var date:String? = null
    var open:Int? = null
    var courseId:Courses? = null
    var mentorId:Mentors? = null

    constructor(
        id: Int?,
        name: String?,
        time: String?,
        date: String?,
        open: Int?,
        courseId: Courses?,
        mentorId: Mentors?
    ) {
        this.id = id
        this.name = name
        this.time = time
        this.date = date
        this.open = open
        this.courseId = courseId
        this.mentorId = mentorId
    }

    constructor(
        name: String?,
        time: String?,
        date: String?,
        open: Int?,
        courseId: Courses?,
        mentorId: Mentors?
    ) {
        this.name = name
        this.time = time
        this.date = date
        this.open = open
        this.courseId = courseId
        this.mentorId = mentorId
    }


}