package str;

public class Manacher {

    public static int manacher(String s){
        if(s == null || s.length() == 0) return 0;
        // 12132 -> #1#2#1#3#2#
        char[] str = manacherString(s);
        // 回文半径
        int[] pArr = new int[str.length];
        int R = -1;
        // 讲解中，C代表最右的扩成功的位置
        // 代码中为了进行边界处理，表示最右的扩成功位置的，再下一个位置
        int C = -1;
        // 最长回文子串的长度
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < str.length; i++){
            // 先将i位置为中心 不用对比的半径放入pArr
            // C 的回文范围 L...R    i`：i关于C的对称点
            // (1)i 在 L...R 之外，则只有一个字符不需要对比
            // (2) i 在 L...R之内
            //   ① i`的回文区域在L...R范围内  ->  i的回文长度和i`相同
            //   ② i`的回文区域左侧超出了L...R  ->  i的回文区域右侧到R
            //   ③ i`的回文区域左侧正好是L  ->  i的回文区域至少到R，从R-i右侧开始对比即可
            // 将四种情况合并，如果i在L...R之外结果就是1，否则就是R和pArr[i`]
            pArr[i] = i < R ? Math.min(R-i, pArr[2*C-i]) : 1;
            // 继续向外扩
            while(i + pArr[i] < str.length && i - pArr[i] > -1){
                if(str[i - pArr[i]] == str[i + pArr[i]]){
                    pArr[i]++;
                }else{
                    break;
                }
            }
            // 更新R和C
            if(pArr[i] + i > R){
                R = i + pArr[i];
                C = i;
            }
            // 更新最大值
            max = Math.max(max, pArr[i]);
        }
        // 因为添加了特殊字符# ,所以原字符串的最大回文子串长度，就是 加入特殊字符后的字符串的最大回文子串的回文半径-1
        return max - 1;
    }

    public static char[] manacherString(String str){
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for(int i = 0; i < res.length; i++){
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    // 对数器
    public static int right(String s){
        if(s == null || s.length() == 0) return 0;
        char[] str = manacherString(s);
        int max = 0;
        for(int i = 0; i < str.length; i++){
            int L = i - 1;
            int R = i + 1;
            while(L >= 0 && R < str.length && str[L] == str[R]){
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
    }

    public static String getRandomString(int possibilities, int size){
        char[] res = new char[(int)(Math.random() * size) + 1];
        for(int i = 0; i < res.length; i++){
            res[i] = (char)((int)(Math.random() * possibilities) + 'a');
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int size = 20;
        int testTimes = 10;
        for(int i = 0; i < testTimes; i++){
            String str = getRandomString(possibilities, size);
            System.out.println(str);
            int res1 = right(str);
            int res2 = manacher(str);
            System.out.println(res1);
            System.out.println(res2);
            if(res1 != res2){
                System.out.println("err");
            }
        }
    }
}