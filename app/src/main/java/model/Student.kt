package model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.bson.types.ObjectId

@RealmClass
open class Student(
    var _id: ObjectId? =ObjectId(),
    var name:String?=null,
    var age:Int = 0,
    var city:String?=null,
):RealmObject()
