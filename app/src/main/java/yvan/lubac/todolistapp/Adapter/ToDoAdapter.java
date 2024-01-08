package yvan.lubac.todolistapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import yvan.lubac.todolistapp.AddNewTask;
import yvan.lubac.todolistapp.MainActivity;
import yvan.lubac.todolistapp.Model.ToDoModel;
import yvan.lubac.todolistapp.R;
import yvan.lubac.todolistapp.Utils.DatabaseHandler;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder>{
    // for data
    private List<ToDoModel> todoList;
    private MainActivity activity;
    private DatabaseHandler db;

    //constructor
    public ToDoAdapter(DatabaseHandler db, MainActivity activity){
        this.db=db;
        this.activity= activity;
    }

    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);

        return new ViewHolder(itemView);

    }


    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    db.updateStatus(item.getId(),1);
                }
                else {
                    db.updateStatus(item.getId(),0);
                }
            }
        });


    }

    public int getItemCount(){
        return todoList.size();
    }

    private boolean toBoolean (int n){
        return n!=0;
    }

    public Context getContext() {
        return activity;
    }

    public void setTasks (List<ToDoModel> todoList) {
        this.todoList=todoList;
        notifyDataSetChanged();
    }

    public void deleteItem (int position) {
        ToDoModel item = todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);

    }

    public void editItem (int position) {
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        //AddNewTask fragment = new AddNewTask();
        AddNewTask fragment = AddNewTask.newInstance();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    //ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox task;
        ViewHolder(View view){
            super (view);
            task = view.findViewById(R.id.todoCheckBox);

        }



    }

}
