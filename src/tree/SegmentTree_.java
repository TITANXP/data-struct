package tree;

// 线段树
public class SegmentTree_ {

    public static class SegmentTree {
        // arr[]保存原序列的信息，下标从1开始（为了可以使用位运算计算左右孩子）
        // sum[] 模拟线段树维护区间和
        // lazy[]为累加和懒惰标记
        // change[]为更新的值
        // update[]为更新懒惰标记
        private int MAXN;
        private int[] arr;
        private int[] sum;
        private int[] lazy;
        private int[] change;
        private boolean[] update;

        // 构造器
        public SegmentTree(int[] origin){
            MAXN = origin.length + 1;
            arr = new int[MAXN]; // arr[0]不使用，从1开始
            for(int i = 1; i < MAXN; i++){
                arr[i] = origin[i-1];
            }
            sum = new int[MAXN << 2]; // 某个范围的累加信息，是原数组空间的4倍
            lazy = new int[MAXN << 2]; // 某个范围没有往下传递的累加任务
            change = new int[MAXN << 2]; // 某个范围没有往下传递的更新任务数
            update = new boolean[MAXN << 2]; // 某个范围内的更新操作，更新成了什么
        }

        // 向父节点汇报sum
        public void pushUp(int rt){
            // sum[父节点] = sum[左子节点] + sum[右子节点]
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        // 之前的所有懒更新、懒增加，从父范围，发送给左右两个子范围
        // ln：左子树元素个数，rn：右子树元素个数
        // rt：当前要下发任务的节点       rt << 1：左子节点     rt << 1 | 1：右子节点
        public void pushDown(int rt, int ln, int rn){
            // 先下发update任务，因为记录的update任务一定是在add之前的
            if(update[rt]){
                // 更新update
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                // 清空lazy
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
                // 更新sum
                sum[rt << 1] = change[rt] * ln;
                sum[rt << 1 | 1] = change[rt] * rn;
                // 下发完成，清空父父节点的懒更新
                update[rt] = false;
            }
            // 下发add任务
            if(lazy[rt] != 0){
                // 更新lazy
                lazy[rt << 1] += lazy[rt];
                lazy[rt << 1 | 1] += lazy[rt];
                // 更新sum
                sum[rt << 1] += lazy[rt] * ln;
                sum[rt << 1 | 1] += lazy[rt] * rn;
                // 下发完成，清空父节点的懒增加
                lazy[rt] = 0;
            }
        }

        // 初始化：先把sum数组填好
        // 在arr[l-r]范围上，去build 1-N
        // rt：这个范围的累加和在sum[]中的下标
        public void build(int l, int r, int rt){
            if(l == r){
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        // L...R 所有值更新成C
        // L...R 任务的范围，不会改变
        // l...r 当前节点所表示的范围，会随递归改变
        public void update(int L, int R, int C, int l, int r, int rt){
            // 如果任务L...R将当前范围l...r全包
            if(l >= L && r <= R){
                // 更新懒更新
                update[rt] = true;
                change[rt] = C;
                // 清空懒增加
                lazy[rt] = 0;
                // 更新sum
                sum[rt] = (r - l + 1) * C;
                return;
            }
            // 如果任务L...R没有将当前范围l...r全包，则将任务下发
            int mid = (l + r) >> 1;
            // 父节点将懒更新、懒增加任务下发
            pushDown(rt, mid - l + 1, r - mid); // mid - 1 + 1：左子范围元素个数，r - mid ：右子范围元素个数
            // 如果任务L...R的范围有一部分在左子节点的范围[l, mid]上，则递归将任务下发给左子节点
            if(L <= mid){
                update(L, R, C, l, mid, rt << 1);
            }
            // 如果任务L...R的范围有一部分在有子节点的范围[mid + 1, r]上，则递归将任务下发给右子节点
            if(R > mid){
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            // 递归完成后，节点rt汇总左右子节点的sum，并更新
            pushUp(rt);
        }

        // L...R 所有数加C
        // L...R 任务的范围，不会改变
        // l...r 当前节点所表示的范围，会随递归改变
        public void add(int L, int R, int C, int l, int r, int rt){
            // 如果此时任务L...R 将范围l...r全包了
            if(l >= L && r <= R){
                // 更新sum：C * 元素个数
                sum[rt] += C * (r - l + 1);
                // 更新lazy，表示有add任务没下发
                lazy[rt] += C;
                return;
            }
            // 任务L...R没有将l...r全包
            int mid = (l + r) >> 1;
            // 父节点将懒更新、懒增加任务下发
            pushDown(rt, mid - l + 1, r - mid); // mid - 1 + 1：左子范围元素个数，r - mid ：右子范围元素个数
            // 递归调用add
            // 如果任务L...R的范围有一部分在左子节点的范围[l, mid]上，则递归将任务给左子节点
            if(L <= mid){
                add(L, R, C, l, mid, rt << 1);
            }
            // 如果任务L...R的范围有一部分在右子节点的范围[mid+1, r]上，则递归将任务给右子节点
            if(R > mid){
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            // 递归完成后，节点rt汇总左右子节点的sum，并更新
            pushUp(rt);
        }

        // 查询L...R范围的累加和
        // L...R 任务的范围，不会改变
        // l...r 当前节点所表示的范围，会随递归改变
        public long query(int L, int R, int l, int r, int rt){
            // 如果任务L...R，将当前范围l...r全包，则直接返回rt的sum
            if(L <= l && R >= r) return sum[rt];
            // 任务L...R不能将范围l...r全包，则将任务下发给子节点
            int mid = (l + r) >> 1;
            // 父节点将懒更新、懒加载任务下发
            pushDown(rt, mid - l + 1, r - mid);
            long ans = 0; // 防止溢出
            // 如果任务L...R的范围有一部分在左子节点的范围[l, mid]上，则将任务下发给左子节点
            if(L <= mid){
                ans += query(L, R, l, mid, rt << 1);
            }
            // 如果任务L...R的范围有一部分在有子节点的范围[mid+1, r]上，则将任务下发给右子节点
            if(R > mid){
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }
    }

    // ----------------对数器 ------------
    public static class Right {
        public int[] arr;
        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }

    // ------------- 测试 -----------

    public static int[] genarateRandomArray(int len, int max){
        int size = (int) (Math.random() * len) + 1;
        int[] res = new int[size];
        for(int i = 0; i < size; i++){
            res[i] = (int)(Math.random() * max);
        }
        return res;
    }

    public static boolean test(){
        int len = 100;
        int max = 1000;
        int testTimes = 50;
        int addOrUpdateTimes = 100;
        int queryTimes = 50;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right right = new Right(origin);
            for(int j = 0; j < addOrUpdateTimes; j++){
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int)(Math.random() * max) - (int)(Math.random() * max);
                if(Math.random() < 0.5){
                    seg.add(L, R, C, S, N, root);
                    right.add(L, R, C);
                }else{
                    seg.update(L, R, C, S, N, root);
                    right.update(L, R, C);
                }
            }
            for(int k = 0; k < queryTimes; k++){
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = right.query(L, R);
                if(ans1 != ans2) return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始
        int N = origin.length; // 整个区间的结束位置
        int root = 1; // 整棵树的头节点位置1
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S, N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }
}
