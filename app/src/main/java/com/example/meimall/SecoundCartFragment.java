package com.example.meimall;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SecoundCartFragment extends Fragment {
    private static final String TAG = "SecoundCartFragment";
    public static final String ORDER_KEY="order";
    private EditText edTxtAddress,edTxtZipcode,edTxtPhone,edTxtEmail;
    private Button btnNext,btnBack;
    private TextView txtWarning;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cart_secound,container,false);

        initView(view);

        Bundle bundle=getArguments();
        if(null !=bundle){
            String jsonOrder= bundle.getString(ORDER_KEY);
            if(null !=jsonOrder){
                Gson gson=new Gson();
                Type type=new TypeToken<Order>(){}.getType();
                Order order=gson.fromJson(jsonOrder,type);
                if(null !=order){
                    edTxtPhone.setText(order.getPhone());
                    edTxtZipcode.setText(order.getZipcode());
                    edTxtEmail.setText(order.getEmail());
                    edTxtAddress.setText(order.getAddress());
                }
            }
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new FirstCartFragment());
                transaction.commit();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()){
                    txtWarning.setVisibility(View.GONE);

                    ArrayList<GroceryItem>cartItems=Utils.getCartItems(getActivity());
                    if(null !=cartItems){
                        Order order=new Order();
                        order.setItems(cartItems);
                        order.setAddress(edTxtAddress.getText().toString());
                        order.setPhone(edTxtPhone.getText().toString());
                        order.setEmail(edTxtEmail.getText().toString());
                        order.setZipcode(edTxtZipcode.getText().toString());
                        order.setTotalPrice(calculateTotalPrice(cartItems));

                        Gson gson=new Gson();
                        String jsonOrder=gson.toJson(order);
                        Bundle bundle=new Bundle();
                        bundle.putString(ORDER_KEY,jsonOrder);

                        Log.d(TAG, "onClick: bundle"+ bundle.toString());

                        ThirdCartFragment thirdCartFragment=new ThirdCartFragment();
                        thirdCartFragment.setArguments(bundle);

                        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container,thirdCartFragment);
                        transaction.commit();

                    }

                }else{
                    txtWarning.setVisibility(View.VISIBLE);
                    txtWarning.setText("Plase Fill All The Blanks");
                }
            }
        });
        return view;
    }

    private boolean validateData() {
        if(edTxtAddress.getText().toString().equals("")||edTxtEmail.getText().toString().equals("")||
                edTxtZipcode.getText().toString().equals("")||edTxtPhone.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private void initView(View view) {
        edTxtAddress=view.findViewById(R.id.edTxtAddress);
        edTxtEmail=view.findViewById(R.id.edTxtEmail);
        edTxtPhone=view.findViewById(R.id.edTxtPhone);
        edTxtZipcode=view.findViewById(R.id.edTxtZipCode);

        btnBack=view.findViewById(R.id.btnBack);
        btnNext=view.findViewById(R.id.btnNext);
        txtWarning=view.findViewById(R.id.txtWarning);
    }
    public double calculateTotalPrice(ArrayList<GroceryItem>items) {
        double price = 0;
        for (GroceryItem i : items) {
            price += i.getPrice();
        }
        price=Math.round(price*100.0)/100.0;
        return price;
    }
}
