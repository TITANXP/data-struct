package sort;

// 插入排序 O(N^2)
public class InsertSort {
    public static void main(String[] args) {
        int[] data = {9, -16, 1, 3, 29, 5};
        System.out.println("排序之前:\n" + java.util.Arrays.toString(data));
        sort(data);
        System.out.println("排序之后:\n" + java.util.Arrays.toString(data));
    }

    public static void sort(int[] arr){
        if(arr == null || arr.length < 2) return;

        // 0 - i 有序
        for(int i = 1; i < arr.length; i++){
            for(int j = i - 1; j >= 0 && arr[j] > arr[j+1]; j--){
                swap(arr, j, j+1);
            }
        }
    }

    public static void swap(int[] arr, int i, int j){
        if(i == j) return;
        arr[i] += arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }
}
