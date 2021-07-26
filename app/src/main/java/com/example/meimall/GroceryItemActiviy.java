package com.example.meimall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.security.AllPermission;
import java.util.ArrayList;

public class GroceryItemActiviy extends AppCompatActivity implements AddReviewDialog.AddReview {
    public static final String GROCERY_ITEM_KEY = "incomming_item";
    private static final String TAG = "GroceryItemActiviy";
    private ReviewAdapter adapter;
    private RecyclerView recyclerView;
    private TextView txtName, txtPrice, txtDesc, txtAddReview;
    private ImageView itemImage, firstEmptyStar, firstFillStar, secoundEmStar,
            secoundFillStar, thridEmStar, thirdFillStar;
    private Button btnAddToCart;
    private RelativeLayout firstStarRelative, secoundRelative, thirdRelative;
    private Toolbar toolbar;
    private GroceryItem incommingItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item_activiy);

        initView();
        adapter = new ReviewAdapter(this);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        if (null != intent) {
            incommingItem = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            if (null != incommingItem) {
                txtName.setText(incommingItem.getName());
                txtDesc.setText(incommingItem.getDescription());
                txtPrice.setText(String.valueOf(incommingItem.getPrice()) + " $");
                Glide.with(this).asBitmap()
                        .load(incommingItem.getImageUrl())
                        .into(itemImage);

//                ArrayList<Review> reviews = incommingItem.getReviews();
                ArrayList<Review>reviews=Utils.getReviewById(this,incommingItem.getId());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                if (null != reviews) {
                    if (reviews.size() > 0) {
                        adapter.setReviews(reviews);
                    }
                }
                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     Utils.addItemToCart(GroceryItemActiviy.this,incommingItem);
//                        Log.d(TAG, "onClick: cart Items"+Utils.getCartItems(GroceryItemActiviy.this));
                        Intent intent =new Intent(GroceryItemActiviy.this,CartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                txtAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddReviewDialog dialog = new AddReviewDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(GROCERY_ITEM_KEY, incommingItem);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "add Review");
                    }
                });

                handleRating();

            }
        }
    }


    private void handleRating() {
        Log.d(TAG, "handleRating: Started");
        switch (incommingItem.getRate()) {
            case 0:
                firstFillStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.VISIBLE);
                secoundFillStar.setVisibility(View.GONE);
                secoundEmStar.setVisibility(View.VISIBLE);
                thirdFillStar.setVisibility(View.GONE);
                thridEmStar.setVisibility(View.VISIBLE);
                break;
            case 1:
                firstFillStar.setVisibility(View.VISIBLE);
                firstEmptyStar.setVisibility(View.GONE);
                secoundFillStar.setVisibility(View.GONE);
                secoundEmStar.setVisibility(View.VISIBLE);
                thirdFillStar.setVisibility(View.GONE);
                thridEmStar.setVisibility(View.VISIBLE);
                break;
            case 2:
                firstFillStar.setVisibility(View.VISIBLE);
                firstEmptyStar.setVisibility(View.GONE);
                secoundFillStar.setVisibility(View.VISIBLE);
                secoundEmStar.setVisibility(View.GONE);
                thirdFillStar.setVisibility(View.GONE);
                thridEmStar.setVisibility(View.VISIBLE);
                break;
            case 3:
                firstFillStar.setVisibility(View.VISIBLE);
                firstEmptyStar.setVisibility(View.GONE);
                secoundFillStar.setVisibility(View.VISIBLE);
                secoundEmStar.setVisibility(View.GONE);
                thirdFillStar.setVisibility(View.VISIBLE);
                thridEmStar.setVisibility(View.GONE);
            default:
                break;

        }
        firstStarRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: firstRelative click");
                if (incommingItem.getRate() != 1) {
                    Utils.changeRate(GroceryItemActiviy.this, incommingItem.getId(), 1);
                    incommingItem.setRate(1);
                    handleRating();
                }
            }
        });
        secoundRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: secoundRelative click");
                if (incommingItem.getRate() != 2) {
                    Utils.changeRate(GroceryItemActiviy.this, incommingItem.getId(), 2);
                    incommingItem.setRate(2);
                    handleRating();
                }
            }
        });
        thirdRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incommingItem.getRate() != 3) {
                    Utils.changeRate(GroceryItemActiviy.this, incommingItem.getId(), 3);
                    incommingItem.setRate(3);
                    handleRating();
                }
            }
        });
    }

    private void initView() {
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDesc = findViewById(R.id.txtDesc);
        txtAddReview = findViewById(R.id.txtAddReview);

        recyclerView = findViewById(R.id.reviewRecView);

        itemImage = findViewById(R.id.image);
        firstEmptyStar = findViewById(R.id.firstEmptyStar);
        firstFillStar = findViewById(R.id.firstFillStar);
        secoundEmStar = findViewById(R.id.secoundEmptyStar);
        secoundFillStar = findViewById(R.id.secoundFillStar);
        thridEmStar = findViewById(R.id.thirdEmptyStar);
        thirdFillStar = findViewById(R.id.thirdFillStar);

        firstStarRelative = findViewById(R.id.firstStarRelative);
        secoundRelative = findViewById(R.id.secoundStarRelative);
        thirdRelative = findViewById(R.id.thirdStarRelative);

        toolbar = findViewById(R.id.toolbar);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    @Override
    public void onAddReviewResult(Review review) {
        Log.d(TAG, "onAddReviewResult: new review" + review);
        Utils.addReview(this, review);
        ArrayList<Review> reviews = Utils.getReviewById(this, review.getGroceryItemId());
        if (null != reviews) {
            adapter.setReviews(reviews);
        }
    }
}