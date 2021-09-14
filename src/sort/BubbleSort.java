package sort;

// 冒泡排序 O(N^2)
public class BubbleSort {
    public static void bubbleSort(int[] data) {
        int length = data.length;
        for(int i = 0; i < length - 1; i++){
            boolean flag = false;
            for(int j = 0; j < length - 1 - i; j++){
                if(data[j] > data[j+1]){
                    data[j] = data[j] + data[j+1];
                    data[j+1] = data[j] - data[j+1];
                    data[j] = data[j] - data[j+1];
                    flag = true;
                }
            }
            if(!flag) break;
        }
    }

    public static void main(String[] args) {
        int[] data = {9, -16, 1, 3, 29, 5};
        System.out.println("排序之前:\n" + java.util.Arrays.toString(data));
        bubbleSort(data);
        System.out.println("排序之后:\n" + java.util.Arrays.toString(data));
    }
}
