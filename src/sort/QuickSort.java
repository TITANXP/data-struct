package sort;


import java.util.Random;

/**
 * 快速排序
 * 时间复杂度：平均时间复杂度O(nlogn)
 * 空间复杂度：O(logn), 因为递归栈空间的使用问题
 */
public class QuickSort {
    public static void quickSort(int[] data, int left, int right){
        if(left < right){
            int pivot = partition(data, left, right);
            quickSort(data, left, pivot-1);
            quickSort(data, pivot+1, right);
        }
    }

     private static int partition1(int[] data, int left, int right){
         int i = left;
         int j = right;
         // 随机选取一个数，和第一个数交换
         swap(data, left + (int) (Math.random() * (right - left + 1)), left);
         int x = data[i];
         while(i < j){
             // 从右向左找第一个小于x的数，并放到i位置，然后将i右移
             while(i < j && data[j] >= x)
                 j--;
             if(i < j) data[i++] = data[j];
             // 从左向右找第一个大于等于x的数，并放到j位置，然后将j左移
             while(i < j && data[i] < x)
                 i++;
             if(i < j) data[j--] = data[i];
             // 将x放入i位置
             data[i] = x;
         }
         return i;
     }

    private static int partition(int[] data, int left, int right){
        // 先随机选取一个数放到末尾
        swap(data, new Random().nextInt(right - left + 1) + left, right);
        // 两个指针small和i，small指向发现的最后一个小于基准数字的位置
        int small = left - 1;
        //  i向右扫描数组，当遇到一个小于基准数的数时，将small右移1位，并交换i和small位置的元素
        for(int i = left; i < right; i++){
            if(data[i] < data[right]){
                small++;
                swap(data, small, i);
            }
        }
        // 将基准数放到中间
        swap(data, right, ++small);
        // 返回基准数字的位置
        return small;
    }



    public static void swap(int[] arr, int a, int b){
        if(a == b) return;
        arr[a] += arr[b];
        arr[b] = arr[a] - arr[b];
        arr[a] = arr[a] - arr[b];
    }



    public static void main(String[] args) {
        int[] data = {2 ,3, 1, 7, 3, 8, 4};
        quickSort(data, 0, data.length-1);
        System.out.println(java.util.Arrays.toString(data));

    }
}