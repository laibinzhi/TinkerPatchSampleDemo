package com.lbz.elitetinker;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tinkerpatch.sdk.TinkerPatch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TinkerPatch.with().fetchPatchUpdate(true);
    }

    public void clickevent(View view) {
//        Toast.makeText(MainActivity.this, "click click 2", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,Main2Activity.class));
    }
}
