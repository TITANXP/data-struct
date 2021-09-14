package sort;

// 基数排序
public class RadixSort {

    // 仅支持非负数
    public static void radixSort(int[] arr){
        if(arr == null || arr.length < 2) return;
        radixSort(arr, 0, arr.length-1, maxbits(arr));
    }

    // 返回数组中的最大位数
    public static int maxbits(int[] arr){
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < arr.length; i++){
            max = Math.max(max, arr[i]);
        }
        int res = 0;
        while(max != 0){
            res++;
            max /= 10;
        }
        return res;
    }

    public static void radixSort(int[] arr, int L, int R, int digit){
        final int radix = 10;
        int i = 0, j = 0;
        // 有多少数准备多少辅助空间
        int[] help = new int[(R - L) + 1];
        for(int d = 1; d <= digit; d++){ // 有多少位就进出几次
            // 10个空间
            // count[0] 当前位(d位)是0的数字有多少个
            // count[1] 当前位(d位)是(0和1)的数字有多少个
            // count[2] 当前位(d位)是(0、1和2)的数字有多少个
            // count[i] 当前位(d位)是(0~i)的数字有多少个
            int[] count = new int[radix];
            for(i = L; i <= R; i++){
                count[getDigit(arr[i], d)]++;
            }
            for(i = 1; i < count.length; i++){
                count[i] += count[i-1];
            }
            // 将数按顺序放入help数组
            for(i = R; i >= 0; i--){
                j = getDigit(arr[i], d);
                help[count[j] - 1] = arr[i];
                count[j]--;
            }
            // 将help数组数据放回原数组
            for(i = 0; i < help.length; i++){
                arr[i] = help[i];
            }


        }
    }

    // 返回某一位上的数
    public static int getDigit(int x, int d){
        return (x / ((int)Math.pow(10, d - 1)) ) % 10;
    }

    public static void main(String[] args) {
        int[] arr = {2, 1, 2, 3, 5, 4};
        radixSort(arr);
        System.out.println(java.util.Arrays.toString(arr));
    }
}