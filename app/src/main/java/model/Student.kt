package model

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class Student(
    @Required
    var name:String?=null,
    var age:Int = 0,
    var city:String?=null,
):RealmObject()
