package com.yusys;

import java.util.ArrayList;
import java.util.List;

public class IntegerAnalysis {
    public static String analysis(long number){
        List<String> list = new ArrayList<String>();
        for (int i=2;;i++){
            String result = "";
            long tmp = 1;
            double middle = Math.pow((double) number,(double) 1/i);
            if (middle<2)break;
            long mid = Math.round(middle);
            tmp = tmp * mid;
            result = mid + "";
            for (long j=1;j<=(i/2);j++) {
                long left = mid-j;
                long right = mid+j;
                if (i%2 == 0) {//偶数个
                    left = left+1;
                }
                if (left<2)break;
                if (j==1 && i%2 == 0){
                    tmp = tmp  * right;
                    result = result +" "+ right;
                }else {
                    tmp = tmp * left * right;
                    result = left +" "+ result +" "+ right;
                }
                if (tmp == number) {
                    list.add(result);
                    break;
                }
                if (tmp > number){
                    break;
                }
            }
        }
        return list.size()==0?"-1":list.toString();
    }

    public static void main(String[] args){
        System.out.println(IntegerAnalysis.analysis(120));
    }
}
