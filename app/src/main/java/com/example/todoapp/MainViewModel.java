package com.example.todoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    //Creating member variable to hold the reference of Repository
    Repository repository;

    //Creating LiveData variable
    LiveData<List<Task>> tasks;

    //Creating member variable to hold the reference of Task
    Task task;


    public MainViewModel(@NonNull Application application) {
        super(application);
        repository=Repository.getRepository(application);
        tasks=repository.getAllTasks();
    }

    /**
     * This method will return a cached list of tasks
     * @return returns a cached list of tasks
     */
    public LiveData<List<Task>> getAllTasks() {

        return tasks;
    }

    /**
     * This method will return a particular task
     * @return the task
     */
    public Task getTask() {
        return task;
    }

    /**
     * This method sets task
     * @param task is the task that needs to be set
     */
    public void setTask(Task task) {
        this.task = task;
    }

}
