package com.codepath.mypizza;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.mypizza.entity.Item;
import com.codepath.mypizza.shared.Shared;

public class InputPrice extends AppCompatActivity {

    private EditText priceEditText;
    private TextView itemNameTextView;
    private Item buildItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_price);

        buildItem = (Item)getIntent().getSerializableExtra(Shared.ITEM_OBJECT);
        priceEditText = (EditText) findViewById(R.id.priceEditText);

        itemNameTextView = (TextView) findViewById(R.id.itemNameTextView);

        itemNameTextView.setText(buildItem.getName() == null
                ? ""
                : buildItem.getName());
        priceEditText.setText(buildItem.getName());

        priceEditText.setText(Float.toString(buildItem.getPrice()));

    }

    public void onGoToPreviousActivityClick(View view){
        try{
            buildItem.setPrice(Float.parseFloat(priceEditText.getText().toString()));
        } catch (NumberFormatException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), priceEditText.getText() + " cant parse", Toast.LENGTH_SHORT).show();
            return ;
        }

        Intent previousActivity = new Intent(this, MainActivity.class);
        previousActivity.putExtra(Shared.ITEM_OBJECT,buildItem);
        startActivity(previousActivity);
    }

    public void onGoToNextActivityClick(View view){
        try{
            buildItem.setPrice(Float.parseFloat(priceEditText.getText().toString()));
        } catch (NumberFormatException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), priceEditText.getText() + " cant parce", Toast.LENGTH_SHORT).show();
            return ;
        }

        Intent nextActivity = new Intent(this, ImageInput.class);
        nextActivity.putExtra(Shared.ITEM_OBJECT,buildItem);
        startActivity(nextActivity);
    }
}
