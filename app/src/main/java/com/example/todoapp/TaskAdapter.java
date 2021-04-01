package com.example.todoapp;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.data.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    // Class variable for the List that holds task data of Task type
    private List<Task> data;

    //Variable to handle item clicks
    final private ItemClickListener mItemClickListener;

    //Class variable for the context
    private Context context;

    //Constant for logging
    private String LOG=TaskAdapter.class.getSimpleName();

    /**
     * Constructor for the TaskAdapter that initializes the Context.
     * @param context is the current Context
     * @param mItemClickListener is the ItemClickListener
     */
    public TaskAdapter(Context context,ItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
        this.context=context;
    }


    /**
     * This method is called when view holder are created to fill the RecyclerView
     * Returns a view holder that holds the view for each task
     */


    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater,parent);
    }



    /**
     * This method is called by the RecyclerView to display data at a specified position in the Cursor.
     * @param holder is The ViewHolder to bind Cursor data to
     * @param position is The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {

        Task task = data.get(position);
        holder.bind(task);

    }



    /**
     * When data changes, this method will update the list of task and will notify the adapter to use the new values
     * @param tasks that need to be set
     */
    public void setData(List<Task> tasks){
        data=tasks;
        notifyDataSetChanged();
    }


    /**
     * Returns the number of items to display
     * @return the number of items to display
     */
    @Override
    public int getItemCount() {
        if(data==null)
        return 0;
        return data.size();
    }

    /**
     * Creating an interface
     */
    public interface ItemClickListener {
        void onItemClickListener(Task task);
    }

    //Inner class for creating view holders
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Variables for TextView
        private TextView title;
        private TextView description;
        private TextView statusTick;

        public ViewHolder(LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.task_item,parent,false));
            title=itemView.findViewById(R.id.title_tv);
            description=itemView.findViewById(R.id.description_tv);
            statusTick=itemView.findViewById(R.id.priorityTextView);
            itemView.setOnClickListener(this);
        }

        public void bind(Task task){
            title.setText(task.getTitle());
            description.setText(task.getDescription());

            Drawable color = null;

            //Setting the colour of the icons to their respective color they symbolize
            //Incomplete as red
            //Complete as green

            if(task.getPriority()==1){
                Log.d(LOG,""+task.getPriority());
                color=ContextCompat.getDrawable(context,R.drawable.ic_baseline_incomplete_check_24);
            }

            else{
               color= ContextCompat.getDrawable(context,R.drawable.ic_baseline_complete_check_24);
            }

            statusTick.setBackground(color);
        }

        @Override
        public void onClick(View v) {
            Task task=data.get(getAdapterPosition());
            mItemClickListener.onItemClickListener(task);
        }
    }
}
