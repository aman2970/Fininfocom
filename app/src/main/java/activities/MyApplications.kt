package activities

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration

class MyApplications : Application() {
    private val appId = "application-0-ovtue"
    private lateinit var app: App
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        app = App(AppConfiguration.Builder(appId).build())

    }
}