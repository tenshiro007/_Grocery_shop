package com.example.meimall;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private DeleteItems deleteItem;
    private ArrayList<GroceryItem> items = new ArrayList<>();
    private Context context;
    private Fragment fragment;
    private TotalPrice totalPrice;
    public CartAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
        calculateTotalPrice();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(items.get(position).getName());
        holder.txtPrice.setText(items.get(position).getPrice() + " $");

        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Deleting...")
                        .setMessage("Are you sure you want to delete this item?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    deleteItem = (DeleteItems) fragment;
                                    deleteItem.onDeleteResult(items.get(position));
                                } catch (ClassCastException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void calculateTotalPrice() {
        double price = 0;
        for (GroceryItem i : items) {
            price += i.getPrice();
        }
        price=Math.round(price*100.0)/100.0;
        try {
            totalPrice = (TotalPrice) fragment;
            totalPrice.getTotalPrice(price);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public interface DeleteItems {
        void onDeleteResult(GroceryItem item);
    }

    public interface TotalPrice {
        void getTotalPrice(double price);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtPrice, txtDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDelete = itemView.findViewById(R.id.txtDelete);

        }
    }
}
