package com.example.codialappdemo.models

class Students:java.io.Serializable {
    var id:Int? = null
    var firstName:String? = null
    var lastName:String? = null
    var date:String? = null
    var groupId:Groups? = null


    constructor(id: Int?, firstName: String?, lastName: String?, date: String?, groupId: Groups?) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.date = date
        this.groupId = groupId
    }

    constructor(firstName: String?, lastName: String?, date: String?, groupId: Groups?) {
        this.firstName = firstName
        this.lastName = lastName
        this.date = date
        this.groupId = groupId
    }


}