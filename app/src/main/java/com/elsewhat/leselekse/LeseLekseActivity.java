package com.elsewhat.leselekse;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.KeyListener;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elsewhat.leselekse.model.LeseLekse;
import com.elsewhat.leselekse.model.LeseLinje;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LeseLekseActivity extends AppCompatActivity {
    private LeseLekse leseLekse;

    TextView mTekstLese;
    RatingBar mStjerner;
    CharacterStyle markertOrdStil = new ForegroundColorSpan(Color.parseColor("#FFDA7A"));

    private static final String LOGTAG = "LeseLekseActivity";

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lese_lekse);

        mVisible = true;
        mContentView = findViewById(R.id.lese_tekst);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });





        LeseLinje[] leseLinjer = {
                new LeseLinje("r, i, s, e, l, o, l, e, r, s, o, i"),
                new LeseLinje("re, ro, ri, se, so, le, li, ro, ri"),
                new LeseLinje("O-le ler."),
                new LeseLinje("Si-ri rir."),
                new LeseLinje("O-le ser ro-se."),
                new LeseLinje("o r s o ø e"),
                new LeseLinje("se"),
                new LeseLinje("se os los ros"),
                new LeseLinje("les"),
                new LeseLinje("les os los ros"),
                new LeseLinje("ros"),
        };
        leseLekse = new LeseLekse("Uke 3",3,leseLinjer);

        mTekstLese = (TextView) findViewById(R.id.lese_tekst);
        mTekstLese.setText(leseLekse.forsteLeseLinje().hentTekst(markertOrdStil));
        mStjerner =(RatingBar) findViewById(R.id.stjerner);
        Drawable mStjernerDrawable = mStjerner.getProgressDrawable();
        DrawableCompat.setTint(mStjernerDrawable, Color.YELLOW);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
        if(event.getRepeatCount()>0 || event.getAction()!= KeyEvent.ACTION_DOWN) {
            return false;
        }
        boolean handled = false;
        //Log.d(LOGTAG,"Key onKey :" + String.valueOf(event.getKeyCode()));

        LeseLinje leseLinje=leseLekse.hentLeseLinje();
        switch (event.getKeyCode()){
            case KeyEvent.KEYCODE_DPAD_CENTER:
                //FireTV center button
                //only allow switch once pr half second
                //TODO: Swap highlightning
                leseLinje.markerNesteOrd();
                mTekstLese.setText(leseLinje.hentTekst(markertOrdStil));
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                //FireTV left button
                leseLinje = leseLekse.forrigeLeseLinje();
                if (leseLinje == null) {
                    leseLinje = leseLekse.forsteLeseLinje();
                }
                mTekstLese.setText(leseLinje.hentTekst(markertOrdStil));
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                //FireTV right button
                leseLinje = leseLekse.nesteLeseLinje();
                if (leseLinje == null) {
                    leseLekse.utfortRepitisjon();
                    mStjerner.setRating(leseLekse.hentRepitisjoner());
                    leseLinje = leseLekse.forsteLeseLinje();
                }
                mTekstLese.setText(leseLinje.hentTekst(markertOrdStil));
                handled = true;
                break;
        }
        return handled;
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
