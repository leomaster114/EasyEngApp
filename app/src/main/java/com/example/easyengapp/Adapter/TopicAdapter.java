package com.example.easyengapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyengapp.Activity.WordDictionary;
import com.example.easyengapp.Model.Topic;
import com.example.easyengapp.R;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private ArrayList<Topic> subjects;
    private ArrayList<Integer> image_subs;
    Context mContext;

    public TopicAdapter(Context context, ArrayList<Topic> subjects, ArrayList<Integer> image_subs) {
        this.subjects = subjects;
       this.mContext = context;
       this.image_subs = image_subs;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item_sub;
        ImageView imageView;
        TextView tv_name_sub;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_sub = itemView.findViewById(R.id.tv_name_sub);
            imageView = itemView.findViewById(R.id.imgv_sub);
            item_sub = itemView.findViewById(R.id.item_sub);
        }
    }

    @NonNull
    @Override
    public TopicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_recycleview, parent, false);
        final TopicAdapter.ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.item_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // xử lý phần hiển thị danh sách các từ trong chủ đề ở đây
                Intent intent = new Intent(mContext, WordDictionary.class);
                int idTopic = subjects.get(viewHolder.getAdapterPosition()).getId();
                intent.putExtra("IdTopic",idTopic);
                mContext.startActivity(intent);
//                Toast.makeText(mContext,""+subjects.get(viewHolder.getAdapterPosition()).getName_topic(),Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_name_sub.setText(subjects.get(position).getName_topic());
        holder.imageView.setImageResource(image_subs.get(position));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
