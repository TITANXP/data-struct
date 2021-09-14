package arr;

// 求一个未排序数组中的第k小的值，需要时间复杂度为O(N)

import java.util.Comparator;
import java.util.PriorityQueue;

public class Bfprt {

    // ---------- 方法1：改写快排，时间复杂度O(N) 但有一定概率 ---------------------
    public static int minKth1(int[] arr, int k){
        int[] arr2 = new int[arr.length];
        System.arraycopy(arr, 0, arr2, 0, arr.length);
        return process1(arr2, 0, arr2.length - 1, k-1);
    }

    public static int process1(int[] arr, int L, int R, int k){
        if(L == R) return arr[L];
        // 随机选一个数
        int pivot = arr[L + (int)(Math.random() * (R - L + 1))];
        // 将数组分为三个区，小于pivot、等于pivot、大于pivot
        // range长度为2， range[0] 表示等于区的开始下标，range[1]表示等于区的结束下标
        int[] range = partition(arr, L, R, pivot);
        // 如果k小于“等于部分”的开始下标，则用”小于部分“继续递归
        if(range[0] > k){
            return process1(arr, L, range[0] - 1, k);
            // 如果k大于“等于部分”的结束下标，则用”大于部分“继续递归
        }else if(range[1] < k){
            return process1(arr, range[1] + 1, R, k);
            // 如果等于部分的下标命中k则返回，
        }else{
            return arr[k];
        }

    }

    public static int[] partition(int[] arr, int L, int R, int pivot){
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while(cur < more){
            if(arr[cur] < pivot){
                swap(arr, cur++, ++less);
            }else if(arr[cur] > pivot){
                swap(arr, cur, --more);
            }else{
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    public static void swap(int[] arr, int a, int b){
        if(a == b) return;
        arr[a] += arr[b];
        arr[b] = arr[a] - arr[b];
        arr[a] = arr[a] - arr[b];
    }

    // ------------- 方法2： bfprt算法， 时间复杂度严格收敛于O(N) ----------------
    public static int minKth2(int[] arr, int k){
        int[] arr2 = new int[arr.length];
        System.arraycopy(arr, 0, arr2, 0, arr.length);
        return bfprt(arr2, 0, arr.length - 1, k-1);
    }

    public static int bfprt(int[] arr, int L, int R, int k){
        if(L == R) return arr[L];
        // 先找到一个数作为基准
        int pivot = mediaOfMedians(arr, L, R);
        // 将数组分为三个区，小于pivot、等于pivot、大于pivot
        // range长度为2， range[0] 表示等于区的开始下标，range[1]表示等于区的结束下标
        int[] range = partition(arr, L, R, pivot);
        if(range[0] > k){
            return bfprt(arr, L, range[0] - 1, k);
        }else if(range[1] < k){
            return bfprt(arr, range[1] + 1, R, k);
        }else{
            return arr[k];
        }

    }

    // 返回作为划分基准的数
    public static int mediaOfMedians(int[] arr, int L, int R){
        int size = R - L + 1;
        int offset = size % 5  == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        // L...R 每5个数一组
        // L ... L + 4
        // L + 5 ... L + 9
        // L + 10 ... L + 14
        for(int team = 0; team < mArr.length; team++){
            int teamFirst = L + team * 5;
            // 每组内部进行排序
            // 选取每组的上中位数形成新的数组
            mArr[team] = getMedian(arr, teamFirst, Math.min(teamFirst + 4, R));
        }
        // 返回新数组mArr的中位数
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    // 返回数组的上中位数
    public static int getMedian(int[] arr, int L, int R){
        insertionSort(arr, L, R);
        return arr[(L + R) / 2];
    }

    // 插入排序
    public static void insertionSort(int[] arr, int L, int R){
        for(int i = L + 1; i <= R; i++){
            for(int j = i-1; j >= L && arr[j] > arr[j + 1]; j--){
                swap(arr, j, j + 1);
            }
        }
    }



// --------------- 测试 --------------------

    // 笔试使用第一种方法

    //对数器：利用大根堆，时间复杂度O(N*logN)
    public static int minKth3(int[] arr, int k ){
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
        // 把前k个放进去
        for(int i = 0; i < k; i++){
            maxHeap.add(arr[i]);
        }
        // 把比堆里面最大元素小的放进去
        for(int i = k; i < arr.length; i++){
            if(arr[i] < maxHeap.peek()){
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();

    }
    public static class MaxHeapComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2){
            return o2 - o1;
        }
    }


    // 生成随机数组
    public static int[] generateRandomArray(int maxSize, int maxValue){
        int size = (int) (Math.random() * maxSize ) + 1;
        int arr[] = new int[size];
        for(int i = 0; i < size; i++){
            arr[i] = (int) (Math.random() * (maxValue+1));
        }
        return arr;
    }



    public static void main(String[] args) {
        int testTimes = 10;
        int maxSize = 10;
        int maxValue = 100;
        for(int i = 0; i < testTimes; i++){
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int)(Math.random() * arr.length) + 1;
            System.out.println(java.util.Arrays.toString(arr) + ":" + k);
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans = minKth3(arr, k);
            System.out.println(java.util.Arrays.toString(arr) + ":" + k);
            System.out.println(ans1);
            System.out.println(ans2);
            System.out.println(ans);
        }
    }


}
