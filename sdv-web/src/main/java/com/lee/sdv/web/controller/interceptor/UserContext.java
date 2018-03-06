package com.lee.sdv.web.controller.interceptor;

public class UserContext {
	private final static ThreadLocal<UserContext> user = new ThreadLocal<UserContext>();

	/**
	 * 用户ID
	 */
	private long id;
	/**
	 * 用户账号
	 */
	private String LocalSessionKey;
	/**
	 * 用户昵称
	 */
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocalSessionKey() {
		return LocalSessionKey;
	}

	public void setLocalSessionKey(String localSessionKey) {
		LocalSessionKey = localSessionKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ThreadLocal<UserContext> getUser() {
		return user;
	}

	/**
	 * 实际上是将userContext放到了actionContext中
	 *
	 * @param userContext
	 *            对象
	 */
	public static void setUserContext(UserContext userContext) {
		user.set(userContext);
	}

	/**
	 * 取出登录的上下文
	 *
	 * @return null 如果没有的话
	 */
	public static UserContext getUserContext() {
		return user.get();
	}

	/**
	 * 删除上下文、其实一般不用删除
	 */
	public static void remove() {
		user.remove();
	}

}
