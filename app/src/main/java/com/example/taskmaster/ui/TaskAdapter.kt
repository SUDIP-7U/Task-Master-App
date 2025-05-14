package com.example.taskmaster.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.R
import com.example.taskmaster.data.Task

class TaskAdapter(
    private val onDeleteClick: (Task) -> Unit,
    private val onCheckChanged: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkboxDone)
        val title: TextView = view.findViewById(R.id.textViewTaskTitle)
        val deleteButton: ImageButton = view.findViewById(R.id.buttonDelete)

        fun bind(task: Task) {
            title.text = task.title
            checkBox.isChecked = task.isDone

            checkBox.setOnCheckedChangeListener(null)
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (task.isDone != isChecked) {
                    onCheckChanged(task.copy(isDone = isChecked))
                }
            }

            deleteButton.setOnClickListener {
                onDeleteClick(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem == newItem
}
