package com.kraftski.testproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int OPEN_REQUEST_CODE = 1;
    RecyclerView mLeft;
    ImageAdapter mLeftAdapter;
    RecyclerView mRight;
    ImageAdapter mRightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //prevent status bar from persisting
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button openButton = findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(setupOpenIntent(), OPEN_REQUEST_CODE);
            }
        });

        mLeft = findViewById(R.id.left);
        mRight = findViewById(R.id.right);

        mLeftAdapter = new ImageAdapter(this);
        mRightAdapter = new ImageAdapter(this);
        mLeft.setAdapter(mLeftAdapter);
        mRight.setAdapter(mRightAdapter);

        GridLayoutManager leftLayoutManager = new GridLayoutManager(this, 2);
        GridLayoutManager rightLayoutManager = new GridLayoutManager(this, 2);
        mLeft.setLayoutManager(leftLayoutManager);
        mRight.setLayoutManager(rightLayoutManager);
        rightLayoutManager.setReverseLayout(true);
    }

    private Intent setupOpenIntent(){
        Intent intent = new Intent();
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_REQUEST_CODE && resultCode == RESULT_OK) {
            //load images files
            ArrayList<Uri> imageUris = new ArrayList<>();
            for (int i = 0; i < 1000; i++) //load a lot of images, so you can see the error
                imageUris.add(data.getClipData().getItemAt(i % data.getClipData().getItemCount()).getUri());

            mLeftAdapter.setImageList(imageUris);
            mRightAdapter.setImageList(imageUris);
        }
    }
}