package ga.tianyuge.test;

import ga.tianyuge.bean.Employee;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.awt.EmbeddedFrame;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/07/07 18:44
 */
public class BigDecimalTest {

    private final static Logger logger = LoggerFactory.getLogger(BigDecimalTest.class);

    @Test
    public void test() {
        BigDecimal bigDecimal = BigDecimal.valueOf(12386.0701234576845387231651235987231423412353764587519345);
        BigDecimal bigDecimal1 = BigDecimal.valueOf(123.100);
        logger.info("bigDecimal的原值: {}", bigDecimal.toString());
        BigDecimal result;

        // negate
        result = bigDecimal.negate();
        printLog("negate()", result, bigDecimal, "返回反数值");
        result = bigDecimal.negate(MathContext.DECIMAL32);
        printLog("negate(MathContext mc)", result, bigDecimal, "返回符合MathContext规则的反数值");

        // plus
        result = bigDecimal.plus();
        printLog("plus()", result, bigDecimal, "返回自己");
        result = bigDecimal.plus(new MathContext(2, RoundingMode.DOWN));
        printLog("plus(MathContext mc)", result, bigDecimal, "返回符合MathContext规则的自己值");

        // add
        result = bigDecimal.add(BigDecimal.ONE);
        printLog("add()", result, bigDecimal, "做加法运算");

        // subtract
        result = bigDecimal.subtract(BigDecimal.ONE);
        printLog("subtract()", result, bigDecimal, "做减法运算");

        // multiply
        result = bigDecimal.multiply(BigDecimal.TEN);
        printLog("multiply()", result, bigDecimal, "做乘法运算");

        // divide
        result = bigDecimal.divide(BigDecimal.TEN, RoundingMode.UP);
        printLog("divide(BigDecimal divisor, RoundingMode roundingMode)",
                result, bigDecimal, "做除法运算");

        // divideToIntegralValue
        result = bigDecimal.divideToIntegralValue(BigDecimal.TEN);
        printLog("divideToIntegralValue(BigDecimal divisor)",
                result, bigDecimal, "做除法运算，返回四舍五入的整数");

        // remainder
        result = bigDecimal.remainder(BigDecimal.TEN);
        printLog("remainder(BigDecimal divisor)",
                result, bigDecimal, "做余数运算");

        // divideAndRemainder
        BigDecimal[] bigDecimals = bigDecimal.divideAndRemainder(BigDecimal.TEN);
        printLog("divideAndRemainder(BigDecimal divisor)",
                bigDecimals, bigDecimal, "做除数运算，返回结果和余数");

        // pow
        result = bigDecimal.pow(2);
        printLog("pow(int n)",
                result, bigDecimal, "做指数运算");

        // abs
        result = bigDecimal.negate().abs();
        printLog("abs()",
                result, bigDecimal, "取绝对值");

        int re;
        // signum
        re = bigDecimal.signum();
        printLog("signum()",
                re, bigDecimal, "根据数的正负返回1，-1，0");

        // precision
        re = bigDecimal.precision();
        printLog("precision()",
                re, bigDecimal, "返回精度");

        BigInteger res;
        // unscaledValue
        res = bigDecimal.unscaledValue();
        printLog("unscaledValue()",
                res, bigDecimal, "返回不包含小数点的数字");

        // movePointLeft
        result = bigDecimal.movePointLeft(1);
        printLog("movePointLeft(int n)",
                result, bigDecimal, "小数点向左移动");

        // movePointRight
        result = bigDecimal.movePointRight(1);
        printLog("movePointRight(int n)",
                result, bigDecimal, "小数点向右移动");

        // scaleByPowerOfTen
        result = bigDecimal.scaleByPowerOfTen(1);
        printLog("scaleByPowerOfTen(int n)",
                result, bigDecimal, "为BigDecimal乘10的n次方");

        // stripTrailingZeros
        result = bigDecimal1.stripTrailingZeros();
        printLog("stripTrailingZeros()",
                result, bigDecimal1, "将数后面没用的0去掉");

        // min
        BigDecimal[] bigs = {bigDecimal1, bigDecimal};
        result = bigDecimal1.min(bigDecimal);
        printLog("min(BigDecimal val)",
                result, bigs, "两者取最小值");

        // ulp
        result = bigDecimal.ulp();
        printLog("ulp()",
                result, bigDecimal, "返回对应精度的最小数字");

        // setScale
        result = bigDecimal.setScale(3, BigDecimal.ROUND_DOWN);
        printLog("setScale(int newScale, int roundingMode)",
                result, bigDecimal, "返回对应精度的最小数字");

        // 0-2+1+10
        result = BigDecimal.valueOf(0).subtract(BigDecimal.valueOf(2)).add(BigDecimal.ONE).add(BigDecimal.TEN);
        printLog("0-2+1+10",
                result, bigDecimal, "0-2+1+10");

    }

    @Test
    public void test1() {
        printLog("test", BigDecimal.valueOf(10.04),
                BigDecimal.valueOf(10.04).setScale(1, RoundingMode.DOWN).stripTrailingZeros(), "test");
    }

    @Test
    public void string2BigDecimalTest() {
        String a = "1";
        System.out.println(new BigDecimal(a).stripTrailingZeros().toPlainString());
    }

