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
 * Created by c0de_h0ng on 2018. 8. 12..
 */

public class ShowTree extends Activity implements Serializable {
    private Database db = new Database(this);

    private static final byte LEAF_SIG_TYPE = 0x0;
    private static final byte INTERNAL_SIG_TYPE = 0x01;

    private static float FONT_SIZE = 18;
    private LinearLayout linearLayout;

    private List<String> leafSig;
    private Node root;
    private int depth;
    private int nnodes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtree);

        linearLayout = (LinearLayout)findViewById(R.id.tree);

        Intent exIntent = getIntent();
        Serializable getArrayList = exIntent.getSerializableExtra("numbers");
        List<String> signatures = (ArrayList<String>) getArrayList;

        List<Node> children = bottomLevel(signatures);
        List<Node> parents = new ArrayList<Node>(children.size() / 2);

        int j = 2;
        int t = 2;

        // 잎노드 출력
        for (int i = 0; i < signatures.size(); i++) {
            TextView leafView = new TextView(this);
            leafView.setTextSize(FONT_SIZE);
            leafView.setTextColor(Color.WHITE);
            System.out.println(i+1 + "번째 사원번호: " + signatures.get(i));

            Node leaf = constructLeafNode(signatures.get(i));
            leafView.setText("<" + (i+1) + "번째 잎노드>"+ "\n" + leaf.sig.toString() + "\n");
            System.out.println("해시: " + leaf.sig.toString());

            linearLayout.addView(leafView);
        }

        // bottomLevel
        if ( signatures.size()%2 != 0)
            signatures.add(signatures.size(), signatures.get(signatures.size() - 1));

        for (int i = 0; i < signatures.size() - 1; i+= 2) {
            TextView bottomView = new TextView(this);
            bottomView.setTextSize(FONT_SIZE);
            bottomView.setTextColor(Color.WHITE);
            Node leaf1 = constructLeafNode(signatures.get(i));
            Node leaf2 = constructLeafNode(signatures.get(i+1));

            Node parent = constructInternalNode(leaf1, leaf2);
            j -= 1;
            bottomView.setText("<BottomLevel" + (i+j) + "노드 해시값> \n" + parent.sig.toString() + "\n");
            System.out.println("bottom: " + parent.sig.toString());
            linearLayout.addView(bottomView);

            db.insertBottomLevel(parent.sig.toString());

        }

        // internalLevel
        if (children.size()%2 != 0) {
            children.add(children.size(), children.get(children.size() - 1));
        }

        for (int i = 0; i < children.size(); i += 2) {
            TextView internalView = new TextView(this);
            internalView.setTextSize(FONT_SIZE);
            internalView.setTextColor(Color.WHITE);
            Node child1 = children.get(i);
            Node child2 = children.get(i+1);

            Node parent = constructInternalNode(child1, child2);
            t -= 1;
            internalView.setText("<InternalLevel" + (i+t) + "노드 해시값> \n" + parent.sig.toString() + "\n");
            System.out.println("internal: " + parent.sig.toString());
            linearLayout.addView(internalView);

            db.insertInternalLevel(parent.sig.toString());

        }

        // root
        ShowTree showTree =  constructMerkleTree();
        ShowTree.Node root = showTree.getRoot();
        TextView rootView = new TextView(this);
        rootView.setTextSize(FONT_SIZE);
        rootView.setTextColor(Color.WHITE);

        rootView.setText("<MerkleRoot>\n" + root.sig.toString());
        linearLayout.addView(rootView);

    }

    public ShowTree() {

    }

    private ShowTree constructMerkleTree() {
        Intent exIntent = getIntent();
        Serializable getArrayList = exIntent.getSerializableExtra("numbers");
        List<String> signatures = (ArrayList<String>) getArrayList;

        return new ShowTree(signatures);
    }

    public ShowTree(List<String> leafSignatures) {
        constructTree(leafSignatures);
    }

    public ShowTree(Node treeRoot, int numNodes, int height, List<String> leafSignatures) {
        root = treeRoot;
        nnodes = numNodes;
        depth = height;
        leafSig = leafSignatures;
    }

    void constructTree(List<String> signatures) {
        if (signatures.size() <= 1) {
            throw new IllegalArgumentException("트리를 생성하기 위해서는 최소 2개의 노드가 존재해야합니다.");
        }

        leafSig = signatures;
        nnodes = signatures.size();
        List<Node> parents = bottomLevel(signatures);
        nnodes += parents.size();
        depth = 1;

        while (parents.size() > 1) {
            parents = internalLevel(parents);
            depth++;
            nnodes += parents.size();
        }
        root = parents.get(0);
    }

    public int getNumNodes() {
        return nnodes;
    }

    public Node getRoot() {
        return root;
    }

    public int getHeight() {
        return depth;
    }

    List<Node> internalLevel(List<Node> children) {
        List<Node> parents = new ArrayList<Node>(children.size() / 2);
        if (children.size()%2 != 0) {
            int maxChildren = children.size();
            int vicemaxChildren = children.size() - 1;
            children.add(maxChildren, children.get(vicemaxChildren));
        }

        for (int i = 0; i < children.size(); i += 2) {
            Node child1 = children.get(i);
            Node child2 = children.get(i+1);

            Node parent = constructInternalNode(child1, child2);
            parents.add(parent);
        }
        return parents;
    }

    List<Node> bottomLevel(List<String> signatures) {
        List<Node> parents = new ArrayList<Node>(signatures.size() / 2);

        if (signatures.size()%2 != 0) {
            signatures.add(signatures.size(), signatures.get(signatures.size() - 1));
        }

        for (int i = 0; i < signatures.size() - 1; i+= 2) {
            Node leaf1 = constructLeafNode(signatures.get(i));
            Node leaf2 = constructLeafNode(signatures.get(i+1));

            Node parent = constructInternalNode(leaf1, leaf2);
            parents.add(parent);
        }
        return parents;
    }

    private Node constructInternalNode(Node child1, Node child2) {
        Node parent = new Node();
        parent.type = INTERNAL_SIG_TYPE;
        parent.sig = internalHash(child1.sig, child2.sig);

        parent.left = child1;
        parent.right = child2;
        return parent;
    }

    // 잎노드 생성
    private static Node constructLeafNode(String signature) {
        Node leaf = new Node();
        leaf.type = LEAF_SIG_TYPE;
        leaf.sig = CryptologyUtil.getHash(signature);
        return leaf;
    }

    public String internalHash(String leftChildSig, String rightChildSig) {
        return CryptologyUtil.getHash(leftChildSig + rightChildSig);
    }

    static class Node {
        public byte type;
        public String sig;
        public Node left;
        public Node right;
    }

}
