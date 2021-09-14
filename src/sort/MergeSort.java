package sort;

/**
 * 归并排序
 * 时间复杂度O( nlog(n) )
 * 空间复杂度O(n)
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] array = new int[]{2, 1, 6, 3, 4, 10};
        MergeSort mergeSort = new MergeSort();
        mergeSort.mergeSort(array);
        for(int n: array){
            System.out.println(n);
        }
    }

    public void mergeSort(int[] array){
        int[] helper = new int[array.length];
        mergeSort(array, helper, 0, helper.length-1);
    }

    void mergeSort(int[] array, int[] helper, int low, int high){
        if(low < high){
            int middle = (low + high) / 2;
            // 排序左半部分
            mergeSort(array, helper, low, middle);
            // 排序右半部分
            mergeSort(array, helper, middle+1, high);
            // 归并
            merge(array, helper, low, middle, high);
        }
    }

    void merge(int[] array, int[] helper, int low, int middle, int high){
        // 将数组array左右两边复制到helper中
        for(int i = low; i <= high; i++){
            helper[i] = array[i];
        }

        int helperLeft = low;
        int helperRight = middle+1;
        int cur = low;
        // 迭代访问helper，比较左右两部分的元素
        while(helperLeft <= middle && helperRight <= high){
            if(helper[helperLeft] <= helper[helperRight]) {
                array[cur] = helper[helperLeft];
                helperLeft++;
            }else{
                array[cur] = helper[helperRight];
                helperRight++;
            }
            cur++;
        }
        //将helper左半部分剩余元素复制到array（右半部分已经在array中）
        int remaining = middle - helperLeft;
        for(int i = 0; i <= remaining; i++){
            array[cur+i] = helper[helperLeft+i];
        }


    }
}
