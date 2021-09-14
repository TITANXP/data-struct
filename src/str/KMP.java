package str;

// 有str（长度为N）和match（长度为M）两个字符串，判断match是否为str的子串，并返回起止位置
public class KMP {

    public static int getIndexOf(String str, String match){
        if(str == null || match == null || match.length() < 1 || str.length() < match.length())
            return -1;
        char[] arr1 = str.toCharArray();
        char[] arr2 = match.toCharArray();
        int x = 0;
        int y = 0;
        // 计算next数组 O(M) m <= n
        int[] next = getNextArray(arr2);
        // O(N)
        // 假设从str的0位置开始匹配，
        // 匹配到str的x位置，match的y位置时不等，则x不动，y退回到match的next[y]位置，继续匹配
        while(x < arr1.length && y < arr2.length){
            if(arr1[x] == arr2[y]){
                x++;
                y++;
                // 如果匹配到最后也没有和x位置的字符相等，则放弃包含x位置的字符串
            }else if(next[y] == -1){ // y = 0
                x++;
            }else{
                y = next[y];
            }
        }
        // x一定等于arr1.length，如果y也等于arr2.length说明匹配成功，返回开始位置
        return y == arr2.length ? x - y : -1;
    }

    // 计算next数组：
    // next[i] 表示，字符串开头到i-1位置的字符串，分别截取开头部分和结尾部分，这两部分相同的最大长度（两部分不能取到整体）
    public static int[] getNextArray(char[] arr2){
        if(arr2.length == 1) return new int[]{-1};
        int[] next = new int[arr2.length];
        // 固定的
        next[0] = -1;
        next[1] = 0;
        // 目前求哪个位置的next
        int i = 2;
        // 当前是哪个位置的字符和i-1位置的字符比较
        int cn = 0;
        // 流程：计算i位置的next值，将i位置的字符和next[i-1]位置的字符比较，
        while(i < next.length){
            // 如果相等，则next[i] = next[i-1] + 1
            if(arr2[i-1] == arr2[cn]){
                next[i++] = ++cn;
                // 如果不相等，则与next[next[i-1]] 继续比较
            }else if(cn > 0){
                cn = next[cn];
                // 如果比较到最后也不相等，则直接返回0
            }else{
                next[i++] = 0;
            }
        }
        return next;
    }




    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5;
        System.out.println("test begin");
        for(int i = 0; i < testTimes; i++){
            String str = getRandomString(possibilities , strSize);
            String match = getRandomString(possibilities, matchSize);
            int res = getIndexOf(str, match);
            System.out.println(str+" "+ match+":"+res);
            if(res != str.indexOf(match))
                System.out.println("Oops!");
        }

    }

    // 测试用，获取随机字符串
    public static String getRandomString(int possibilities, int size){
        char[] ans = new char[(int) (Math.random()*size + 1)];
        for(int i = 0; i < ans.length; i++){
            ans[i] = (char) ((int) (Math.random()*possibilities) + 'a');
        }
        return String.valueOf(ans);
    }
}
