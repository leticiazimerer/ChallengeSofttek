package com.softtek.mindcare.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.softtek.mindcare.models.*

@Database(
    entities = [
        MoodEntry::class,
        StressEntry::class,
        QuestionnaireResponse::class,
        SupportResource::class,
        UserSettings::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
    abstract fun stressDao(): StressDao
    abstract fun questionnaireDao(): QuestionnaireDao
    abstract fun supportDao(): SupportDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mindcare_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance = newInstance
                newInstance
            }
        }
    }
}