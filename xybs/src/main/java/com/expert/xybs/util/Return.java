package com.expert.xybs.util;

import com.alibaba.fastjson.JSONArray;

public class Return {
	private Object Data;
	private String msg;
	private Integer code;
	private Integer maxPage;
	
	
	public Object getData() {
		return Data;
	}
	public String getMsg() {
		return msg;
	}
	public Integer getCode() {
		return code;
	}
	public void setData(Object data) {
		Data = data;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
	}
	public void set_200(String msg,Object data,Integer maxPage) {
		this.Data = data;
		this.msg = msg;
		this.maxPage = maxPage;
		this.code = 200;
	}
	public void set_201(String msg) {
		this.Data = new JSONArray();
		this.msg = msg;
		this.code = 201;
	}
	public void set_400(String msg) {
		this.Data = new JSONArray();
		this.msg = msg;
		this.code = 400;
	}
	@Override
	public String toString() {
		return "Return [Data=" + Data + ", msg=" + msg + ", code=" + code + ", maxPage=" + maxPage + "]";
	}
	
	
}
