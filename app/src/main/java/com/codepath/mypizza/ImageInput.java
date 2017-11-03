package com.codepath.mypizza;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.mypizza.adapter.ItemsDbAdapter;
import com.codepath.mypizza.entity.Item;
import com.codepath.mypizza.shared.Shared;
import com.codepath.mypizza.util.ImageUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;


public class ImageInput extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    private TextView itemNameTextView;
    private TextView itemPriceTextView;
    private Item buildItem;
    private ItemsDbAdapter dbHelper;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_input);
        dbHelper = new ItemsDbAdapter(this);
        dbHelper.open();

        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), READ_REQUEST_CODE);

        buildItem = (Item)getIntent().getSerializableExtra(Shared.ITEM_OBJECT);

        itemNameTextView = (TextView) findViewById(R.id.itemNameTextView);
        itemPriceTextView = (TextView) findViewById(R.id.itemPriceTextView);


        itemNameTextView.setText(buildItem.getName() == null
                ? ""
                : buildItem.getName());

        itemPriceTextView.setText(Float.toString(buildItem.getPrice()));


        myRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
                buildItem.setPathToPhoto(ImageUtils.encodeTobase64(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onImageViewClick(View view){
        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), READ_REQUEST_CODE);

    }

    public void onSaveButtonClick(View view){

        myRef.child(user.getUid()).child("Items").push().setValue(new Item(buildItem.getName(),buildItem.getPrice(),buildItem.getSelected(), buildItem.getPathToPhoto()));

        Cursor c = dbHelper.fetchAllItems();
        Intent allItems = new Intent(this, MainActivity.class);
        startActivity(allItems);
    }
}

