package com.example.rog.efarmer1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.Button;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class new_product extends AppCompatActivity{

    private EditText name, estimate_time, estimate_amount, phone_number;
    private Spinner spn;
    private Bitmap bitmap, bitmap1, bitmap2, bitmap3;
    private Button btn , button;
    private TextView selectedItems;
    private ImageView imageView, imageView1, imageView2, imageView3;
    String getImgDesc;
    String getImgDesc1;
    String getImgDesc2;
    String getImgDesc3;
    String getImgDesc4;
    String getImgTag;
    String email;
    String priceS;
    static boolean isInside = false;
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int REQUEST_IMAGE_CAPTURE1 = 1;
    private static final int REQUEST_IMAGE_CAPTURE2 = 2;
    private static final int REQUEST_IMAGE_CAPTURE3 = 3;
    private static final int RESULT_LOAD_IMAGE = 4;
    private static final int RESULT_LOAD_IMAGE1 = 5;
    private static final int RESULT_LOAD_IMAGE2 = 6;
    private static final int RESULT_LOAD_IMAGE3 = 7;
    public static final String UPLOAD_URL = "https://veonic.com/aigogo.co/e-Farmer/UploadFarming.php";
    public static final String UPLOAD_KEY = "img";
    public static final String UPLOAD_KEY1 = "img1";
    public static final String UPLOAD_KEY2 = "img2";
    public static final String UPLOAD_KEY3 = "img3";
    public static final String UPLOAD_TIME = "est_time";
    public static final String UPLOAD_AMOUNT = "est_amount";
    public static final String UPLOAD_LOCATION= "farm_loc";
    public static final String UPLOAD_PRICE="price";
    public static final String UPLOAD_NAME = "pro_name";
    public static final String UPLOAD_TAG = "tag";
    ArrayList<String> items=new ArrayList<>();
    SpinnerDialog spinnerDialog;
    boolean imagepresent = false;
    boolean imagepresent1 = false;
    boolean imagepresent2 = false;
    boolean imagepresent3 = false;
    String desc = "";
    EditText descEdit;
    EditText price;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        email = prefs.getString("username","none");
        items.add("Asajaya,Sarawak");
        items.add("Balingian,Sarawak");
        items.add("Baram,Sarawak");
        items.add("Bau,Sarawak");
        items.add("Bekenu,Sarawak");
        items.add("Belaga,Sarawak");
        items.add("Belawai,Sarawak");
        items.add("Betong,Sarawak");
        items.add("Bintangor,Sarawak");
        items.add("Bintulu,Sarawak");
        items.add("Dalat,Sarawak");
        items.add("Daro,Sarawak");
        items.add("Debak,Sarawak");
        items.add("Engkilili,Sarawak");
        items.add("Julau,Sarawak");
        items.add("Kabong,Sarawak");
        items.add("Kanowit,Sarawak");
        items.add("Kapit,Sarawak");
        items.add("Kota Samarahan,Sarawak");
        items.add("Kuching,Sarawak");
        items.add("Lawas,Sarawak");
        items.add("Limbang,Sarawak");
        items.add("Lingga,Sarawak");
        items.add("Long Lama,Sarawak");
        items.add("Lubok Antu,Sarawak");
        items.add("Lundu,Sarawak");
        items.add("Maradong,Sarawak");
        items.add("Marudi,Sarawak");
        items.add("Matu,Sarawak");
        items.add("Miri,Sarawak");
        items.add("Mukah,Sarawak");
        items.add("Nanga Medamit,Sarawak");
        items.add("Niah,Sarawak");
        items.add("Pusa,Sarawak");
        items.add("Roban,Sarawak");
        items.add("Saratok,Sarawak");
        items.add("Sarikei,Sarawak");
        items.add("Sebauh,Sarawak");
        items.add("Sebuyau,Sarawak");
        items.add("Serian,Sarawak");
        items.add("Sibu,Sarawak");
        items.add("Simunjan,Sarawak");
        items.add("Song,Sarawak");
        items.add("Spaoh,Sarawak");
        items.add("Sri Aman,Sarawak");
        items.add("Sundar,Sarawak");
        items.add("Tanjung Kidurong,Sarawak");
        items.add("Tatau,Sarawak");
        items.add("Others");
        descEdit = findViewById(R.id.desc);
        name = (EditText)findViewById(R.id.complaintdesc4);
        estimate_time = (EditText)findViewById(R.id.complaintdesc);
        estimate_amount = (EditText)findViewById(R.id.complaintdesc2);
        btn = (Button)findViewById(R.id.button);
        selectedItems = (TextView)findViewById(R.id.location); //This is for location
        button = (Button)findViewById(R.id.button_upload);
        price = findViewById(R.id.price);
        System.out.println("Email in newProduct: " + email);
        spinnerDialog=new SpinnerDialog(new_product.this,items,"Select or Search City",R.style.DialogAnimations_SmileWindow,"Close");// With 	Animation
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                selectedItems.setText(item);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog.showSpinerDialog();
            }
        });
        btn.setTransformationMethod(null);

        spn  = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.complaint_arrays, R.layout.myspinner);
        adapter.setDropDownViewResource(R.layout.myspinnerdrop);
        spn.setAdapter(adapter);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent1();
            }
        });
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent2();
            }
        });
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent3();
            }
        });
        System.out.println("onCreate new_product");
    }
    public void dispatchTakePictureIntent() {

        final CharSequence[] items={"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(new_product.this);
        builder.setTitle("Choose Picture");
        builder.setIcon(R.drawable.ic_image_black_24dp);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("Camera")){

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    System.out.println("image_one_is_capture");
                }
                else if(items[which].equals("Gallery")){

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select file"), RESULT_LOAD_IMAGE);
                    System.out.println("image_one_is_capture");
                }
            }
        });
        builder.show();
    }
    public void dispatchTakePictureIntent1() {

        final CharSequence[] items={"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(new_product.this);
        builder.setTitle("Choose Picture");
        builder.setIcon(R.drawable.ic_image_black_24dp);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("Camera")){

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE1);

                }
                else if(items[which].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent.createChooser(intent, "Select file"), RESULT_LOAD_IMAGE1);

                }
            }
        });
        builder.show();
    }
    public void dispatchTakePictureIntent2() {

        final CharSequence[] items={"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(new_product.this);
        builder.setTitle("Choose Picture");
        builder.setIcon(R.drawable.ic_image_black_24dp);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("Camera")){

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE2);

                }
                else if(items[which].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent.createChooser(intent, "Select file"), RESULT_LOAD_IMAGE2);

                }
            }
        });
        builder.show();
    }
    public void dispatchTakePictureIntent3() {

        final CharSequence[] items={"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(new_product.this);
        builder.setTitle("Choose Picture");
        builder.setIcon(R.drawable.ic_image_black_24dp);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("Camera")){

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE3);
                }
                else if(items[which].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent.createChooser(intent, "Select file"), RESULT_LOAD_IMAGE3);
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
                bitmap = imageBitmap;
                imagepresent = true;
                return;
            }
            else if (requestCode == REQUEST_IMAGE_CAPTURE1) {
                Bundle extras = data.getExtras();
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView1.setImageBitmap(imageBitmap);
                bitmap = imageBitmap;
                imagepresent1 = true;
                return;
            }
            else if (requestCode == REQUEST_IMAGE_CAPTURE2) {
                Bundle extras = data.getExtras();
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView2.setImageBitmap(imageBitmap);
                bitmap = imageBitmap;
                imagepresent2 = true;
                return;
            }
            else if (requestCode == REQUEST_IMAGE_CAPTURE3) {
                Bundle extras = data.getExtras();
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView3.setImageBitmap(imageBitmap);
                bitmap = imageBitmap;
                imagepresent3 = true;
                return;
            }
            else if(requestCode == RESULT_LOAD_IMAGE){

                Uri selectedImage = data.getData();
                imageView.setImageURI(selectedImage);
                imagepresent = true;
                return;
            }
            else if(requestCode == RESULT_LOAD_IMAGE1){

                Uri selectedImage = data.getData();
                imageView1.setImageURI(selectedImage);
                imagepresent1 = true;
                return;
            }
            else if(requestCode == RESULT_LOAD_IMAGE2){

                Uri selectedImage = data.getData();
                imageView2.setImageURI(selectedImage);
                imagepresent2 = true;
                return;
            }
            else if(requestCode == RESULT_LOAD_IMAGE3){

                Uri selectedImage = data.getData();
                imageView3.setImageURI(selectedImage);
                imagepresent3 = true;
                return;
            }
        }

    }

    public void upload() {

        if (imagepresent && imagepresent1 && imagepresent2 && imagepresent3){

                    System.out.println("All four image is present");
                    bitmap =  ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    bitmap1 = ((BitmapDrawable) imageView1.getDrawable()).getBitmap();
                    bitmap2 = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();
                    bitmap3 = ((BitmapDrawable) imageView3.getDrawable()).getBitmap();
                    getImgTag = spn.getSelectedItem().toString();
                    getImgDesc = estimate_time.getText().toString();
                    getImgDesc1 = estimate_amount.getText().toString();
                    getImgDesc2 = selectedItems.getText().toString();    //This is for location
                    priceS = price.getText().toString();
                    getImgDesc4 = name.getText().toString();
                    desc = descEdit.getText().toString();
                    uploadImage4();
        }

        else if (imagepresent)
        {
                    System.out.println("Only 1 image is present");
                    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                    if (imagepresent1){
                        System.out.println("Only 2 image is present");
                        bitmap1 = ((BitmapDrawable) imageView1.getDrawable()).getBitmap();

                        if (imagepresent2){
                            System.out.println("Only 3 image is present");
                            bitmap2 = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();
                            getImgTag = spn.getSelectedItem().toString();
                            getImgDesc = estimate_time.getText().toString();
                            getImgDesc1 = estimate_amount.getText().toString();
                            getImgDesc2 = selectedItems.getText().toString();    //This is for location
                            getImgDesc4 = name.getText().toString();
                            desc = descEdit.getText().toString();
                            priceS = price.getText().toString();
                            isInside = true;
                            System.out.println("Inside imagepresent2");
                            uploadImage3();

                        }
                        else if (imagepresent3){
                            System.out.println("Only 3 image is present");
                            bitmap2 = ((BitmapDrawable) imageView3.getDrawable()).getBitmap();
                            getImgTag = spn.getSelectedItem().toString();
                            getImgDesc = estimate_time.getText().toString();
                            getImgDesc1 = estimate_amount.getText().toString();
                            getImgDesc2 = selectedItems.getText().toString();    //This is for location
                            getImgDesc4 = name.getText().toString();
                            desc = descEdit.getText().toString();
                            priceS = price.getText().toString();
                            isInside = true;
                            uploadImage3();
                        }
                        if (!isInside) {
                            getImgTag = spn.getSelectedItem().toString();
                            getImgDesc = estimate_time.getText().toString();
                            getImgDesc1 = estimate_amount.getText().toString();
                            getImgDesc2 = selectedItems.getText().toString();    //This is for location
                            getImgDesc4 = name.getText().toString();
                            desc = descEdit.getText().toString();
                            priceS = price.getText().toString();
                            isInside = true;
                            uploadImage2();
                        }
                    }
                    else if (imagepresent2){
                        System.out.println("Only 2 image is present");
                        bitmap1 = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();

                        if (imagepresent3){
                            System.out.println("Only 3 image is present");
                            bitmap2 = ((BitmapDrawable) imageView3.getDrawable()).getBitmap();
                            getImgTag = spn.getSelectedItem().toString();
                            getImgDesc = estimate_time.getText().toString();
                            getImgDesc1 = estimate_amount.getText().toString();
                            getImgDesc2 = selectedItems.getText().toString();    //This is for location
                            getImgDesc4 = name.getText().toString();
                            priceS = price.getText().toString();
                            desc = descEdit.getText().toString();
                            isInside = true;
                            uploadImage3();
                        }
                        if (!isInside) {
                            getImgTag = spn.getSelectedItem().toString();
                            getImgDesc = estimate_time.getText().toString();
                            getImgDesc1 = estimate_amount.getText().toString();
                            getImgDesc2 = selectedItems.getText().toString();    //This is for location
                            priceS = price.getText().toString();
                            getImgDesc4 = name.getText().toString();
                            desc = descEdit.getText().toString();
                            isInside = true;
                            uploadImage2();
                        }
                    }
                    else if (imagepresent3){
                        System.out.println("Only 2 image is present");
                        bitmap1 = ((BitmapDrawable) imageView3.getDrawable()).getBitmap();
                        getImgTag = spn.getSelectedItem().toString();
                        getImgDesc = estimate_time.getText().toString();
                        getImgDesc1 = estimate_amount.getText().toString();
                        getImgDesc2 = selectedItems.getText().toString();    //This is for location
                        priceS = price.getText().toString();
                        getImgDesc4 = name.getText().toString();
                        isInside = true;
                        uploadImage2();
                    }
                    if (!isInside) {
                        getImgTag = spn.getSelectedItem().toString();
                        getImgDesc = estimate_time.getText().toString();
                        getImgDesc1 = estimate_amount.getText().toString();
                        getImgDesc2 = selectedItems.getText().toString();    //This is for location
                        getImgDesc4 = name.getText().toString();
                        priceS = price.getText().toString();
                        desc = descEdit.getText().toString();
                        System.out.println("Inside imagepresent");
                        isInside = true;
                        uploadImage();
                    }
        }
        else if (imagepresent1)
        {
                    System.out.println("Only 1 image is present");
                    bitmap = ((BitmapDrawable) imageView1.getDrawable()).getBitmap();

                    if (imagepresent2){
                        System.out.println("Only 2 image is present");
                        bitmap1 = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();

                        if (imagepresent3){
                            System.out.println("Only 3 image is present");
                            bitmap2 = ((BitmapDrawable) imageView3.getDrawable()).getBitmap();
                            getImgTag = spn.getSelectedItem().toString();
                            getImgDesc = estimate_time.getText().toString();
                            priceS = price.getText().toString();
                            getImgDesc1 = estimate_amount.getText().toString();
                            getImgDesc2 = selectedItems.getText().toString();    //This is for location

                            getImgDesc4 = name.getText().toString();
                            desc = descEdit.getText().toString();
                            isInside = true;
                            uploadImage3();
                        }
                        if (!isInside) {
                            getImgTag = spn.getSelectedItem().toString();
                            getImgDesc = estimate_time.getText().toString();
                            getImgDesc1 = estimate_amount.getText().toString();
                            getImgDesc2 = selectedItems.getText().toString();    //This is for location
                            priceS = price.getText().toString();
                            getImgDesc4 = name.getText().toString();
                            desc = descEdit.getText().toString();
                            isInside = true;
                            uploadImage2();
                        }
                    }
                    else if (imagepresent3){
                        System.out.println("Only 2 image is present");
                        bitmap1 = ((BitmapDrawable) imageView3.getDrawable()).getBitmap();
                        getImgTag = spn.getSelectedItem().toString();
                        getImgDesc = estimate_time.getText().toString();
                        getImgDesc1 = estimate_amount.getText().toString();
                        getImgDesc2 = selectedItems.getText().toString();    //This is for location
                        priceS = price.getText().toString();
                        getImgDesc4 = name.getText().toString();
                        desc = descEdit.getText().toString();
                        isInside = true;
                        uploadImage2();

                    }
                if (!isInside) {
                    getImgTag = spn.getSelectedItem().toString();
                    getImgDesc = estimate_time.getText().toString();
                    getImgDesc1 = estimate_amount.getText().toString();
                    getImgDesc2 = selectedItems.getText().toString();    //This is for location
                    priceS = price.getText().toString();
                    getImgDesc4 = name.getText().toString();
                    desc = descEdit.getText().toString();
                    isInside = true;
                    uploadImage();
                }
        }
        else if (imagepresent2)
        {
                    System.out.println("Only 1 image is present");
                    bitmap = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();

                    if (imagepresent3){
                        System.out.println("Only 2 image is present");
                        bitmap1 = ((BitmapDrawable) imageView3.getDrawable()).getBitmap();
                        getImgTag = spn.getSelectedItem().toString();
                        getImgDesc = estimate_time.getText().toString();
                        getImgDesc1 = estimate_amount.getText().toString();
                        getImgDesc2 = selectedItems.getText().toString();    //This is for location
                        priceS = price.getText().toString();
                        getImgDesc4 = name.getText().toString();
                        desc = descEdit.getText().toString();
                        isInside = true;
                        uploadImage2();
                    }
                if (!isInside) {
                    getImgTag = spn.getSelectedItem().toString();
                    getImgDesc = estimate_time.getText().toString();
                    getImgDesc1 = estimate_amount.getText().toString();
                    getImgDesc2 = selectedItems.getText().toString();    //This is for location
                    priceS = price.getText().toString();
                    getImgDesc4 = name.getText().toString();
                    desc = descEdit.getText().toString();
                    isInside = true;
                    uploadImage();
                }
        }
        else if (imagepresent3)
        {
                    System.out.println("Only 1 image is present");
                    bitmap = ((BitmapDrawable) imageView3.getDrawable()).getBitmap();
                    getImgTag = spn.getSelectedItem().toString();
                    getImgDesc = estimate_time.getText().toString();
                    getImgDesc1 = estimate_amount.getText().toString();
                    getImgDesc2 = selectedItems.getText().toString();    //This is for location
            priceS = price.getText().toString();
                    getImgDesc4 = name.getText().toString();
                    desc = descEdit.getText().toString();
                    isInside = true;
                    uploadImage();
        }
        else
        {
            Toast.makeText(new_product.this, "Image not found. Picture cannot be empty",Toast.LENGTH_LONG).show();
        }
    }

    private void uploadImage(){

        class UploadImage extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(new_product.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                System.out.println("Error: \n" + s);
                name.setText("");
                estimate_time.setText("");
                estimate_amount.setText("");
                selectedItems.setText(""); //This is for location
                spn.setSelection(0);
                descEdit.setText("");
                price.setText("");
                imagepresent = false;
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                data.put(UPLOAD_TIME, getImgDesc);
                data.put(UPLOAD_AMOUNT, getImgDesc1);
                data.put(UPLOAD_LOCATION, getImgDesc2);
                data.put(UPLOAD_NAME, getImgDesc4);
                data.put(UPLOAD_TAG, getImgTag);
                data.put(UPLOAD_PRICE, priceS);
                data.put("desc", desc);
                data.put("email", email);
                System.out.println("Email doInBackground: " + email);
                String result = rh.sendPostRequest(UPLOAD_URL,data);
                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
    private void uploadImage2(){

        class UploadImage extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(new_product.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                name.setText("");
                estimate_time.setText("");
                estimate_amount.setText("");
                selectedItems.setText(""); //This is for location
                spn.setSelection(0);
                price.setText("");
                descEdit.setText("");
                imagepresent = false;
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                Bitmap bitmap1 = params[1];
                String uploadImage = getStringImage(bitmap);
                String uploadImage1 = getStringImage(bitmap1);
                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_PRICE, priceS);
                data.put(UPLOAD_KEY, uploadImage);
                data.put(UPLOAD_KEY1, uploadImage1);
                data.put(UPLOAD_TIME, getImgDesc);
                data.put(UPLOAD_AMOUNT, getImgDesc1);
                data.put(UPLOAD_LOCATION, getImgDesc2);
                data.put(UPLOAD_NAME, getImgDesc4);
                data.put(UPLOAD_TAG, getImgTag);
                data.put("desc", desc);
                data.put("email", email);
                String result = rh.sendPostRequest(UPLOAD_URL,data);
                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap, bitmap1);
    }
    private void uploadImage3(){

        class UploadImage extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(new_product.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                name.setText("");
                estimate_time.setText("");
                estimate_amount.setText("");
                selectedItems.setText(""); //This is for location
                spn.setSelection(0);
                descEdit.setText("");
                price.setText("");
                imagepresent = false;
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                Bitmap bitmap1 = params[1];
                Bitmap bitmap2 = params[2];
                String uploadImage = getStringImage(bitmap);
                String uploadImage1 = getStringImage(bitmap1);
                String uploadImage2 = getStringImage(bitmap2);
                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_PRICE, priceS);
                data.put(UPLOAD_KEY, uploadImage);
                data.put(UPLOAD_KEY1, uploadImage1);
                data.put(UPLOAD_KEY2, uploadImage2);
                data.put(UPLOAD_TIME, getImgDesc);
                data.put(UPLOAD_AMOUNT, getImgDesc1);
                data.put(UPLOAD_LOCATION, getImgDesc2);
                data.put(UPLOAD_NAME, getImgDesc4);
                data.put(UPLOAD_TAG, getImgTag);
                data.put("desc", desc);
                data.put("email", email);
                String result = rh.sendPostRequest(UPLOAD_URL,data);
                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap, bitmap1, bitmap2);
    }
    private void uploadImage4(){

        class UploadImage extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(new_product.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                name.setText("");
                estimate_time.setText("");
                estimate_amount.setText("");
                selectedItems.setText(""); //This is for location
                spn.setSelection(0);
                price.setText("");
                descEdit.setText("");
                imagepresent = false;
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                Bitmap bitmap1 = params[1];
                Bitmap bitmap2 = params[2];
                Bitmap bitmap3 = params[3];
                String uploadImage = getStringImage(bitmap);
                String uploadImage1 = getStringImage(bitmap1);
                String uploadImage2 = getStringImage(bitmap2);
                String uploadImage3 = getStringImage(bitmap3);
                System.out.println("Upload image: \n" + uploadImage);
                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_PRICE, priceS);
                data.put(UPLOAD_KEY, uploadImage);
                data.put(UPLOAD_KEY1, uploadImage1);
                data.put(UPLOAD_KEY2, uploadImage2);
                data.put(UPLOAD_KEY3, uploadImage3);
                data.put(UPLOAD_TIME, getImgDesc);
                data.put(UPLOAD_AMOUNT, getImgDesc1);
                data.put(UPLOAD_LOCATION, getImgDesc2);
                data.put(UPLOAD_NAME, getImgDesc4);
                data.put(UPLOAD_TAG, getImgTag);
                data.put("desc", desc);
                data.put("email", email);
                String result = rh.sendPostRequest(UPLOAD_URL,data);
                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap, bitmap1, bitmap2, bitmap3);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
