package org.github.framework.common.lang;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class CustomStringUtils extends StringUtils {

    public static final String AND = "and";
    public static final String AT = "@";

    public static final String CRLF = "\r\n";

    public static final String DOT = ".";
    public static final String EMPTY = "";
    public static final String EQUALS = "=";

    public static final String FALSE = "false";
    public static final String SLASH = "/";

    public static final String LEFT_BRACE = "{";
    public static final String LEFT_BRACKET = "(";
    public static final String LEFT_CHEV = "<";
    public static final String LEFT_SQ_BRACKET = "[";

    public static final String NEWLINE = "\n";

    public static final String N = "n";
    public static final String NO = "no";
    public static final String NULL = "null";
    public static final String OFF = "off";
    public static final String ON = "on";
    public static final String PERCENT = "%";
    public static final String PIPE = "|";
    public static final String PLUS = "+";

    public static final String QUESTION_MARK = "?";
    public static final String QUOTE = "\"";
    public static final String RETURN = "\r";
    public static final String TAB = "\t";

    public static final String RIGHT_BRACE = "}";
    public static final String RIGHT_BRACKET = ")";
    public static final String RIGHT_CHEV = ">";
    public static final String RIGHT_SQ_BRACKET = "]";
    public static final String UTF_8 = "UTF-8";
    public static final String UNDERSCORE = "_";

    public static final String TRUE = "true";
    public static final String SPACE = " ";

    public static final String Y = "y";
    public static final String YES = "yes";
    public static final String ONE = "1";
    public static final String ZERO = "0";

    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp";
    public static final String HTML_QUOTE = "&quot;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";

    public static final String COLON = ":";

    /**
     * ??????
     */
    public static final String COMMA = ",";

    /**
     * ??????
     */
    public static final String SEMICOLON = ";";

    /**
     * ?????????????????????????????????
     */
    public static final String REGEX_NUMBER = "\\d+(.\\d+)?[fF]?";

    /**
     * ?????????????????????
     */
    public static final String REGEX_MOBILE = "^1(3|4|5|7|8)\\d{9}$";

    /**
     * ?????????????????????
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,3}$";



    /**
     * ????????????
     */
    public static boolean isNumber(final String num) {
        return CustomStringUtils.defaultString(num).matches(REGEX_NUMBER);
    }

    /**
     * ???????????????
     */
    public static boolean isMobile(final String mobile) {
        return CustomStringUtils.defaultString(mobile).matches(REGEX_MOBILE);
    }

    /**
     * ??????????????????
     */
    public static boolean isEmail(final String email) {
        return CustomStringUtils.defaultString(email).matches(REGEX_EMAIL);
    }

    /**
     *
     * ??????JDK?????????UUID, ??????Random????????????, ?????????-??????.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????
     * @param s     ?????????
     * @param regex ?????????
     * @return ???????????????
     */
    public static String[] splitIgnoreBlank(String s, String regex) {

        if (isEmpty(s)) {
            return new String[0];
        }

        if (isEmpty(regex)) {
            return new String[]{s};
        }

        String[] arr = split(s, regex);
        List<String> data = Arrays.asList(arr);
        for (Iterator<String> iter = data.iterator(); iter.hasNext();) {
            String item = iter.next();
            if (isEmpty(item)) {
                iter.remove();
            }
        }
        return data.toArray(new String[data.size()]);

    }

    /**
     * ???????????????????????????????????????????????????
     */
    public static String[] splitIgnoreBlank(String s) {
        return splitIgnoreBlank(s, COMMA);
    }


}
