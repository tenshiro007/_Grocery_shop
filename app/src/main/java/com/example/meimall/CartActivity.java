package com.example.meimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.meimall.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.meimall.AllCategoriesDialog.CALLING_ACTIVITY;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";
    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Log.d(TAG, "onCreate: cart items"+Utils.getCartItems(CartActivity.this));
        initView();
        initBottomNavView();
        setSupportActionBar(toolbar);

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new FirstCartFragment());
        transaction.commit();
    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {

                switch(item.getItemId()){
                    case R.id.search:
                        Intent intent=new Intent(CartActivity.this,SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.home:
                         intent=new Intent(CartActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initView() {
        bottomNavigationView=findViewById(R.id.bottomNav);
        toolbar=findViewById(R.id.toolbar);
    }
}