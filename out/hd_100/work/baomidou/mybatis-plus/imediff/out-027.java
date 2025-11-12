package com.baomidou.mybatisplus.toolkit;

/**
 * <p>
<<<<<<< commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/A.java
 * EscapeOfString ，数据库字符串转义
||||||| commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/O.java
 * SQL 自动注入器
=======
 * StringEscape ，数据库字符串转义
>>>>>>> commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/B.java
 * </p>
 *
 * @author Caratacus
 * @Date 2016-10-16
 */
public class StringEscape {
<<<<<<< commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/A.java
	/**
	 * 字符串是否需要转义
	 * 
	 * @param x
	 * @param stringLength
	 * @return
	 */
||||||| commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/O.java
=======
	/**
	 * 字符串是否需要转义
	 *
	 * @param x
	 * @param stringLength
	 * @return
	 */
>>>>>>> commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/B.java
	private static boolean isEscapeNeededForString(String x, int stringLength) {

		boolean needsHexEscape = false;

		for (int i = 0; i < stringLength; ++i) {
			char c = x.charAt(i);

			switch (c) {
<<<<<<< commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/A.java
			case '\\':
				needsHexEscape = true;
||||||| commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/O.java
=======
				case 0: /* Must be escaped for 'mysql' */
>>>>>>> commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/B.java

<<<<<<< commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/A.java
				break;
||||||| commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/O.java
	protected DBType dbType = DBType.MYSQL;
=======
					needsHexEscape = true;
>>>>>>> commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/B.java
<<<<<<< commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/A.java

			case '\'':
				needsHexEscape = true;

				break;

			case '"': /* Better safe than sorry */
				needsHexEscape = true;

				break;

			}

			if (needsHexEscape) {
				break; // no need to scan more
			}
		}
		return needsHexEscape;
	}
||||||| commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/O.java
=======
					break;

				case '\n': /* Must be escaped for logs */
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

				case '"': /* Better safe than sorry */
					needsHexEscape = true;

					break;

				case '\032': /* This gives problems on Win32 */
					needsHexEscape = true;
					break;
			}

			if (needsHexEscape) {
				break; // no need to scan more
			}
		}
		return needsHexEscape;
	}
>>>>>>> commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/B.java

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
<<<<<<< commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/A.java

			StringBuilder buf = new StringBuilder((int) (x.length() * 1.1));

			//
			// Note: buf.append(char) is _faster_ than appending in blocks,
			// because the block append requires a System.arraycopy().... go
			// figure...
			//

			for (int i = 0; i < stringLength; ++i) {
				char c = x.charAt(i);

				switch (c) {

				case '\\':
||||||| commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/O.java
=======

			StringBuilder buf = new StringBuilder((int) (x.length() * 1.1));

			//
			// Note: buf.append(char) is _faster_ than appending in blocks,
			// because the block append requires a System.arraycopy().... go
			// figure...
			//

			for (int i = 0; i < stringLength; ++i) {
				char c = x.charAt(i);

				switch (c) {
					case 0: /* Must be escaped for 'mysql' */
>>>>>>> commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/B.java
<<<<<<< commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/A.java
					buf.append('\\');
||||||| commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/O.java
			inject(configuration, builderAssistant, mapperClass);
=======
						buf.append('\\');
>>>>>>> commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/B.java
<<<<<<< commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/A.java
					buf.append('\\');
||||||| commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/O.java
			mapperRegistryCache.add(className);
=======
						buf.append('0');
>>>>>>> commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/B.java
<<<<<<< commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/A.java

					break;

				case '\'':
					buf.append('\\');
					buf.append('\'');
					break;

				case '"': /* Better safe than sorry */
					buf.append('\\');
					buf.append('"');
					break;

				default:
					buf.append(c);
||||||| commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/O.java
=======

						break;

					case '\n': /* Must be escaped for logs */
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

					case '"': /* Better safe than sorry */
						buf.append('\\');
						buf.append('"');

						break;

					case '\032': /* This gives problems on Win32 */
						buf.append('\\');
						buf.append('Z');

						break;

					default:
						buf.append(c);
>>>>>>> commits-hd_100/baomidou/mybatis-plus/fecca2fadb1e32cde3bbed78ff3a219018930d65-9b521b0c07ce969c8967dc755541dc6e664dab9a/B.java
				}
			}

			parameterAsString = buf.toString();
		}
		return "\'" + parameterAsString + "\'";
	}
}