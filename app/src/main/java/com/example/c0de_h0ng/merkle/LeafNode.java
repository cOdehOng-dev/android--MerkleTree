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

public class LeafNode extends Activity implements Serializable {
    private Database db = new Database(this);

    private static float FONT_SIZE = 18;

    public static final byte LEAF_SIG_TYPE = 0x0;
    public static final byte INTERNAL_SIG_TYPE = 0x01;

    private List<String> leafSigs;
    private MerkleTree.Node root;
    private int depth;
    private int nnodes;

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaf);

        linearLayout = (LinearLayout)findViewById(R.id.lv);

        Intent exIntent = getIntent();
        Serializable getArrayList = exIntent.getSerializableExtra("numbers");
        List<String> signatures = (ArrayList<String>) getArrayList;
        System.out.println("사원번호 모음: " + signatures);
        System.out.println("길이: " + signatures.size());

        for (int i = 0; i < signatures.size(); i++) {
            TextView textView = new TextView(this);
            textView.setTextSize(FONT_SIZE);
            textView.setTextColor(Color.WHITE);
            System.out.println(i+1 + "번째 사원번호: " + signatures.get(i));

            MerkleTree.Node leaf = constructLeafNode(signatures.get(i));
            textView.setText("<" + (i+1) + "번째 잎노드>"+ "\n" + leaf.sig.toString() + "\n");
            System.out.println("해시: " + leaf);

            linearLayout.addView(textView);
            db.insertLeafNode(leaf.sig.toString());
        }

    }

    private static MerkleTree.Node constructLeafNode(String signature) {
        MerkleTree.Node leaf = new MerkleTree.Node();
        leaf.type = LEAF_SIG_TYPE;
        leaf.sig = CryptologyUtil.getHash(signature);

        return leaf;
    }

}