package com.example.c0de_h0ng.merkle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c0de_h0ng on 2018. 8. 8..
 */

public class MerkleTree {

    public static final byte LEAF_SIG_TYPE = 0x0;
    public static final byte INTERNAL_SIG_TYPE = 0x01;

    private List<String> leafSigs;
    private Node root;
    private int depth;
    private int nnodes;

    public MerkleTree(List<String> leafSignatures) {
        System.out.println("leafSig: " + leafSignatures.toString());
        constructTree(leafSignatures);
    }

    public MerkleTree(Node treeRoot, int numNodes, int height, List<String> leafSignatures) {
        root = treeRoot;
        nnodes = numNodes;
        depth = height;
        leafSigs = leafSignatures;
    }

    void constructTree(List<String> signatures) {
        if (signatures.size() <= 1) {
            throw new IllegalArgumentException("트리를 생성하기 위해서는 최소 2개의 노드가 존재해야합니다.");
        }

        leafSigs = signatures;
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

        for (int i = 0; i < signatures.size() - 1; i += 2) {
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