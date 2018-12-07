package org.lamb.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:字符串工具类
 *
 * @author zhoulongbiao
 * @Date 2018/11/27 10:40
 */
@Slf4j
public class StringUtil extends StringUtils {

    public static final DecimalFormat textDf = new DecimalFormat("#0.00");

    public static String subStr(String value, int beginIndex, int offset) {
        return value.substring(beginIndex, beginIndex + offset);
    }

    public static boolean isPattern(String str, String rex) {
        if (isNotBlank(str)) {
            Pattern pattern = Pattern.compile(rex);
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 字符串截取后tailNumber+1位
     *
     * @param value
     * @param tailNumber
     * @return
     */
    public static String subStrTail(String value, int tailNumber) {
        if (hasBlank(value))
            return "";
        return value.substring(value.length() - tailNumber);
    }

    /**
     * 字符串是否是数字型
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return isPattern(str, "[0-9.]*");
    }

    /**
     * 字符串是否是正整数
     *
     * @param str
     * @return
     */
    public static boolean isPositiveNumber(String str) {
        return isPattern(str, "[0-9]*");
    }

    /**
     * 判断字符串是否是金额
     *
     * @param str 字符串
     * @return
     */
    public static boolean isStringMoney(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后一位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 获取字母和数字
     *
     * @param str
     * @return
     */
    public static String getLetterOrDigit(String str) {
        StringBuffer sb = new StringBuffer();
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (Character.isLetterOrDigit(arr[i])) {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public static String[] getStringByComma(String strComma) {
        if (StringUtils.isBlank(strComma)) {
            throw new RuntimeException("strComma:" + strComma + "is null or  blank ");
        }
        String[] strArr = strComma.split(",");
        return strArr;
    }

    /**
     * 将Set集合转换为数组
     *
     * @param set
     * @return
     */
    public static String[] setToArray(Set<String> set) {
        final int size = set.size();
        String[] strArray = set.toArray(new String[size]);
        return strArray;
    }

    /**
     * 把Map<String, String>参数转换成queryString
     *
     * @param params
     * @return
     */
    public static final String getQueryString(Map<String, String> params) {
        if (null == params || params.size() == 0)
            return null;
        StringBuilder sb = new StringBuilder();
        for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext(); ) {
            Entry<String, String> entry = it.next();
            String val = (String) entry.getValue();
            if (val != null) {
                Object key = entry.getKey();
                sb.append(key).append("=").append(val).append("&");
            }

        }

        return (sb.length() > 0) ? sb.substring(0, sb.length() - 1) : null;
    }

    /**
     * 组装queryString （*不校验key-value的合法性*）
     *
     * @param params
     * @return
     */
    public static String joinQueryString(Map<String, String[]> params) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<Entry<String, String[]>> it = params.entrySet().iterator(); it.hasNext(); ) {
            Entry<String, String[]> entry = it.next();
            String[] val = (String[]) entry.getValue();
            if (val != null) {
                Object key = entry.getKey();
                for (String v : val) {
                    sb.append(key).append("=").append(v).append("&");
                }
            }

        }
        return (sb.length() > 0) ? sb.substring(0, sb.length() - 1) : "";
    }

    /**
     * 解析request 的query字符串
     *
     * @param queryString
     * @param encoding
     * @return
     */
    public static Map<String, String[]> parseQueryString(String queryString, String encoding) {
        Map<String, String[]> result = new LinkedHashMap<String, String[]>();
        if (queryString != null && queryString.length() > 0) {
            if (null == encoding)
                encoding = "UTF-8";
            try {
                for (String kv : queryString.split("&")) {
                    int idx = kv.indexOf('=');
                    String name = null;
                    String value = null;
                    if (idx > 0) {
                        name = URLDecoder.decode(kv.substring(0, idx), encoding);
                        value = URLDecoder.decode(kv.substring(idx + 1), encoding);
                    } else {
                        name = URLDecoder.decode(kv, encoding);
                        value = "";
                    }
                    if (result.containsKey(name)) {
                        String[] currentParamValues = result.get(name);
                        List<String> valList = new ArrayList<String>(Arrays.asList(currentParamValues));
                        valList.add(value);
                        String[] newParamValues = new String[valList.size()];
                        result.put(name, (String[]) valList.toArray(newParamValues));
                    } else {
                        result.put(name, new String[]{value});
                    }
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * 按','分割字符串-Set
     *
     * @param s
     * @return
     */
    public static Set<String> commaDelimitedStringToSet(String s) {
        if (s == null || s.trim().length() == 0) {
            return null;
        }
        Set<String> set = new HashSet<String>();
        String[] split = s.split(",");
        for (String aSplit : split) {
            String trimmed = aSplit.trim();
            if (trimmed.length() > 0)
                set.add(trimmed);
        }
        return set;
    }

    /**
     * 将Textarea字符串转换为Database字符串
     *
     * @param str
     * @return
     */
    public static String convertTextareaToDatabase(String str) {
        if (str == null || str.equals("")) {
            return str;
        }
        str = str.replace("\r\n", "<br>").replace("\n", "<br>");
        return str;
    }

    /**
     * 将Database字符串转换为Textarea字符串
     *
     * @param str
     * @return
     */
    public static String convertDatabaseToTextarea(String str) {
        if (str == null || str.equals("")) {
            return str;
        }
        str = str.replace("<br>", "\r\n");
        return str;
    }

    /**
     * 限制字符串长度
     *
     * @param source 输入的字符串
     * @param length 限制长度
     * @param suffix 超过限制长度时追加字符
     * @return
     */
    public static String truncate(String source, int length, String suffix) {
        if (StringUtils.isBlank(source)) {
            return "";
        }
        int currLen = source.length();
        if (currLen <= length) {
            return source;
        } else {
            return source.substring(0, length) + (suffix == null ? "..." : suffix);
        }
    }

    /**
     * 替换字符
     *
     * @param source
     * @param placeholder
     * @return
     */
    public static String replace(String source, String placeholder) {
        if (StringUtils.isNotBlank(source)) {
            return source.replace(source, placeholder);
        }
        return source;
    }

    /**
     * 指定字符截取
     *
     * @param source
     * @param startChar
     * @param endChar
     * @return
     */
    public static String substring(String source, String startChar, String endChar) {
        if (isBlank(source)) {
            return "";
        }
        int endIndex = source.lastIndexOf(endChar);
        if (endIndex < source.lastIndexOf(startChar)) {
            endIndex = source.length();
        }
        return source.substring(source.lastIndexOf(startChar) + 1, endIndex);
    }

    /**
     * 获取字符串长度
     *
     * @param source
     * @return
     */
    public static int getLength(String source) {
        if (isBlank(source)) {
            return 0;
        }
        return source.length();
    }

    /**
     * 截取指定字节长度字符串（汉字占2个字节）
     *
     * @param sourceStr 源字符串
     * @param length    截取字节长度
     * @param suffix    超过限制长度时追加字符
     * @return
     */
    public static String truncateStringByByte(String sourceStr, int length, String suffix) {
        if (isBlank(sourceStr)) {
            return "";
        }
        if (sourceStr.getBytes().length < length) {
            return sourceStr;
        }

        int strByteLen = 0;
        StringBuilder strBuider = new StringBuilder();
        for (int i = 0; i < sourceStr.length(); i++) {
            String tempStr = sourceStr.substring(i, i + 1);
            int tempByteLen = tempStr.getBytes().length;
            if (tempByteLen == 3 || tempByteLen == 2) { // 汉字
                strByteLen += 2;
            } else {
                strByteLen += 1;
            }
            if (strByteLen > length) {
                break;
            }
            strBuider.append(tempStr);
            if (strByteLen == length) {
                break;
            }
        }

        if (sourceStr.equals(strBuider.toString())) {
            return sourceStr;
        }

        return strBuider.toString() + (suffix == null ? "" : suffix);
    }


    public static String getFIrstSixAndLastFourBankCardNo(String bankCardNo) {
        try {
            if (StringUtil.isBlank(bankCardNo)) return "";
            String realCardNo = bankCardNo.replace(" ", "").trim();
            if (realCardNo.length() <= 10) return "";
            String firstSixStr = realCardNo.substring(0, 6);
            String lastFourStr = realCardNo.substring(realCardNo.length() - 4);
            StringBuffer sb = new StringBuffer();
            sb.append(firstSixStr);
            for (int i = 0; i < realCardNo.length() - 10; i++) {
                sb.append("*");
            }
            sb.append(lastFourStr);
            return sb.toString();
        } catch (Exception e) {
            log.error("getFIrstSixAndLastFourBankCardNo: param : {}, error : {}", new Object[]{bankCardNo, e.getMessage()});
            return "";
        }
    }


    /**
     * 对字符串进行格式化 替换STR里面的变量.
     *
     * @param str
     * @param array
     * @return
     */
    public static String messageFormatHandle(String str, Object[] array) {
        if (StringUtils.isBlank(str) || null == array) {
            return null;
        }
        MessageFormat msformat = new MessageFormat(str);
        return msformat.format(array);
    }

    /**
     * 拼接字符串
     *
     * @param params
     * @return
     */
    public static String concat(Object... params) {
        if (params == null || params.length == 0) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        for (Object param : params) {
            str.append(param);
        }
        return str.toString();
    }

    /**
     * 参数中是否有空值，只要有一个为空，则返回true，全不为空则返回false<br>
     * "空值"说明：
     * <ul>
     * <li>1，如果是字符串：null，或"", " "
     * <li>2，如果是long类型：null, 或者<=0
     * <li>3，如果是其它对象：为null
     * </ul>
     *
     * @param params
     * @return
     */
    public static boolean hasBlank(Object... params) {
        for (Object param : params) {
            if (param instanceof String) {
                if (StringUtils.isBlank((String) param)) {
                    return true;
                }
            } else if (param instanceof Long) {
                if (param == null || ((Long) param) < 0) {
                    return true;
                }
            } else if (param instanceof Integer) {
                if (param == null || ((Integer) param) < 0) {
                    return true;
                }
            } else if (param == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        if (isBlank(str)) {
            return false;
        }
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 截取字符串后size位
     *
     * @param src  原字符串
     * @param size 截取的后面的几位
     * @return
     */
    public static String interceptLastStr(String src, int size) {
        src = src.trim();
        if (size < 0 || src.length() < size) {
            return null;
        }
        return src.substring(src.length() - size, src.length());
    }

    /**
     * 生产length位随机Code
     *
     * @param length          随机数的长度
     * @param isOnlyNumber    是否只有数字
     * @param isOnlyLowerCase 是否只有小写
     */
    public static String getRandomCode(int length, boolean isOnlyNumber, boolean isOnlyLowerCase) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            if (isOnlyNumber) {
                charOrNum = "num";
            }
            if ("char".equalsIgnoreCase(charOrNum)) {// 字符串
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;// 取得大写字母还是小写字母
                if (isOnlyLowerCase) {
                    choice = 97;
                }
                char v = (char) (choice + random.nextInt(26));
                if (v != 'l' && v != 'O') {
                    val += v;
                } else {
                    i--;
                }
            } else if ("num".equalsIgnoreCase(charOrNum)) {// 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 防止js注入
     *
     * @param source
     * @return
     */
    public static String preventXXS(String source) {
        if (isNotBlank(source)) {
            source = source.replaceAll("&", "&amp").replaceAll("<", "&lt").replaceAll(">", "&gt")
                    .replaceAll("\"", "&quot");
        }
        return source;
    }


    /**
     * 将GET方式请求过来的中文参数进行转换
     *
     * @param paramVal
     * @return
     */
    public static String getMethodConvertChiStr(String paramVal) {
        try {
            paramVal = new String(paramVal.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return paramVal;
    }

    /**
     * 将下划线分隔的字符串转换为驼峰式，首字母小写.
     *
     * @param s 下划线分隔的字符串
     * @return 驼峰式字符串
     */
    public static String toCamelCase(String s) {
        if (StringUtil.isBlank(s)) {
            return "";
        }
        s = s.toLowerCase();
        StringBuffer sb = new StringBuffer();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '_') {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /***
     * 首字母转换
     * @param str 待转换的字符
     * @param isUpper true:首字母转换大写 , false:首字母转换小写
     * @return 转换后的字符
     * **/
    public static String firstLetterConvert(String str, boolean isUpper) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuffer buff = new StringBuffer(str);
        buff.replace(0, 1, String.valueOf(isUpper ? Character.toUpperCase(str.charAt(0)) : Character.toLowerCase(str.charAt(0))));
        return buff.toString();
    }

    /**
     * 字符替换
     *
     * @param str    要替换的字符
     * @param sindex 起始位置
     * @param eindex 结束位置
     * @param newStr 要替换的新字符
     * @return String 替换后的字符串..
     **/
    public static String replace(String str, int sindex, int eindex, String newStr) {
        StringBuffer sb = new StringBuffer(str);
        if (sindex < 0) return sb.toString();
        if (eindex < 0) return sb.toString();
        if (sindex > eindex) return sb.toString();
        sb.replace(sindex, eindex, newStr);
        return sb.toString();
    }

    /**
     * 如果原字符串为<code>null</code>，则更改为供选择的字符串.
     *
     * @param original    原字符串
     * @param alternative 供选择的字符串
     * @return 更改后的字符串
     */
    public static String alterIfNull(String original, String alternative) {
        if (original == null) {
            return alternative;
        }
        return original;
    }

    /**
     * 如果原字符串为空白字符串，则更改为供选择的字符串.
     *
     * @param original    原字符串
     * @param alternative 供选择的字符串
     * @return
     */
    public static String alterIfBlank(String original, String alternative) {
        if (isBlank(original)) {
            return alternative;
        }
        return original;
    }

    /**
     * 计算有多少页
     * @param num 被除数
     * @param divisor 除数
     * @return
     */
    public static int getPageNum(Integer num, Integer divisor) {
        if (divisor == 0) {
            return 0;
        }
        return num % divisor == 0 ? (num / divisor) : (num / divisor) + 1;
    }
}
