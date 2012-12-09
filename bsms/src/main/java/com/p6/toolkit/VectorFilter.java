/**
 * @author Laud
 * @version 1.0.0
 * copyright powersix
 */
package com.p6.toolkit;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.p6.toolkit.DateChooser.ParseStringAndDate;

public class VectorFilter {

	/**
	 * 私有构造函数
	 */
	private VectorFilter() {
		
	}
	
	/**
	 * 过滤指定数据
	 * @param filter 过滤数组,其中存储要保留的数据在Vector中的位置,
	 * filter参见CUSTOMERFILTER,CONTACTFILTER,STAFFFILTER,PROJECTFILTER,TRACKRECORDFILTER
	 * @param vector 待过滤数据
	 */
	public static Vector<String> singleFilter(int[] filter, Vector<String> vector) {
		int length = filter.length;
		Vector<String> result = new Vector<String>(length);
		
		for (int i = 0; i < length; ++i) {
			result.add(vector.get(filter[i]));
		}
		
		return result;
	}
	
	/**
	 * 过滤指定数据
	 * @param filter 过滤数组,其中存储要保留的数据在Vector中的位置,
	 * filter参见CUSTOMERFILTER,CONTACTFILTER,STAFFFILTER,PROJECTFILTER,TRACKRECORDFILTER
	 * @param vector 待过滤数据
	 */
	public static Vector<Vector<String>> doubleFilter(int[] filter, Vector<Vector<String>> vector) {
		int length = filter.length;
		int vSize = vector.size();
		Vector<Vector<String>> result = new Vector<Vector<String>>(vSize);
		
		for (int i = 0; i < vSize; ++i) {
			Vector<String> v = new Vector<String>(length);
			for (int j = 0; j < length; ++j) {
				v.add(vector.get(i).get(filter[j]));
			}
			result.add(v);
		}
		
		return result;
	}
	
	/**
	 * 过滤已签约项目
	 * @param vector 待过滤的项目内容
	 */
	public static Vector<Vector<String>> signedFilter(Vector<Vector<String>> vector) {
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		int vSize = vector.size();
		
		for (int i = 0; i < vSize; ++i) {
			Vector<String> v = vector.get(i);
			if (!v.get(PROJECTSTATEPOSITION).equals("签约")) {
				result.add(v);
			}
		}
		
		return result;
	}
	
	/**
	 * 过滤已终止项目
	 * @param vector 待过滤的项目内容
	 */
	public static Vector<Vector<String>> endedFilter(Vector<Vector<String>> vector) {
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		int vSize = vector.size();
		
		for (int i = 0; i < vSize; ++i) {
			Vector<String> v = vector.get(i);
			if (!v.get(PROJECTSTATEPOSITION).equals("中止")) {
				result.add(v);
			}
		}
		
		return result;
	}
	
	/**
	 * 过滤已离职人员
	 * @param vector 待过滤的销售人员
	 */
	public static Vector<Vector<String>> outDateFilter(Vector<Vector<String>> vector) {
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		int vSize = vector.size();
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		logger.debug("今天是: " + date + " - " + VectorFilter.class.getName());
		Date outDate = new Date();
		
		for (int i = 0; i < vSize; ++i) {
			outDate = ParseStringAndDate.parseString2Date(vector.get(i).get(OUTDATEPOSITION), "-");
			logger.debug("第" + i + "个记录销售人员的离职日期为" + outDate + " - " + VectorFilter.class.getName());
			if (outDate.after(date)) {
				result.add(vector.get(i));
			}
		}
		
		return result;
	}
	
	/**
	 * 筛选
	 * @param str 筛选字符
	 * @param vector Vector<Vector<String>>类型数据
	 * @return Vector<Vector<String>>类型数据
	 */
	public static Vector<Vector<String>> Filter(String str, Vector<Vector<String>> vector) {
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		int size = vector.size();
		String regex = "^" + str + ".*";
		Pattern pattern = Pattern.compile(regex);
		
		for (int i = 0; i < size; ++i) {
			String v = vector.get(i).lastElement();
			Matcher matcher = pattern.matcher(v);
			boolean matches = matcher.matches();
			if (matches == true) {
				result.add(vector.get(i));
			}
		}
		
		return result;
	}
	
	/**
	 * 过滤得到指定位置，指定值的数据
	 * @param pos 位置
	 * @param data 过滤数据串
	 * @param vector 待过滤数据
	 */
	public static Vector<Vector<String>> positionFilter(int pos, String data, Vector<Vector<String>> vector) {
		if (data == null || data.equals("")) {
			return vector;
		}
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		int vSize = vector.size();
		
		for (int i = 0; i < vSize; ++i) {
			Vector<String> v = vector.get(i);
			if (v.get(pos).trim().equals(data))
				result.add(v);
		}
		return result;
	}
	
	/**
	 * 过滤指定位置，指定值的数据,保留其他数据
	 * @param pos 位置
	 * @param data 过滤数据串
	 * @param vector 待过滤数据
	 */
	public static Vector<Vector<String>> dataFilter(int pos, String data, Vector<Vector<String>> vector) {
		if (data == null || data.equals("")) {
			return vector;
		}
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		int vSize = vector.size();
		
		for (int i = 0; i < vSize; ++i) {
			Vector<String> v = vector.get(i);
			if (v.get(pos).trim().equals(data))
				continue;
			result.add(v);
		}
		return result;
	}
	
	/**
	 * 过滤得到指定时间范围内的数据
	 * @param pos 数据的位置
	 * @param before　某日期之前
	 * @param after 某日期之后
	 */
	public static Vector<Vector<String>> dateFilter(int pos, Date before, Date after, Vector<Vector<String>> vector) {
		if (before == null || after == null) {
			return vector;
		}
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		int vSize = vector.size();
		Date current = null;
		
		for (int i = 0; i < vSize; ++i) {
			Vector<String> v = vector.get(i);
			current = ParseStringAndDate.parseString2Date(v.get(pos), "-");
			if (current.after(before) && current.before(after))
				result.add(v);
		}
		return result;
	}
	
	public static final int CUSTOMERFILTER[] = {0, 1, 2, 3, 4, 5, 6};
	public static final int CONTACTFILTER[] = {0, 1, 2, 4, 5, 6, 7, 9};
	public static final int STAFFFILTER[] = {0, 1, 2, 3, 4, 6, 11, 12};
	public static final int PROJECTFILTER[] = {0, 1, 2, 4, 5, 6, 8, 10};
	public static final int TRACKRECORDFILTER[] = {0, 1, 2, 3, 4, 5};
	private static final int PROJECTSTATEPOSITION = 6; //项目状态在table中的列的位置
	private static final int OUTDATEPOSITION = 7; //离职人员在table中的列的位置
	private static Logger logger = Logger.getLogger(VectorFilter.class);
}
