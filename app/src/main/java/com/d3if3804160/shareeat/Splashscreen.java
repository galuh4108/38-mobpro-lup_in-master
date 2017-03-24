package com.d3if3804160.shareeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Fajar on 11/10/2016.
 */

public class Splashscreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
