package com.kominfo.anaksehat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.helpers.ShowcaseHelper;
import com.kominfo.anaksehat.models.SubDistrict;
import com.kominfo.anaksehat.models.Villages;
import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.CropSquareTransformation;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.helpers.ImageFilePath;
import com.kominfo.anaksehat.helpers.apihelper.UtilsApi;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.District;
import com.kominfo.anaksehat.models.Mother;
import com.kominfo.anaksehat.models.State;
import com.kominfo.anaksehat.models.User;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormMotherActivity extends BaseActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_CHOOSE_PHOTO = 2;
    static final int MY_PERMISSIONS_REQUEST_CAMERA = 99;
    static final int MY_PERMISSIONS_REQUEST_STORAGE = 100;

    private EditText etBirthDate,etName,etHeight,etWeight,etSpouseName, etBloodPressureTop,
            etBloodPressureBottom, etAddress, etNamaKK, etNIK, etJamStatus;
    private TextView tvKecamatan, tvKabupaten, tvDesa, tvAlamat;
    private AutoCompleteTextView actvState, actvDistrict, actSubDistrict, actVillage;
    private Spinner spBloodType;
    private ImageView ivThumbnail, ivPickDate;
    private String mCurrentPhotoPath;
    private State selectedState;
    private District selectedDistrict;
    private SubDistrict selectedSubDistrict;
    private Villages selectedVillage;
    private User user;
    private boolean editMode = false;
    private Mother mother;
    private Picasso picasso;
    //private ShowcaseHelper showcaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mother);

        checkSession();
        user = getUserSession();
        picasso = Picasso.get();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setTitle(R.string.title_create_mother);

        ivThumbnail = findViewById(R.id.thumbnail);
        etBirthDate = findViewById(R.id.birth_date);
        etName = findViewById(R.id.name);
        etSpouseName = findViewById(R.id.spouse_name);
        spBloodType = findViewById(R.id.blood_type);
        etHeight = findViewById(R.id.height);
        etWeight = findViewById(R.id.weight);
        etBloodPressureTop = findViewById(R.id.blood_pressure_top);
        etBloodPressureBottom = findViewById(R.id.blood_pressure_bottom);
        etAddress = findViewById(R.id.address);
        actvDistrict = findViewById(R.id.district);
        ivPickDate = findViewById(R.id.birth_icon);
        etNamaKK = findViewById(R.id.kk_name);
        etNIK = findViewById(R.id.nik);
        etJamStatus = findViewById(R.id.jampersal_status);
        actSubDistrict = findViewById(R.id.sub_district);
        actVillage = findViewById(R.id.village);
        tvDesa = findViewById(R.id.tv_desa);
        tvKecamatan = findViewById(R.id.tv_kecamatan);
        tvKabupaten = findViewById(R.id.tv_kabupaten);
        tvAlamat = findViewById(R.id.tv_alamat);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_types, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBloodType.setAdapter(adapter);

        etBirthDate.setInputType(InputType.TYPE_NULL);
        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivPickDate.performClick();
            }
        });
        etWeight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if(validateData())
                        submitData();
                    handled = true;
                }
                return handled;
            }
        });

        mApiService.getDistrict(37214, appSession.getData(AppSession.TOKEN)).enqueue(districtcallback.getCallback());
        mApiService.getSubDistricts(appSession.getData(AppSession.TOKEN)).enqueue(subdistrictcallback.getCallback());

        //showcaseHelper = new ShowcaseHelper(context, ShowcaseHelper.FORM_MOTHER_ID);

        editMode = getIntent().getBooleanExtra("edit_mode", false);
        if(!editMode) {
            if (user.getPosyandu() == 1)
                etName.setText(user.getName());
        } else {
            initEditForm();
            initEditData();
        }

        //startShowcase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mothers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onClickMenu(item.getItemId());
        return true;
    }

    public void onClickMenu(int resId){
        switch (resId){
            case android.R.id.home:
                backFunction();
                break;
            default:
//                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initEditData(){
        mother = new Gson().fromJson(getIntent().getStringExtra("edit_data"), Mother.class);
        setTitle(R.string.title_edit_mother);
        picasso.load(UtilsApi.BASE_URL+mother.getPhoto_url())
                .transform(new CropSquareTransformation())
                .error(R.drawable.menumother)
                .placeholder(R.drawable.menumother)
                .into(ivThumbnail);
        etBirthDate.setText(DateHelper.getDateServer(mother.getBirth_date()));
        etName.setText(mother.getName());
        etSpouseName.setText(mother.getSpouse_name());
        if(mother.getBlood_type()!=null)
            switch (mother.getBlood_type()){
                case "B":
                    spBloodType.setSelection(1);
                    break;
                case "AB":
                    spBloodType.setSelection(2);
                    break;
                case "O":
                    spBloodType.setSelection(3);
                    break;
                default:
                    spBloodType.setSelection(0);
                    break;
            }
        etHeight.setText(""+mother.getHeight());
        etWeight.setText(replaceDotToComma(""+mother.getWeight()));
        etBloodPressureTop.setText(""+mother.getBlood_pressure_top());
        etBloodPressureBottom.setText(""+mother.getBlood_pressure_bottom());
        etNamaKK.setText(mother.getKk_name());
        etNIK.setText(mother.getNik());
        etJamStatus.setText(mother.getJampersal_status());
        etAddress.setText(mother.getAddress());
        actvDistrict.setText(mother.getDistrict_name());
        actSubDistrict.setText(mother.getSub_district_name());
        actVillage.setText(mother.getVillage_name());
        etAddress.setText(mother.getAddress());
    }

    public void onClickForm(View v){
        switch (v.getId()){
            case R.id.take_photo:
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkCameraPermission(MY_PERMISSIONS_REQUEST_CAMERA))
                        dispatchTakePictureIntent();
                } else
                    dispatchTakePictureIntent();
                break;
            case R.id.choose_photo:
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkCameraPermission(MY_PERMISSIONS_REQUEST_STORAGE))
                        dispatchChoosePictureIntent();
                } else
                    dispatchChoosePictureIntent();
                break;
            case R.id.submit:
                if(validateData())
                    submitData();
