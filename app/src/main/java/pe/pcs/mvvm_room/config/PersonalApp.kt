package pe.pcs.mvvm_room.config

import android.app.Application
import androidx.room.Room

// Esta clase iniciara la creacion de la DB
class PersonalApp: Application() {

    companion object {
        lateinit var db: PersonalDB
    }

    // Crea la DB al iniciar la app
    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            PersonalDB::class.java,
            "personalDB"
        ).build()
    }
}