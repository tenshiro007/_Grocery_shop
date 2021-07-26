package com.example.meimall;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirstCartFragment extends Fragment  implements CartAdapter.DeleteItems , CartAdapter.TotalPrice {

    private static final String TAG = "FirstCartFragment";
    private RecyclerView recyclerView;
    private TextView txtSum,txtNoItems;
    private Button btnNext;
    private RelativeLayout itemRelative;
    private CartAdapter adapter;
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view=getActivity().getLayoutInflater().inflate(R.layout.fragment_cart_frist,container,false);
        initView(view);

        adapter=new CartAdapter(getContext(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<GroceryItem>cartItems=Utils.getCartItems(getActivity());
        if(null !=cartItems){
            if(cartItems.size()>0){
                adapter.setItems(cartItems);
                txtNoItems.setVisibility(View.GONE);
                itemRelative.setVisibility(View.VISIBLE);
            }else{
                txtNoItems.setVisibility(View.VISIBLE);
                itemRelative.setVisibility(View.GONE);
            }
        }else{
            txtNoItems.setVisibility(View.VISIBLE);
            itemRelative.setVisibility(View.GONE);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new SecoundCartFragment());
                transaction.commit();
            }
        });


        return view;

    }

    private void initView(View view) {
        recyclerView=view.findViewById(R.id.recyclerView);
        txtSum=view.findViewById(R.id.txtTotalPrice);
        txtNoItems=view.findViewById(R.id.txtNoItem);
        btnNext=view.findViewById(R.id.btnNext);
        itemRelative=view.findViewById(R.id.itemRelative);
    }

    @Override
    public void onDeleteResult(GroceryItem item) {
        Utils.deleteItemFromCart(getActivity(),item);
        ArrayList<GroceryItem>cartItems=Utils.getCartItems(getActivity());
        if(null !=cartItems){
            if(cartItems.size()>0){
                adapter.setItems(cartItems);
                txtNoItems.setVisibility(View.GONE);
                itemRelative.setVisibility(View.VISIBLE);
            }else{
                txtNoItems.setVisibility(View.VISIBLE);
                itemRelative.setVisibility(View.GONE);
            }
        }else{
            txtNoItems.setVisibility(View.VISIBLE);
            itemRelative.setVisibility(View.GONE);
        }
    }

    @Override
    public void getTotalPrice(double price) {
        txtSum.setText(String.valueOf(price));
    }
}
