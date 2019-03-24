package com.example.bucketlist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bucketlist.R;

public class CreateItem extends AppCompatActivity {

    private EditText titleTextView, descriptionTextView;
    private Button addItemBtn;
    private String titleText, descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        titleTextView = findViewById(R.id.editTitleText);
        descriptionTextView = findViewById(R.id.editDescriptionText);
        addItemBtn = findViewById(R.id.addItemBtn);

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleText = titleTextView.getText().toString();
                descriptionText = descriptionTextView.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("title", titleText);
                returnIntent.putExtra("description", descriptionText);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
