package com.example.meimall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

import static com.example.meimall.SecoundCartFragment.ORDER_KEY;

public class PaymentResultFragment extends Fragment {

    private TextView txtMessage;
    private Button btnHome;
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_payment_result,container,false);

        initView(view);

        Bundle bundle=getArguments();
        if(null !=bundle){
            String jsonOrder=bundle.getString(ORDER_KEY);
            if(null !=jsonOrder){
                Gson gson=new Gson();
                Type type=new TypeToken<Order>(){}.getType();
                Order order=gson.fromJson(jsonOrder,type);
                if(null !=order){
                    txtMessage.setText("Payment was successful\n\t your order will arrive in 3days");
                    // TODO: 7/25/2021 incress popularity point
                    // TODO: 7/25/2021 Incress user point
                    Utils.clearCartItems(getContext());
                    for(GroceryItem i: order.getItems()){
                        Utils.increatePopularityPoint(getActivity(),i,1);
                    }
                }else{
                    txtMessage.setText("Payment failed,\n Plase try payment method");
                }
            }else{
                txtMessage.setText("Payment failed,\n Plase try payment method");
            }
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public void initView(View view){
        txtMessage=view.findViewById(R.id.txtMessage);
        btnHome=view.findViewById(R.id.btnHome);

    }
}
