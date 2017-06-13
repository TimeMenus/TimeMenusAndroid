package com.timemenus.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import com.timemenus.android.models.Item;
import com.timemenus.android.models.Picture;
import com.timemenus.android.services.DataService;
import com.timemenus.android.services.UtilsService;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class AddItemActivity extends BaseActivity {

    ImageView viewImage;
    Uri downloadUrl;
    String picName;
    String itemNameStr;
    String itemDescStr;
    String menuItemSelStr;
    Boolean dashboardSel;

    private EditText itemName, itemDesc;
    private Spinner menuItem;
    private CheckBox dashboardChk;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        //for menu drop down item
        Spinner countryView = (Spinner) findViewById(R.id.menuitems);

        // Create an adapter from the string array resource and use
        // spinner_textview_align.xml for aligining and formatting
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.menu_arrays, R.layout.spinner_textview_align);
        // Set the layout to use for each dropdown item
        adapter.setDropDownViewResource(R.layout.spinner_textview_align);
        countryView.setAdapter(adapter);

        viewImage = (ImageView) findViewById(R.id.viewImage);


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_additem);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    public void saveItem(View view) {

        showProgressDialog();

        itemName = (EditText) findViewById(R.id.item_name);
        itemDesc = (EditText) findViewById(R.id.item_desc);
        menuItem = (Spinner) findViewById(R.id.menuitems);
        dashboardChk = (CheckBox) findViewById(R.id.dashboard);


        itemNameStr = itemName.getText().toString();
        itemDescStr = itemDesc.getText().toString();

        menuItemSelStr = menuItem.getSelectedItem().toString();
        dashboardSel = dashboardChk.isChecked();

        picName = UtilsService.generateName();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("images/" + picName);

        UploadTask uploadTask = storageRef.putFile(fileUri);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                downloadUrl = taskSnapshot.getDownloadUrl();
                Picture pic = new Picture(picName, downloadUrl.toString());

                String categoryID = DataService.getCategoryByName(menuItemSelStr);

                Item item = new Item(categoryID, dashboardSel, itemDescStr, itemNameStr, pic);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("cafes/EXPRESSDC1/menues/" + DataService.getTodayMenu().getKey() + "/items");
                myRef.push().setValue(item);
                finish();
                startActivity(getIntent());
                hideProgressDialog();
            }
        });

    }

    public void imageStuff(View view) {
        selectImage();
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    // create Intent to take a picture and return control to the calling application
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = getOutputMediaFile(1);
                    fileUri = Uri.fromFile(file); // create
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
                    viewImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;

            } else if (requestCode == 2) {

                fileUri = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(fileUri, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image", picturePath + "");
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }

    /**
     * Create a File for saving an image
     */
    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "TIMEMenus");

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + UtilsService.generateName());
        } else {
            return null;
        }

        return mediaFile;
    }

    //for back buttonm on top of the screen
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), AdminActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }


}
