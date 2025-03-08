package com.example.bai_tap_tuan_8

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "courses.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_COURSES = "courses"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        //Câu lệnh tạo table.
        val createTableStatement = ("CREATE TABLE $TABLE_COURSES("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, "
                + "$COLUMN_DESCRIPTION TEXT)")

        //Thực thi câu lệnh.
        db?.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        //Xóa table nếu nó đã tồn tại.
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_COURSES")

        //Rồi tạo table mới.
        onCreate(db)
    }

    //Insert data.
    fun addCourse(course: Course): Boolean {

        //db để write.
        val db = this.writableDatabase

        //Get values from course.
        val contentValues = ContentValues().apply {

            put(COLUMN_NAME, course.name)

            put(COLUMN_DESCRIPTION, course.description)
        }

        //Insert data (contentValues) to table.
        val result = db.insert(TABLE_COURSES, null, contentValues)

        db.close()

        return result != -1L //Returns true if insert was successful.
    }

    @SuppressLint("Range")
    fun getCourses(): List<Course> {

        val courseList = mutableListOf<Course>()

        //db để read.
        val db = this.readableDatabase

        //Read courses, select data.
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_COURSES", null)

        //Add to List (Đổ data (từ cursor) vào list(courseList)).
        if (cursor.moveToFirst()) {

            do {

                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))

                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))

                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))

                courseList.add(Course(id, name, description))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return courseList
    }

    fun updateCourse(course: Course): Boolean {

        val db = this.writableDatabase

        //Get values from course.
        val contentValues = ContentValues().apply {

            put(COLUMN_NAME, course.name)

            put(COLUMN_DESCRIPTION, course.description)
        }

        //Update course to database.
        val result = db.update(TABLE_COURSES, contentValues,"$COLUMN_ID = ?", arrayOf(course.id.toString()))

        db.close()

        return result > 0 //Returns true if result > 0
    }

    fun deleteCourse(id: Int): Boolean {

        val db = this.writableDatabase

        //Delete course.
        val result = db.delete(TABLE_COURSES, "$COLUMN_ID = ?", arrayOf(id.toString()))

        db.close()

        return result > 0 //Returns true if result > 0
    }
}