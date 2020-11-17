package algorithm.sort;

import com.sun.xml.internal.bind.v2.model.annotation.Quick;

/**
 * 快速排序
 * 时间复杂度 O( nlog(n) )
 * 空间复杂度 O( log(n) )
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] array = new int[]{2, 1, 6, 3, 4, 10};
        QuickSort quickSort = new QuickSort();
        quickSort.quickSort(array, 0, array.length-1);
        for(int n: array){
            System.out.println(n);
        }
    }

    public void quickSort(int[] array, int left, int right){
        int index = partition(array, left, right);
        //排序左半部分
        if(index-1 > left){
            quickSort(array, left, index-1);
        }
        //排序右半部分
        if(index < right){
            quickSort(array, index, right);
        }
    }

    int partition(int[] array, int left, int right){
        // 选择一个基准点
        int pivot = array[(left + right) / 2];
        while(left <= right) {
            // 找出左边中应该被放到右边的元素
            while(array[left] < pivot) left++;
            // 找出右边中应该被放到左边的元素
            while(array[right] > pivot) right--;
            if(left <= right){
                // 交换元素
                int tmp = array[left];
                array[left] = array[right];
                array[right] = tmp;
                left++;
                right--;
            }
        }
        // pivot的下标+1
        return left;
    }

}
