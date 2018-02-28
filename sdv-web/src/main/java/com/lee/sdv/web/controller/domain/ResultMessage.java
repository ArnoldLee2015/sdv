package com.lee.sdv.web.controller.domain;

/**
 * 结果对象
 * 
 * @author 供应商管理
 */
public class ResultMessage<T> {
	private String code;// 编码
	private String message;// 结果
	private T data;// 数据

	public ResultMessage() {
		// 默认构造方法
	}

	public ResultMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> ResultMessage<T> create(String code, String message) {
		return new ResultMessage<T>(code, message);
	}

	public static <T> ResultMessage<T> success() {
		return success("操作成功!");
	}

	public static <T> ResultMessage<T> success(String msg) {
		return create("success", msg);
	}

	public static <T> ResultMessage<T> failure() {
		return failure("操作失败!");
	}

	public static <T> ResultMessage<T> failure(String msg) {
		return create("failure", msg);
	}

	public static <T> ResultMessage<T> failure(Exception ex) {
		if (ex == null) {
			return failure();
		}
		return failure("系统异常:" + ex.getMessage());
	}
}