//                submitDataMother();
                break;
            case R.id.cancel:
                backFunction();
                break;
        }
    }

    private boolean validateData(){
        String birth_date = etBirthDate.getText().toString();
        String name = etName.getText().toString();
        String spouse_name = etSpouseName.getText().toString();
        String address = etAddress.getText().toString();
        String height = etHeight.getText().toString();
        String weight = replaceCommaToDot(etWeight.getText().toString());
        String blood_pressure_top = etBloodPressureTop.getText().toString();
        String blood_pressure_bottom = etBloodPressureBottom.getText().toString();

        if(name.length()<4){
            etName.setError(getString(R.string.error_name));
            etName.requestFocus();
            return false;
        }
        if(height.isEmpty()){
            etHeight.setError(getString(R.string.error_height_person));
            etHeight.requestFocus();
            return false;
        }
        if(weight.isEmpty()){
            etWeight.setError(getString(R.string.error_weight));
            etWeight.requestFocus();
            return false;
        }
        if(editMode) {
            if (birth_date.isEmpty()) {
                etBirthDate.setError(getString(R.string.error_birth_date));
                etBirthDate.requestFocus();
                return false;
            }
            if (spouse_name.length() < 4) {
                etSpouseName.setError(getString(R.string.error_spouse_name));
                etSpouseName.requestFocus();
                return false;
            }
            if (address.length() < 4) {
                etAddress.setError(getString(R.string.error_address));
                etAddress.requestFocus();
                return false;
            }
            if (blood_pressure_top.isEmpty()) {
                etBloodPressureTop.setError(getString(R.string.error_blood_pressure_top));
                etBloodPressureTop.requestFocus();
                return false;
            }
            if (blood_pressure_bottom.isEmpty()) {
                etBloodPressureBottom.setError(getString(R.string.error_blood_pressure_bottom));
                etBloodPressureBottom.requestFocus();
                return false;
            }

//            if (selectedDistrict == null) {
//                actvDistrict.setError(getString(R.string.error_district));
//                actvDistrict.requestFocus();
//                return false;
//            }

            if (selectedSubDistrict == null){
                actSubDistrict.setError("Kecamatan harus dipilih");
                actSubDistrict.requestFocus();
                return false;
            }

            if (selectedVillage == null){
                actVillage.setError("Desa harus dipilih");
                actVillage.requestFocus();
                return false;
            }

        }

        return true;
    }

    private void submitData(){
        if(mCurrentPhotoPath!=null){
            submitDataPhoto();
            return;
        }
        String auth_token = appSession.getData(AppSession.TOKEN);
        String name = etName.getText().toString();
        int height = Integer.parseInt(etHeight.getText().toString());
        double weight = Double.parseDouble(replaceCommaToDot(etWeight.getText().toString()));
        String birth_date = etBirthDate.getText().toString();
        String kk_name = etNamaKK.getText().toString();
        String nik = etNIK.getText().toString();
        String jampersal_status = etJamStatus.getText().toString();
        if(!editMode){
            showProgressBar(true);
            mApiService.createMother(auth_token, name, birth_date, height,37214, weight, kk_name,nik,jampersal_status).enqueue(formCallback.getCallback());
            return;
        }

        String blood_type = spBloodType.getSelectedItem().toString();
        String spouse_name = etSpouseName.getText().toString();
        String address = etAddress.getText().toString();
//        long state_id = selectedState.getId();
//        long district_id = selectedDistrict.getId();
        int blood_pressure_top = Integer.parseInt(etBloodPressureTop.getText().toString());
        int blood_pressure_bottom = Integer.parseInt(etBloodPressureBottom.getText().toString());
        long id = mother.getId();
        long sub_district_id = selectedSubDistrict.getId();
        long village = selectedVillage.getId();
        showProgressBar(true);
        mApiService.updateMother(id, id, auth_token, birth_date, name, blood_type,
                spouse_name, 37214, height, weight,
                blood_pressure_top, blood_pressure_bottom,kk_name, nik,jampersal_status,sub_district_id, village,address).enqueue(formCallback.getCallback());

    }

    private void submitDataPhoto(){
        String auth_token = appSession.getData(AppSession.TOKEN);
        String birth_date = etBirthDate.getText().toString();
        String name = etName.getText().toString();
        String blood_type = spBloodType.getSelectedItem().toString();
        String spouse_name = etSpouseName.getText().toString();
        String address = etAddress.getText().toString();
//        String state_id = ""+selectedState.getId();
        String district_id = ""+selectedDistrict.getId();
        String height = etHeight.getText().toString();
        String weight = replaceCommaToDot(etWeight.getText().toString());
        String blood_pressure_top = etBloodPressureTop.getText().toString();
        String blood_pressure_bottom = etBloodPressureBottom.getText().toString();
        String kk_name = etNamaKK.getText().toString();
        String nik = etNIK.getText().toString();
        String jampersal_status = etJamStatus.getText().toString();
        File photo = new File(mCurrentPhotoPath);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        photo
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", photo.getName(), requestFile);

        RequestBody rb_auth_token =
                RequestBody.create(
                        MediaType.parse("text/plain"), auth_token);

        RequestBody rb_name =
                RequestBody.create(
                        MediaType.parse("text/plain"), name);

        RequestBody rb_height =
                RequestBody.create(
                        MediaType.parse("text/plain"), height);

        RequestBody rb_weight =
                RequestBody.create(
                        MediaType.parse("text/plain"), weight);

        RequestBody rb_birth_date =
                RequestBody.create(
                        MediaType.parse("text/plain"), birth_date);

        RequestBody districtId =
                RequestBody.create(
                        MediaType.parse("text/plain"), ""+37214);

        RequestBody rb_kkName =
                RequestBody.create(MediaType.parse("text/plain"), kk_name);

        RequestBody rb_nik =
                RequestBody.create(MediaType.parse("text/plain"), nik);

        RequestBody rb_jampersalStatus =
                RequestBody.create(MediaType.parse("text/plain"), jampersal_status);

        if(!editMode){
            mApiService.createMother(rb_auth_token, rb_name, rb_birth_date, rb_height, rb_weight, districtId, rb_kkName, rb_nik, rb_jampersalStatus,
                    body).enqueue(formCallback.getCallback());
            return;
        }

        RequestBody rb_blood_type =
                RequestBody.create(
                        MediaType.parse("text/plain"), blood_type);

        RequestBody rb_spouse_name =
                RequestBody.create(
                        MediaType.parse("text/plain"), spouse_name);

        RequestBody rb_address =
                RequestBody.create(
                        MediaType.parse("text/plain"), address);

//        RequestBody rb_state_id =
//                RequestBody.create(
//                        MediaType.parse("text/plain"), state_id);

        RequestBody rb_district_id = RequestBody.create(MediaType.parse("text/plain"), ""+37214);

        RequestBody rb_sub_district_id =
                RequestBody.create(
                        MediaType.parse("text/plain"), ""+selectedSubDistrict.getId());

        RequestBody rb_village_id =
                RequestBody.create(
                        MediaType.parse("text/plain"), ""+selectedVillage.getId());

        RequestBody rb_blood_pressure_top =
                RequestBody.create(
                        MediaType.parse("text/plain"), blood_pressure_top);

        RequestBody rb_blood_pressure_bottom =
                RequestBody.create(
                        MediaType.parse("text/plain"), blood_pressure_bottom);

        showProgressBar(true);
        RequestBody rb_id =
                RequestBody.create(
                        MediaType.parse("text/plain"), ""+mother.getId());
        mApiService.updateMother(mother.getId(), rb_id, rb_auth_token, rb_birth_date, rb_name, rb_blood_type,
                rb_spouse_name, rb_district_id, rb_height, rb_weight,
                rb_blood_pressure_top, rb_blood_pressure_bottom, rb_kkName, rb_nik, rb_jampersalStatus, rb_sub_district_id, rb_village_id, rb_address, body).enqueue(formCallback.getCallback());
    }

    ApiCallback formCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.submit_ok), Toast.LENGTH_SHORT).show();
            if(!editMode) {
                Gson gson = createGsonDate();
//                Gson gson = createGsonDate();
                mother = gson.fromJson(result, Mother.class);
            }
            mApiService.getMother(mother.getId(), appSession.getData(AppSession.TOKEN))
                    .enqueue(motherCallback.getCallback());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    private void dispatchChoosePictureIntent(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                REQUEST_CHOOSE_PHOTO);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.kominfo.anaksehat.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();
        }

        if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK && data != null){
            mCurrentPhotoPath = ImageFilePath.getPath(context, data.getData());
            AppLog.d(mCurrentPhotoPath);
            setPic();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = ivThumbnail.getWidth();
        int targetH = ivThumbnail.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ivThumbnail.setImageBitmap(bitmap);
    }

    public boolean checkCameraPermission(int permission){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        permission);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        permission);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        dispatchTakePictureIntent();
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(context, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        dispatchChoosePictureIntent();
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(context, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Override
    public void onBackPressed() {
        backFunction();
    }

//    ApiCallback statecallback = new ApiCallback() {
//        @Override
//        public void onApiSuccess(String result) {
//            ApiData<State> stateApiData = new Gson().fromJson(result, new TypeToken<ApiData<State>>(){}.getType());
//            AppLog.d(new Gson().toJson(stateApiData));
//            ArrayAdapter<State> adapter = new ArrayAdapter<State>(context,
//                    android.R.layout.simple_dropdown_item_1line, stateApiData.getData());
//            actvState.setAdapter(adapter);
//            actvState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    selectedState = (State) parent.getAdapter().getItem(position);
//                    AppLog.d(new Gson().toJson(selectedState));
//                    mApiService.getDistrics(appSession.getData(AppSession.TOKEN),
//                            selectedState.getId()).enqueue(districtcallback.getCallback());
//                }
//            });
//            if(editMode){
//                selectedState = getState(mother.getState_name(), stateApiData.getData());
//                if(selectedState!=null){
//                    actvState.setText(selectedState.getName());
//                    mApiService.getDistrics(appSession.getData(AppSession.TOKEN),
//                            selectedState.getId()).enqueue(districtcallback.getCallback());
//                }
//            }
//        }
//
//        @Override
//        public void onApiFailure(String errorMessage) {
//
//        }
//    };

    ApiCallback districtcallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            District districtApiData = new Gson().fromJson(result, District.class);
            AppLog.d(new Gson().toJson(districtApiData));
//            ArrayAdapter<District> adapter = new ArrayAdapter<District>(context,
//                    android.R.layout.simple_dropdown_item_1line, districtApiData);
//            actvDistrict.setAdapter(adapter);
            actvDistrict.setEnabled(false);
            actvDistrict.setText(districtApiData.getName());
//            actvDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    selectedDistrict = (District) parent.getAdapter().getItem(position);
//                    AppLog.d(new Gson().toJson(selectedDistrict));
//
//                }
//            });
//            if(editMode){
//                selectedDistrict = getDistrict(mother.getDistrict_name(), districtApiData.getData());
//                if(selectedDistrict!=null){
//                    actvDistrict.setText(selectedDistrict.getName());
//                    mApiService.getSubDistricts(appSession.getData(AppSession.TOKEN)).enqueue(subdistrictcallback.getCallback());
//                }
//            }
        }

        @Override
        public void onApiFailure(String errorMessage) {

        }
    };


    ApiCallback subdistrictcallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            ApiData<SubDistrict> subDistrictApiData = new Gson().fromJson(result, new TypeToken<ApiData<SubDistrict>>(){}.getType());
            ArrayAdapter<SubDistrict> adapter = new ArrayAdapter<SubDistrict>(context,
                    android.R.layout.simple_dropdown_item_1line, subDistrictApiData.getData());
            actSubDistrict.setAdapter(adapter);
            actSubDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedSubDistrict = (SubDistrict) parent.getAdapter().getItem(position);
                    AppLog.d(new Gson().toJson(selectedSubDistrict));
                    mApiService.getVillages(selectedSubDistrict.getId(),
                            appSession.getData(AppSession.TOKEN)).enqueue(villagecallback.getCallback());
                }
            });

            if(editMode) {
                if (!mother.getSub_district_name().equals("")) {
                    selectedSubDistrict = getSubDistrict(mother.getSub_district_name(), subDistrictApiData.getData());
                    if(selectedSubDistrict!=null) {
                        actSubDistrict.setText(selectedSubDistrict.getName());
                    }
                    mApiService.getVillages(selectedSubDistrict.getId(),
                            appSession.getData(AppSession.TOKEN)).enqueue(villagecallback.getCallback());
                }
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {

        }
    };

    ApiCallback villagecallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            ApiData<Villages> villagesApiData = new Gson().fromJson(result, new TypeToken<ApiData<Villages>>(){}.getType());
            ArrayAdapter<Villages> adapter = new ArrayAdapter<Villages>(context,
                    android.R.layout.simple_dropdown_item_1line, villagesApiData.getData());
            actVillage.setAdapter(adapter);
            actVillage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedVillage = (Villages) parent.getAdapter().getItem(position);
                    AppLog.d(new Gson().toJson(selectedVillage));
                }
            });

            if(editMode){
                if (!mother.getVillage_name().equals("")) {
                    selectedVillage = getVillages(mother.getVillage_name(), villagesApiData.getData());
                    if(selectedVillage!=null)
                        actVillage.setText(selectedVillage.getName());
                }
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {

        }
    };

