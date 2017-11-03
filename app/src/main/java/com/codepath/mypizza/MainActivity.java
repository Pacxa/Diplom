package com.codepath.mypizza;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.codepath.mypizza.entity.Item;
import com.codepath.mypizza.fragments.ItemDetailFragment;
import com.codepath.mypizza.fragments.ItemMenuFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemMenuFragment.OnItemSelectedListener {


    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    ArrayList<Item> item_list = new ArrayList<>();

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();


        myRef.child(user.getUid()).child("Items").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
                refreshAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (savedInstanceState == null) {
            // Instance of first fragment
            ItemMenuFragment firstFragment = new ItemMenuFragment();

            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ItemDetailFragment secondFragment = new ItemDetailFragment();
            Bundle args = new Bundle();
            args.putInt("position", 0);
            secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft2.add(R.id.flContainer2, secondFragment);                               // add    Fragment
            ft2.commit();                                                            // commit FragmentTransaction
        }
    }

    @Override
    public void onPizzaItemSelected(int position) {
        Toast.makeText(this, item_list.get(position).getName(), Toast.LENGTH_SHORT).show();

        // Load Item Detail Fragment
        ItemDetailFragment secondFragment = new ItemDetailFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("itemName", item_list.get(position).getName());
        args.putString("itemPrice", Float.toString(item_list.get(position).getPrice()));
        args.putString("itemPhoto", item_list.get(position).getPathToPhoto());
        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer2, secondFragment) // replace flContainer
                    //.addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, secondFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        Item item = new Item();
        item.setName(dataSnapshot.getValue(Item.class).getName());
        item.setPrice(dataSnapshot.getValue(Item.class).getPrice());
        item.setSelected(dataSnapshot.getValue(Item.class).getSelected());
        item.setPathToPhoto(dataSnapshot.getValue(Item.class).getPathToPhoto());
        item_list.add(item);
        //item_keys.add(dataSnapshot.getKey());
    }

    public void refreshAdapter() {
        //multiSelectAdapter.selected_usersList = multiselect_list;
        //multiSelectAdapter.itemList = item_list;
        // multiSelectAdapter.notifyDataSetChanged();
    }

}
