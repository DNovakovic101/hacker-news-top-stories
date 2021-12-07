package pmf.novak101.hackernewstopstories.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pmf.novak101.hackernewstopstories.R;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder>  {


    private ArrayList<HackerNewsStory> localDataSet;
    private  ItemClickListener clickListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder   {
        private final TextView textViewName;

        public ViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewTitle);
        }

        public TextView getTextViewName() {
            return textViewName;
        }




        }


    public StoryAdapter(ArrayList<HackerNewsStory> dataSet, ItemClickListener clickListener) {
        this.clickListener = clickListener;
        localDataSet = dataSet;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getTextViewName().setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClicked(localDataSet.get(position));
            }
        });
        holder.getTextViewName().setText(localDataSet.get(position).getStoryTitle());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}
