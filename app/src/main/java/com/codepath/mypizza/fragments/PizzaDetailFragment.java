package com.codepath.mypizza.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.mypizza.NameIntput;
import com.codepath.mypizza.R;
import com.codepath.mypizza.data.Pizza;
import com.codepath.mypizza.util.ImageUtils;

import org.w3c.dom.Text;

/**
 * Created by Shyam Rokde on 8/5/16.
 */
public class PizzaDetailFragment extends Fragment {
  int position = 0;
  TextView tvTitle;
  TextView tvDetails;
  TextView itemNameTextView;
  TextView itemPriceTextView;
  ImageView itemPhotoImageView;
  Button addNewItemButton;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if(savedInstanceState == null){
      // Get back arguments
      if(getArguments() != null) {
        position = getArguments().getInt("position", 0);
      }
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

    // Inflate the xml file for the fragment
    return inflater.inflate(R.layout.fragment_pizza_detail, parent, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    // Set values for view here
    itemNameTextView = (TextView) view.findViewById(R.id.itemNameTextView);
    itemPriceTextView = (TextView) view.findViewById(R.id.itemPriceTextView);
    itemPhotoImageView = (ImageView) view.findViewById(R.id.itemPhotoImageView);
    addNewItemButton = (Button) view.findViewById(R.id.addItemButton);

    // update view
//    tvTitle.setText(Pizza.pizzaMenu[position]);
//    tvDetails.setText(Pizza.pizzaDetails[position]);
    itemNameTextView.setText("Name: "+getArguments().getString("itemName"));
    itemPriceTextView.setText("Price: "+getArguments().getString("itemPrice"));
    itemPhotoImageView.setImageBitmap(ImageUtils.decodeBase64(getArguments().getString("itemPhoto")));
    addNewItemButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startNameInputActivity();
      }
    });

  }

  // Activity is calling this to update view on Fragment
  public void updateView(int position){
    tvTitle.setText(Pizza.pizzaMenu[position]);
    tvDetails.setText(Pizza.pizzaDetails[position]);
  }

  public void startNameInputActivity(){
    Intent i = new Intent(getContext(), NameIntput.class);
    startActivity(i);
  }
}
