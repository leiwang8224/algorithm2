package AlgoExpert.Medium;

public class BSTConstruction {
    public static void main(String[] args) {

    }

    static class BST{
        public int value;
        public BST left;
        public BST right;

        public BST(int value) {
            this.value = value;
        }

        // Avg: O(log(n)) time | O(log(n)) space
        // Worst: O(n) time | O(n) space
        public BST insert(int value) {
            if (value < this.value) {
                if (left == null) {
                    BST newBST = new BST(value);
                    left = newBST;
                } else {
                    left.insert(value);
                }
            } else {
                if (right == null) {
                    BST newBST = new BST(value);
                    right = newBST;
                } else {
                    right.insert(value);
                }
            }
            return this;
        }

        // Average: O(log(n)) time | O(log(n)) space
        // Worst: O(n) time | O(n) space
        public boolean contains(int value) {
            if (value < this.value) {
                if (left == null) {
                    return false;
                } else {
                    return left.contains(value);
                }
            } else if (value > this.value) {
                if (right == null) {
                    return false;
                } else {
                    return right.contains(value);
                }
            } else {
                return true;
            }
        }

        // Average: O(log(n)) time | O(log(n)) space
        // Worst: O(n) time | O(n) space
        public BST remove(int value) {
            remove (value, null);
            return this;
        }

        public void remove(int value, BST parent) {
            if (value < this.value) {
                if (left != null) {
                    left.remove(value, this);
                }
            } else if (value > this.value) {
                if (right != null) {
                    right.remove(value, this);
                }
            } else {
                // leaf node, simply remove from tree
                if (left != null && right != null) {
                    this.value = right.getMinValue();
                    right.remove(this.value, this);
                } else if (parent == null) {
                    // node to be deleted has two children
                    if (left != null) {
                        this.value = left.value;
                        right = left.right;
                        left = left.left;
                    } else if (right != null) {
                        this.value = right.value;
                        left = right.left;
                        right = right.right;
                    } else {
                        // do nothing since this is a single node tree
                    }
                } else if (parent.left == this) {
                    // node to be deleted has one child
                    parent.left = left != null ? left : right;
                } else if (parent.right == this) {
                    // node to be deleted has one child
                    parent.right = left != null ? left : right;
                }
            }
        }

        private int getMinValue() {
            if (left == null) return this.value;
            else return left.getMinValue();
        }
    }

    static class BST2{
        public int value;
        public BST2 left;
        public BST2 right;

        public BST2(int value) {
            this.value = value;
        }

        // Average: O(log(n)) time | O(1) space
        // Worst: O(n) time | O(1) space
        public BST2 insert(int value) {
            BST2 currentNode = this;
            while (true) {
                if (value < currentNode.value) {
                    if (currentNode.left == null) {
                        BST2 newNode = new BST2(value);
                        currentNode.left = newNode;
                        break;
                    } else {
                        currentNode = currentNode.left;
                    }
                } else {
                    if (currentNode.right == null) {
                        BST2 newNode = new BST2(value);
                        currentNode.right = newNode;
                        break;
                    } else {
                        currentNode = currentNode.right;
                    }
                }
            }
            return this;
        }

        // Average: O(log(n)) time | O(1) space
        // Worst: O(n) time | O(1) space
        public boolean contains(int value) {
            BST2 currentNode = this;
            while (currentNode != null) {
                if (value < currentNode.value) {
                    currentNode = currentNode.left;
                } else if (value > currentNode.value) {
                    currentNode = currentNode.right;
                } else {
                    return true;
                }
            }
            return false;
        }

        // Average: O(log(n)) time | O(1) space
        // Worst: O(n) time | O(1) space
        public BST2 remove(int value) {
            remove(value, null);
            return this;
        }

        public void remove(int value, BST2 parentNode) {
            BST2 currentNode = this;
            while (currentNode != null) {
                if (value < currentNode.value) {
                    parentNode = currentNode;
                    currentNode = currentNode.left;
                } else if (value > currentNode.value) {
                    parentNode = currentNode;
                    currentNode = currentNode.right;
                } else {
                    // leaf node
                    if (currentNode.left != null && currentNode.right != null) {
                        currentNode.value = currentNode.right.getMinValue();
                        currentNode.right.remove(currentNode.value, currentNode);
                    } else if (parentNode == null) {
                        // node to be deleted with two children
                        if (currentNode.left != null) {
                            currentNode.value = currentNode.left.value;
                            currentNode.right = currentNode.left.right;
                            currentNode.left = currentNode.left.left;
                        } else if (currentNode.right != null) {
                            currentNode.value = currentNode.right.value;
                            currentNode.left = currentNode.right.left;
                            currentNode.right = currentNode.right.right;
                        } else {
                            // this is single node tree, do nothing
                        }
                    } else if (parentNode.left == currentNode) {
                        // node to be deleted has one child
                        parentNode.left = currentNode.left != null ?
                                currentNode.left : currentNode.right;
                    } else if (parentNode.right == currentNode) {
                        // node to be deleted has one child
                        parentNode.right = currentNode.left != null ?
                                currentNode.left : currentNode.right;
                    }
                    break;
                }
            }
        }

        private int getMinValue() {
            if (left == null) return value;
            else return left.getMinValue();
        }
    }
}
