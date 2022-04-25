package com.expert.xybs.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 *
 *
 */
public class MyUtil {


	private static SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	/**
	 * 获得格式化字符串yyyyMMddHHmmss
	 */
	public static String getDateFormatString1() {
		return sd.format(new Date());
	}



	public static String dealDateFormat(String oldDate) {
		Date date1 = null;
		DateFormat df2 = null;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date date = df.parse(oldDate);
			SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
			date1 = df1.parse(date.toString());
			df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return df2.format(date1);
	}

	/**
	 * 获取项目的根路径
	 * 
	 * @param upIndex
	 *            往上跳n级目录
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static String getProjectRootPath(int upIndex) throws IOException, ClassNotFoundException {
		// classes顶级目录
		String clazzPath = Class.forName("MyUtil").getResource("/").getPath();
		// //System.out.println(clazzPath);
		String index = "";
		for (int i = 0; i < upIndex; i++) {
			index += "../";
		}
		File f = new File(clazzPath, index);
		// //System.out.println(f.getCanonicalPath());
		return f.getCanonicalPath();
	}

	/**
	 * 调用类的set方法,给object对象赋值
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws NoSuchFieldException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setter(Object obj, String setName, Object setValue)
			throws NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = obj.getClass();
		// private java.util.Date
		// com.chinaglasses.glasses.model.StoreApply.sa_finalTime
		//得到指定的属性
		Field f = clazz.getDeclaredField(setName);
		// class java.util.Date
		//得到指定属性的类型
		Class<?> type = f.getType();
		Method method = clazz.getMethod("set" + MyUtil.UpperFirstChar(setName), type);
		if ("java.util.Date".equals(type.getName())) {
			method.invoke(obj, new Date(new Long(setValue.toString())));
		}
		if ("java.lang.Integer".equals(type.getName())) {
			method.invoke(obj, new Integer(setValue.toString()));
		}
		if ("java.lang.Double".equals(type.getName())) {
			method.invoke(obj, new Double(setValue.toString()));
		}
		if ("java.lang.String".equals(type.getName())) {
			method.invoke(obj, setValue.toString());
		}
	}

	/**
	 * 调用类的get方法
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getter(Object obj, String att) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = obj.getClass();
		Method method = clazz.getMethod(att);
		Object value = method.invoke(obj);
		return value;
	}

	/**
	 * 把object对象转成json
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static JSONObject object2json(Object object)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException, ClassNotFoundException {
		JSONObject json = new JSONObject();
		Class<?> clazz = object.getClass();
		// 取得本类的全部属性
		Field[] fields = clazz.getDeclaredFields();
		Object fieldvalue = null;
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			// 属性的名字,也就是json的键
			String fieldname = field.getName();
			// 过滤掉serialVersionUID
			if ("serialVersionUID".equals(fieldname)) {
				continue;
			}
			// 属性类型
			Class<?> type = field.getType();
			@SuppressWarnings("unused")
			String fieldtype = type.getName();
			// //System.out.println(fieldtype);
			// 获得getXXX方法
			Method method = clazz.getMethod("get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1));
			// if("java.lang.Integer".equals(fieldtype)){//整型
			// //执行get方法,获得属性的值,
			// fieldvalue = method.invoke(clazz.newInstance(),int.class);
			// }else if("java.lang.Double".equals(fieldtype)){//double
			// fieldvalue = method.invoke(clazz.newInstance(),Double.class);
			// }else if("java.util.Date".equals(fieldtype)){//Date
			// fieldvalue = method.invoke(clazz.newInstance(),Date.class);
			// }
			fieldvalue = method.invoke(object);
			if (fieldvalue == null) {
				fieldvalue = "";
			}
			if (fieldvalue instanceof List) {
				// System.out.println(JSON.toJSONString(fieldvalue));
			}
			json.put(fieldname, fieldvalue);
			// 给json赋值
		}
		return json;
	}

	/**
	 * 判断list是否为空
	 * 
	 * @param <T>
	 */
	public static <T> boolean isEmpty(List<T> list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串首字母大写
	 */
	public static String UpperFirstChar(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 是否为 中文 或者 纯中文
	 * 
	 * @param str
	 *            指定的字符串
	 * @param type
	 *            设置查询模式: true(纯中文), false(包含中文)
	 * 
	 * @return 是为true，否则false
	 */
	public static Boolean isChinese(String str, boolean type) {
		Boolean isChinese = false;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取一个字符
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字符
				if (temp.matches(chinese)) {
					isChinese = true;
				} else {
					// 判断模式
					if (type) {
						isChinese = false;
					}
				}
			}
		}
		return isChinese;
	}

	/**
	 * 判断是否是银行卡号(简单检查)
	 * 
	 * @param cardId
	 * @return 是为true，否则false
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;
	}

	/**
	 * 检查银行卡号的格式(private)
	 * 
	 * @param nonCheckCodeCardId
	 * @return
	 */
	private static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	/**
	 * 判断是否是中国的手机号
	 * 
	 * @param phone
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkChineseMobile(String phone) {
		Pattern pattern = Pattern.compile("^(13[0-9]|15[0-9]|17[0-9]|180|18[23]|18[5-9])\\d{8}$");
		Matcher matcher = pattern.matcher(phone);

		return matcher.matches();

	}

	/**
	 * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
	 * 
	 * @param mobile
	 *            移动、联通、电信运营商的号码段
	 *            <p>
	 *            移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
	 *            、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
	 *            </p>
	 *            <p>
	 *            联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
	 *            </p>
	 *            <p>
	 *            电信的号段：133、153、180（未启用）、189
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "(\\+\\d+)?1[34587]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}

	/**
	 * 验证固定电话号码.
	 * 
	 * @param phone
	 *            电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
	 *            <p>
	 *            <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9
	 *            的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
	 *            </p>
	 *            <p>
	 *            <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
	 *            对不使用地区或城市代码的国家（地区），则省略该组件。
	 *            </p>
	 *            <p>
	 *            <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false.
	 */
	public static boolean checkPhone(String phone) {
		String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		return Pattern.matches(regex, phone);
	}

