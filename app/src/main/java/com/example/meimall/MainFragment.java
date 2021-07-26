package com.example.meimall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment {

    private RecyclerView newItemRecy,popularRecy,suggestedItemRecy;
    private BottomNavigationView bottomNavigationView;

    private GroceryItemAdapter newItemAdapter,popularAdapter,suggestedAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);

        initView(view);
        initBottomNavView();

//        Utils.clearSharedPreferences(getActivity());

        initRecView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecView();
    }

    private void initRecView() {
        newItemAdapter=new GroceryItemAdapter(getContext());
        popularAdapter=new GroceryItemAdapter(getActivity());
        suggestedAdapter=new GroceryItemAdapter(getActivity());

        newItemRecy.setAdapter(newItemAdapter);
        newItemRecy.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        popularRecy.setAdapter(popularAdapter);
        popularRecy.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        suggestedItemRecy.setAdapter(suggestedAdapter);
        suggestedItemRecy.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        ArrayList<GroceryItem>newItems=Utils.getAllItems(getActivity());
        if(null !=newItems){
            Comparator<GroceryItem>newItemsCompariter=new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getId() -o2.getId();
                }
            };

            Comparator<GroceryItem>reverseComparator= Collections.reverseOrder(newItemsCompariter);
            Collections.sort(newItems,reverseComparator);
            newItemAdapter.setItems(newItems);
        }

        ArrayList<GroceryItem> popularItems=Utils.getAllItems(getActivity());
        if(null != popularItems){
            Comparator<GroceryItem>popularItemsComparitor=new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getPopularityPoint()-o2.getPopularityPoint();
                }
            };
            Collections.sort(popularItems,Collections.reverseOrder(popularItemsComparitor));
            popularAdapter.setItems(popularItems);
        }

        ArrayList<GroceryItem>suggestedItems=Utils.getAllItems(getActivity());
        if(null !=suggestedItems){
            Comparator<GroceryItem>suggestItemsComparitor=new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getUserPoint()-o2.getUserPoint();
                }
            };
            Collections.sort(suggestedItems,Collections.reverseOrder(suggestItemsComparitor));
            suggestedAdapter.setItems(suggestedItems);
        }
    }

//    private int newItemsCutomComparitor(GroceryItem item1,GroceryItem item2){
//        if(item1.getId() >item2.getId()){
//            return 1;
//        }else if(item1.getId() < item2.getId()){
//            return -1;
//        }else{
//            return 0;
//        }
//    }

    //default select home when start screen
    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        // TODO: 7/20/2021 finish bottom menu
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                switch(item.getItemId()){
                    case R.id.search:
                        Intent intent=new Intent(getActivity(),SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.home:
                        break;
                    case R.id.cart:
                         intent=new Intent(getActivity(),CartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initView(View view) {
        bottomNavigationView=view.findViewById(R.id.bottomView);
        newItemRecy=view.findViewById(R.id.newItemRecView);
        popularRecy=view.findViewById(R.id.popularRecyView);
        suggestedItemRecy=view.findViewById(R.id.suggestRecyView);
    }
}
