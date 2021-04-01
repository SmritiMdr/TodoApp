package com.example.todoapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {

    //Member variables
    static Repository INSTANCE;
    AppDatabase db;
    TodoDao dao;

    //Dao is passed into the Repository constructor
    private Repository(Application application){
        db=AppDatabase.getDatabase(application);
        dao=db.todoDao();
    }

    public static Repository getRepository(Application application){
        if(INSTANCE == null){
            INSTANCE=new Repository(application);
        }
        return INSTANCE;
    }

    /**
     * The getAllTasks method returns the LiveData list of tasks from Room
     * LiveData will notify the observer when the data has changed
     * @return lists of tasks
     */
    public LiveData<List<Task>> getAllTasks(){

        return dao.getAllTasks();
    }

    /**
     * Inserts tasks
     * @param task is the tasks inserted by the user
     */
    public void addTask(Task task) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(task);
            }
        });
    }

    /**
     * Deletes a all tasks present in the list
     */
    public void deleteAll(){
       AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

    /**
     * Updates a specific task
     * @param task is the task that needs to be updated
     */
    public void update(Task task){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(task);
            }
        });
    }

    /**
     * Deletes a particular task
     * @param task is the task that needs to be deleted
     */
    public void delete(Task task){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(task);
            }
        });
    }
}
