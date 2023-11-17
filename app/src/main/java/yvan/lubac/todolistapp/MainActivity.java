package yvan.lubac.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import yvan.lubac.todolistapp.Adapter.ToDoAdapter;
import yvan.lubac.todolistapp.Model.ToDoModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private List<ToDoModel> tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        tasksList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        //Données de tests
        ToDoModel task = new ToDoModel();
        task.setTask("This is a Test task");
        task.setStatus(0);
        task.setId(1);

        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);

        tasksAdapter.setTasks(tasksList);









    }
}