package com.example.c0de_h0ng.merkle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by c0de_h0ng on 2018. 8. 8..
 */

public class Name extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        Button register = (Button) findViewById(R.id.memberRegister);
        register.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText memberText = (EditText) findViewById(R.id.member);
                String members = memberText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Input.class);
                intent.putExtra("memberNumber", members);
                startActivity(intent);
            }
        });
    }
}
