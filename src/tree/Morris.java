package tree;

// 一颗二叉树的前序、中序、后序遍历，有递归和非递归两种方式，时间复杂度都是O(N)，空间复杂度都是O(h)（h：树的高度）
// Morris遍历：
//    时间复杂度O(N)，空间复杂度O(1)
public class Morris {

    public static class Node{
        Node left;
        Node right;
        int val;

        public Node(int value){
            this.val = value;
        }
    }

    // Morris遍历
    public static void morris(Node head){
        if(head == null) return ;
        Node cur = head;
        Node mostRight = null;
        while(cur != null){
            System.out.print(cur.val + " ");
            mostRight = cur.left;
            // cur有左树
            if(mostRight != null){
                // 找到最右节点
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                // 最右节点右指针为空，说明是第一次到cur
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                    // 最右节点右指针不为空，则一定为cur，说明是第二次到cur
                }else{
                    mostRight.right = null;
                }

            }
            // cur没有左树
            cur = cur.right;
        }
        System.out.println();
    }

    // Morris先序遍历
    //  在第一次到节点时打印即可
    public static void morrisPre(Node head){
        if(head == null) return;
        Node cur = head;
        Node mostRight = null;
        while(cur != null){
            mostRight = cur.left;
            // cur有左树
            if(mostRight != null){
                // 找到左树的最右节点
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                // 最右节点的right为空，说明是第一次到该节点
                if(mostRight.right == null){
                    System.out.print(cur.val + " ");
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                    // 最右节点的right不为空，则一定为cur，说明是第二次到该节点
                }else{
                    mostRight.right = null;
                }
                // cur没有左树，没有左树的节点只会访问一次
            }else{
                System.out.print(cur.val + " ");
            }
            cur = cur.right;
        }
        System.out.println();
    }

    // Morris中序遍历
    //  一个节点要往右移动的时候打印
    public static void morrisIn(Node head){
        if(head == null) return;
        Node cur = head;
        Node mostRight = null;
        while(cur != null){
            mostRight = cur.left;
            // cur有左树
            if(mostRight != null){
                // 找到左树的最右节点
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                // 最右节点的right为空，说明是第一次到cur
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                    // 最右节点的right不为空，则一定为cur，说明时第二次到cur
                }else{
                    mostRight.right = null;
                }
            }
            // cur 无左树
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    // Morris后序遍历
    //  第二次回到节点的时候倒序打印该节点的左树右边界,最后再打印整个树的右边界
    public static void morrisPos(Node head){
        if(head == null) return;
        Node cur = head;
        Node mostRight = null;
        while(cur != null){
            mostRight = cur.left;
            // cur有左树
            if(mostRight != null){
                // 找到左树的最右节点
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                // 最右节点的right为空，说明是第一次到cur
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                    // 最右节点的right不为空，则一定为cur，说明时第二次到cur
                }else{
                    mostRight.right = null;
                    printEdge(cur.left);
                }
            }
            // cur无左树
            cur = cur.right;
        }
        printEdge(head);
        System.out.println();
    }

    // 倒序打印右边界
    public static void printEdge(Node head){
        Node tail = reverseEdge(head);
        Node cur = tail;
        while(cur != null){
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    // 将一条边的指针反向
    public static Node reverseEdge(Node from){
        Node next = null;
        Node pre = null;
        while(from != null){
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }

    // 应用：判断一颗树是否为二叉搜索树
    public static boolean isBST(Node head){
        if(head == null) return true;
        Node cur = head;
        Node mostRight = null;
        Node pre = null;
        boolean res = true;
        while(cur != null){
            mostRight = cur.left;
            if(mostRight != null){
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }
            if(pre != null && pre.val >= cur.val) res = false;
            pre = cur;
            cur = cur.right;
        }
        return res;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        morris(head);
        morrisPre(head);
        morrisIn(head);
        morrisPos(head);
    }
}
