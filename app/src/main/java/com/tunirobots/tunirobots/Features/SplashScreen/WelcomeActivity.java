package com.tunirobots.tunirobots.Features.SplashScreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daimajia.androidanimations.library.Techniques;
import com.tunirobots.tunirobots.MainActivity;
import com.tunirobots.tunirobots.R;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class WelcomeActivity extends AwesomeSplash {


    @Override
    public void initSplash(ConfigSplash configSplash) {

        // Actionbar logo
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        /* you don't have to override every property */

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(2000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_TOP); //or Flags.REVEAL_TOP

        //Customize Path
        configSplash.setLogoSplash(R.drawable.logo_anniv); //set path String
        configSplash.setOriginalHeight(500); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(500); //in relation to your svg (path) resource


    }

    @Override
    public void animationsFinished() {
        Intent i = new Intent(this, MainActivity.class);
        finish();
        startActivity(i);
    }
}
