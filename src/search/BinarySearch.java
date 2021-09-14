package search;

/**
 * 二分查找
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 6, 10};
        BinarySearch search = new BinarySearch();
        int index1 = search.binarySearchRecursive(array, 3, 0, array.length-1);
        int index2 = search.binarySearch(array, 3);
        System.out.println(index1);
        System.out.println(index2);
    }

    // 循环
    public int binarySearch(int[] array, int x){
        int low = 0;
        int high = array.length-1;
        int mid;
        while(low <= high){
            mid = (low + high) / 2;
            if(x < array[mid]){
                high = mid - 1;
            }else if(x > array[mid]){
                low = mid + 1;
            }else{
                return mid;
            }
        }
        return -1;
    }

    // 递归
    public int binarySearchRecursive(int[] array, int x, int low, int high){
        int mid = (low + high) / 2;
        if(x < array[mid]){
            return binarySearchRecursive(array, x, low, mid-1);
        }else if(x > array[mid]){
            return binarySearchRecursive(array, x, mid+1, high);
        }else{
            return mid;
        }
    }
}
