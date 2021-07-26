package com.example.meimall;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.meimall.SecoundCartFragment.ORDER_KEY;

public class ThirdCartFragment extends Fragment {
    private static final String TAG = "ThirdCartFragment";
    private Button btnBack,btnCheckout;
    private TextView txtItems,txtAddress,txtPhone,txtTotalPrice;
    private RadioGroup rgPayment;

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cart_third,container,false);

        initView(view);

        Bundle bundle=getArguments();
        if(null !=bundle){
            String jsonOrder=bundle.getString(ORDER_KEY);
            Gson gson=new Gson();
            Type type=new TypeToken<Order>(){}.getType();
            Order order=gson.fromJson(jsonOrder,type);
            Log.d(TAG, "onCreateView: order"+order.toString());
            if(null != order){
                String items="";
                for(GroceryItem i : order.getItems()){
                    items+="\n\t"+i.getName();
                }
                    txtItems.setText(items);
                    txtAddress.setText(order.getAddress());
                    txtPhone.setText(order.getPhone());
                    try {
                        Log.d(TAG, "onCreateView: Order totalPrice"+order.getTotalPrice());
                        String allPrice=String.valueOf(order.getTotalPrice());
                    txtTotalPrice.setText(allPrice);

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putString(ORDER_KEY,jsonOrder);
                            SecoundCartFragment secoundCartFragment=new SecoundCartFragment();
                            secoundCartFragment.setArguments(bundle);

                            FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container,secoundCartFragment);
                            transaction.commit();

                        }
                    });
                    btnCheckout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch(rgPayment.getId()){
                                case R.id.rbPaypal:
                                    order.setPaymentMethod("Paypal");
                                    break;
                                case R.id.rbCredicard:
                                    order.setPaymentMethod("Credit Card");
                                    break;
                                default:
                                    order.setPaymentMethod("Unknown");
                                    break;
                            }
                            order.setSuccess(true);

                            Log.d(TAG, "onClick: order"+order.toString());

                            HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY);

                            OkHttpClient client=new OkHttpClient.Builder()
                                    .addInterceptor(interceptor)
                                    .build();


                            Retrofit retrofit=new Retrofit.Builder()
                                    .baseUrl("https://jsonplaceholder.typicode.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(client)
                                    .build();

                            OrderEndPoint endPoint=retrofit.create(OrderEndPoint.class);
                            Call<Order> call=endPoint.newOrder(order);
                            call.enqueue(new Callback<Order>() {
                                @Override
                                public void onResponse(Call<Order> call, Response<Order> response) {
                                    Log.d(TAG, "onResponse: code: "+response.code());
                                    if (response.isSuccessful()){
                                        Toast.makeText(getActivity(), "payment successfully", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "onResponse: responseBody"+response.body());
                                        Bundle bundle=new Bundle();
                                        bundle.putString(ORDER_KEY, gson.toJson(response.body()));

                                        PaymentResultFragment payment=new PaymentResultFragment();
                                        payment.setArguments(bundle);
                                        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container,payment);
                                        transaction.commit();

                                    }else{
                                        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container,new PaymentResultFragment());
                                        transaction.commit();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Order> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });
            }
        }
        return view;
    }

    private void initView(View view) {
        btnBack=view.findViewById(R.id.btnBack);
        btnCheckout=view.findViewById(R.id.bntCheckout);
        txtItems=view.findViewById(R.id.txtItems);
        txtAddress=view.findViewById(R.id.txtAddress);
        txtPhone=view.findViewById(R.id.txtPhone);
        txtTotalPrice=view.findViewById(R.id.txtPrice);
        rgPayment=view.findViewById(R.id.rgPayment);
    }
}
