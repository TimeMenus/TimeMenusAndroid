package com.express.apps.expresscafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.express.apps.expresscafe.models.Item;
import com.express.apps.expresscafe.models.Picture;
import com.express.apps.expresscafe.services.DataService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdminActivity extends AppCompatActivity implements OnClickListener {

    private TextView dateView;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText itemName, itemDesc;
    private Spinner menuItem;
    private CheckBox dashboardChk;
    ImageView viewImage;
    Uri downloadUrl;
    String picName;
    String itemNameStr;
    String itemDescStr;
    String dateStr;
    String menuItemSelStr;
    Boolean dashboardSel;
    private Uri fileUri;
    private ProgressDialog progress;


    DataService dataService = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dataService =DataService.newInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //for menu drop down item
        Spinner countryView = (Spinner) findViewById(R.id.menuitems);

        // Create an adapter from the string array resource and use
        // spinner_textview_align.xml for aligining and formatting
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.menu_arrays, R.layout.spinner_textview_align);
        // Set the layout to use for each dropdown item
        adapter.setDropDownViewResource(R.layout.spinner_textview_align);
        countryView.setAdapter(adapter);

//        For the calendar and date
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//        dateView = (TextView) findViewById(R.id.picked_date);
        viewImage = (ImageView) findViewById(R.id.viewImage);
//        setDateTimeField();
    }

//    private void setDateTimeField() {
//        dateView.setOnClickListener(this);
//        Calendar newCalendar = Calendar.getInstance();
//        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//                dateView.setText(dateFormatter.format(newDate.getTime()));
//            }
//        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//    }

    @Override
    public void onClick(View view) {
        if (view == dateView) {
            datePickerDialog.show();
        }
    }

    public void saveItem(View view) {

        progress=new ProgressDialog(this);
        progress.setMessage("Please Wait!!! Loading ...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(false);
        progress.show();

        itemName = (EditText) findViewById(R.id.item_name);
        itemDesc = (EditText) findViewById(R.id.item_desc);
        menuItem = (Spinner) findViewById(R.id.menuitems);
        dashboardChk = (CheckBox) findViewById(R.id.dashboard);


        itemNameStr = itemName.getText().toString();
        itemDescStr = itemDesc.getText().toString();
//        dateStr = dateView.getText().toString();
        menuItemSelStr = menuItem.getSelectedItem().toString();
        dashboardSel = dashboardChk.isChecked();

//        File objFile = new File(String.valueOf(fileUri));
//        picName = objFile.getName();
        picName = generateName();

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

                String categoryID=dataService.getCategoryByName(menuItemSelStr);
                //"-KMy-TrOornKuDEgcJjB"

                Item item = new Item(categoryID, dashboardSel, itemDescStr, itemNameStr, pic);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("menues/"+dataService.getTodayMenuKey()+"/items");
                myRef.push().setValue(item);
                finish();
                startActivity(getIntent());
                progress.dismiss();
            }
        });

    }

    public void imageStuff(View view) {
        selectImage();
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
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
                Environment.DIRECTORY_DCIM), "CafeExpress");

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + generateName());
        } else {
            return null;
        }

        return mediaFile;
    }

    private String generateName() {
        /**Create a media file name*/
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String picName = "IMG_" + timeStamp + ".jpg";
        return picName;
    }

    //for back buttonm on top of the screen
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), AdminMainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }


}
