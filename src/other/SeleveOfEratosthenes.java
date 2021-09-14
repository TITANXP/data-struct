package other;

/**
 * 生成素数序列： 埃拉托斯特尼筛法
 *      能够非常高效的生成素数序列，原理是剔除所有可能被素数整除的非素数。
 *      1. 先列出到max为止的所有数字
 *      2. 划掉所有可能被2整除的数（2保留）
 *      3. 找到下一个素数，并划掉所有能被它整除的数
 *      4. 最终得到 2-max 之间的素数序列
 */
public class SeleveOfEratosthenes {
    public static void main(String[] args) {
        SeleveOfEratosthenes s = new SeleveOfEratosthenes();
        boolean[] flags = s.seleveOfEratosthenes(100);
        for(int i = 0; i < flags.length; i++){
            if(flags[i]) System.out.println(i);
        }
    }

    public boolean[] seleveOfEratosthenes(int max){
        boolean[] flags = new boolean[max+1];
        int count = 0;
        // 除了1和0，其它设为true
        init(flags);
        int prime = 2;
        while(prime <= Math.sqrt(max)){
            // 剔除能被prime整除的
            crossOff(flags, prime);
            // 找到下一个素数
            prime = getNextPrime(flags, prime);
        }
        return flags;
    }

    public void crossOff(boolean[] flags, int prime){
        // 从prime*prime开始
        // 因为如果存在一个数 k*prime（k<prime）
        // 那么这个数应该已经在前面的迭代中删除
        for(int i = prime*prime; i < flags.length; i+=prime){
            flags[i] = false;
        }
    }

    public int getNextPrime(boolean[] flags, int curPrime){
        int i = curPrime + 1;
        while(i < flags.length && flags[i] == false)
            i++;
        return i;
    }

    public void init(boolean[] flags){
        for(int i = 2; i < flags.length; i++){
            flags[i] = true;
        }
    }


}
