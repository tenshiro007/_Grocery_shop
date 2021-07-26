package com.example.meimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.meimall.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.meimall.AllCategoriesDialog.CALLING_ACTIVITY;

public class SearchActivity extends AppCompatActivity implements AllCategoriesDialog.GetCategory {

    private static final String TAG = "SearchActivity";
    private MaterialToolbar toolbar;
    private EditText searchBox;
    private ImageView btnSearch;
    private TextView firstCat,secoundCat,thirdCat,txtAllCategories;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private GroceryItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        initBottomNavView();
        setSupportActionBar(toolbar);

        adapter=new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        Intent intent=getIntent();
        if(null !=intent){
            String category=intent.getStringExtra("category");
            if(category!=null){
                ArrayList<GroceryItem>items=Utils.getItemsByCategory(SearchActivity.this,category);
                if(null !=items){

                adapter.setItems(items);
                }
            }
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "click", Toast.LENGTH_SHORT).show();
                initSearch();
            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayList<String>categories=Utils.getCategories(this);
        if(categories.size()>0){
            if(categories.size()==1){
                showCategories(categories,1);
            }else if(categories.size()==2){
                showCategories(categories,2);
            }else if(categories.size()==3) {
                showCategories(categories, 3);
            }
        }
        txtAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(SearchActivity.this, "Testing", Toast.LENGTH_SHORT).show();
               AllCategoriesDialog dialog=new AllCategoriesDialog();
               Bundle bundle=new Bundle();
                bundle.putStringArrayList(ALL_CATEGORIES,Utils.getCategories(SearchActivity.this));
                bundle.putString(CALLING_ACTIVITY,"search_activity");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(),"all categories dialog");
            }
        });
    }
    private void showCategories(ArrayList<String>categories,int i){
        switch (i){
            case 1:
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));
                secoundCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);

                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem>items=Utils.getItemsByCategory(SearchActivity.this,categories.get(0));
                        if(null !=items){
                            adapter.setItems(items);
                        }
                    }
                });
                break;
            case 2:
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));
                secoundCat.setVisibility(View.VISIBLE);
                secoundCat.setText(categories.get(1));
                thirdCat.setVisibility(View.GONE);

                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem>items=Utils.getItemsByCategory(SearchActivity.this,categories.get(0));
                        if(null !=items){
                            adapter.setItems(items);
                        }
                    }
                });
                secoundCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem>items=Utils.getItemsByCategory(SearchActivity.this,categories.get(1));
                        if(null !=items){
                            adapter.setItems(items);
                        }
                    }
                });

                break;
            case 3:
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));
                secoundCat.setVisibility(View.VISIBLE);
                secoundCat.setText(categories.get(1));
                thirdCat.setVisibility(View.VISIBLE);
                thirdCat.setText(categories.get(1));
                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem>items=Utils.getItemsByCategory(SearchActivity.this,categories.get(0));
                        if(null !=items){
                            adapter.setItems(items);
                        }
                    }
                });
                secoundCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem>items=Utils.getItemsByCategory(SearchActivity.this,categories.get(1));
                        if(null !=items){
                            adapter.setItems(items);
                        }
                    }
                });
                thirdCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem>items=Utils.getItemsByCategory(SearchActivity.this,categories.get(2));
                        if(null !=items){
                            adapter.setItems(items);
                        }
                    }
                });
                break;
            default:
                firstCat.setVisibility(View.GONE);
                secoundCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);
                break;
        }
    }


    private void initSearch() {
        if(!searchBox.getText().toString().equals("")){
            // TODO: 7/24/2021 get Items
            String name=searchBox.getText().toString();
            Log.d(TAG, "initSearch: searchBox"+name);
            ArrayList<GroceryItem>items=Utils.searchForItems(this,name);
            if(null !=items){
                Log.d(TAG, "initSearch: items"+items);
                adapter.setItems(items);
            }
        }
    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.search);
        // TODO: 7/20/2021 finish bottom menu
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.search:
                        break;
                    case R.id.home:
                        Intent intent=new Intent(SearchActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
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
        toolbar=findViewById(R.id.toolbar);
        searchBox=findViewById(R.id.searchBox);
        btnSearch=findViewById(R.id.btnSearch);
        firstCat=findViewById(R.id.firstCategory);
        secoundCat=findViewById(R.id.secoundCategory);
        thirdCat=findViewById(R.id.thirdCategory);
        bottomNavigationView=findViewById(R.id.bottomView);
        recyclerView=findViewById(R.id.categoriesRec);
        txtAllCategories=findViewById(R.id.txtAllCategories);
    }

    @Override
    public void onGetCategoryResult(String category) {
        ArrayList<GroceryItem>items=Utils.getItemsByCategory(this,category);
        if(null !=items){
            adapter.setItems(items);
        }
    }
}