package sort;

// 选择排序 O(N^2)
public class SelectionSort {
    public static void main(String[] args) {
        int[] arr = {9, -16, 1, 3, 29, 5};
        System.out.println("排序之前:\n" + java.util.Arrays.toString(arr));
        sort(arr);
        System.out.println("排序之后:\n" + java.util.Arrays.toString(arr));
    }

    public static void sort(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        // 0 - n-1
        // 1 - n-1
        // ...
        for(int i = 0; i < arr.length; i++){
            // 最小值在哪个位置上：i - n-1
            int minIndex = i;
            for(int j = i + 1; j < arr.length; j++){
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            swap(arr, i, minIndex);
        }
    }

    public static void swap(int[] arr, int i, int j){
        if(i == j) return;
        arr[i] += arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }
}