//    private State getState(String state_name, List<State> states){
//        for (State row : states) {
//            if (row.getName().compareToIgnoreCase(state_name)==0)
//                return row;
//        }
//        return null;
//    }

    private District getDistrict(String district_name, List<District> districts){
        for (District row : districts) {
            if (row.getName().compareToIgnoreCase(district_name)==0)
                return row;
        }
        return null;
    }

    private SubDistrict getSubDistrict(String sub_district_name, List<SubDistrict> subDistricts){
        for (SubDistrict row : subDistricts) {
            if (row.getName().compareToIgnoreCase(sub_district_name)==0)
                return row;
        }
        return null;
    }

    private Villages getVillages(String village_name, List<Villages> villages){
        for (Villages row : villages) {
            if (row.getName().compareToIgnoreCase(village_name)==0)
                return row;
        }
        return null;
    }

    ApiCallback motherCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
//            Gson gson = createGsonDate();
            mother = gson.fromJson(result, Mother.class);
            if(user.getPosyandu()==0){
                user.setMotherId(mother.getId());
                appSession.setData(AppSession.USER, new Gson().toJson(user));
                appSession.setData(AppSession.MOTHER, new Gson().toJson(mother));
            }
            Intent intent = new Intent(context, MotherDetailActivity.class);
            intent.putExtra("data", new Gson().toJson(mother));
            startActivity(intent);
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    private void backFunction(){
        if(editMode){
            Intent intent = new Intent(context, MotherDetailActivity.class);
            intent.putExtra("data", new Gson().toJson(mother));
            showWarning(intent, R.string.warning, R.string.warning_edit_mother, R.string.ok, R.string.cancel);
        } else {
            showWarning(R.string.warning, R.string.warning_create_mother, R.string.ok, R.string.cancel);
        }
    }

