package com.codepath.mypizza.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.codepath.mypizza.NameIntput;
import com.codepath.mypizza.R;
import com.codepath.mypizza.data.Pizza;
import com.codepath.mypizza.entity.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shyam Rokde on 8/5/16.
 */
public class PizzaMenuFragment extends Fragment {


    ArrayAdapter<String> itemsAdapter;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private Button addItemButton;
    ArrayList<Item> item_list = new ArrayList<>();


    ArrayList<String> items_names = new ArrayList<String>();

    FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().

                getReference();

        database = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the xml file for the fragment

        item_list = new ArrayList<>();
        items_names = new ArrayList<>();


        myRef.child(user.getUid()).

                child("Items").

                addChildEventListener(new ChildEventListener() {
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

        itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items_names);
        return inflater.inflate(R.layout.fragment_pizza_menu, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ListView lvItems = (ListView) view.findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // go to activity to load pizza details fragment
                listener.onPizzaItemSelected(position); // (3) Communicate with Activity using Listener
            }
        });

//        addItemButton = (Button) getView().findViewById(R.id.addItemButton);
//        addItemButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startInputNameActivity();
//            }
//        });
    }

    private OnItemSelectedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {      // context instanceof YourActivity
            this.listener = (OnItemSelectedListener) context; // = (YourActivity) context
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement PizzaMenuFragment.OnItemSelectedListener");
        }
    }

    public interface OnItemSelectedListener {

        void onPizzaItemSelected(int position);
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        Item item = new Item();
        item.setName(dataSnapshot.getValue(Item.class).getName());
        item.setPrice(dataSnapshot.getValue(Item.class).getPrice());
        item.setSelected(dataSnapshot.getValue(Item.class).getSelected());
        item.setPathToPhoto(dataSnapshot.getValue(Item.class).getPathToPhoto());
        item_list.add(item);
        items_names.add(item.getName());
    }

    public void refreshAdapter() {

//        for (Item item : item_list) {
//            items_names.add(item.getName());
//        }
        itemsAdapter.notifyDataSetChanged();

    }

//    public void startInputNameActivity(){
//        Intent i = new Intent(getView().getContext(), NameIntput.class);
//        startActivity(i);
//    }

}
