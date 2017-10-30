/**
 * Created by Xiaojing Zhang on 10/18/2017.
 */
// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )                 --> Insert x
// void remove( x )                 --> Remove x
// boolean contains( x )            --> Return true if x is present
// Comparable findMin( )            --> Return smallest item
// Comparable findMax( )            --> Return largest item
// boolean isEmpty( )               --> Return true if empty; else false
// void makeEmpty( )                --> Remove all items
// void printTree( )                --> Print tree in sorted order
// int nodeCount()                  --> Return the total number of nodes in the tree
// boolean isFull()                 --> Return true if it's a full tree; else false
// boolean compareStructure()       --> Return true if two trees have the same structure; else false
// boolean equals()                 --> Return true if two trees are identical; else false
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public BinarySearchTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        root = remove( x, root );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }


    /**
     * Recursively traverses the tree and returns the count of nodes.
     */
    public int nodeCount() {
        return nodeCount(root);
    }

    /**
     * Returns true if the tree is full.  A full tree has every node
     as either a leaf or a parent with two children.
     */
    public boolean isFull( )
    {
        return isFull(root);
    }

    /**
     *  Compares the structure of current tree to another tree and returns
     true if they match.
     * @param t, the other tree to compare to
     */
    public boolean compareStructure(BinarySearchTree<AnyType> t)
    {
        return compareStructure(root, t.root);
    }

    /**
     *  Compares the current tree to another tree and returns true
     if they are identical.
     * @param t, the other tree to compare to
     */
    public boolean equals(BinarySearchTree<AnyType> t)
    {
        return equals(root, t.root);
    }

    /**
     *  Creates and returns a new tree that is a copy of the original tree.
     */
    public BinarySearchTree<AnyType> copy()
    {
        BinarySearchTree<AnyType> res = new BinarySearchTree<AnyType>();
        res.root = copy(root, res.root);
        return res;
    }

    /**
     *  Creates and returns a new tree that is a mirror of the original tree.
     */
    public BinarySearchTree<AnyType> mirror()
    {
        BinarySearchTree<AnyType> res = new BinarySearchTree<AnyType>();
        res.root = mirror(root, res.root);
        return res;
    }

    /**
     *  Returns true if the tree is a mirror of the passed tree.
     */
    public boolean isMirror(BinarySearchTree<AnyType> t2)
    {
        return isMirror(root, t2.root);
    }

    /**
     *  Performs a single right rotation on the node having the passed value.
     */
    public BinarySearchTree<AnyType> rotateRight(AnyType val)
    {
        BinarySearchTree<AnyType> t = new BinarySearchTree<AnyType>();
        t.root = rotateRight(root, val);
        return t;
    }

    /**
     *  Performs a single left rotation on the node having the passed value.
     */
    public BinarySearchTree<AnyType> rotateLeft(AnyType val)
    {
        BinarySearchTree<AnyType> t = new BinarySearchTree<AnyType>();
        t.root = rotateLeft(root, val);
        return t;
    }

    /**
     * Print the tree level-by-level.
     */
    public List<List<AnyType>> printLevels( )
    {
        List<List<AnyType>> levels = new ArrayList<>();
        if( isEmpty( ) ) {
            levels.add(new ArrayList<AnyType>());
            System.out.println( "Empty tree" );
        }
        else {
            printLevels( root, levels, 0 );
            System.out.println("Tree print level by level is: ");
            for(List<AnyType> list : levels) {
                System.out.println();
                for(AnyType val : list) {
                    System.out.print(val + " ");
                }
            }
        }
        return levels;
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return new BinaryNode<>( x, null, null );

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ;  // Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return t;   // Item not found; do nothing

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
    {
        if( t != null )
            while( t.right != null )
                t = t.right;

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return false;

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            return contains( x, t.left );
        else if( compareResult > 0 )
            return contains( x, t.right );
        else
            return true;    // Match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree( BinaryNode<AnyType> t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }

    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height( BinaryNode<AnyType> t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );
    }

    /**
     * Internal method to recursively traverses the tree and returns the count of nodes.
     * @param t the node that roots the subtree.
     */
    private int nodeCount( BinaryNode<AnyType> t )
    {
        int count = 0;
        if(t == null) return count;
        return 1 + nodeCount(t.left) + nodeCount(t.right);
    }

    /**
     * Internal method to check whether a tree is full or not. Returns true if the tree is full.  A full tree has every node
     as either a leaf or a parent with two children.
     * @param t the node that roots the subtree.
     */
    private boolean isFull( BinaryNode<AnyType> t )
    {
        if(t == null || (t.left == null && t.right == null)) return true;
        if(t.left != null && t.right != null) {
            return isFull(t.left) && isFull(t.right);
        }
        return false;
    }

    /**
     * Internal method to compare the structure of current tree to another tree and returns
     true if they match.
     * @param t the node that roots the subtree of current tree.
     * @param t2 the node that roots the subtree of the other tree comparing to.
     */
    private boolean compareStructure( BinaryNode<AnyType> t, BinaryNode<AnyType> t2)
    {
        if(t == null && t2 == null) return true;
        if(t == null || t2 == null) return false;
        return compareStructure(t.left, t2.left) && compareStructure(t.right, t2.right);
    }

    /**
     * Internal method to compares the current tree to another tree and returns true
     if they are identical.
     * @param t the node that roots the subtree of current tree.
     * @param t2 the node that roots the subtree of the other tree comparing to.
     */
    private boolean equals( BinaryNode<AnyType> t, BinaryNode<AnyType> t2)
    {
        if(t == null && t2 == null) return true;
        if(t == null || t2 == null || t.element.compareTo(t2.element) != 0) return false;
        return compareStructure(t.left, t2.left) && compareStructure(t.right, t2.right);
    }

    /**
     * Internal method to creates and returns a new tree that is a copy of the original tree.
     * @param t the node that roots the subtree of current tree.
     * @param t2 the node that roots the subtree of the copied tree.
     */
    private BinaryNode<AnyType> copy( BinaryNode<AnyType> t, BinaryNode<AnyType> t2)
    {
        if(t == null) return t2;
        t2 = new BinaryNode<AnyType>(t.element, null, null);
        t2.left = copy(t.left, t2.left);
        t2.right = copy(t.right, t2.right);
        return t2;
    }

    /**
     * Internal method to creates and returns a new tree that is a mirror of the original tree.
     * @param t the node that roots the subtree of current tree.
     * @param t2 the node that roots the subtree of the mirror tree.
     */
    private BinaryNode<AnyType> mirror( BinaryNode<AnyType> t, BinaryNode<AnyType> t2)
    {
        if(t == null) return t2;
        t2 = new BinaryNode<AnyType>(t.element, null, null);
        t2.right = mirror(t.left, t2.right);
        t2.left = mirror(t.right, t2.left);
        return t2;
    }

    /**
     * Internal method to check whether two trees are mirror.
     * @param t the node that roots the subtree of current tree.
     */
    private boolean isMirror( BinaryNode<AnyType> t, BinaryNode<AnyType> t2)
    {
        if(t == null && t2 == null) return true;
        if(t == null || t2 == null || t.element.compareTo(t2.element) != 0) return false;
        return isMirror(t.left, t2.right) && isMirror(t.right, t2.left);
    }

    /**
     * Internal method to performs a single right rotation on the node having the passed value.
     * @param t the node that roots the subtree of current tree.
     * @param val the value hold by the node, which needs to be rotated.
     */
    private BinaryNode<AnyType> rotateRight( BinaryNode<AnyType> t, AnyType val)
    {
        if(t == null) {
            System.out.println("Node holds passed value is not found. Tree is not changed.");
        } else {
            if(t.element == val) {
                if(t.left == null) {
                    System.out.println("Left subtree is null. Can't be rotated. Tree is not changed.");
                } else {
                    BinaryNode<AnyType> old = t;
                    t = t.left;
                    old.left = t.right;
                    t.right = old;
                }
            } else {
                int compareResult = val.compareTo(t.element);
                if(compareResult < 0) {
                    t.left = rotateRight(t.left, val);
                } else {
                    t.right = rotateRight(t.right, val);
                }
            }
        }
        return t;
    }

    /**
     * Internal method to performs a single left rotation on the node having the passed value.
     * @param t the node that roots the subtree of current tree.
     * @param val the value hold by the node, which needs to be rotated.
     */
    private BinaryNode<AnyType> rotateLeft( BinaryNode<AnyType> t, AnyType val)
    {
        if(t == null) {
            System.out.println("Node holds passed value is not found. Tree is not changed.");
        } else {
            if(t.element == val) {
                if(t.right == null) {
                    System.out.println("Left subtree is null. Can't be rotated. Tree is not changed.");
                } else {
                    BinaryNode<AnyType> old = t;
                    t = t.right;
                    old.right = t.left;
                    t.left = old;
                }
            } else {
                int compareResult = val.compareTo(t.element);
                if(compareResult < 0) {
                    t.left = rotateLeft(t.left, val);
                } else {
                    t.right = rotateLeft(t.right, val);
                }
            }
        }
        return t;
    }

    /**
     * Internal method to performs a single rotation on the node having the passed value.
     * @param t the node that roots the subtree of current tree.
     */
    private void printLevels( BinaryNode<AnyType> t, List<List<AnyType>> levels, int level)
    {
        if(t == null) return;
        if(level == levels.size()) {
            levels.add(new ArrayList<AnyType>());
        }
        levels.get(level).add(t.element);
        printLevels(t.left, levels, level + 1);
        printLevels(t.right, levels, level + 1);
    }

    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType>
    {
        // Constructors
        BinaryNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
        }

        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
    }


    /** The tree root. */
    private BinaryNode<AnyType> root;


    // Test program
    public static void main( String [ ] args )
    {
        BinarySearchTree<Integer> t = new BinarySearchTree<>( );
        BinarySearchTree<Integer> t2 = new BinarySearchTree<>();
        // pool of trees
        int[][] trees = {{}, {20}, {30}, {20, 13}, {30, 17}, {20, 22}, {30, 35},
                {20, 13, 22}, {30, 20, 17}, {30, 20, 35}, {20, 13, 11}, {20, 21, 24},
                {5, 3, 8, 1, 4}, {10, 5, 15, 2, 7}, {100, 50, 150, 40, 45}, {5, 3, 8, 1, 4, 9}};

        // make an array to store the isFull values for the trees
        boolean[] full = {true, true, true, false, false, false, false, true, false,
                true, false, false, true, true, false, false};

        // make a multi-dimensional array to store compareStructure values
        // compare each tree inside trees with itself and all other trees inside trees
        boolean[][] compareValue = {{true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false},
                {false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true}};

        // make a multi-dimensional array to store equals values
        // compare each tree inside trees with itself and all other trees inside trees
        boolean[][] equalValue = {{true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true}};

        int[][] rotateR1 = {{}, {20}, {30}, {13, 20}, {17, 30}, {20, 22}, {30, 35},
                {13, 20, 22}, {20, 30, 17}, {20, 30, 35}, {13, 20, 11}, {20, 21, 24},
                {3, 5, 8, 1, 4}, {5, 10, 15, 2, 7}, {50, 100, 150, 40, 45}, {3, 5, 8, 1, 4, 9}};

        int[][] rotateR2 = {{}, {20}, {30}, {20, 13}, {30, 17}, {20, 22}, {30, 35},
                {20, 13, 22}, {30, 17, 20}, {30, 20, 35}, {20, 11, 13}, {20, 21, 24},
                {5, 1, 8, 3, 4}, {10, 2, 15, 5, 7}, {100, 40, 150, 50, 45}, {5, 1, 8, 3, 4, 9}};

        int[][] rotateL1 = {{}, {20}, {30}, {20, 13}, {30, 17}, {22, 20}, {35, 30},
                {22, 20, 13}, {30, 20, 17}, {35, 30, 20}, {20, 13, 11}, {21, 20, 24},
                {8, 5, 3, 1, 4}, {15, 10, 5, 2, 7}, {150, 100, 50, 40, 45}, {8, 5, 3, 1, 4, 9}};

        int[][] rotateL2 = {{}, {20}, {30}, {20, 13}, {30, 17}, {20, 22}, {30, 35},
                {20, 13, 22}, {30, 20, 17}, {30, 20, 35}, {20, 13, 11}, {20, 24, 21},
                {5, 4, 8, 3, 1}, {10, 7, 15, 5, 2}, {100, 50, 150, 40, 45}, {5, 4, 8, 3, 1, 9}};

        Integer[][][] levels = {{{}}, {{20}}, {{30}}, {{20}, {13}}, {{30}, {17}}, {{20}, {22}},
                {{30}, {35}}, {{20}, {13, 22}}, {{30}, {20}, {17}}, {{30}, {20, 35}},
                {{20},{13}, {11}}, {{20}, {21}, {24}}, {{5}, {3, 8}, {1, 4}}, {{10}, {5, 15}, {2, 7}},
                {{100}, {50, 150}, {40}, {45}}, {{5}, {3, 8}, {1, 4, 9}}};



        //test nodeCount()
        for(int[] tree : trees) { // go through the tree pool to count the number of nodes of each tree and add them to res list
            t = makeTree(t, tree);
            printTreeNodes(tree);
            testNodeCount(t, tree.length);
        }


        //test isFull()
        for(int i = 0; i < trees.length; i++) { // go through the tree pool to count the number of nodes of each tree and add them to res list
            t = makeTree(t, trees[i]);
            printTreeNodes(trees[i]);
            testIsFull(t,full[i]);
        }

        // test compareStructure()
        for(int i = 0; i < trees.length; i++) {
            t = makeTree(t, trees[i]);
            System.out.print("Original tree -- ");
            printTreeNodes(trees[i]);
            for(int j = 0; j < trees.length; j++) {
                t2 = makeTree(t2, trees[j]);
                System.out.print("Comparing tree -- ");
                printTreeNodes(trees[j]);
                testCompareStructure(t, t2, compareValue[i][j]);
            }
        }

        // test equals()
        for(int i = 0; i < trees.length; i++) {
            t = makeTree(t, trees[i]);
            System.out.print("Original tree -- ");
            printTreeNodes(trees[i]);
            for(int j = 0; j < trees.length; j++) {
                t2 = makeTree(t2, trees[j]);
                System.out.print("Comparing tree -- ");
                printTreeNodes(trees[j]);
                testEquals(t, t2, equalValue[i][j]);
            }
        }

        // test copy()
        for(int[] tree : trees) { // go through the tree pool to count the number of nodes of each tree and add them to res list
            t = makeTree(t, tree);
            testCopy(t, t2);
        }

        // test mirror()
        // make a list to store returned BinarySearchTree mirror
        List<BinarySearchTree<Integer>> mirror = new ArrayList<>();
        // make a tree to hold the corresponding mirror tree
        BinarySearchTree<Integer> m = new BinarySearchTree<>();

        for(int[] tree : trees) { // go through the tree pool to count the number of nodes of each tree and add them to res list
            t = makeTree(t, tree);
            m = makeMirrorTree(m, tree);
            testMirror(t, t2, m, mirror);
        }

        // test isMirror()
        for(int i = 0; i < trees.length; i++) { // go through the tree pool to count the number of nodes of each tree and add them to res list
            t = makeTree(t, trees[i]);
            testIsMirror(t, mirror.get(i));
        }

        // test rotateRight()
        for(int i = 0; i < trees.length; i++) { // go through the tree pool to count the number of nodes of each tree and add them to res list
            t = makeTree(t, trees[i]);
            BinarySearchTree<Integer> rt = new BinarySearchTree<>();
            rt = makeTree(rt, rotateR1[i]);
            int node = trees[i].length == 0 ? 0 : trees[i][0];
            testRotateRight(t, rt, node);
            t = makeTree(t, trees[i]);
            rt = makeTree(rt, rotateR2[i]);
            int node2 = trees[i].length < 2 ? 0 : trees[i][1];
            testRotateRight(t, rt, node2);
        }

        // test rotateLeft()
        for(int i = 0; i < trees.length; i++) { // go through the tree pool to count the number of nodes of each tree and add them to res list
            t = makeTree(t, trees[i]);
            BinarySearchTree<Integer> lt = new BinarySearchTree<>();
            lt = makeTree(lt, rotateL1[i]);
            int node = trees[i].length == 0 ? 0 : trees[i][0];
            testRotateLeft(t, lt, node);
            t = makeTree(t, trees[i]);
            lt = makeTree(lt, rotateL2[i]);
            int node2 = trees[i].length < 2 ? 0 : trees[i][1];
            testRotateLeft(t, lt, node2);
        }

        // test printLevels()
        for(int i = 0; i < trees.length; i++) { // go through the tree pool to count the number of nodes of each tree and add them to res list
            t = makeTree(t, trees[i]);
            List<List<Integer>> tLevels = new ArrayList<>();
            for(Integer[] l : levels[i]) {
                List<Integer> level = new ArrayList<>();
                level.addAll(Arrays.asList(l));
                tLevels.add(level);
            }
            testPrintLevels(t, tLevels);
        }

    }

    public static void printTreeNodes(int[] tree) {
        System.out.print("The tree is: [ ");
        for(int n : tree) {
            System.out.print(n + " ");
        }
        System.out.print("]");
        System.out.println();
    }
    public static BinarySearchTree<Integer> makeTree(BinarySearchTree<Integer> t, int[] tree) {
        t = new BinarySearchTree<>();
        for(int node : tree) {
            t.insert(node);
        }
        return t;
    }
    public static BinarySearchTree<Integer> makeMirrorTree(BinarySearchTree<Integer> m, int[] tree) {
        m = new BinarySearchTree<>();
        for(int node : tree) {
            m.root = m.insertMirror(node, m.root);
        }
        return m;
    }
    private static BinaryNode<Integer> insertMirror(Integer x, BinaryNode<Integer> t )
    {
        if( t == null )
            return new BinaryNode<>( x, null, null );

        int compareResult = x.compareTo( t.element );

        if( compareResult > 0 )
            t.left = insertMirror( x, t.left );
        else if( compareResult < 0 )
            t.right = insertMirror( x, t.right );
        else
            ;  // Duplicate; do nothing
        return t;
    }
    public static void testNodeCount(BinarySearchTree<Integer> t, int size) {
        System.out.println("Test result for nodeCount: ");
        System.out.println("The expected count of nodes is: " + size);
        int res = t.nodeCount();
        System.out.println("The count of nodes got is: " + res);
        if(size != res) {
            System.out.println("NodeCount error!");
        }
        System.out.println("\n\n\n");
    }
    public static void testIsFull(BinarySearchTree<Integer> t, boolean isFull) {
        System.out.println("Test result for isFull: ");
        System.out.println("The expected answer is: " + isFull);
        boolean res = t.isFull();
        System.out.println("The result got is: " + res);
        if(isFull != res) {
            System.out.println("IsFull error!");
        }
        System.out.println("\n\n\n");
    }
    public static void testCompareStructure(BinarySearchTree<Integer> t, BinarySearchTree<Integer> t2, boolean isMatch) {
        System.out.println("Test result for compareStructure: ");
        System.out.println("The expected answer is: " + isMatch);
        boolean res = t.compareStructure(t2);
        System.out.println("The result got is: " + res);
        if(isMatch != res) {
            System.out.println("CompareStructure error!");
        }
        System.out.println("\n\n\n");
    }
    public static void testEquals(BinarySearchTree<Integer> t, BinarySearchTree<Integer> t2, boolean isEqual) {
        System.out.println("Test result for equals: ");
        System.out.println("The expected answer is: " + isEqual);
        boolean res = t.equals(t2);
        System.out.println("The result got is: " + res);
        if(isEqual != res) {
            System.out.println("Equals error!");
        }
        System.out.println("\n\n\n");
    }
    public static void testCopy(BinarySearchTree<Integer> t, BinarySearchTree<Integer> t2) {
        t2 = t.copy();
        System.out.println("Test result for copy: ");
        System.out.println("The expected answer is: " + true);
        boolean res = t.equals(t2);
        System.out.println("The result got is: " + res);
        if(!res) {
            System.out.println("Copy error!");
        }
        System.out.println("\n\n\n");
    }
    public static void testMirror(BinarySearchTree<Integer> t, BinarySearchTree<Integer> t2, BinarySearchTree<Integer> m, List<BinarySearchTree<Integer>> mirror) {
        t2 = t.mirror();
        mirror.add(t2);
        System.out.println("Test result for mirror: ");
        System.out.println("The expected answer is: " + true);
        boolean res = t2.equals(m);
        System.out.println("The result got is: " + res);
        if(!res) {
            System.out.println("Mirror error!");
        }
        System.out.println("\n\n\n");
    }
    public static void testIsMirror(BinarySearchTree<Integer> t, BinarySearchTree<Integer> mirror) {
        System.out.println("Test result for isMirror: ");
        System.out.println("The expected answer is: " + true);
        boolean res = t.isMirror(mirror);
        System.out.println("The result got is: " + res);
        if(!res) {
            System.out.println("IsMirror error!");
        }
        System.out.println("\n\n\n");
    }
    public static void testRotateRight(BinarySearchTree<Integer> t, BinarySearchTree<Integer> rt, int node) {
        System.out.println("Test result for rotateRight: ");
        System.out.println("The expected answer is: " + true);
        BinarySearchTree<Integer> rotatedTree = t.rotateRight(node);
        boolean res = rotatedTree.equals(rt);
        System.out.println("The result got is: " + res);
        if(!res) {
            System.out.println("RotateRight error!");
        }
        System.out.println("\n\n\n");
    }
    public static void testRotateLeft(BinarySearchTree<Integer> t, BinarySearchTree<Integer> lt, int node) {
        System.out.println("Test result for rotateLeft: ");
        System.out.println("The expected answer is: " + true);
        BinarySearchTree<Integer> rotatedTree = t.rotateLeft(node);
        boolean res = rotatedTree.equals(lt);
        System.out.println("The result got is: " + res);
        if(!res) {
            System.out.println("RotateLeft error!");
        }
        System.out.println("\n\n\n");
    }
    public static void testPrintLevels(BinarySearchTree<Integer> t, List<List<Integer>> tLevels) {
        System.out.println("Test result for printLevels: ");
        System.out.println("The expected answer is: " + true);
        boolean res = t.printLevels().equals(tLevels);
        System.out.println();
        System.out.println("The result got is: " + res);
        if(!res) {
            System.out.println("PrintLevels error!");
        }
        System.out.println("\n\n\n");
    }
}