//    private void startShowcase(){
//        if(editMode) {
//            showcaseHelper.addShowcaseView(etName, getString(R.string.guide_form_mother_name));
//            showcaseHelper.addShowcaseView(etBirthDate, getString(R.string.guide_form_mother_birth_date));
//            showcaseHelper.addShowcaseView(etSpouseName, getString(R.string.guide_form_mother_spouse_name));
//            showcaseHelper.addShowcaseView(etAddress, getString(R.string.guide_form_mother_address));
//            showcaseHelper.addShowcaseView(actvState, getString(R.string.guide_form_mother_state));
//            showcaseHelper.addShowcaseView(actvDistrict, getString(R.string.guide_form_mother_district));
//            showcaseHelper.addShowcaseView(spBloodType, getString(R.string.guide_form_mother_blood));
//            showcaseHelper.addShowcaseView(etHeight, getString(R.string.guide_form_mother_height));
//            showcaseHelper.addShowcaseView(etWeight, getString(R.string.guide_form_mother_weight));
//            showcaseHelper.addShowcaseView(findViewById(R.id.layout_pressure), getString(R.string.guide_form_mother_blood_pressure));
//        }
//        else {
//            showcaseHelper.addShowcaseView(etName, getString(R.string.guide_form_mother_name));
//            showcaseHelper.addShowcaseView(etHeight, getString(R.string.guide_form_mother_height));
//            showcaseHelper.addShowcaseView(etWeight, getString(R.string.guide_form_mother_weight));
//        }
//        showcaseHelper.startGuide();
//    }

    private void initEditForm(){
        int[] edit_layouts = {R.id.birth_date_layout,R.id.spouse_name,R.id.address,
                R.id.district, R.id.blood_type_layout, R.id.blood_pressure_layout, R.id.sub_district,
                R.id.village, R.id.tv_alamat, R.id.tv_desa, R.id.tv_kecamatan, R.id.tv_kabupaten};
        for (int i=0;i<edit_layouts.length;i++) {
            View view = findViewById(edit_layouts[i]);
            view.setVisibility(View.VISIBLE);
        }
    }
}
