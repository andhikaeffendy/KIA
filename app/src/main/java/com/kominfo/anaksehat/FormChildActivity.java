package com.kominfo.anaksehat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.helpers.ShowcaseHelper;
import com.kominfo.anaksehat.helpers.adapters.AutoCompleteAdapter;
import com.kominfo.anaksehat.helpers.adapters.BabyCheckboxAdapter;
import com.kominfo.anaksehat.helpers.adapters.ChildrenAdapter;
import com.kominfo.anaksehat.helpers.adapters.OnItemCheckListener;
import com.kominfo.anaksehat.models.Checkbox;
import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.CropSquareTransformation;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.helpers.ImageFilePath;
import com.kominfo.anaksehat.helpers.apihelper.UtilsApi;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.Mother;
import com.kominfo.anaksehat.models.User;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormChildActivity extends BaseActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_CHOOSE_PHOTO = 2;
    static final int MY_PERMISSIONS_REQUEST_CAMERA = 99;
    static final int MY_PERMISSIONS_REQUEST_STORAGE = 100;

    private EditText etBirthDate,etName,etFirstLength,etFirstWeight,etHeight,etWeight,
            etFirstHeadRound, etNote, etJampersalStatus, etChildNumber;
    private Spinner spBloodType;
    private ImageView ivThumbnail, ivPickDate;
    private RadioButton rbGenderMale, rbGenderFemale;
    private String mCurrentPhotoPath;
    private AutoCompleteTextView actvMotherName;
    private Mother selectedMother;
    private User user;
    private boolean editMode = false;
    private Child child;
    private long give_birth_id = 0;
    private Picasso picasso;
    private ShowcaseHelper showcaseHelper;

    private RecyclerView rvBabyCondition, rvBabyTreatment;
    private BabyCheckboxAdapter mAdapterConditions;
    private BabyCheckboxAdapter mAdapterTreatment;
    private List<Checkbox> dataListConditions;
    private List<Checkbox> dataListTreatment;

    private List<Integer> _isCheckedConditions = new ArrayList<Integer>();
    private List<Integer> _isCheckedTreatment = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_child);

        checkSession();
        user = getUserSession();
        picasso = Picasso.get();
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        setTitle(R.string.title_create_child);

        ivThumbnail = findViewById(R.id.thumbnail);
        etBirthDate = findViewById(R.id.birth_date);
        etName = findViewById(R.id.name);
        spBloodType = findViewById(R.id.blood_type);
        etFirstLength = findViewById(R.id.first_length);
        etFirstWeight = findViewById(R.id.first_weight);
        etHeight = findViewById(R.id.height);
        etWeight = findViewById(R.id.weight);
        etFirstHeadRound = findViewById(R.id.first_head_round);
        etNote = findViewById(R.id.et_note);
        etChildNumber = findViewById(R.id.child_number);
        etJampersalStatus = findViewById(R.id.et_jampersal_status);
        actvMotherName = findViewById(R.id.mother_name);
        rbGenderMale = findViewById(R.id.gender);
        rbGenderFemale = findViewById(R.id.gender_female);
        ivPickDate = findViewById(R.id.birth_icon);

        rvBabyCondition = findViewById(R.id.rv_baby_condition_ids);
        rvBabyTreatment = findViewById(R.id.rv_baby_treatment_ids);

        rvBabyCondition = findViewById(R.id.rv_baby_condition_ids);
        rvBabyTreatment = findViewById(R.id.rv_baby_treatment_ids);
        dataListConditions = new ArrayList<Checkbox>();
        dataListTreatment = new ArrayList<Checkbox>();
        mAdapterConditions = new BabyCheckboxAdapter(context, dataListConditions, new OnItemCheckListener<Checkbox, String>() {
            @Override
            public void onItemCheck(Checkbox data, String type) {
                _isCheckedConditions.add(data.getId());
                Log.d("DEBUG", "CHECKED CONDITIONS" + _isCheckedConditions);
            }

            @Override
            public void onItemUncheck(Checkbox data, String type) {
                _isCheckedConditions.remove(Integer.valueOf(data.getId()));
                Log.d("DEBUG", "UNCHECKED CONDITIONS" + _isCheckedConditions);
            }

        }, "Conditions");
        mAdapterTreatment = new BabyCheckboxAdapter(context, dataListTreatment, new OnItemCheckListener<Checkbox, String>() {
            @Override
            public void onItemCheck(Checkbox data, String type) {
                _isCheckedTreatment.add(data.getId());
                Log.d("DEBUG", "CHECKED TREATMENTS" + _isCheckedTreatment);
            }

            @Override
            public void onItemUncheck(Checkbox data, String type) {
                _isCheckedTreatment.remove(Integer.valueOf(data.getId()));
                Log.d("DEBUG", "UNCHECKED TREATMENTS" + _isCheckedTreatment);
            }
        }, "Treatments");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        rvBabyCondition.setLayoutManager(mLayoutManager);
        rvBabyCondition.setItemAnimator(new DefaultItemAnimator());
        rvBabyCondition.setAdapter(mAdapterConditions);
