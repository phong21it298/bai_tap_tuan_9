package com.example.bai_tap_tuan_8

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bai_tap_tuan_8.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private var courses: MutableList<Course> = mutableListOf()
    private lateinit var courseAdapter: CourseAdapter
    private var selectedCourseId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Khởi tạo viewbinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        //Views
        binding.recyclerViewCourses.layoutManager = LinearLayoutManager(this)

        courseAdapter = CourseAdapter(
            this,
            courses,
            { course -> updateCourse(course) },
            { id -> deleteCourse(id) })

        binding.recyclerViewCourses.adapter = courseAdapter

        // Add Course
        binding.buttonAddCourse.setOnClickListener {

            addOrUpdateCourse()
        }

        loadCourses()
    }

    //Add or Update.
    private fun addOrUpdateCourse() {

        val name = binding.editTextCourseName.text.toString()
        val description = binding.editTextCourseDescription.text.toString()

        if (name.isNotEmpty() && description.isNotEmpty()) {

            if (selectedCourseId == null) {//id == null > Add

                //Add new course
                val course = Course(0, name, description) //ID will be auto-incremented.

                if (databaseHelper.addCourse(course)) {

                    loadCourses()

                    clearInputFields()
                }
            } else {//id != null > Update existing course.

                val course = Course(selectedCourseId!!, name, description)

                if (databaseHelper.updateCourse(course)) {

                    loadCourses() //Refresh the list after update.

                    clearInputFields()

                    selectedCourseId = null

                    binding.buttonAddCourse.text = "Add Course" //Reset button text.
                    binding.buttonAddCourse.visibility = View.VISIBLE //Show the button again.
                }
            }
        }
    }

    //Update data.
    private fun loadCourses() {

        courses.clear()

        //Đổ data từ database ra lại list.
        courses.addAll(databaseHelper.getCourses())

        //Apply lên
        courseAdapter.updateCourses(courses) //Từ CourseAdapter, nó gán lại cái list bên class Adapter này.
    }

    //Click buton Update.
    private fun updateCourse(course: Course) {

        binding.editTextCourseName.setText(course.name)
        binding.editTextCourseDescription.setText(course.description)
        selectedCourseId = course.id

        binding.buttonAddCourse.text = "Update Course" //Change button text to indicate updating.
        binding.buttonAddCourse.visibility = View.VISIBLE //Show the button during editing.
    }

    //Click buton Delete.
    private fun deleteCourse(id: Int) {

        if (databaseHelper.deleteCourse(id)) {//Xóa xong thì

            loadCourses() //load lại recyclerView.
        }
    }

    //Clear views.
    private fun clearInputFields() {

        binding.editTextCourseName.text.clear()
        binding.editTextCourseDescription.text.clear()
    }
}