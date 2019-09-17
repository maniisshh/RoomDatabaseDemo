package com.manisks.roomdatabasedemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by user on 12-09-2019.
 */

@Dao
interface EmployeeDao {

    @Insert
    fun insert(employee: Employee)

    @Query("SELECT * FROM employee ORDER BY name ASC")
    fun getAllEmployees(): List<Employee>
}