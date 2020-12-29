package com.example.expressblog.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expressblog.Activities.CreatPost;
import com.example.expressblog.R;
import com.example.expressblog.entities.Draft;

import java.util.List;

public class DraftAdapter extends RecyclerView.Adapter<DraftAdapter.DraftViewHolder>{

    private List<Draft> drafts;
    DraftAdapterListener draftAdapterListener;
    private Context context;
    public DraftAdapter(List<Draft> drafts, DraftAdapterListener draftAdapterListener,Context context) {
        this.draftAdapterListener = draftAdapterListener;
        this.drafts = drafts;
        this.context = context;
    }

     public interface DraftAdapterListener{
        void sendData(String title, String blogBody);
    }

    @NonNull
    @Override
    public DraftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new DraftViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_container_draft,
                parent,
                false
        ),draftAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final DraftViewHolder holder, final int position) {
        holder.setDraft(drafts.get(position));
        holder.layoutHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draftAdapterListener.sendData(drafts.get(position).getTitle(),drafts.get(position).getDesc());
                context.startActivity(new Intent(context.getApplicationContext(),CreatPost.class));
            }
        });
    }


    @Override
    public int getItemCount() {
        return drafts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class DraftViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textDesc, textDateTime, imagePath;
        LinearLayout layoutHolder;
        DraftAdapterListener draftAdapterListener;
        public DraftViewHolder(@NonNull View itemView, DraftAdapterListener draftAdapterListener) {
            super(itemView);
            this.draftAdapterListener = draftAdapterListener;
            layoutHolder  = itemView.findViewById(R.id.layout_draft);
            textTitle = itemView.findViewById(R.id.draft_title);
            textDateTime = itemView.findViewById(R.id.draft_time);
            textDesc = itemView.findViewById(R.id.draft_desc);
//            imagePath = itemView.findViewById(R.id.draft_image_path);
        }

        void setDraft(Draft draft)
        {
            Log.e("Draft Details 1",draft.getTitle());
            textTitle.setText(draft.getTitle());
            textDateTime.setText(draft.getDateTime());
            textDesc.setText(draft.getDesc());
//            imagePath.setText(draft.getImagePath());
        }

        public void startActivityOnCLick(){

        }

    }
}
