package com.kominfo.anaksehat.controllers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.kominfo.anaksehat.ChildHistoryDetailActivity;
import com.kominfo.anaksehat.LoginActivity;
import com.kominfo.anaksehat.MainActivity;
import com.kominfo.anaksehat.PregnancyHistoryDetailActivity;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.apihelper.BaseApiService;
import com.kominfo.anaksehat.helpers.apihelper.UtilsApi;
import com.kominfo.anaksehat.models.ApiStatus;
import com.kominfo.anaksehat.models.Mother;
import com.kominfo.anaksehat.models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class BaseActivity extends AppCompatActivity {
    public final static String TAG = "Stunting App";

    private static int calendarResourceId = R.id.birth_date;
    public AppSession appSession;
    public Context context;
    public BaseApiService mApiService;
    private Dialog progressDialog;
    public String link2App = "https://play.google.com/store/apps/details?coming-soon";

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if(context.getClass().getSimpleName().compareToIgnoreCase("MainActivity")!=0)
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setTitle(String title){
//        TextView tv_title = findViewById(R.id.header_title);
//        tv_title.setText(title);
    }

    public void setTitle(int titleId){
//        TextView tv_title = findViewById(R.id.header_title);
//        tv_title.setText(titleId);
    }

    protected void initViews(){
        context = this;
        appSession = new AppSession(this);
        mApiService = UtilsApi.getApiService();
        progressDialog = new Dialog(context);
    }

    public boolean checkSession(){
        if(!appSession.isLogin()) {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
            finish();
            return false;
        }
        return true;
    }

    public void checkSession(Context context){
        if(!appSession.isLogin()) {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void checkSession2(){
        if(appSession.isLogin()) {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void logout2(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.warning_logout);
        TextView close = dialog.findViewById(R.id.close);
        Button submit = dialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar(true);
                mApiService.logoutRequest(appSession.getData(AppSession.TOKEN))
                        .enqueue(logoutCallback.getCallback());
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.logout)
                .setMessage(R.string.warning_logout);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                showProgressBar(true);
                mApiService.logoutRequest(appSession.getData(AppSession.TOKEN))
                        .enqueue(logoutCallback.getCallback());
            }
            });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

            }
            });


        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    public void showProgressBar(boolean show){
        if(show){
            showProgres();
        } else {
            progressDialog.dismiss();
        }
    }

    public abstract class ApiCallback{
        private Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        ApiStatus status = new Gson().fromJson(result, ApiStatus.class);
                        if(status==null){
                            onApiFailure(getString(R.string.error_parsing));
                            return;
                        }
                        if(status.getStatus()!=null) {
                            if (status.getStatus().compareToIgnoreCase("success") == 0) {
                                onApiSuccess(result);
                            } else {
                                if(status.getMessage().equalsIgnoreCase("You need to login")){
                                    onApiFailure(status.getMessage());
                                    appSession.logout();
                                    checkSession();
                                } else onApiFailure(status.getMessage());
                            }
                        } else {
                            onApiFailure(getString(R.string.error_parsing));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    String errormsg = "";
                    switch (response.code()){
                        case 500:
                            errormsg = getString(R.string.error_500);
                            break;
                        case 400:
                            errormsg = getString(R.string.error_400);
                            break;
                        case 403:
                            errormsg = getString(R.string.error_403);
                            break;
                        case 404:
                            errormsg = getString(R.string.error_404);
                            break;
                        default:
                            errormsg = getString(R.string.error_xxx);
                            break;
                    }
                    onApiFailure(errormsg);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onApiFailure(t.getMessage());
            }
        };

        public Callback<ResponseBody> getCallback() {
            return callback;
        }

        public abstract void onApiSuccess(String result);
        public abstract void onApiFailure(String errorMessage);
    }

    ApiCallback logoutCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            appSession.logout();
            checkSession();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            appSession.logout();
            checkSession();
//            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public EditText holder;
        public Date minDate;
        public Date maxDate;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            if(minDate!=null)
                dpd.getDatePicker().setMinDate(minDate.getTime());
            if(maxDate!=null){
                dpd.getDatePicker().setMaxDate(maxDate.getTime());
            } else dpd.getDatePicker().setMaxDate(new Date().getTime());
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            if(holder==null) {
                EditText date = getActivity().findViewById(calendarResourceId);
                AppLog.d(calendarResourceId+"");
                if(date!=null)
                    date.setText(getTwoDigitNumber(day)+"-"+getTwoDigitNumber(month+1)+"-"+year);
            } else {
                holder.setText(getTwoDigitNumber(day)+"-"+getTwoDigitNumber(month+1)+"-"+year);
            }
        }

        private String getTwoDigitNumber(int number){
            if(number/10 > 0)return ""+number;
            return "0"+number;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        calendarResourceId = v.getNextFocusUpId();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showWarning(int title, int message, int ok, int cancel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
        if(ok!=0){
            builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    finish();
                }
            });
        }
        if(cancel!=0) {
            builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button

                }
            });
        }

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    public void showWarning(final Intent intent, int title, int message, int ok, int cancel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
        if(ok!=0){
            builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    startActivity(intent);
                    finish();
                }
            });
        }
        if(cancel!=0) {
            builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button

                }
            });
        }

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    private void showProgres(){
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public String replaceCommaToDot(String s){
        return s.replace(",",".");
    }

    public String replaceDotToComma(String s){
        return s.replace(".",",");
    }

    public User getUserSession(){
        return new Gson().fromJson(appSession.getData(AppSession.USER), User.class);
    }

    public Mother getMotherSession(){
        return new Gson().fromJson(appSession.getData(AppSession.MOTHER), Mother.class);
    }

    public void checkMotherData(){
        if(getUserSession().getPosyandu()==0) {
            if (appSession.getData(AppSession.MOTHER, null) == null) {
                Toast.makeText(context, R.string.error_mother_data, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private class ShareImage extends AsyncTask<String, Void, Uri> {
        private String stringText = "";

        protected Uri doInBackground(String... data) {
            stringText = data[0];
            return share();
        }

        protected void onPostExecute(Uri uri) {
            showProgressBar(false);
            if(uri==null) {
                Toast.makeText(context, R.string.error_creating_image, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_TEXT, stringText);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/png");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Share image via"));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    private Uri share(){
        Bitmap bitmap =getBitmapFromView(findViewById(R.id.full_layout));
        try {
            File file = new File(this.getExternalCacheDir(),"stunting.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            Uri uri = FileProvider.getUriForFile(
                    context,
                    context.getApplicationContext()
                            .getPackageName() + ".fileprovider", file);
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onClickShare(View view){
        if(view.getId()==R.id.share){
            if(context.getClass().getSimpleName()
                    .compareToIgnoreCase(ChildHistoryDetailActivity.class.getSimpleName())==0){
                doShare(R.string.share_child_history);
            } else if (context.getClass().getSimpleName()
                    .compareToIgnoreCase(PregnancyHistoryDetailActivity.class.getSimpleName())==0){
                doShare(R.string.share_pregnancy_history);
            } else doShare(R.string.share_immunization);
        }
    }

    public void doShare(int stringId){
        showProgressBar(true);
        new ShareImage().execute(createEditor(stringId));
    }

    public String createEditor(int stringId){
        String editor = getString(stringId);
        editor = editor.replace("[link download apps KIA]", link2App);
        return editor;
    }

    public Gson createGsonDate(){
        return new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            @Override
            public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return df.parse(json.getAsString());
                } catch (ParseException e) {
                    return null;
                }
            }
        }).create();
    }
}
