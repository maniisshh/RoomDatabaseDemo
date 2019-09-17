package com.manisks.roomdatabasedemo

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private var employeeDatabase: EmployeeDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        employeeDatabase = EmployeeDatabase.getDatabase(this)!!
        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val age = etAge.text.toString().trim()
            when {
                name.isEmpty() -> {
                    Toast.makeText(this, "Name can't be empty", Toast.LENGTH_SHORT).show()
                    etName.error = "Name can't be empty"
                    return@setOnClickListener
                }
                age.isEmpty() -> {
                    Toast.makeText(this, "Age can't be empty", Toast.LENGTH_SHORT).show()
                    etAge.error = "Age can't be empty"
                    return@setOnClickListener
                }
                else -> {
                    val employee = Employee(name, age.toInt())
                    InsertTask(this, employee).execute()
                }
            }
        }

        btnGetData.setOnClickListener {
            GetDataTask(this).execute()
        }
    }

    class GetDataTask(context: MainActivity) : AsyncTask<Void, Void, List<Employee>>() {
        private var weakReference: WeakReference<MainActivity> = WeakReference(context)

        override fun doInBackground(vararg p0: Void?): List<Employee> {
            return weakReference.get()!!.employeeDatabase!!.employeeDao().getAllEmployees()
        }

        override fun onPostExecute(result: List<Employee>?) {
            super.onPostExecute(result)
            Toast.makeText(
                weakReference.get()!!,
                "Size of employees " + result!!.size,
                Toast.LENGTH_LONG
            ).show()

            for (employee in result) {
                println("Employee  Id: " + employee.id + " Name: " + employee.name + " Age: " + employee.age)
            }
        }

    }

    class InsertTask(context: MainActivity, var employee: Employee) :
        AsyncTask<Void, Void, Boolean>() {
        private var weakReference: WeakReference<MainActivity> = WeakReference(context)

        override fun doInBackground(vararg p0: Void?): Boolean {
            weakReference.get()!!.employeeDatabase!!.employeeDao().insert(employee)
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            var context = weakReference.get()!!
            if (result!!) {
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
                context.etAge.setText("")
                context.etName.setText("")
            }
        }
    }
}
