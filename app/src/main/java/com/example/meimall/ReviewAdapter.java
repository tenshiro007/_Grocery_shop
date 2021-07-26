package com.example.meimall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Review> reviews=new ArrayList<>();

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ReviewAdapter.ViewHolder holder, int position) {
        holder.txtReview.setText(reviews.get(position).getText());
        holder.txtUsername.setText(reviews.get(position).getUserName());
        holder.txtDate.setText(reviews.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtUsername,txtReview,txtDate;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            txtUsername=itemView.findViewById(R.id.txtUserName);
            txtDate=itemView.findViewById(R.id.txtDate);
            txtReview=itemView.findViewById(R.id.txtReview);
        }
    }
}
