package vn.edu.hust.studentman

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(students) { student, action ->
            when (action) {
                "edit" -> showEditStudentDialog(student)
                "delete" -> showDeleteStudentDialog(student)
            }
        }

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            showAddNewStudentDialog()
        }
    }

    private fun showAddNewStudentDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.edit_hoten)
        val idEditText = dialogView.findViewById<EditText>(R.id.edit_mssv)
        val okButton = dialogView.findViewById<Button>(R.id.button_ok)
        val cancelButton = dialogView.findViewById<Button>(R.id.button_cancel)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Thêm sinh viên")
            .setView(dialogView)
            .create()

        okButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()
            val newStudent = StudentModel(name, id)
            students.add(newStudent)
            studentAdapter.notifyItemInserted(students.size - 1)
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showEditStudentDialog(student: StudentModel) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.edit_hoten)
        val idEditText = dialogView.findViewById<EditText>(R.id.edit_mssv)
        val okButton = dialogView.findViewById<Button>(R.id.button_ok)
        val cancelButton = dialogView.findViewById<Button>(R.id.button_cancel)

        nameEditText.setText(student.name)
        idEditText.setText(student.id)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Sửa sinh viên")
            .setView(dialogView)
            .create()

        okButton.setOnClickListener {
            student.name = nameEditText.text.toString()
            student.id = idEditText.text.toString()
            studentAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDeleteStudentDialog(student: StudentModel) {
        AlertDialog.Builder(this)
            .setTitle("Xóa sinh viên")
            .setMessage("Ban chac chan muon xoa\n${student.name}?")
            .setPositiveButton("Delete") { _, _ ->
                val position = students.indexOf(student)
                students.remove(student)
                studentAdapter.notifyItemRemoved(position)
                Snackbar.make(findViewById(R.id.recycler_view_students), "${student.name} deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        students.add(position, student)
                        studentAdapter.notifyItemInserted(position)
                    }
                    .show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}