//        rvBabyCondition.setVisibility(View.GONE);

        rvBabyTreatment.setLayoutManager(mLayoutManager1);
        rvBabyTreatment.setItemAnimator(new DefaultItemAnimator());
        rvBabyTreatment.setAdapter(mAdapterTreatment);
//        rvBabyTreatment.setVisibility(View.GONE);

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
        etFirstHeadRound.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

        showcaseHelper = new ShowcaseHelper(context, ShowcaseHelper.FORM_CHILD_ID);

        give_birth_id = getIntent().getLongExtra("give_birth_id", 0);
        editMode = getIntent().getBooleanExtra("edit_mode", false);
        if(!editMode) {
            initCreateData();
        } else initEditData();

        //startShowcase();
    }

    private void initData() {
        mApiService.getBabyConditions(appSession.getData(AppSession.TOKEN))
                .enqueue(babyConditionsCallback.getCallback());

        mApiService.getBabyTreatment(appSession.getData(AppSession.TOKEN))
                .enqueue(babyTreatmentCallback.getCallback());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.children, menu);
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

    private void initCreateData(){
        if(user.getPosyandu()==0&&user.getMotherId()>0){
            selectedMother = new Mother();
            selectedMother.setId(user.getMotherId());
//            actvMotherName.setVisibility(View.GONE);
        } else {
            String mother = getIntent().getStringExtra("mother");
            String motherBirthDate = getIntent().getStringExtra("birth_date");
            long motherId = getIntent().getLongExtra("mother_id", 0);
            if (motherId > 0) {
                Log.d("DEBUG", "MOTHER "+mother);
                selectedMother = new Mother();
                selectedMother.setId(motherId);
//                selectedMother.setId(motherId);
                actvMotherName.setText(mother + "("+motherBirthDate+")");
                actvMotherName.setEnabled(false);
            }
//            else {
//                Log.d("DEBUG", "MASUK ELSE");
//                selectedMother.setId(user.getMotherId());
//            }
            mApiService.getMothers(appSession.getData(AppSession.TOKEN))
                    .enqueue(mothersCallback.getCallback());
        }
    }

