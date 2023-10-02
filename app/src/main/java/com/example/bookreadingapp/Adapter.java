package com.example.bookreadingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    private final ArrayList<ModelClass> arrayList;
    private final Context context;
    public Adapter(ArrayList<ModelClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_card , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.viewHolder holder, int position) {
        ModelClass modelClass = arrayList.get(position);
        holder.titleCard.setText(modelClass.getTitle());
        holder.publisherCard.setText(modelClass.getPublisher());
        holder.pageCountCard.setText("No. of pages = " + modelClass.getPageCount());
        Picasso.get().load(modelClass.getThumbnail()).into(holder.imageViewCard);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context , "CLicked" , Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView titleCard, publisherCard, pageCountCard;
        ImageView imageViewCard;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titleCard = itemView.findViewById(R.id.card_title_text);
            publisherCard = itemView.findViewById(R.id.card_publisher_text);
            pageCountCard = itemView.findViewById(R.id.card_page_count_text);
            imageViewCard = itemView.findViewById(R.id.image_view);
        }
    }
}
