package com.example.todoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends Fragment {
    private static final String LOG_TAG = TaskDetailFragment.class.getSimpleName();

    private Task mTask;
    private Repository sTaskRepository;
    private MainViewModel mViewModel;

    public static TaskDetailFragment newInstance() {
        return new TaskDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);


        sTaskRepository = Repository.getRepository(getActivity().getApplication());

        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        updateUI(view);
        return view;

    }

    private void updateUI(View view) {

        mTask = mViewModel.getTask();

        TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewTitle.setText(mTask.getTitle());

        TextView textViewDetail = view.findViewById(R.id.textViewDetail);
        textViewDetail.setText(mTask.getDescription());

        Button deleteButton = view.findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(mTaskListener);

        Button editButton = view.findViewById(R.id.buttonUpdate);
        editButton.setOnClickListener(mTaskListener);
    }

    public void setViewModel(MainViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    /* Create an anonymous implementation of OnClickListener for all clickable view objects */
    private View.OnClickListener mTaskListener = new View.OnClickListener() {

        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.buttonUpdate:
                    sTaskRepository.update(mTask);
                    doSubmit();
                    break;


                case R.id.buttonDelete:
                    sTaskRepository.delete(mTask);
                    doSubmit();
                    break;

                default:
                    break;
            }
        }
    };

    private void doSubmit() {

        mViewModel.setTask(mTask);

        TaskFragment taskFragment = TaskFragment.newInstance();

        assert getParentFragmentManager() != null;
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        transaction.replace(R.id.container, taskFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}