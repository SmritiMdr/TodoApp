package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    //Creating variables for EditText,Button and Repository
    private EditText titleEditText;
    private EditText descEditText;
    private Button addButton;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //Getting id
        titleEditText=findViewById(R.id.title_at);
        descEditText=findViewById(R.id.desc_at);
        addButton=findViewById(R.id.add_button);
        repository=Repository.getRepository(this.getApplication());

        /**
         * Method to be executed when user clicks on add button
         * Adds new task
         */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titleEditText.getText().toString();
                String desc=descEditText.getText().toString();
                Task task=new Task(title,desc,1,new Date());
                repository.addTask(task);
                finish();
            }
        });
    }
}