package com.example.todoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends Fragment {

    //Constant fr logging
    private static final String LOG=TaskDetailFragment.class.getSimpleName();

    //Variable for Task class to create object of its type
    private Task mTask;

    //Variable for Repository class to make object of its type
    private Repository sTaskRepository;

    //Variable for MainViewModel
    private MainViewModel mViewModel;

    //Variable for View
    private View view;

    //Variables for EditText view
    private EditText textViewTitle;
    private EditText textViewDetail;

    //Variables for String Data type
    private String title;
    private String description;

    //Variable for storing the priority number
    private int priority;

    //Variable for RadioGroup view
    private RadioGroup radioG;

    //Variables for RadioButton view
    private RadioButton complete;
    private RadioButton incomplete;

    //Variables for Button
    private Button editButton;
    private Button deleteButton;

    // Constants for priority
    //Priority_Incomplete is set as default i.e. 1
    public static final int Priority_Complete = 2;
    public static final int Priority_Incomplete = 1;

    public static TaskDetailFragment newInstance() {
        return new TaskDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        sTaskRepository = Repository.getRepository(getActivity().getApplication());

        view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        updateUI(view);
        return view;

    }


    //Updating UI
    private void updateUI(View view) {

        mTask = mViewModel.getTask();

        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewTitle.setText(mTask.getTitle());

        textViewDetail = view.findViewById(R.id.textViewDetail);
        textViewDetail.setText(mTask.getDescription());

        radioG=view.findViewById(R.id.radioGroups);

        complete= view.findViewById(R.id.compButton);
        complete.setChecked(mTask.getPriority()==Priority_Complete);
        complete.setOnClickListener(mTaskListener);

        incomplete= view.findViewById(R.id.IncompButton);
        incomplete.setChecked(mTask.getPriority()==Priority_Incomplete);
        incomplete.setOnClickListener(mTaskListener);

        deleteButton = view.findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(mTaskListener);

        editButton = view.findViewById(R.id.buttonUpdate);
        editButton.setOnClickListener(mTaskListener);
    }


    public void setViewModel(MainViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    /* Create an anonymous implementation of OnClickListener for all clickable view objects */
    private View.OnClickListener mTaskListener = new View.OnClickListener() {

        public void onClick(View view) {

            //switch case to determine which view is clicked
            //methods to be executed while clicking a particular view inside switch case

            switch (view.getId()) {

                //if update button clicked
                case R.id.buttonUpdate:

                    Log.d(LOG,"Update Button Clicked!");


                    title = textViewTitle.getText().toString();
                    Log.d(LOG,"ID: "+title);
                    mTask.setTitle(title);

                    description = textViewDetail.getText().toString();
                    Log.d(LOG,"ID: "+description);

                    mTask.setDescription(description);

                    mTask.setPriority(getPriorityFromViews());

                    Log.d(LOG,"Task Status :"+mTask.getPriority());
                    sTaskRepository.update(mTask);
                    doSubmit();
                    break;


                //if complete radio button clicked
                case R.id.compButton:
                    mTask.setPriority(2);
                    priority=2;
                    Log.d(LOG," "+mTask.getPriority());
                    break;


                //if Incomplete radio button clicked
                case R.id.IncompButton:
                    mTask.setPriority(1);
                    priority=1;
                    Log.d(LOG," "+mTask.getPriority());
                    break;

                //if delete button is clicked
                case R.id.buttonDelete:
                    sTaskRepository.delete(mTask);
                    doSubmit();
                    break;

                default:
                    break;
            }
        }
    };

    //Method that returns integer when user selects a radio button
    // 1 as Incomplete
    // 2 as Complete
    public int getPriorityFromViews(){
        int checkedId=radioG.getCheckedRadioButtonId();

        switch(checkedId){

            case R.id.compButton:
                priority=2;
                break;

            case R.id.IncompButton:
                priority=1;
                break;
        }
        return priority;
    }

    private void doSubmit() {

        mViewModel.setTask(mTask);
        TaskFragment taskFragment = TaskFragment.newInstance();

        //Get the Fragment Manager and start the transaction
        assert getParentFragmentManager() != null;
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        transaction.replace(R.id.container, taskFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }



}