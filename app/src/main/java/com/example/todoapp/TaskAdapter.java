package com.example.todoapp;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> data;
    final private ItemClickListener mItemClickListener;
    private Context mContext;
    int priority;

    public TaskAdapter(ItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public void setData(List<Task> tasks){

        data=tasks;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {

        Task task = data.get(position);
        holder.bind(task);

//        String priorityString = "" + priority; // converts int to String
//        holder.priorityView.setText(priorityString);
//
//        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
//        // Get the appropriate background color based on the priority
//        int priorityColor = getPriorityColor(priority);
//        priorityCircle.setColor(priorityColor);
    }

    /*
    Helper method for selecting the correct priority circle color.
    P1 = red, P2 = orange, P3 = yellow
    */
//    private int getPriorityColor(int priority) {
//        int priorityColor = 0;
//
//        switch (priority) {
//            case 1:
//                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
//                break;
//            case 2:
//                priorityColor = ContextCompat.getColor(mContext, R.color.materialGreen);
//                break;
//            default:
//                break;
//        }
//        return priorityColor;
//    }
//

    @Override
    public int getItemCount() {
        if(data==null)
        return 0;
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView description;
        //TextView priorityView;

        public ViewHolder(LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.task_item,parent,false));
            title=itemView.findViewById(R.id.title_tv);
            description=itemView.findViewById(R.id.description_tv);

            itemView.setOnClickListener(this);
        }

        public void bind(Task task){
            title.setText(task.getTitle());
            description.setText(task.getDescription());

            //priority = task.getPriority();

        }

        @Override
        public void onClick(View v) {
            Task task=data.get(getAdapterPosition());
            mItemClickListener.onItemClickListener(task);
        }
    }

    public interface ItemClickListener {
        void onItemClickListener(Task task);
    }


}
