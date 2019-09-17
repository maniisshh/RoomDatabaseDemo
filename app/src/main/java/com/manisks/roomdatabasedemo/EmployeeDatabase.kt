package com.manisks.roomdatabasedemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by user on 12-09-2019.
 */
@Database(entities = [Employee::class], version = 1)
abstract class EmployeeDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    companion object {
        private var INSTANCE: EmployeeDatabase? = null
        fun getDatabase(context: Context): EmployeeDatabase? {
            if (INSTANCE == null) {
                synchronized(EmployeeDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, EmployeeDatabase::class.java, "employee.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}