package com.example.meimall;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.provider.Telephony;
import android.util.Log;
import android.widget.GridLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class Utils {
    private static int id=0;
    private static int ORDERID=0;

    private static final String TAG = "Utils";
    public static final String ALL_ITEMS_KEY="all_items";
    private static final String DB_NAME="fake_database";
    public static final String CART_ITEMS_KEY="cart_items";

    private static Gson gson =new Gson();
//    json->arraylist
    private static Type groceryListType=new TypeToken<ArrayList<GroceryItem>>(){}.getType();

    public static void initSharedPreferences(Context context){
        //have only one sharepreference
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);

//        check sharepreference if have some data return list if not return null
        ArrayList<GroceryItem>allItems=gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY,null),groceryListType);
        if(null ==allItems){
            initAllItems(context);
        }


    }
    public static void clearSharedPreferences(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    private static void initAllItems(Context context) {
        ArrayList<GroceryItem>allItems=new ArrayList<>();
        GroceryItem milk=new GroceryItem("Milk",
                "a nutrient-rich liquid food produced by the mammary glands of mammals."
        ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQh1yuiXEGXDvgJfrbVcgm9xIPsozmX3d22MQ&usqp=CAU"
        ,"drink"
        ,2.3
        ,8);
        allItems.add(milk);

        GroceryItem ice_cream=new GroceryItem("Ice cream",
                "a colloidal emulsion made with water, ice, milk fat, milk protein, sugar and air."
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Ice_cream_with_whipped_cream%2C_chocolate_syrup%2C_and_a_wafer_%28cropped%29.jpg/330px-Ice_cream_with_whipped_cream%2C_chocolate_syrup%2C_and_a_wafer_%28cropped%29.jpg"
                ,"food"
                ,5.3
                ,10);
        ice_cream.setPopularityPoint(2);
        ice_cream.setUserPoint(2);
        allItems.add(ice_cream);

        GroceryItem soda=new GroceryItem("Soda","Carbonated drinks or fizzy drinks are beverages that contain dissolved carbon dioxide."
        ,"https://upload.wikimedia.org/wikipedia/commons/thumb/c/cf/Tumbler_of_cola_with_ice.jpg/330px-Tumbler_of_cola_with_ice.jpg"
        ,"drink",0.99,15);
        soda.setPopularityPoint(1);
        soda.setUserPoint(3);
        allItems.add(soda);


        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY,gson.toJson(allItems));
        editor.commit();
    }

    public static int getId() {
        id++;
        return id;
    }
    public static ArrayList<GroceryItem>getAllItems(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<GroceryItem>allItems=gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY,null),groceryListType);
        return allItems;
    }

    public static void  changeRate(Context context,int itemId,int newRate){
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        ArrayList<GroceryItem>allitems=gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY,null),groceryListType);
        if(null !=allitems){
            ArrayList<GroceryItem>newItems=new ArrayList<>();
            for(GroceryItem i:allitems){
                if(i.getId()==itemId){
                    i.setRate(newRate);
                    newItems.add(i);
                }else{
                    newItems.add(i);
                }
            }
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY,gson.toJson(newItems));
            editor.commit();

        }
    }
    public static void addReview(Context context,Review review){
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        ArrayList<GroceryItem>allItems=getAllItems(context);
        if(null !=allItems){
            ArrayList<GroceryItem>newItems=new ArrayList<>();
            for(GroceryItem i:allItems){
                if(i.getId()==review.getGroceryItemId()){
                    ArrayList<Review>reviews=i.getReviews();
                    reviews.add(review);
                    i.setReviews(reviews);
                    newItems.add(i);
                }else{
                    newItems.add(i);
                }
            }
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY,gson.toJson(newItems));
            editor.commit();
        }
    }
    public static ArrayList<Review>getReviewById(Context context,int itemId){

        ArrayList<GroceryItem>allItems=getAllItems(context);
        if(null !=allItems) {
            for(GroceryItem i:allItems){
                if(i.getId()==itemId){
                    ArrayList<Review>reviews=i.getReviews();
                    return reviews;
                }
            }

        }
        return null;
    }
    public static void addItemToCart(Context context,GroceryItem item){
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        ArrayList<GroceryItem>cartItems=gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY,null),groceryListType);
        if(cartItems==null){
            cartItems=new ArrayList<>();
        }
        cartItems.add(item);
        editor.remove(CART_ITEMS_KEY);
        editor.putString(CART_ITEMS_KEY, gson.toJson(cartItems));
        editor.commit();
        Log.d(TAG, "addItemToCart: Utils addCart"+cartItems);

    }
    public static ArrayList<GroceryItem>getCartItems(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<GroceryItem>cartItem=gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY,"null"),groceryListType);
        return cartItem;
    }
    public static ArrayList<GroceryItem>searchForItems(Context context,String text){
        ArrayList<GroceryItem>allItems=getAllItems(context);
            ArrayList<GroceryItem>items=new ArrayList<>();
        if(null !=allItems){
            for(GroceryItem  i:allItems){
                if(i.getName().equalsIgnoreCase(text)){
                    items.add(i);
                }
                String[] names=i.getName().split(" ");
                for(int j=0;j<names.length;j++){
                    if(text.equalsIgnoreCase(names[j])){
                        boolean doesExist=false;
                        for(GroceryItem k:items){
                            if(k.getId()==i.getId()){
                                doesExist=true;
                            }
                        }
                        if(!doesExist){
                            items.add(i);
                        }
                    }
                }
            }
        }
        return items;
    }
    public static ArrayList<String>getCategories(Context context){
        ArrayList<GroceryItem>allItems=getAllItems(context);
            ArrayList<String>categories=new ArrayList<>();
        if(null !=allItems){
            for(GroceryItem i:allItems){
                boolean doesExist=false;
                for(String s:categories){
                    if(i.getCategory().equals(s)){
                        doesExist=true;
                    }
                }
                if(!doesExist){
                    categories.add(i.getCategory());
                }
            }
        }
        return categories;
    }

    public static ArrayList<GroceryItem>getItemsByCategory(Context context,String category){
        ArrayList<GroceryItem>allItems=getAllItems(context);
        ArrayList<GroceryItem>items=new ArrayList<>();
        if(allItems!=null){
            for(GroceryItem i:allItems){
                if(i.getCategory().equals(category)){
                    items.add(i);
                }
            }
        }
        return items;
    }

    public static void deleteItemFromCart(Context context,GroceryItem item){
        ArrayList<GroceryItem> cartItems=getCartItems(context);
        if(null !=cartItems){
            ArrayList<GroceryItem> newItems=new ArrayList<>();
            for(GroceryItem i:cartItems){
                if(i.getId()!=item.getId()){
                    newItems.add(i);
                }
            }
            SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.remove(CART_ITEMS_KEY);
            editor.putString(CART_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }
    public static int getOrderId(){
        ORDERID++;
        return ORDERID;
    }
    public static void clearCartItems(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.commit();

    }
    public static void increatePopularityPoint(Context context,GroceryItem item,int point){
        ArrayList<GroceryItem>allItems=getAllItems(context);
        if(null !=allItems){
            ArrayList<GroceryItem>newItems=new ArrayList<>();
            for(GroceryItem i:allItems){
                if(i.getId()==item.getId()){
                    i.setPopularityPoint(i.getPopularityPoint()+point);
                    newItems.add(i);
                }else{
                    newItems.add(i);
                }
            }
            SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }
}
