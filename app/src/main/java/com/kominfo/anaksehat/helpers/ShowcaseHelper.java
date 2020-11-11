package com.kominfo.anaksehat.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kominfo.anaksehat.R;

import java.util.ArrayList;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseTooltip;

public class ShowcaseHelper {
    public final static String HOME_ID = "main";
    public final static String FORM_CHILD_ID = "form-child";
    public final static String FORM_CHILD_HISTORY_ID = "form-child-history";
    public final static String FORM_MOTHER_ID = "form-mothers";
    public final static String FORM_PREGNANCY_ID = "form-pregnancy";
    public final static String FORM_PREGNANCY_HISTORY_ID = "form-pregnancy-history";

    private Context context;
    private Activity activity;
    private List<View> views;
    private List<String> texts;
    private AppSession appSession;

    private String ID;
    private int counter = 1;

    public ShowcaseHelper(Context context){
        this.context = context;
        this.appSession = new AppSession(context);
        this.activity = (Activity)context;
        this.views = new ArrayList<>();
        this.texts = new ArrayList<>();
    }

    public ShowcaseHelper(Context context, String ID){
        this.context = context;
        this.appSession = new AppSession(context);
        this.activity = (Activity)context;
        this.views = new ArrayList<>();
        this.texts = new ArrayList<>();
        this.ID = ID+":";
    }

    public void addShowcaseView(View view, String text){
        views.add(view);
        texts.add(text);
    }

    public void show(final int iterate){
        if(iterate == views.size())
            return;
        View view = views.get(iterate);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.guide, null, false);
        LinearLayout steps = itemView.findViewById(R.id.guide_steps);
        TextView text = itemView.findViewById(R.id.guide_text);
        TextView prev = itemView.findViewById(R.id.guide_prev);
        TextView next = itemView.findViewById(R.id.guide_next);
        for (int i =0;i<views.size();i++){
            ImageView ivstep;
            ivstep = new ImageView(context);
            ivstep.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));
            if(i==iterate){
                ivstep.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            steps.addView(ivstep, params);
        }
        if(iterate == 0){
            prev.setVisibility(View.INVISIBLE);
        }
        if(iterate+1 == views.size()){
            next.setText(R.string.button_finish);
        }
        text.setText(texts.get(iterate));

//        view.requestFocus();
        InputMethodManager imm =  (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        ShowcaseTooltip tooltip = ShowcaseTooltip.build(context)
                .corner(30)
                .color(Color.parseColor("#31353e"))
                .customView(itemView);
        final MaterialShowcaseView showcaseView = new MaterialShowcaseView.Builder(activity)
                .setTarget(view)
                .renderOverNavigationBar()
                .withRectangleShape()
                .setToolTip(tooltip)
                .setDismissOnTargetTouch(true)
                .setMaskColour(Color.parseColor("#66000000"))
//                .singleUse(ID+counter)
                .show();

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showcaseView.animateOut();
                show(iterate-1);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showcaseView.animateOut();
                show(iterate+1);
            }
        });

        counter++;
    }

    public void startGuide(){
        if(appSession.isShowedGuide(ID)){
            AppLog.d(ID + "is showed");
        } else {
            AppLog.d(ID + "is showing");
            show(0);
            appSession.setShowGuide(ID);
        }
    }
}
