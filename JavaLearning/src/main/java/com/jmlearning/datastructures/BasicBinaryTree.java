package com.jmlearning.datastructures;

public class BasicBinaryTree<X extends Comparable<X>> {

    private Node root;
    private int size;

    public BasicBinaryTree() {

        this.root = null;
    }

    public int size() {

        return size;
    }

    public void add(X item) {

        Node node = new Node(item);

        // if the tree is empty, set as root
        if(root == null) {

            this.root = node;
            System.out.println("Set Root: " + node.getItem());
            this.size++;
        }
        // otherwise, insert item into tree
        else {

            insert(this.root, node);
        }
    }

    public boolean contains(X item) {

        Node currentNode = getNode(item);

        if(currentNode == null) {

            return false;
        }
        else {

            return true;
        }
    }

    public boolean delete(X item) {

        boolean deleted = false;

        if(this.root == null) {

            return false;
        }

        Node currentNode = getNode(item);

        if(currentNode != null) {

            // if the node to delete doesnt have children, delete
            if(currentNode.getLeft() == null && currentNode.getRight() == null) {

                unlink(currentNode, null);
                deleted = true;
            }
            // if the node to delete only has a right child, remove it in the hierarchy
            else if(currentNode.getLeft() == null && currentNode.getRight() != null) {

                unlink(currentNode, currentNode.getRight());
                deleted = true;
            }
            else if(currentNode.getLeft() != null && currentNode.getRight() == null) {

                unlink(currentNode, currentNode.getLeft());
                deleted = true;
            }
            else {

                // the node has both children, do a node swap to delete
                // swap out the node with the right most leaf node on the left side
                Node child = currentNode.getLeft();

                while(child.getRight() != null && child.getLeft() != null) {

                    child = child.getRight();
                }

                // with the right most leaf node, we can replace the current node.
                child.getParent().setRight(null); // remove the leaf node from its current position

                child.setLeft(currentNode.getLeft());
                child.setRight(currentNode.getRight());

                unlink(currentNode, child);
                deleted = true;
            }
        }

        if(deleted) {

            this.size--;
        }

        return deleted;
    }

    private void unlink(Node currentNode, Node newNode) {

        // if this is the root node, replace
        if(currentNode == this.root) {

            newNode.setLeft(currentNode.getLeft());
            newNode.setRight(currentNode.getRight());
            this.root = newNode;
        }
        else if(currentNode.getParent().getRight() == currentNode) {

            currentNode.getParent().setRight(newNode);
        }
        else {

            currentNode.getParent().setLeft(newNode);
        }
    }

    private Node getNode(X item) {

        Node currentNode = this.root;

        while(currentNode != null) {

            int val = item.compareTo(currentNode.getItem());

            // if the value is less than 0, go left. otherwise go right
            if(val == 0) {

                return currentNode;
            }
            else if(val < 0) {

                currentNode = currentNode.getLeft();
            }
            else {

                currentNode = currentNode.getRight();
            }
        }

        return null;
    }

    private void insert(Node parent, Node child) {

        // if the child is less than the parent, it goes on the left
        if(child.getItem().compareTo(parent.getItem()) < 0) {

            // if the left node is null, insert here
            if(parent.getLeft() == null) {

                parent.setLeft(child);
                child.setParent(parent);
                this.size++;
            }
            else {

                // otherwise we need to call insert again and test for left or right (recursion)
                insert(parent.getLeft(), child);
            }
        }
        // if the child is greater than the parent, it goes on the right
        else if(child.getItem().compareTo(parent.getItem()) > 0) {

            // if the right node is null, insert here
            if(parent.getRight() == null) {

                parent.setRight(child);
                child.setParent(parent);
                this.size++;
            }
            // otherwise call insert again (recursion)
            else {

                insert(parent.getRight(), child);
            }
        }

        // if the parent and child are equal, we dont do anything
        // data in a binary tree is assumed to be unique and the value already exists in the tree
    }

    private class Node {

        private Node left;
        private Node right;
        private Node parent;
        private X item;

        public Node(X item) {

            this.item = item;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public X getItem() {
            return item;
        }

        public void setItem(X item) {
            this.item = item;
        }
    }
}