    @Test
    public void divideTest() {
        BigDecimal bigDecimal = new BigDecimal(13);
        System.out.println(bigDecimal.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
    }

    @Test
    public void test2() {
        System.out.println(BigDecimal.valueOf(10.04).doubleValue());
    }

    /**
     * BigDecimal类型的计算不要用stream流处理
     */
    @Test
    public void forEachVsStream() {
        BigDecimal[] bigDecimals = {
                BigDecimal.valueOf(127385.56),
                BigDecimal.valueOf(56742314512354123634123.98)
        };
        BigDecimal result = BigDecimal.ZERO;
        List<BigDecimal> bigDecimals1 = Arrays.asList(bigDecimals);
        for (BigDecimal bigDecimal : bigDecimals1) {
            result = result.add(bigDecimal);
        }
        System.out.println(result);
        double sum = bigDecimals1.stream().flatMapToDouble(t -> DoubleStream.of(t.doubleValue())).sum();
        System.out.println(BigDecimal.valueOf(sum).toPlainString());
    }

    @Test
    public void bigDecimalConstructorVsValueOf() {
        String value = "1232432453455345.3453345321";
        double doubleValue = 124545.3453;
        System.out.println(new BigDecimal(doubleValue));
//        System.out.println(Double.parseDouble(value));
        System.out.println(BigDecimal.valueOf(doubleValue));

        System.out.println(new BigDecimal(value));
    }

    @Test
    public void compareTest() {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        BigDecimal bigDecimal1 = new BigDecimal("14246.4000000000");
        bigDecimal = bigDecimal.subtract(bigDecimal1);
        System.out.println(bigDecimal.compareTo(BigDecimal.ZERO) > 0);
        if (bigDecimal.compareTo(bigDecimal1) > 0) {
            System.out.print("111");
        }
    }

    @Test
    public void errorTest() {
        String a = "763,280.46";
        BigDecimal bigDecimal = new BigDecimal(a.replace(",",""));
        System.out.println(bigDecimal);
    }

    @Test
    public void errorTest1() {
        BigDecimal a = new BigDecimal("8.000000000");
        BigDecimal b = new BigDecimal("0E-10");
        System.out.println(b.toPlainString() + "是否为零: " + BigDecimal.ZERO.equals(b));
        b = b.stripTrailingZeros();
        System.out.println(b.toPlainString() + "是否为零: " + BigDecimal.ZERO.equals(b));
        BigDecimal c = a.subtract(b);
//        BigDecimal d = c.divide(b, 4, RoundingMode.HALF_UP);
        BigDecimal result = c.multiply(new BigDecimal("100"));
        System.out.println(a.toPlainString());
        System.out.println(b.toPlainString());
        System.out.println(c.toPlainString());
        System.out.println(result.toPlainString());
    }

    @Test
    public void errorTest2() {
        BigDecimal bigDecimal1 = BigDecimal.valueOf(123123.1231);
        BigDecimal bigDecimal2 = new BigDecimal("10420317.090000000000000");
        BigDecimal add = bigDecimal1.add(new BigDecimal(bigDecimal2.toString()));
        System.out.println(add);

    }

    @Test
    public void errorTest3() {
        BigDecimal bigDecimal2 = new BigDecimal("104203345234562435324617.090000000000000000003450004564000000");
        Employee employee = new Employee();
        employee.setPrice(bigDecimal2);
        System.out.println(employee.getPrice().stripTrailingZeros().toPlainString());


        // stream

        // key

        // for stream map
        // key in map
    }
    @Test
    public void errorTest4() {
        BigDecimal bigDecimal = new BigDecimal("999999999999999999999999999.9999999");
        String s = bigDecimal.toPlainString();
        if (s.contains(".")) {
            String[] split = s.split("\\.");
            System.out.println(s);
            System.out.println(split.length);
            System.out.println(split[0]);
            System.out.println(split[1]);
        }
    }


    /**
     * 打印日志
     * @param methodName 方法名
     * @param n1 返回BigDecimal值
     * @param n2 原BigDecimal值
     * @param role 作用描述
     */
    private void printLog(String methodName, BigDecimal n1, BigDecimal n2, String role) {
        logger.info("\n\t方法: {}\n\t新值: {}\n\t原值: {}\n\t作用：{}",
                methodName,
                n1.toPlainString(),
                n2.toPlainString(),
                role);
    }

    /**
     * 打印日志
     * @param methodName 方法名
     * @param n1 返回BigDecimal值
     * @param n2 原BigDecimal值
     * @param role 作用描述
     */
    private void printLog(String methodName, int n1, BigDecimal n2, String role) {
        logger.info("\n\t方法: {}\n\t新值: {}\n\t原值: {}\n\t作用：{}",
                methodName,
                n1,
                n2.toString(),
                role);
    }

    /**
     * 打印日志
     * @param methodName 方法名
     * @param n1 返回BigDecimal值
     * @param n2 原BigDecimal值
     * @param role 作用描述
     */
    private void printLog(String methodName, BigInteger n1, BigDecimal n2, String role) {
        logger.info("\n\t方法: {}\n\t新值: {}\n\t原值: {}\n\t作用：{}",
                methodName,
                n1,
                n2.toString(),
                role);
    }

    /**
     * 打印日志
     * @param methodName 方法名
     * @param n1s 返回BigDecimal值
     * @param n2 原BigDecimal值
     * @param role 作用描述
     */
    private void printLog(String methodName, BigDecimal[] n1s, BigDecimal n2, String role) {
        logger.info("\n\t方法: {}\n\t新值: {}\n\t原值: {}\n\t作用：{}",
                methodName,
                Arrays.toString(n1s),
                n2.toString(),
                role);
    }

    /**
     * 打印日志
     * @param methodName 方法名
     * @param n1 返回BigDecimal值
     * @param n2s 原BigDecimal值
     * @param role 作用描述
     */
    private void printLog(String methodName, BigDecimal n1, BigDecimal[] n2s, String role) {
        logger.info("\n\t方法: {}\n\t新值: {}\n\t原值: {}\n\t作用：{}",
                methodName,
                n1.toString(),
                Arrays.toString(n2s),
                role);
    }
}
