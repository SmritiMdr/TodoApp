package com.example.todoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment implements TaskAdapter.ItemClickListener{

    //Constant for logging
    private static final String LOG_TAG = TaskFragment.class.getSimpleName();

    //Variables for RecyclerView, TaskAdapter, Repository, MainViewModel,FloatingActionButton,EditText and Button views

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private Repository repository;
    private MainViewModel viewModel;
    private FloatingActionButton add_button;
    private FloatingActionButton delete_button;
    private EditText mShareTextEditText;
    private Button share_btn;

    public TaskFragment() {
        // Required empty public constructor
    }


    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreateView");



        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if (tasks != null)
                    adapter.setData(tasks);
            }
        });

        //Inflating the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        updateUI(view);
        return view;
    }

    private void updateUI(View view) {

        recyclerView=view.findViewById(R.id.task_list);
        repository=Repository.getRepository(getActivity().getApplication());

        //Initializing the adapter and attaching it to the RecyclerView
        adapter=new TaskAdapter(getActivity(),this);
        recyclerView.setAdapter(adapter);

        add_button=view.findViewById(R.id.floatingActionButton);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplication(), AddTaskActivity.class);
                startActivity(intent);

                assert getParentFragmentManager() != null;
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                //transaction.replace(R.id.container, taskAddFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        delete_button=view.findViewById(R.id.delete_btn);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.deleteAll();

            }
        });


        mShareTextEditText=view.findViewById(R.id.share_text);
        share_btn=view.findViewById(R.id.share_button);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = mShareTextEditText.getText().toString();
                String mimeType = "text/plain";

                ShareCompat.IntentBuilder
                        .from(getActivity())
                        .setType(mimeType)
                        .setChooserTitle(R.string.chooser_title_share_text)
                        .setText(txt)
                        .startChooser();
            }
        });
    }

    @Override
    public void onItemClickListener(Task task) {
        viewModel.setTask(task);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new TaskDetailFragment())
        .commit();
    }


}