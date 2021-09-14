package sort;

// 基于非比较的排序——计数排序
public class CountSort {

    public static void countSort(int[] arr){
        if(arr == null || arr.length < 2) return;
        // 找到最大值
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] > max) max = arr[i];
        }
        // 计数
        int[] bucket = new int[max+1];
        for(int i = 0; i < arr.length; i++){
            bucket[arr[i]]++;
        }
        // 把数按大小顺序放回原数组
        int j = 0;
        for(int i = 0; i < bucket.length; i++){
            while(bucket[i]-- > 0){
                arr[j++] = i;
            }
        }

    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 4, 1, 1, 2};
        countSort(arr);
        System.out.println(java.util.Arrays.toString(arr));
    }
}
