package com.example.taskmaster.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmaster.data.Task
import com.example.taskmaster.data.TaskDatabase
import com.example.taskmaster.databinding.ActivityMainBinding
import com.example.taskmaster.repository.TaskRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskRepository(TaskDatabase.getDatabase(this).taskDao()))
    }

    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView Adapter with callbacks
        adapter = TaskAdapter(
            onDeleteClick = { task -> viewModel.delete(task) },
            onCheckChanged = { updatedTask -> viewModel.update(updatedTask) }
        )

        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTasks.adapter = adapter

        // Observe LiveData from ViewModel
        viewModel.allTasks.observe(this) { tasks ->
            adapter.submitList(tasks)
        }

        // Add Task FAB action
        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val input = EditText(this).apply {
            hint = "Enter task"
            inputType = InputType.TYPE_CLASS_TEXT
        }

        AlertDialog.Builder(this)
            .setTitle("New Task")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val title = input.text.toString().trim()
                if (title.isNotEmpty()) {
                    viewModel.insert(Task(title = title))
                } else {
                    Toast.makeText(this, "Task title can't be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
