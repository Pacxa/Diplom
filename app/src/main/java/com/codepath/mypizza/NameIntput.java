package com.codepath.mypizza;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.mypizza.entity.Item;
import com.codepath.mypizza.shared.Shared;


public class NameIntput extends AppCompatActivity {

    private static final int REQUESTS_FOR_RECORD = 0;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;

    private static final String LOG_TAG = "AudioRecordTest";

    private EditText nameEditText;
    private Item buildItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_intput);

        buildItem = getIntent().getSerializableExtra(Shared.ITEM_OBJECT) == null
                ? new Item()
                : (Item) getIntent().getSerializableExtra(Shared.ITEM_OBJECT);

        nameEditText = (EditText) findViewById(R.id.itemNameEditText);
//        nameEditText.setText(buildItem.getName() == null
//                ? ""
//                : buildItem.getName());

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_DOCUMENTS) != PackageManager.PERMISSION_GRANTED) {

// Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.MANAGE_DOCUMENTS)) {


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_DOCUMENTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUESTS_FOR_RECORD: {
// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// Do the task you need to do.
                    Log.i(LOG_TAG, "Permission was granted, yay!");
                } else {
// Disable the functionality that depends on this permission.
                    Log.i(LOG_TAG, "Permission denied, boo!");
                }
            }
// other 'case' lines to check for other
// permissions this app might request
        }
    }
    public void onGoToNextActivityClick(View view) {

        buildItem.setName(nameEditText.getText().toString());
        Intent nextActivity = new Intent(this, InputPrice.class);
        nextActivity.putExtra(Shared.ITEM_OBJECT, buildItem);
        startActivity(nextActivity);


    }
}
