package com.example.c0de_h0ng.merkle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by c0de_h0ng on 2018. 8. 8..
 */

public class Input extends Activity implements Serializable {
    private Database db = new Database(this);

    private static float FONT_SIZE = 18;
    private static float SIZE = 16;
    private LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        container = (LinearLayout)findViewById(R.id.parent);

        Intent intent = getIntent();
        String name = intent.getStringExtra("memberNumber");
        int number = Integer.valueOf(name);

        final ArrayList<String> signature = new ArrayList<String>();
        final ArrayList<String> arrayList = new ArrayList<>();


        final int[] UserData = new int[number];


        // 동적 생성
        for (int i = 0; i < UserData.length; i++) {

            TextView tv = new TextView(this);
            tv.setText(i+1 + "번째 " + "사원번호를 입력하세요. \n");
            tv.setTextSize(FONT_SIZE);
            tv.setTextColor(Color.WHITE);

            final EditText et = new EditText(this);
            et.setTextSize(SIZE);
            et.setTextColor(Color.WHITE);
            et.setHint(i+1 + "번째");
            et.setHintTextColor(Color.GRAY);
            et.setId(i);
            et.setEnabled(true);

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = et.getText().toString();
                    System.out.println("text: " + text);
                    arrayList.add(et.getId(), text);

                }
            };
            et.addTextChangedListener(textWatcher);

            container.addView(tv);
            container.addView(et);

            arrayList.add(et.getText().toString());
            db.insertNumber(et.getText().toString());

        }

        Button button = new Button(this);
        button.setText("머클트리 생성");
        button.setTextSize(25);
        container.addView(button);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                for (int j = 0; j < UserData.length; j++) {
                    arrayList.get(j);
                    System.out.println("결과: " + arrayList.get(j).toString());
                    signature.add(arrayList.get(j).toString());
                    System.out.println("signature: " + signature);
                }

                Intent intent1 = new Intent(Input.this, Menu.class);
                intent1.putStringArrayListExtra("numbers", signature);
                System.out.println("보낼 사원번호: " + signature);
                startActivity(intent1);
            }
        });
    }
}
