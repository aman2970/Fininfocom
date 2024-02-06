package activities

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplications : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config: RealmConfiguration = RealmConfiguration.Builder().name("Fin").schemaVersion(1)
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}