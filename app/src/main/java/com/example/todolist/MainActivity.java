package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Task> tasks = new ArrayList<>();
    private TaskAdapter adapter;
    private int nextId = 1; // Для генерации ID задач

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Инициализация адаптера
        adapter = new TaskAdapter(tasks, this::onTaskChecked, this::onTaskDeleted);
        recyclerView.setAdapter(adapter);

        // Добавление новой задачи
        Button btnAdd = findViewById(R.id.btnAdd);
        EditText editTextTask = findViewById(R.id.editTextTask);

        btnAdd.setOnClickListener(v -> {
            String taskTitle = editTextTask.getText().toString().trim();
            if (!taskTitle.isEmpty()) {
                tasks.add(new Task(nextId++, taskTitle));
                adapter.notifyItemInserted(tasks.size() - 1);
                editTextTask.setText("");
            } else {
                Toast.makeText(this, "Введите задачу!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Обработка отметки выполнения
    private void onTaskChecked(int position, boolean isChecked) {
        tasks.get(position).setCompleted(isChecked);
        adapter.notifyItemChanged(position);
    }

    // Обработка удаления задачи
    private void onTaskDeleted(int position) {
        tasks.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
