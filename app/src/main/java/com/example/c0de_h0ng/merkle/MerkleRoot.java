package com.example.c0de_h0ng.merkle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c0de_h0ng on 2018. 8. 9..
 */

public class MerkleRoot extends Activity implements Serializable {
    private Database db = new Database(this);

    private static float FONT_SIZE = 30;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merkleroot);

        linearLayout = (LinearLayout) findViewById(R.id.root);
        MerkleTree merkleTree = constructMerkleTree();
        MerkleTree.Node root = merkleTree.getRoot();
        TextView rootView = new TextView(this);
        rootView.setTextSize(FONT_SIZE);
        rootView.setTextColor(Color.WHITE);
        System.out.println("트리: " + merkleTree.toString());
        System.out.println("루트: " + root.sig.toString());
        rootView.setText(root.sig.toString());

        linearLayout.addView(rootView);

        db.insertMerkleRoot(root.sig.toString());

    }

    private MerkleTree constructMerkleTree() {
        Intent exIntent = getIntent();
        Serializable getArrayList = exIntent.getSerializableExtra("numbers");
        List<String> signatures = (ArrayList<String>) getArrayList;

        return new MerkleTree(signatures);
    }
}

