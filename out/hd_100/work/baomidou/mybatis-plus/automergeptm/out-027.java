package com.baomidou.mybatisplus.toolkit;

/**
 * <p>
 * EscapeOfString ，数据库字符串转义
 * </p>
 *
 * @author Caratacus
 * @Date 2016-10-16
 */
public class StringEscape {
  /**
	 * 字符串是否需要转义
	 * 
	 * @param x
	 * @param stringLength
	 * @return
	 */
  private static boolean isEscapeNeededForString(String x, int stringLength) {
    boolean needsHexEscape = false;
    for (int i = 0; i < stringLength; ++i) {
      char c = x.charAt(i);
      switch (c) {
        case 0:
        needsHexEscape = true;
        break;
        case '\n':
        needsHexEscape = true;
        break;
        case '\r':
        needsHexEscape = true;
        break;
        case '\\':
        needsHexEscape = true;
        break;
        case '\'':
        needsHexEscape = true;
        break;
        case '\"':
        needsHexEscape = true;
        break;
        case '\u001a':
        needsHexEscape = true;
        break;
      }
      if (needsHexEscape) {
        break;
      }
    }
    return needsHexEscape;
  }

  /**
	 * 转义字符串
	 * 
	 * @param x
	 * @return
	 */
  public static String escapeString(String x) {
    if (x.matches("\'(.+)\'")) {
      x = x.substring(1, x.length() - 1);
    }
    String parameterAsString = x;
    int stringLength = x.length();
    if (isEscapeNeededForString(x, stringLength)) {
      StringBuilder buf = new StringBuilder((int) (x.length() * 1.1));
      for (int i = 0; i < stringLength; ++i) {
        char c = x.charAt(i);
        switch (c) {
          case 0:
          buf.append('\\');
          buf.append('0');
          break;
          case '\n':
          buf.append('\\');
          buf.append('n');
          break;
          case '\r':
          buf.append('\\');
          buf.append('r');
          break;
          case '\\':
          buf.append('\\');
          buf.append('\\');
          break;
          case '\'':
          buf.append('\\');
          buf.append('\'');
          break;
          case '\"':
          buf.append('\\');
          buf.append('\"');
          break;
          case '\u001a':
          buf.append('\\');
          buf.append('Z');
          break;
          default:
          buf.append(c);
        }
      }
      parameterAsString = buf.toString();
    }
    return "\'" + parameterAsString + "\'";
  }
}