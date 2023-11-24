package yvan.lubac.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import yvan.lubac.todolistapp.Adapter.ToDoAdapter;
import yvan.lubac.todolistapp.Model.ToDoModel;
import yvan.lubac.todolistapp.Utils.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;


    private List<ToDoModel> tasksList;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        db=new DatabaseHandler(this);
        db.openDatabase();

        tasksList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db,this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        fab = findViewById(R.id.fab);

        tasksList = db.getAllTasks();
        Collections.reverse(tasksList);
        tasksAdapter.setTasks(tasksList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);

            }
        });

    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        tasksList = db.getAllTasks();
        //Pour afficher les dernières entrées dans la DB en premier (en effet, par défaut chaque nouvelle entrée en DB est à la fin de la table)
        Collections.reverse(tasksList);
        tasksAdapter.setTasks(tasksList);
    }
}