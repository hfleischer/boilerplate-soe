package com.mysynergis.soe.util;

import com.esri.arcgis.server.json.JSONArray;
import com.esri.arcgis.server.json.JSONObject;

/**
 * basic test of any object<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class VoidUtil {

    public static final String STR_NULL_MARKER = "<NULL>";
    public static final String STR_NULL = "null";
    public static final String STR_NULL_UC = "NULL";

    private VoidUtil() { /* utility class */
        // no public instance
    }

    /**
     * @param arg can be null (applicable to a String)
     * @return true when{@code arg} is null or space characters only ("", " ") or
     * <NULL> string
     *
     * Objects.isVoid("") = true Objects.isVoid(" ") = true
     * Objects.isVoid(null) = true Objects.isVoid("test") = false
     * Objects.isVoid(" test ") = false Objects.isVoid("<NULL>") = true
     *
     * The same functionality as StringUtils.isBlank
     * http://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html#isBlank(java.lang.CharSequence)
     *
     */
    public static boolean isVoid(CharSequence arg) {
        return isVoid(arg, false);
    }

    public static boolean isNotVoid(CharSequence arg) {
        return !isVoid(arg, false);
    }

    public static boolean isNotVoid(JSONArray arg) {
        return arg != null && arg.length() > 0;
    }

    public static boolean isNotVoid(JSONObject arg) {
        return arg != null;
    }

    /**
     * Similar functionality as StringUtils.isBlank
     *
     * <ul>
     * <li>Objects.isVoid("") = true</li>
     * <li>Objects.isVoid(" ") = true</li>
     * <li>Objects.isVoid(null) = true</li>
     * <li>Objects.isVoid("null") = true</li>
     * <li>Objects.isVoid("NULL") = true</li>
     *
     * <li>Objects.isVoid("<NULL>", true) = false</li>
     * <li>Objects.isVoid("null", true) = false</li>
     * <li>Objects.isVoid("NULL", true) = false</li>
     * <li>Objects.isVoid("test") = false</li>
     * <li>Objects.isVoid(" test ") = false</li>
     * </ul>
     *
     * @param arg can be null (applicable to a String)
     * @param isStrict if set to false, the values "<NULL>", "null" and "NULL" are
     * regarded as null value
     * @return is {@code arg} null or empty?
     *
     * @see http://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html#isBlank(java.lang.CharSequence)
     */
    public static boolean isVoid(CharSequence arg, boolean isStrict) {
        if (arg != null) {

            if (!isStrict && (VoidUtil.STR_NULL_MARKER.equals(arg) || VoidUtil.STR_NULL.equals(arg) || VoidUtil.STR_NULL_UC.equals(arg)))
                return true;

            for (int i = 0; i < arg.length(); i++)
                if (!Character.isSpaceChar(arg.charAt(i)))
                    return false;
        }
        return true;
    }

}
