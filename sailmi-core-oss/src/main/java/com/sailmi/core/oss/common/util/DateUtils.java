package com.sailmi.core.oss.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 * @author asf
 */
public class DateUtils {

	/**
	 * 根据给定的日期格式将日期字符串转化成对应的日期格式
	 * @param str 日期字符串
	 * @param format  日期格式
	 * @return 转化后的日期类型
	 */
	public static Date getDate(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			if(str == null || "".equals(str)) {
				return new Date();
			}
			return sdf.parse(str);
		} catch (ParseException e) {

		}
		return new Date();
	}

	/**
	 * 根据给定的日期格式将日期字符串转化成对应的日期格式字符串
	 * @param str 日期字符串
	 * @param pattern 日期格式
	 * @return 转化后的日期类型
	 */
	public static String getStrDate(String str, String pattern) {
		//针对数据库中存在的这种格式日期字符串进行处理
		//2019-11-21 15:52:55.0
		str = (str.contains(".0")) ? str.substring(0, str.indexOf(".0")): str;
		//针对传入日期字符串和日期格式不一致数据进行处理
		str = (str.trim().length() > pattern.trim().length()) ? str.substring(0, pattern.length()) : str;
		LocalDate localDate = LocalDate.parse(str, DateTimeFormatter.ofPattern(pattern));

		return localDate.format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 根据给定的日期格式将日期转化成对应的日期格式字符串
	 * @param dateTime 日期
	 * @param pattern 日期格式
	 * @return 转化后的日期类型
	 */
	public static String getStrDate(Date dateTime, String pattern) {

		LocalDate localDate = dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return localDate.format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 根据给定的日期格式将当前日期转化成对应的日期格式字符串
	 * @param pattern 日期格式
	 * @return 转化后的日期类型
	 */
	public static String getStrDate(String pattern) {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 *
	 * @param str
	 * @param sdf
	 * @return
	 */
	public static Date getDate(String str, SimpleDateFormat sdf) {
		try {
			if(str == null || "".equals(str)) {
				return new Date();
			}
			return sdf.parse(str);
		} catch (ParseException e) {

		}
		return new Date();
	}

	/**
	 * 根据字符串格式日期获取LocalDateTime对象
	 * @param str
	 * @return
	 */
	public static LocalDateTime getLocalDateTime(String str) throws ParseException {
		LocalDateTime localDateTime = null;
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime;
	}

	/**
	 * 根据字符串格式日期和日期格式获取LocalDateTime对象
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static LocalDateTime getLocalDateTime(String str, String pattern) throws ParseException {
		LocalDateTime localDateTime = null;
		Date date = new SimpleDateFormat(pattern).parse(str);
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime;
	}
}

