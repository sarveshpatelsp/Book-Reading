package com.example.bookreadingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final ArrayList<ModelClass> arrayList;

    public Adapter(ArrayList<ModelClass> bookInfoArrayList) {
        this.arrayList = bookInfoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelClass modelClass = arrayList.get(position);
        holder.title.setText(modelClass.getTitle());
        holder.publisher.setText(modelClass.getPublisher());
        holder.pageCount.setText("No of Pages : " + modelClass.getPageCount());
        Picasso.get().load(modelClass.getThumbnail()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, publisher, pageCount;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title_text);
            publisher = itemView.findViewById(R.id.card_publisher_text);
            pageCount = itemView.findViewById(R.id.card_page_count_text);
            imageView = itemView.findViewById(R.id.card_image_view);
        }
    }
}