//    private void initCreateData(){
//        if(user.getPosyandu()==0&&user.getMotherId()>0){
//            String mother = getIntent().getStringExtra("mother");
//            String motherBirthDate = getIntent().getStringExtra("birth_date");
//            long motherId = getIntent().getLongExtra("mother_id", 0);
//            selectedMother = new Mother();
//            if (motherId > 0) {
//                selectedMother.setId(motherId);
//                actvMotherName.setText(mother + "("+motherBirthDate+")");
//                actvMotherName.setEnabled(false);
//            } else {
//                selectedMother.setId(user.getMotherId());
//            }
////            actvMotherName.setVisibility(View.GONE);
//
//        }
//
//        mApiService.getMothers(appSession.getData(AppSession.TOKEN))
//                .enqueue(mothersCallback.getCallback());
//    }

    private void initEditData(){
        child = new Gson().fromJson(getIntent().getStringExtra("edit_data"), Child.class);
        give_birth_id = child.getGive_birth_id();
        if(user.getPosyandu()==0&&user.getMotherId()>0){
            selectedMother = getMotherSession();
            actvMotherName.setVisibility(View.GONE);
        } else
            mApiService.getMothers(appSession.getData(AppSession.TOKEN))
                    .enqueue(mothersCallback.getCallback());

        setTitle(R.string.title_edit_child);
        picasso.load(UtilsApi.BASE_URL+child.getPhoto_url())
                .transform(new CropSquareTransformation())
                .error(R.drawable.menuchild)
                .placeholder(R.drawable.menuchild)
                .into(ivThumbnail);

        etBirthDate.setText(DateHelper.getDateServer(child.getBirth_date()));
        etName.setText(child.getName());
        switch (child.getBlood_type()){
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
        etFirstLength.setText(""+child.getFirst_length());
        etFirstWeight.setText(replaceDotToComma(""+child.getFirst_weight()));
        etHeight.setText(""+child.getHeight());
        etWeight.setText(replaceDotToComma(""+child.getWeight()));;
        etFirstHeadRound.setText(""+child.getFirst_head_round());
        if(child.getGender().compareToIgnoreCase("female")==0)
            rbGenderFemale.setChecked(true);

        etChildNumber.setText(""+child.getChild_number());
        etNote.setText(child.getNote());
        etJampersalStatus.setText(child.getJampersal_status());
        for (Checkbox condition : child.getNb_baby_conditions()) {
            _isCheckedConditions.add(condition.getId());
        }
        for (Checkbox treatment : child.getNb_baby_treatments()) {
            _isCheckedTreatment.add(treatment.getId());
        }
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
        String height = etHeight.getText().toString();
        String weight = etWeight.getText().toString();
        String first_head_round = etFirstHeadRound.getText().toString();

        if(birth_date.isEmpty()){
            etBirthDate.setError(getString(R.string.error_birth_date));
            etBirthDate.requestFocus();
            return false;
        }
        if(name.length()<4){
            etName.setError(getString(R.string.error_name));
            etName.requestFocus();
            return false;
        }
        if(height.isEmpty()){
            etHeight.setError(getString(R.string.error_height));
            etHeight.requestFocus();
            return false;
        }
        if(weight.isEmpty()){
            etWeight.setError(getString(R.string.error_weight));
            etWeight.requestFocus();
            return false;
        }
        if(first_head_round.isEmpty()){
            etFirstHeadRound.setError(getString(R.string.error_first_head_round));
            etFirstHeadRound.requestFocus();
            return false;
        }
        if(mCurrentPhotoPath==null){

        }
        return true;
    }

    private void submitData(){
        if(mCurrentPhotoPath!=null){
            submitDataPhoto();
            return;
        }
        String auth_token = appSession.getData(AppSession.TOKEN);
        String birth_date = etBirthDate.getText().toString();
        String name = etName.getText().toString();
        String blood_type = spBloodType.getSelectedItem().toString();
        String gender = "Male";
        int height = Integer.parseInt(etHeight.getText().toString());
        double weight = Double.parseDouble(replaceCommaToDot(etWeight.getText().toString()));
        int first_head_round = Integer.parseInt(etFirstHeadRound.getText().toString());
        long mother_id = selectedMother.getId();

        if(!rbGenderMale.isChecked()) gender = "Female";

        String note = etNote.getText().toString();
        String jampersal_status = etJampersalStatus.getText().toString();
        int child_number = Integer.parseInt(etChildNumber.getText().toString());
        String nb_baby_treatments = _isCheckedTreatment.toString();
        String nb_baby_conditions = _isCheckedConditions.toString();

        showProgressBar(true);
        if(editMode){
            long id = child.getId();
            mApiService.updateChild(id, id, auth_token, birth_date, name, gender, blood_type,
                    height, weight, first_head_round, mother_id, child_number, jampersal_status,
                    note, nb_baby_conditions, nb_baby_treatments)
                    .enqueue(formCallback.getCallback());
        } else
            mApiService.createChild(auth_token, birth_date, name, gender, blood_type,
                height, weight, first_head_round, mother_id, give_birth_id, child_number,
                    jampersal_status, note, nb_baby_conditions, nb_baby_treatments)
                .enqueue(formCallback.getCallback());
    }

    private void submitDataPhoto(){
        String auth_token = appSession.getData(AppSession.TOKEN);
        String birth_date = etBirthDate.getText().toString();
        String name = etName.getText().toString();
        String blood_type = spBloodType.getSelectedItem().toString();
        String gender = "Male";
        String height = etHeight.getText().toString();
        String weight = replaceCommaToDot(etWeight.getText().toString());
        String first_head_round = etFirstHeadRound.getText().toString();
        String mother_id = ""+ selectedMother.getId();
        File photo = new File(mCurrentPhotoPath);

        if(!rbGenderMale.isChecked()) gender = "Female";

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

        RequestBody rb_birth_date =
                RequestBody.create(
                        MediaType.parse("text/plain"), birth_date);

        RequestBody rb_name =
                RequestBody.create(
                        MediaType.parse("text/plain"), name);

        RequestBody rb_gender =
                RequestBody.create(
                        MediaType.parse("text/plain"), gender);

        RequestBody rb_blood_type =
                RequestBody.create(
                        MediaType.parse("text/plain"), blood_type);

        RequestBody rb_height =
                RequestBody.create(
                        MediaType.parse("text/plain"), height);

        RequestBody rb_weight =
                RequestBody.create(
                        MediaType.parse("text/plain"), weight);

        RequestBody rb_first_head_round =
                RequestBody.create(
                        MediaType.parse("text/plain"), first_head_round);

        RequestBody rb_mother_id =
                RequestBody.create(
                        MediaType.parse("text/plain"), mother_id);

        RequestBody rb_give_birth_id =
                RequestBody.create(
                        MediaType.parse("text/plain"), ""+give_birth_id);

        showProgressBar(true);
        if(editMode){
            long id = child.getId();
            RequestBody rb_id =
                    RequestBody.create(
                            MediaType.parse("text/plain"), ""+child.getId());
            mApiService.updateChild(id, rb_id, rb_auth_token, rb_birth_date, rb_name, rb_gender, rb_blood_type,
                    rb_height, rb_weight, rb_first_head_round, rb_mother_id, body)
                    .enqueue(formCallback.getCallback());
        } else
            mApiService.createChild(rb_auth_token, rb_birth_date, rb_name, rb_gender, rb_blood_type,
                rb_height, rb_weight, rb_first_head_round, rb_mother_id, rb_give_birth_id, body)
                .enqueue(formCallback.getCallback());
    }

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

    ApiCallback formCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.submit_ok), Toast.LENGTH_SHORT).show();
            if(!editMode) {
                Gson gson = createGsonDate();
                child = gson.fromJson(result, Child.class);
            }
            if(!editMode && give_birth_id > 0){
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",new Gson().toJson(child));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            } else
                mApiService.getChild(child.getId(), appSession.getData(AppSession.TOKEN))
                    .enqueue(childCallback.getCallback());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback mothersCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<Mother> stateApiData = gson.fromJson(result, new TypeToken<ApiData<Mother>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
//            ArrayAdapter<Mother> adapter = new ArrayAdapter<Mother>(context,
//                    android.R.layout.simple_dropdown_item_1line, stateApiData.getData());
            AutoCompleteAdapter adapter = new AutoCompleteAdapter(context, R.layout.custom_row_autocomplete, stateApiData.getData());
            actvMotherName.setAdapter(adapter);
            actvMotherName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedMother = (Mother) parent.getAdapter().getItem(position);
                    AppLog.d(new Gson().toJson(selectedMother));
                }
            });

            if(editMode){
                selectedMother = getMother(child.getMother_name(), stateApiData.getData());
                if(selectedMother!=null)
                    actvMotherName.setText(selectedMother.getName());
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback childCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            child = gson.fromJson(result, Child.class);
            Intent intent = new Intent(context, ChildDetailActivity.class);
            intent.putExtra("data", new Gson().toJson(child));
            startActivity(intent);
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
//            finish();
        }
    };

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

    private Mother getMother(String mother_name, List<Mother> mothers){
        for (Mother row : mothers) {
            if (row.getName().compareToIgnoreCase(mother_name)==0)
                return row;
        }
        return null;
    }

    private void backFunction(){
        if(editMode){
            Intent intent = new Intent(context, ChildDetailActivity.class);
            intent.putExtra("data", new Gson().toJson(child));
            showWarning(intent, R.string.warning, R.string.warning_edit_child, R.string.ok, R.string.cancel);
        } else {
            showWarning(R.string.warning, R.string.warning_create_child, R.string.ok, R.string.cancel);
        }
    }

    private void startShowcase(){
        showcaseHelper.addShowcaseView(etName, getString(R.string.guide_form_child_name));
        showcaseHelper.addShowcaseView(etBirthDate, getString(R.string.guide_form_child_birth_date));
        if(actvMotherName.getVisibility()==View.VISIBLE)
            showcaseHelper.addShowcaseView(actvMotherName, getString(R.string.guide_form_child_mother_name));
        showcaseHelper.addShowcaseView(findViewById(R.id.layout_gender), getString(R.string.guide_form_child_gender));
        showcaseHelper.addShowcaseView(spBloodType, getString(R.string.guide_form_child_blood));
        showcaseHelper.addShowcaseView(etHeight, getString(R.string.guide_form_child_height));
        showcaseHelper.addShowcaseView(etWeight, getString(R.string.guide_form_child_weight));
        showcaseHelper.addShowcaseView(etFirstHeadRound, getString(R.string.guide_form_child_round));
        showcaseHelper.startGuide();
    }

    ApiCallback babyConditionsCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<Checkbox> stateApiData = gson.fromJson(result, new TypeToken<ApiData<Checkbox>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
            if(editMode){
                mAdapterConditions.setData(stateApiData.getData(), child.getNb_baby_conditions());
            } else {
                mAdapterConditions.setData(stateApiData.getData());
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback babyTreatmentCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<Checkbox> stateApiData = gson.fromJson(result, new TypeToken<ApiData<Checkbox>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
            if(editMode){
                mAdapterTreatment.setData(stateApiData.getData(), child.getNb_baby_treatments());
            } else {
                mAdapterTreatment.setData(stateApiData.getData());
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };
}
