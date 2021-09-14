package sort;

import java.util.PriorityQueue;

// 堆排序
public class HeapSort {
    public static void main(String[] args) {
        // java自带的堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(6);
        heap.add(8);
        heap.add(0);
        heap.add(2);
        heap.add(9);
        heap.add(1);

        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }
        System.out.println("-----");
        int[] arr = { 2, 1, 3, 5, 4};
        heapSort(arr);
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
    // 堆排序额外空间复杂度O(1)
    public static void heapSort(int[] arr){
        // O(N*logN)
        //	for (int i = 0; i < arr.length; i++) { // O(N)
        //		heapInsert(arr, i); // O(logN)
        //	}
        // 从下到上调整为堆
        for(int i = arr.length - 1; i >= 0; i--){
            heapify(arr, i, arr.length);
        }
        // 没次取堆顶的最大元素放到末尾
        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        // O(N*logN)
        while(heapSize > 0){ // O(N)
            heapify(arr, 0, heapSize); // O(logN)
            swap(arr, 0, --heapSize); // O(1)
        }
    }

    public static void heapInsert(int[] arr, int index){
        while(arr[index] > arr[(index - 1) / 2]){
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public static void heapify(int[] arr, int index, int heapSize){
        int left = 2 * index + 1; // 左子节点的下标
        // 如果还有左子节点
        while(left < heapSize){
            // 两个孩子中，谁的值大，把下标给largest
            // 1）只有左孩子，left -> largest
            // 2) 同时有左孩子和右孩子，右孩子的值<= 左孩子的值，left -> largest
            // 3) 同时有左孩子和右孩子并且右孩子的值> 左孩子的值， right -> largest
            int largest = left + 1 < heapSize && arr[left+1] > arr[left] ? left + 1 : left;
            largest = arr[largest] > arr[index] ? largest : index;
            if(largest == index) break;
            swap(arr, index, largest);
            index = largest;
            left = 2 * index + 1;
        }
    }

    private static void swap(int[] arr, int i, int j){
        if(i == j) return;
        arr[i] += arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }
}
