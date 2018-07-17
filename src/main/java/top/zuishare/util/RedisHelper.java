package top.zuishare.util;

/**
 * @author niange
 * @ClassName: RedisHelper
 * @desp:
 * @date: 2018/7/17 下午10:46
 * @since JDK 1.7
 */
public class RedisHelper {

    public static double getZsetScore(int priority, long autoId){
        return getZsetScore(priority, autoId, 1.0);
    }

    public static double getZsetScore(int priority, long autoId, double priorityWeight){
        return priority * priorityWeight + autoId * (1.0 - priorityWeight);
    }
    
    public static void main(String[] args){
        double result = getZsetScore(0, 1);

        double result_2 = getZsetScore(0, 100);
        
        System.out.println(result);

        System.out.println(result_2);

    }

}
