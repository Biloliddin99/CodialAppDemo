package com.example.codialappdemo.models

class Mentors {
    var id:Int? = null
    var name:String? = null
    var lastName:String? = null
    var number:String? = null
    var courseId:Courses? = null

    constructor(id: Int?, name: String?, lastName: String?, number: String?, courseId: Courses?) {
        this.id = id
        this.name = name
        this.lastName = lastName
        this.number = number
        this.courseId = courseId
    }

    constructor(name: String?, lastName: String?, number: String?, courseId: Courses?) {
        this.name = name
        this.lastName = lastName
        this.number = number
        this.courseId = courseId
    }


}