	/**
	 * 功能：判断字符串是否为纯数字(负数有符号,所以返回false)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式的正确时间(比如2017-02-29返回false)
	 * 
	 * @param str
	 *            字符串
	 * @return boolean.
	 */
	public static boolean isDataFormat(String str) {
		boolean flag = false;
		String regXStr = "^((\\d{2}(([02468][048])|([13579][26]))" + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]"
				+ "?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|" + "(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))"
				+ "|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))" + "|(\\d{2}(([02468][1235679])|([13579][01345789]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]" + "?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])"
				+ "|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))"
				+ "|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))"
				+ "(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)" + "|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regXStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 描述：是否是邮箱.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 是否是邮箱:是为true，否则false
	 */
	public static Boolean checkEmail(String str) {
		Boolean isEmail = false;
		String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		if (str.matches(expr)) {
			isEmail = true;
		}
		return isEmail;
	}

	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：return"" , 无效：return String的信息
	 * @throws ParseException
	 */
	@SuppressWarnings("unused")
	public static boolean checkIdCard(String IDStr) {
		String errorInfo;// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return false;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return false;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "身份证生日不在有效范围。";
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return false;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		@SuppressWarnings("rawtypes")
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return false;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return false;
			}
		} else {
			return true;
		}
		return true;
	}

	/**
	 * 检查是否是一个合格的钱的格式。
	 * 
	 * @param money
	 * @return
	 */
	public static boolean isMoney(String money) {

		if (isEmpty(money))
			return false;

		double qian = 0;
		try {
			qian = Double.parseDouble(money);
		} catch (Exception e) {
			qian = -1;
		}
		return qian >= 0 ? true : false;
	}

	/**
	 * 检查是否是一个合格的钱的格式，要求两位小数。 返回错误码
	 * 
	 * @param money
	 * @return -3:负数 , <br>
	 *         -2:输入字符串为非纯数字(带符号,如"-") , <br>
	 *         -1:小数点后大于两位 <br>
	 *         0:值为0 , <br>
	 *         1:小数点后小于或等于两位 <br>
	 * @throws NumberFormatException-if
	 *             the string does not contain a parsable double.
	 */
	public static int isMoney2(String money) {
		double qian = 0;
		try {
			qian = Double.parseDouble(money);
		} catch (NumberFormatException e) {
			return -2;
		}
		if (qian < 0) {
			return -3;
		}
		if (qian == 0) {
			return 0;
		}
		if (money.contains(".") && money.indexOf(".") != money.length() - 1) {
			if (money.length() - money.indexOf(".") < 2 + 2) {
				return 1;
			} else {
				return -1;
			}
		}

		return 2;
	}

	/**
	 * 对url进行编码<br>
	 * 比如网址<br>
	 * <li>"https://www.baidu.com/哈哈"<br>
	 * 返回值:<br>
	 * <li>"https%3A%2F%2Fwww.baidu.com%2F%E5%93%88%E5%93%88"
	 * 
	 * @param url
	 * @return
	 */
	public static String encodeURL(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 对参数进行编码 例:<br>
	 * <li>map.put("1", "1");<br>
	 * <li>map.put("2", "1");<br>
	 * 打印结果:<br>
	 * <li>1=1&2=1
	 * 
	 * @param params
	 * @return
	 * 
	 */
	public static String encodeURL(Map<String, String> params) {
		StringBuilder entityBuilder = new StringBuilder("");
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				entityBuilder.append(entry.getKey()).append('=');
				try {
					entityBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				entityBuilder.append('&');
			}
			entityBuilder.deleteCharAt(entityBuilder.length() - 1);
		}
		return entityBuilder.toString();
	}

	/**
	 * 对url进行解码(encodeURL方法的逆过程)
	 * 
	 * @param url
	 * @return
	 */
	public static String decodeURL(String url) {
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断URL地址是否存在
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isURLExist(String url) {
		try {
			URL u = new URL(url);
			HttpURLConnection urlconn = (HttpURLConnection) u.openConnection();
			int state = urlconn.getResponseCode();
			if (state == 200) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 抓取网页内容,自动识别编码(如果用encodeURL方法编码了,需要用decodeURL解码)
	 * 
	 * @param urlString
	 * @return
	 */
	public static String url2Str(String urlString) {
		try {
			StringBuffer html = new StringBuffer();
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			URLConnection c = url.openConnection();
			c.connect();
			String contentType = c.getContentType();
			String characterEncoding = null;
			int index = contentType.indexOf("charset=");
			if (index == -1) {
				characterEncoding = "UTF-8";
			} else {
				characterEncoding = contentType.substring(index + 8, contentType.length());
			}
			InputStreamReader isr = new InputStreamReader(conn.getInputStream(), characterEncoding);
			BufferedReader br = new BufferedReader(isr);
			String temp;
			while ((temp = br.readLine()) != null) {
				html.append(temp).append("\n");
			}
			br.close();
			isr.close();
			return html.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断是否为空字符串(文本类工具)
	 * 
	 * @param value
	 * @return 空为true，否则false
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查对象是否为数字型字符串,包含负数开头的。
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		char[] chars = obj.toString().toCharArray();
		int length = chars.length;
		if (length < 1)
			return false;
		int i = 0;
		if (length > 1 && chars[0] == '-')
			i = 1;
		for (; i < length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 * 
	 * @param values
	 * @return
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 把通用字符编码的字符串转化为汉字编码(感觉没什么用)
	 * 
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}

	/**
	 * 过滤不可见字符
	 * 
	 * @param input
	 * @return
	 */

	public static String stripNonValidXMLCharacters(String input) {
		if (input == null || ("".equals(input)))
			return "";
		StringBuilder out = new StringBuilder();
		char current;
		for (int i = 0; i < input.length(); i++) {
			current = input.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD)) || ((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}

	/**
	 * 过滤空NULL
	 * 
	 * @param o
	 * @return
	 */
	public static String FilterNull(Object o) {
		return o != null && !"null".equals(o.toString()) ? o.toString().trim() : "";
	}

	/**
	 * 是否为空
	 * 
	 * @param o
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		Field[] filed = o.getClass().getDeclaredFields();
		int i = 0;
		for (Field f : filed) {
			f.setAccessible(true);
			if (f.getName() == "serialVersionUID") {
				i++;
				continue;
			}
			try {
				if (f.get(o) == null) {
					i++;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (i == filed.length) {
			return true;
		}
		if ("".equals(FilterNull(o.toString()))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否不为空
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNotEmpty(Object o) {
		if (o == null) {
			return false;
		}
		if ("".equals(FilterNull(o.toString()))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 是否可转化为数字
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNum(Object o) {
		try {
			new BigDecimal(o.toString());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 是否可转化为Long型数字
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isLong(Object o) {
		try {
			new Long(o.toString());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 转化为Long型数字, 不可转化时返回0
	 * 
	 * @param o
	 * @return
	 */
	public static Long toLong(Object o) {
		if (isLong(o)) {
			return new Long(o.toString());
		} else {
			return 0L;
		}
	}

	/**
	 * 转化为int型数字, 不可转化时返回0
	 * 
	 * @param o
	 * @return
	 */
	public static int toInt(Object o) {
		if (isNum(o)) {
			return new Integer(o.toString());
		} else {
			return 0;
		}
	}

	/**
	 * 按字符从左截取固定长度字符串, 防止字符串超长, 默认截取50
	 * 
	 * @param o
	 * @return
	 */
	public static String holdmaxlength(Object o) {
		int maxlength = 50;
		if (o == null) {
			return "";
		}
		return subStringByByte(o, maxlength);
	}

	/**
	 * 从左截取固定长度字符串, 防止字符串超长, maxlength为0时默认50
	 * 
	 * @param o
	 * @return
	 */
	public static String holdmaxlength(Object o, int maxlength) {
		maxlength = maxlength <= 0 ? 50 : maxlength;
		if (o == null) {
			return "";
		}
		return subStringByByte(o, maxlength);
	}

	/**
	 * 按字节截取字符串
	 * 
	 * @param
	 * @param len
	 * @return
	 */
	public static String subStringByByte(Object o, int len) {
		if (o == null) {
			return "";
		}
		String str = o.toString();
		String result = null;
		if (str != null) {
			byte[] a = str.getBytes();
			if (a.length <= len) {
				result = str;
			} else if (len > 0) {
				result = new String(a, 0, len);
				int length = result.length();
				if (str.charAt(length - 1) != result.charAt(length - 1)) {
					if (length < 2) {
						result = null;
					} else {
						result = result.substring(0, length - 1);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 逗号表达式_添加
	 * 
	 * @param commaexpress
	 *            原逗号表达式 如 A,B
	 * @param newelement
	 *            新增元素 C
	 * @return A,B,C
	 */
	public static String comma_add(String commaexpress, String newelement) {
		return comma_rect(FilterNull(commaexpress) + "," + FilterNull(newelement));
	}

	/**
	 * 逗号表达式_删除
	 * 
	 * @param commaexpress
	 *            原逗号表达式 如 A,B,C
	 * @param delelement
	 *            删除元素 C,A
	 * @return B
	 */
	public static String comma_del(String commaexpress, String delelement) {
		if ((commaexpress == null) || (delelement == null) || (commaexpress.trim().equals(delelement.trim()))) {
			return "";
		}
		String[] deletelist = delelement.split(",");
		String result = commaexpress;
		for (String delstr : deletelist) {
			result = comma_delone(result, delstr);
		}
		return result;
	}

	/**
	 * 逗号表达式_单一删除
	 * 
	 * @param commaexpress
	 *            原逗号表达式 如 A,B,C
	 * @param delelement
	 *            删除元素 C
	 * @return A,B
	 */
	public static String comma_delone(String commaexpress, String delelement) {
		if ((commaexpress == null) || (delelement == null) || (commaexpress.trim().equals(delelement.trim()))) {
			return "";
		}
		String[] strlist = commaexpress.split(",");
		StringBuffer result = new StringBuffer();
		for (String str : strlist) {
			if ((!str.trim().equals(delelement.trim())) && (!"".equals(str.trim()))) {
				result.append(str.trim() + ",");
			}
		}
		return result.toString().substring(0, result.length() - 1 > 0 ? result.length() - 1 : 0);
	}

	/**
	 * 逗号表达式_判断是否包含元素
	 * 
	 * @param commaexpress
	 *            逗号表达式 A,B,C
	 * @param element
	 *            C
	 * @return true
	 */
	public static boolean comma_contains(String commaexpress, String element) {
		boolean flag = false;
		commaexpress = FilterNull(commaexpress);
		element = FilterNull(element);
		if (!"".equals(commaexpress) && !"".equals(element)) {
			String[] strlist = commaexpress.split(",");
			for (String str : strlist) {
				if (str.trim().equals(element.trim())) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 逗号表达式_取交集
	 * 
	 * @param commaexpressA
	 *            逗号表达式1 A,B,C
	 * @param commaexpressB
	 *            逗号表达式2 B,C,D
	 * @return B,C
	 */
	public static String comma_intersect(String commaexpressA, String commaexpressB) {
		commaexpressA = FilterNull(commaexpressA);
		commaexpressB = FilterNull(commaexpressB);
		StringBuffer result = new StringBuffer();
		String[] strlistA = commaexpressA.split(",");
		String[] strlistB = commaexpressB.split(",");
		for (String boA : strlistA) {
			for (String boB : strlistB) {
				if (boA.trim().equals(boB.trim())) {
					result.append(boA.trim() + ",");
				}
			}
		}
		return comma_rect(result.toString());
	}

	/**
	 * 逗号表达式_规范
	 * 
	 * @param commaexpress
	 *            逗号表达式 ,A,B,B,,C
	 * @return A,B,C
	 */
	public static String comma_rect(String commaexpress) {
		commaexpress = FilterNull(commaexpress);
		String[] strlist = commaexpress.split(",");
		StringBuffer result = new StringBuffer();
		for (String str : strlist) {
			if (!("".equals(str.trim())) && !("," + result.toString() + ",").contains("," + str + ",")
					&& !"null".equals(str)) {
				result.append(str.trim() + ",");
			}
		}
		return result.toString().substring(0, (result.length() - 1 > 0) ? result.length() - 1 : 0);
	}

	/**
	 * 逗号表达式_反转
	 * 
	 * @param commaexpress
	 *            A,B,C
	 * @return C,B,A
	 */
	public static String comma_reverse(String commaexpress) {
		commaexpress = FilterNull(commaexpress);
		String[] ids = commaexpress.split(",");
		StringBuffer str = new StringBuffer();
		for (int i = ids.length - 1; i >= 0; i--) {
			str.append(ids[i] + ",");
		}
		return comma_rect(str.toString());
	}

	/**
	 * 逗号表达式_获取首对象
	 * 
	 * @param commaexpress
	 *            A,B,C
	 * @return A
	 */
	public static String comma_first(String commaexpress) {
		commaexpress = FilterNull(commaexpress);
		String[] ids = commaexpress.split(",");
		// //System.out.println("length:" + ids.length);
		if ((ids != null) && (ids.length > 0)) {
			return ids[0];
		}
		return null;
	}

	/**
	 * 逗号表达式_获取尾对象
	 * 
	 * @param commaexpress
	 *            A,B,C
	 * @return C
	 */
	public static String comma_last(String commaexpress) {
		commaexpress = FilterNull(commaexpress);
		String[] ids = commaexpress.split(",");
		if ((ids != null) && (ids.length > 0)) {
			return ids[(ids.length - 1)];
		}
		return null;
	}

	/**
	 * 替换字符串,支持字符串为空的情形
	 * 
	 * @param strData
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replace(String strData, String regex, String replacement) {
		return strData == null ? "" : strData.replaceAll(regex, replacement);
	}

	/**
	 * 字符串转为HTML显示字符
	 * 
	 * @param strData
	 * @return
	 */
	public static String String2HTML(String strData) {
		if (strData == null || "".equals(strData)) {
			return "";
		}
		strData = replace(strData, "&", "&amp;");
		strData = replace(strData, "<", "&lt;");
		strData = replace(strData, ">", "&gt;");
		strData = replace(strData, "\"", "&quot;");
		return strData;
	}

	/**
	 * 把异常信息转换成字符串，以方便保存
	 * 
	 * @param e
	 * @return
	 */

	public static String getExceptionInfo(Exception e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			e.printStackTrace(new PrintStream(baos));
		} finally {
			try {
				baos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return baos.toString();
	}

	/**
	 * 过滤特殊符号
	 * 
	 * @param str
	 * @return
	 */
	public static String regex(String str) {
		Pattern pattern = Pattern.compile("[0-9-:/ ]");// 中文汉字编码区间
		Matcher matcher;
		char[] array = str.toCharArray();
		for (int i = 0; i < array.length; i++) {
			matcher = pattern.matcher(String.valueOf(array[i]));
			if (!matcher.matches()) {// 空格暂不替换
				str = str.replace(String.valueOf(array[i]), "");// 特殊字符用空字符串替换
			}
		}

		return str;
	}

	/**
	 * 向字符串(commaexpress)里在下标(index)处插入字符串(newelement)
	 * 
	 * @param commaexpress
	 * @param newelement
	 * @param index
	 * @return
	 */
	public static String comma_insert(String commaexpress, String newelement, int index) {
		int length = commaexpress.length();
		if (index > length) {
			index = length;
		} else if (index < 0) {
			index = 0;
		}
		String result = commaexpress.substring(0, index) + newelement
				+ commaexpress.substring(index, commaexpress.length());
		return result;
	}

	/**
	 * 将"/"替换成"\"
	 * 
	 * @param strDir
	 * @return
	 */
	public static String changeDirection(String strDir) {
		String s = "/";
		String a = "\\";
		if (strDir != null && !" ".equals(strDir)) {
			if (strDir.contains(s)) {
				strDir = strDir.replace(s, a);
			}
		}
		return strDir;
	}

	/**
	 * 去除字符串中 头和尾的空格，中间的空格保留
	 * 
	 */
	public static String trim(String s) {
		int i = s.length();// 字符串最后一个字符的位置
		int j = 0;// 字符串第一个字符
		int k = 0;// 中间变量
		char[] arrayOfChar = s.toCharArray();// 将字符串转换成字符数组
		while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
			++j;// 确定字符串前面的空格数
		while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
			--i;// 确定字符串后面的空格数
		return (((j > 0) || (i < s.length())) ? s.substring(j, i) : s);// 返回去除空格后的字符串
	}

	/**
	 * 得到大括号中的内容
	 * 
	 * @param str
	 * @return
	 */
	public static String getBrackets(String str) {
		int a = str.indexOf("{");
		int c = str.indexOf("}");
		if (a >= 0 && c >= 0 & c > a) {
			return (str.substring(a + 1, c));
		} else {
			return str;
		}
	}

	/**
	 * 将字符串中所有的，替换成|
	 * 
	 * @param str
	 * @return
	 */
	public static String commaToVerti(String str) {
		if (str != null && !"".equals(str) && str.contains(",")) {
			return str.replaceAll(",", "|");
		} else {
			return str;
		}
	}

	/**
	 * 去掉字符串中、前、后的空格
	 * 
	 * @param
	 * @throws IOException
	 */
	public static String extractBlank(String name) {
		if (name != null && !"".equals(name)) {
			return name.replaceAll(" +", "");
		} else {
			return name;
		}
	}

	/**
	 * 将null换成""
	 * 
	 * @param str
	 * @return
	 */
	public static String ConvertStr(String str) {
		return str != null && !"null".equals(str) ? str.trim() : "";
	}

	/**
	 * 验证整数（正整数和负整数）.
	 * 
	 * @param digit
	 *            一位或多位0-9之间的整数.
	 * @return 验证成功返回true，验证失败返回false.
	 */
	public static boolean checkDigit(String digit) {
		String regex = "\\-?[1-9]\\d+";
		return Pattern.matches(regex, digit);
	}

	/**
	 * 验证整数和浮点数（正负整数和正负浮点数）
	 * 
	 * @param decimals
	 *            一位或多位0-9之间的浮点数，如：1.23，233.30
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDecimals(String decimals) {
		String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
		return Pattern.matches(regex, decimals);
	}

	/**
	 * 验证空白字符.
	 * 
	 * @param blankSpace
	 *            . 空白字符，包括：空格、\t、\n、\r、\f、\x0B.
	 * @return 验证成功返回true，验证失败返回false.
	 */
	public static boolean checkBlankSpace(String blankSpace) {
		String regex = "\\s+";
		return Pattern.matches(regex, blankSpace);
	}

	/**
	 * 验证中文.
	 * 
	 * @param chinese
	 *            中文字符.
	 * @return 验证成功返回true，验证失败返回false.
	 */
	public static boolean checkChinese(String chinese) {
		String regex = "^[\u4E00-\u9FA5]+$";
		return Pattern.matches(regex, chinese);
	}

	/**
	 * 验证日期（年月日）.
	 * 
	 * @param birthday
	 *            日期，格式：1992-09-03，或1992.09.03.
	 * @return 验证成功返回true，验证失败返回false.
	 */
	public static boolean checkBirthday(String birthday) {
		String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
		return Pattern.matches(regex, birthday);
	}

	/**
	 * 验证URL地址
	 * 
	 * @param url
	 *            格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或
	 *            http://www.csdn.net:80
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkUrL(String url) {
		String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w" + "+(\\.[a-zA-Z]+)*(:\\d{1,5})?"
				+ "(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
		return Pattern.matches(regex, url);
	}

	/**
	 * 匹配中国邮政编码.
	 * 
	 * @param postcode
	 *            邮政编码.
	 * @return 验证成功返回true，验证失败返回false.
	 */
	public static boolean checkPostcode(String postcode) {
		String regex = "[1-9]\\d{5}";
		return Pattern.matches(regex, postcode);
	}

	/**
	 * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
	 * 
	 * @param ipAddress
	 *            IPv4标准地址
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIpAddress(String ipAddress) {
		String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))" + "\\.(0|([1-9](\\d{1,2})?))"
				+ "\\.(0|([1-9](\\d{1,2})?))";
		return Pattern.matches(regex, ipAddress);
	}

	/**
	 * json 转化为 字符串
	 * 
	 * @param json
	 * @return
	 */
	public static String dealJson2S(JSONObject json) {
		Set<String> set = json.keySet();
		StringBuilder pBuilder = new StringBuilder();
		Iterator<String> pp = set.iterator();
		while (pp.hasNext()) {
			String pk = pp.next();
			pBuilder.append(pk);
			pBuilder.append("=");
			try {
				pBuilder.append(json.get(pk.toString()).toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			pBuilder.append("&");
		}
		String kString = null;
		try {
			kString = pBuilder.substring(0, pBuilder.length() - 1).toString();
		} catch (Exception e) {
		}
		return kString;
	}

	/**
	 * json转化为ArrayList
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> dealJson2L(JSONObject json) {
		Set<String> set = json.keySet();
		Iterator<String> pp = set.iterator();
		@SuppressWarnings("rawtypes")
		List re = new ArrayList<String>();
		while (pp.hasNext()) {
			String pk = (String) pp.next();
			try {
				re.add(pk + "=" + json.getString(pk.toString()) + "&");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return re;
	}

	/**
	 * json 转化为TreeMap
	 * 
	 * @param json
	 * @return
	 */
	public static TreeMap<String, String> dealJson2TM(JSONObject json) {
		Set<String> set = json.keySet();
		Iterator<String> pp = set.iterator();
		TreeMap<String, String> re = new TreeMap<String, String>();
		while (pp.hasNext()) {
			String pk = (String) pp.next();
			try {
				re.put(pk, json.get(pk).toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return re;
	}

	/**
	 * json 转化为HashMap
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, String> dealJson2M(JSONObject json) {
		Set<String> pp = json.keySet();
		Map<String, String> re = new HashMap<String, String>();
		Iterator<String> iterator = pp.iterator();
		while (iterator.hasNext()) {
			String pk = iterator.next();
			try {
				re.put(pk, json.get(pk).toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return re;
	}

	/**
	 * 把map转成param1=a&param2=b..的键值对形式
	 * 
	 * @param params
	 * @return
	 */
	public static String mapToStringParams(Map<String, String> params) {
		String queryString = "";
		if (params != null) {
			for (String key : params.keySet()) {
				String value = params.get(key);
				queryString += key + "=" + value + "&";
			}
		}
		if (queryString.length() > 0) {
			queryString = queryString.substring(0, queryString.length() - 1);
		}
		return queryString;
	}

	/**
	 * 将请求参数还原为key=value的形式,for struts2
	 * 
	 * @param params
	 * @return
	 */
	public static String getParamString(Map<?, ?> params) {
		StringBuffer queryString = new StringBuffer(256);
		Iterator<?> it = params.keySet().iterator();
		int count = 0;
		while (it.hasNext()) {
			String key = (String) it.next();
			String[] param = (String[]) params.get(key);
			for (int i = 0; i < param.length; i++) {
				if (count == 0) {
					count++;
				} else {
					queryString.append("&");
				}
				queryString.append(key);
				queryString.append("=");
				try {
					queryString.append(URLEncoder.encode((String) param[i], "UTF-8"));
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		return queryString.toString();
	}

	/**
	 * 汉语中数字大写
	 */
	private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	/**
	 * 汉语中货币单位大写，这样的设计类似于占位符
	 */
	private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾",
			"佰", "仟", "兆", "拾", "佰", "仟" };
	/**
	 * 特殊字符：整
	 */
	private static final String CN_FULL = "整";
	/**
	 * 特殊字符：负
	 */
	private static final String CN_NEGATIVE = "负";
	/**
	 * 金额的精度，默认值为2
	 */
	private static final int MONEY_PRECISION = 2;
	/**
	 * 特殊字符：零元整
	 */
	private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

	/**
	 * 将以元为单位的钱转化为以分为单位（必须是整数）的钱
	 */
	public static String dealy2f(String yuan) {
		return yuan;
	}

	/**
	 * 将以分为单位的钱转化为以元为单位的钱
	 */
	public static String dealf2y(String fen) {
		return fen;

	}

	/**
	 * 最常用的数值过滤函数，将得到的数值以元的形式正确显示<br>
	 * 将得到的object转化为以元为单位的钱
	 */
	public static String dealo2y(Object ori) {
		return ori.toString();
	}

	/**
	 * 将得到的object转化为以分为单位的钱
	 */
	public static String dealo2f(Object ori) {
		return dealy2f(dealo2y(ori));
	}

	/**
	 * 把输入的金额转换为汉语中人民币的大写
	 * 
	 * @param ori
	 *            输入的金额
	 * @return 对应的汉语大写
	 */
	public static String number2CNMontrayUnit(String ori) {
		BigDecimal numberOfMoney = new BigDecimal(ori);
		StringBuffer sb = new StringBuffer();
		// -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
		// positive.
		int signum = numberOfMoney.signum();
		// 零元整的情况
		if (signum == 0) {
			return CN_ZEOR_FULL;
		}
		// 这里会进行金额的四舍五入
		long number = numberOfMoney.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();
		// 得到小数点后两位值
		long scale = number % 100;
		int numUnit = 0;
		int numIndex = 0;
		boolean getZero = false;
		// 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
		if (!(scale > 0)) {
			numIndex = 2;
			number = number / 100;
			getZero = true;
		}
		if ((scale > 0) && (!(scale % 10 > 0))) {
			numIndex = 1;
			number = number / 10;
			getZero = true;
		}
		int zeroSize = 0;
		while (true) {
			if (number <= 0) {
				break;
			}
			// 每次获取到最后一个数
			numUnit = (int) (number % 10);
			if (numUnit > 0) {
				if ((numIndex == 9) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
				}
				if ((numIndex == 13) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
				}
				sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				getZero = false;
				zeroSize = 0;
			} else {
				++zeroSize;
				if (!(getZero)) {
					sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				}
				if (numIndex == 2) {
					if (number > 0) {
						sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
					}
				} else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				}
				getZero = true;
			}
			// 让number每次都去掉最后一个数
			number = number / 10;
			++numIndex;
		}
		// 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
		if (signum == -1) {
			sb.insert(0, CN_NEGATIVE);
		}
		// 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
		if (!(scale > 0)) {
			sb.append(CN_FULL);
		}
		return sb.toString();
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };
	private static final char[] hexDigits2 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();
		for (byte aB : b) {
			resultSb.append(byteToHexString(aB));
		}
		return resultSb.toString();
	}

	/**
	 * 转换byte到16进制
	 * 
	 * @param b
	 *            要转换的byte
	 * @return 16进制格式
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * MD5编码
	 * 
	 * @param origin
	 *            原始字符串
	 * @return 经过MD5加密之后的结果
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = origin;
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	/**
	 * 文件的md5值
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getMd5ByFile(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	/**
	 * 生成二维码
	 * 
	 * @param paramString
	 * @return
	 */
	public static String hexdigest(String paramString) {
		try {
			String str = hexdigest(paramString.getBytes());
			return str;
		} catch (Exception localException) {
		}
		return null;
	}

	private static String hexdigest(byte[] paramArrayOfByte) {
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramArrayOfByte);
			byte[] arrayOfByte = localMessageDigest.digest();
			char[] arrayOfChar = new char[32];
			int i = 0;
			int j = 0;
			while (true) {
				if (i >= 16)
					return new String(arrayOfChar);
				int k = arrayOfByte[i];
				int m = j + 1;
				arrayOfChar[j] = hexDigits2[(0xF & k >>> 4)];
				j = m + 1;
				arrayOfChar[m] = hexDigits2[(k & 0xF)];
				i++;
			}
		} catch (Exception localException) {
		}
		return null;
	}

	private static String mYear;
	private static String mMonth;
	private static String mDay;
	private static String mHour;
	private static String mMinute;
	private static String mSecond;
	private static String mWay;

	public static final long DAYLONG = 86400000L;
	/**
	 * 默认的日历对象
	 */
	public static Calendar DEFAULTC = Calendar.getInstance();
	/**
	 * 缺省的日期格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	/**
	 * 缺省的日期时间格式
	 */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final int MATCHDAY = 1;// 每天
	public static final int MATCHWEEK = 2;// 每周
	public static final int MATCHMOUTH = 3;// 每月
	public static final int MATCHYEAR = 4;// 每年
	public static final int MATCHNERVER = 0;// 单次	public static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static final int MATCHMOUTHEND = 6;// 新增月末模式匹配
	public static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date sdf1Format(String s_date) throws ParseException {
		return sdf1.parse(s_date);
	}

	public static String sdf1Parse(Date date) {
		return sdf1.format(date);
	}

	public static String sdf2Parse2(Date date) {
		return sdf2.format(date);
	}

	public static Date sdf2Format(String s_date) throws ParseException {
		return sdf2.parse(s_date);
	}



	/**
	 * 按照模式进行天数的匹配
	 *
	 * @param mode
	 *            选择模式
	 * @param date
	 *            要匹配的日期
	 * @param init
	 *            原始日期
	 * @return
	 */
	public static boolean matchDateMode(int mode, Date date, Date init) {
		if (date == null || init == null) {
			return false;
		}
		if (mode == MATCHDAY) {
			return true;// 暂且返回
			// return false;
		}
		try {
			Calendar xxx = Calendar.getInstance();
			xxx.setTime(date);
			int week = xxx.get(Calendar.DAY_OF_WEEK);
			int day = xxx.get(Calendar.DAY_OF_MONTH);
			int mouth = xxx.get(Calendar.MONTH);
			Calendar yyy = Calendar.getInstance();
			yyy.setTime(init);
			switch (mode) {
			case MATCHWEEK:
				if (week == yyy.get(Calendar.DAY_OF_WEEK)) {
					return true;
				} else {
					return false;
				}
			case MATCHMOUTH:
				if (day == yyy.get(Calendar.DAY_OF_MONTH)) {
					return true;
				} else {
					return false;
				}
			case MATCHYEAR:
				if (day == yyy.get(Calendar.DAY_OF_MONTH) && mouth == yyy.get(Calendar.MONTH)) {
					return true;
				} else {
					return false;
				}
			case MATCHNERVER:
				return isSameDay(date, init);
			case MATCHMOUTHEND:
				return isMouthEnd(date);
			default:
				// //System.out.println("没有对应的匹配模式，请及早核对数据，模式应该在上述常量间" + mode);
			}

		} catch (Exception es) {
			es.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断是否是月末的这天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isMouthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 周的多选的模式匹配
	 * 
	 * @param mode
	 * @param date
	 * @param ks
	 *            多选项
	 * @return
	 */
	public static boolean matchDateMuti(int mode, Date date, int... ks) {
		if (date == null) {
			return false;
		}
		try {
			Calendar xxx = Calendar.getInstance();
			xxx.setTime(date);
			switch (mode) {
			case MATCHWEEK:
				for (int k : ks) {
					if (xxx.get(Calendar.DAY_OF_WEEK) == k) {
						return true;
					}
				}
				return false;
			case MATCHMOUTH:
				for (int k : ks) {
					if (xxx.get(Calendar.DAY_OF_MONTH) == k) {
						return true;
					}
				}
				return false;
			default:
				break;
			}
		} catch (Exception es) {
			es.printStackTrace();
		}
		return false;
	}

	/**
	 * 这个月有多少天
	 * 
	 * @param year
	 * @param mouth
	 * @return
	 */
	public static int getMouthDay(int year, int mouth) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.set(year, mouth, 1);
			return cal.getActualMaximum(Calendar.DATE);
		} catch (Exception es) {
			es.printStackTrace();
		}
		return -1;
	}

	/**
	 * 这天的月份有多少天
	 * 
	 * @param date
	 * @return
	 */
	public static int getMouthDay(Date date) {
		if (date == null) {
			return 0;
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.getActualMaximum(Calendar.DATE);
		} catch (Exception es) {
			es.printStackTrace();
		}
		return -1;
	}

	/**
	 * 这天的月前星期空的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMouthNull(Date date) {
		if (date == null) {
			return 0;
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int mouth = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			cal.set(year, mouth, 1);
			return cal.get(Calendar.DAY_OF_WEEK);
		} catch (Exception es) {
			es.printStackTrace();
		}
		return -1;
	}

	/**
	 * 这月的月前星期空的天数
	 * 
	 * @param year
	 * @param mouth
	 * @return
	 */
	public static int getMouthNull(int year, int mouth) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.set(year, mouth, 1);
			return cal.get(Calendar.DAY_OF_WEEK) - 1;
		} catch (Exception es) {
			es.printStackTrace();
		}
		return -1;
	}

	/**
	 * 当天的这月在日历上有几行
	 * 
	 * @param date
	 * @return
	 */
	public static int getMouthCol(Date date) {
		if (date == null) {
			return 0;
		}
		if (getMouthNull(date) + getMouthDay(date) > 35) {
			return 6;
		} else {
			return 5;
		}
	}

	/**
	 * 这月在日历上有几行
	 * 
	 * @param year
	 * @param mouth
	 * @return
	 */
	public static int getMouthCol(int year, int mouth) {
		if (getMouthNull(year, mouth) + getMouthDay(year, mouth) > 35) {
			return 6;
		} else {
			return 5;
		}
	}

	/**
	 * 判断两天是否是同一天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		try {
			Calendar xxx = Calendar.getInstance();
			xxx.setTime(date1);
			Calendar yyy = Calendar.getInstance();
			yyy.setTime(date2);
			if (xxx.get(Calendar.YEAR) != yyy.get(Calendar.YEAR)) {
				return false;
			}
			if (xxx.get(Calendar.DAY_OF_MONTH) != yyy.get(Calendar.DAY_OF_MONTH)) {
				return false;
			}
			if (xxx.get(Calendar.MONTH) != yyy.get(Calendar.MONTH)) {
				return false;
			}
			return true;
		} catch (Exception es) {
			es.printStackTrace();

		}
		return false;
	}

	/**
	 * 这天是否在起止日期中间
	 * 
	 * @param startTime
	 * @param date
	 * @param endTime
	 * @return
	 */
	public static boolean betw(Date startTime, Date date, Date endTime) {
		if (date == null || startTime == null) {
			return false;
		}
		date = reSetDate(date);
		if (date.getTime() + 86400000 - 1 < startTime.getTime()) {
			return false;
		}
		if (endTime != null && date.getTime() > endTime.getTime()) {
			return false;
		}
		return true;
	}

	/**
	 * 重置日期，使时间回到00:00:00这一刻。
	 *
	 * @param date
	 * @return
	 */
	public static Date reSetDate(Date date) {
		long kkk = (date.getTime() / 3600000) * 3600000;
		Calendar k = Calendar.getInstance();
		k.setTime(new Date(kkk));
		k.set(Calendar.HOUR_OF_DAY, 0);
		return k.getTime();
	}

	/**
	 * 这天是否在起始时间之后
	 * 
	 * @param startTime
	 * @param date
	 * @return
	 */
	public static boolean dateAfter(Date startTime, Date date) {
		if (date == null || startTime == null) {
			return false;
		}
		date = reSetDate(date);
		if (date.getTime() - 1000 < startTime.getTime()) {
			return true;
		}
		return false;
	}

	public static String stringData() {
		DEFAULTC.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		mYear = String.valueOf(DEFAULTC.get(Calendar.YEAR)); // 获取当前年份
		mMonth = String.valueOf(DEFAULTC.get(Calendar.MONTH) + 1);// 获取当前月份
		mDay = String.valueOf(DEFAULTC.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
		return mYear + "-" + mMonth + "-" + mDay;
	}

	/**
	 * 获取当前时间的时分秒.
	 * 
	 * @return 08-16-25.
	 */
	public static String getDate() {
		mHour = String.valueOf(DEFAULTC.get(Calendar.HOUR_OF_DAY));// 获取当天的时间
		mMinute = String.valueOf(DEFAULTC.get(Calendar.MINUTE));// 获取分钟
		mSecond = String.valueOf(DEFAULTC.get(Calendar.SECOND));// 获取秒数
		if (mMinute.length() == 1) {
			return " " + mHour + ":0" + mMinute + ":" + mSecond;
		} else if (mSecond.length() == 1) {
			return " " + mHour + ":" + mMinute + ":0" + mSecond;
		} else {
			return " " + mHour + ":" + mMinute + ":" + mSecond;
		}
	}

	/**
	 * 返回时间的星期几的字符串.
	 * 
	 * @return 星期三.
	 */
	public static String stringWeek() {
		mWay = String.valueOf(DEFAULTC.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "天";
		} else if ("2".equals(mWay)) {
			mWay = "一";
		} else if ("3".equals(mWay)) {
			mWay = "二";
		} else if ("4".equals(mWay)) {
			mWay = "三";
		} else if ("5".equals(mWay)) {
			mWay = "四";
		} else if ("6".equals(mWay)) {
			mWay = "五";
		} else if ("7".equals(mWay)) {
			mWay = "六";
		}
		return "星期" + mWay;
	}

	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return .
	 */
	public static String getCurrentTime() {
		return sdf2.format(new Date());
	}

	/**
	 * 获取当天日期 yyyy-MM-dd
	 * 
	 * @return .
	 */
	public static String getCurrentDate() {
		return sdf1.format(new Date());
	}

	/**
	 * 为拍完照照片命名 yyyy-MM-dd HH:mm:ss.png
	 * 
	 * @return
	 */
	public static String getCurrentTimeForImage() {
		return getCurrentTime() + ".png";
	}

	/**
	 * 封装json格式的响应
	 * 
	 * @param response
	 * @param json
	 */
	public static void resJson(HttpServletResponse response, JSONObject json) {
		try {
			byte[] jsonBytes = json.toString().getBytes("UTF-8");
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentLength(jsonBytes.length);
			response.getOutputStream().write(jsonBytes);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成会员卡号(随机6位不重复数组) 这是典型的随机洗牌算法。
	 * 流程是从备选数组中选择一个放入目标数组中，将选取的数组从备选数组移除（放至最后，并缩小选择区域）
	 *
	 */
	public static String generateNumber() {
		String no = "";
		// 6位
		int length = 6;
		// 初始化备选数组---[0,1,2,3,4,5,6,7,8,9]
		int[] defaultNums = new int[10];
		for (int i = 0; i < defaultNums.length; i++) {
			defaultNums[i] = i;
		}
		Random random = new Random();
		int[] nums = new int[length];
		// 默认数组中可以选择的部分长度
		int canBeUsed = 10;
		// 填充目标数组
		for (int i = 0; i < nums.length; i++) {
			// 将随机选取的数字存入目标数组
			int index = random.nextInt(canBeUsed);
			nums[i] = defaultNums[index];
			// 将已用过的数字扔到备选数组最后，并减小可选区域
			swap(index, canBeUsed - 1, defaultNums);
			canBeUsed--;
		}
		if (nums.length > 0) {
			for (int i = 0; i < nums.length; i++) {
				no += nums[i];
			}
		}
		return "jly" + no;
	}

	public static String getUserNO() {
		String no = "";
		// 6位
		int length = 8;
		// 初始化备选数组---[0,1,2,3,4,5,6,7,8,9]
		int[] defaultNums = new int[10];
		for (int i = 0; i < defaultNums.length; i++) {
			defaultNums[i] = i;
		}
		Random random = new Random();
		int[] nums = new int[length];
		// 默认数组中可以选择的部分长度
		int canBeUsed = 10;
		// 填充目标数组
		for (int i = 0; i < nums.length; i++) {
			// 将随机选取的数字存入目标数组
			int index = random.nextInt(canBeUsed);
			nums[i] = defaultNums[index];
			// 将已用过的数字扔到备选数组最后，并减小可选区域
			swap(index, canBeUsed - 1, defaultNums);
			canBeUsed--;
		}
		if (nums.length > 0) {
			for (int i = 0; i < nums.length; i++) {
				no += nums[i];
			}
		}
		return "10" + no;
	}

	private static void swap(int i, int j, int[] nums) {
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}

	public static String generateNumber2() {
		String no = "";
		int num[] = new int[6];
		int c = 0;
		for (int i = 0; i < num.length; i++) {
			num[i] = new Random().nextInt(10);
			c = num[i];
			for (int j = 0; j < i; j++) {
				if (num[j] == c) {
					i--;
					break;
				}
			}
		}
		if (num.length > 0) {
			for (int i = 0; i < num.length; i++) {
				no += num[i];
			}
		}
		return no;
	}

	/**
	 * 生成N位随机数
	 * 
	 * @param n
	 * @return
	 */
	public static long generateRandomNumber(int n) {

		if (n < 1) {
			throw new IllegalArgumentException("随机数位数必须大于0");
		}
		return (long) (Math.random() * 9 * Math.pow(10, n - 1)) + (long) Math.pow(10, n - 1);
	}

	/**
	 * 生成当前电脑ip地址
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getIpAddr(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 生成随机数
	 * 
	 * @param bits
	 * @return
	 */
	public static String produceUID(int bits) {
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < bits; i++) {
			sb.append((int) (Math.random() * 10));
		}
		String tmp = sb.toString();
		if (list.contains(tmp)) {
			produceUID(bits);
		} else {
			list.add(tmp);
			return tmp;
		}
		return "-1";
	}

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	/**
	 * 生成N位随机数,字母大小写,数字0-9
	 * 
	 * @param count
	 * @return
	 */
	public static String generateShortUUID(int count) {
		long s= new Date().getTime()/1000;
		Random random = new Random();
		int[] nums = new int[6];
		String numCode ="";
		char num;
		for(int i = 0 ; i < 6 ; i++){
			do{
				nums[i] = random.nextInt(123);
				if(nums[i] >= 0 && nums[i] <= 9){
//					System.out.print(nums[i]+"1");
					numCode = numCode + String.valueOf(nums[i]);
					break;
				}else if(nums[i] >= 65 && nums[i] <= 90){
					num = (char)nums[i];
//					System.out.print(num +"2");
					numCode = numCode+ String.valueOf(num);
					break;
				}else if(nums[i] >= 97 && nums[i] <= 122){
					num = (char)nums[i];
					numCode = numCode+ String.valueOf(num);
//					System.out.print(num+"3");
					break;
				}
			}while(true);
		}
		return s+numCode;
	}

	public static boolean checkJTlogin(String code, String pwd) {
		if (code.length() != pwd.length()) {
			return false;
		}
		List<String> list = new ArrayList<String>();
		int length = pwd.length() / 4;
		for (int i = 0; i < length; i++) {
			list.add(pwd.substring(i * 4, (i + 1) * 4));
		}
		for (int i = 0; i < length; i++) {
			if (list.contains(code.substring(i * 4, (i + 1) * 4))) {
				list.remove(list.indexOf(code.substring(i * 4, (i + 1) * 4)));
			}
		}
		if (!list.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 拆分json为两个字符串 ，示例 {"lens_brand":"1","lens_model":"2"}
	 * ---"lens_brand,lens_model","1,2"
	 * 
	 * @param json
	 */
	public static String splitJson(JSONObject json) {
		String str1 = "";
		String str2 = "";
		Set<String> set = json.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object val = json.get(key);
			if (isEmpty(val)) {
				continue;
			}
			str1 += key + ",";
			str2 += "'" + val.toString() + "',";
		}
		str1 = str1.substring(0, str1.length() - 1);
		str2 = str2.substring(0, str2.length() - 1);
		return str1 + "#" + str2;
	}

	/**
	 * 拆分json为两个字符串 ，示例 {"lens_brand":"1","lens_model":"2"}
	 * ---"lens_brand,lens_model","1,2"
	 * 
	 * @param json
	 *            如果val是空的，就跳过
	 */
	public static String splitJson1(JSONObject json) {
		String str1 = "";
		String str2 = "";
		Set<String> set = json.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object val = json.get(key);
			if (isEmpty(val)) {
				continue;
			}
			str1 += key + ",";
			str2 += "'" + val.toString() + "',";
		}
		str1 = str1.substring(0, str1.length() - 1);
		str2 = str2.substring(0, str2.length() - 1);
		return str1 + "#" + str2;
	}
	
	
	
	/**
	 * 获取两字符串的相似度
	 * @param str
	 * @param target
	 * @return
	 */
	public static float getSimilarityRatio(String str, String target) {
		return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
	};
	private static int compare(String str, String target) {
		int d[][]; // 矩阵
		int n = str.length();
		int m = target.length();
		int i; // 遍历str的
		int j; // 遍历target的
		char ch1; // str的
		char ch2; // target的
		int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];
		for (i = 0; i <= n; i++) { // 初始化第一列
			d[i][0] = i;
		}
		for (j = 0; j <= m; j++) { // 初始化第一行
			d[0][j] = j;
		}
		for (i = 1; i <= n; i++) { // 遍历str
			ch1 = str.charAt(i - 1);
			// 去匹配target
			for (j = 1; j <= m; j++) {
				ch2 = target.charAt(j - 1);
				if (ch1 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}
				// 左边+1,上边+1, 左上角+temp取最小
				d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
			}
		}
		return d[n][m];
	}
	private static int min(int one, int two, int three) {
		return (one = one < two ? one : two) < three ? one : three;
	}
	/**
	    * 
	    * String转map
	    * @param str
	    * @return
	    */
   public static Map<String,String> getStringToMap(String str){
       //根据逗号截取字符串数组
	   String[] str1 = str.split(",");
	   //创建Map对象
	   Map<String,String> map = new HashMap<>();
	   //循环加入map集合
	   for (int i = 0; i < str1.length; i++) {
	       //根据":"截取字符串数组
			   String[] str2 = str1[i].split(":");
			   //str2[0]为KEZY,str2[1]为值
	           map.put(str2[0],str2[1]);
       }
       return map;
   }
	
   public static String getIpAddrReal(HttpServletRequest request) {
       String ipAddress = null;
       try {
           ipAddress = request.getHeader("x-forwarded-for");
           if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
               ipAddress = request.getHeader("Proxy-Client-IP");
           }
           if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
               ipAddress = request.getHeader("WL-Proxy-Client-IP");
           }
           if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
               ipAddress = request.getRemoteAddr();
               if (ipAddress.equals("127.0.0.1")) {
                   // 根据网卡取本机配置的IP
                   InetAddress inet = null;
                   try {
                       inet = InetAddress.getLocalHost();
                   } catch (UnknownHostException e) {
                       e.printStackTrace();
                   }
                   ipAddress = inet.getHostAddress();
               }
           }
           // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
           if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                                                               // = 15
               if (ipAddress.indexOf(",") > 0) {
                   ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
               }
           }
       } catch (Exception e) {
           ipAddress="";
       }
       // ipAddress = this.getRequest().getRemoteAddr();
       
       return ipAddress;
   }
   
   
	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("key", "1");
		json.put("key", "2");
		json.put("date", new Date());
		json.toString();
		String s = produceUID(3);
		System.out.println(s);
		
		
	}

	public static BigDecimal fenToYuan(Integer fen){
		return new BigDecimal(fen).divide(new BigDecimal(100));
	}



}
