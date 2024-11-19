package vn.edu.hust.studentman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: List<StudentModel>,
    private val onActionClick: (StudentModel, String) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int = students.size

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.text_student_name)
        private val idTextView: TextView = itemView.findViewById(R.id.text_student_id)
        private val editImageView: ImageView = itemView.findViewById(R.id.image_edit)
        private val removeImageView: ImageView = itemView.findViewById(R.id.image_remove)

        fun bind(student: StudentModel) {
            nameTextView.text = student.name
            idTextView.text = student.id

            editImageView.setOnClickListener {
                onActionClick(student, "edit")
            }

            removeImageView.setOnClickListener {
                onActionClick(student, "delete")
            }
        }
    }
}