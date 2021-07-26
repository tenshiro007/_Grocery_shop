package com.example.meimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import static com.example.meimall.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.meimall.AllCategoriesDialog.CALLING_ACTIVITY;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        Utils.initSharedPreferences(this);
        Log.d(TAG, "onCreate: initSharedPreference"+Utils.getAllItems(this));

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.drawer_open,R.string.draw_close);

        drawer.addDrawerListener(toggle);
        //animation syn
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.cart:
                        Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.categories:
//                        Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                        AllCategoriesDialog dialog=new AllCategoriesDialog();
                        Bundle bundle=new Bundle();
                        bundle.putString(CALLING_ACTIVITY,"main_activity");
                        bundle.putStringArrayList(ALL_CATEGORIES,Utils.getCategories(MainActivity.this));
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(),"all categoires dialog");
                        break;
                    case R.id.about:
                        break;
                    case R.id.licences:
                        break;
                    case R.id.terms:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new MainFragment());
        transaction.commit();

    }

    private void initView() {
        drawer=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigationView);
        toolbar=findViewById(R.id.toolbar);
    }
}