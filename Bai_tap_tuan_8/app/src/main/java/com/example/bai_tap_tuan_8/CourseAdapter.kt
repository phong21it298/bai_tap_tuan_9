package com.example.bai_tap_tuan_8

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(

    private val context: Context,
    private var courses: List<Course>,
    private val onUpdateClick: (Course) -> Unit,
    private val onDeleteClick: (Int) -> Unit
): RecyclerView.Adapter<CourseAdapter.CourseViewHolder>(){

    inner class CourseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val textViewName: TextView = itemView.findViewById(R.id.textViewCourseName)

        val textViewDescription: TextView = itemView.findViewById(R.id.textViewCourseDescription)

        val buttonUpdate: Button = itemView.findViewById(R.id.buttonUpdate)

        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)

        init {

            buttonUpdate.setOnClickListener {

                val course = courses[adapterPosition]

                onUpdateClick(course)
            }

            buttonDelete.setOnClickListener {

                val courseId = courses[adapterPosition].id

                onDeleteClick(courseId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_course, parent, false)

        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {

        val course = courses[position]

        holder.textViewName.text = course.name
        holder.textViewDescription.text = course.description
    }

    override fun getItemCount(): Int {

        return courses.size
    }

    fun updateCourses(newCourses: List<Course>) {

        courses = newCourses

        notifyDataSetChanged()
    }
}