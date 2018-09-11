package com.example.c0de_h0ng.merkle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by c0de_h0ng on 2018. 8. 12..
 */

public class Menu extends AppCompatActivity implements View.OnClickListener, Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setClickListeners();

    }

    @Override
    public void onClick(View v) {
        Intent exIntent = getIntent();
        Serializable getArrayList = exIntent.getSerializableExtra("numbers");
        ArrayList<String> signatures = (ArrayList<String>) getArrayList;


        switch (v.getId()) {
            // 잎노드 만들기 버튼
            case R.id.makeLeafNode:
                Intent makeLeafNode = new Intent(this, LeafNode.class);
                makeLeafNode.putStringArrayListExtra("numbers", signatures);
                startActivity(makeLeafNode);
                break;

            case R.id.makeMerkleRoot:
                Intent makeMerkleRoot = new Intent(this, MerkleRoot.class);
                makeMerkleRoot.putStringArrayListExtra("numbers", signatures);
                startActivity(makeMerkleRoot);
                break;

            case R.id.makeMerkleTree:
                Intent makeMerkleTree = new Intent(this, ShowTree.class);
                makeMerkleTree.putStringArrayListExtra("numbers", signatures);
                startActivity(makeMerkleTree);
                break;

            // 앱 완전 종료 버튼
            case R.id.logout:
                System.exit(0);
                break;


        }
    }
    private void setClickListeners() {
        findViewById(R.id.makeLeafNode).setOnClickListener(this);
        findViewById(R.id.makeMerkleRoot).setOnClickListener(this);
        findViewById(R.id.makeMerkleTree).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);

    }
}
