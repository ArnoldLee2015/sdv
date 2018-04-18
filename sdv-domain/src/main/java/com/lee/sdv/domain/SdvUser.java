/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.domain;

import com.lee.sdv.domain.base.BaseDomain;

import java.util.Date;

/**
 * sdv_user 数据库表名为sdv_user
 * 
 * @author lipeng
 */
public class SdvUser extends BaseDomain {
	private static final long serialVersionUID = 1L;
	/** 系统生成开始,请勿修改,重新生成会覆盖 */
	/**
	 * 用户名称
	 */
	private String name;
	/**
	 * 微信账号
	 */
	private String wechatAccount;
	/**
	 * 微信用户唯一标识
	 */
	private String openId;
	/**
	 * 用户在开放平台的唯一标识符
	 */
	private String unionId;
	/**
	 * 会话密钥
	 */
	private String sessionKey;
	/**
	 * 系统会话秘钥
	 */
	private String localSessionKey;
	/**
	 * 系统会话秘钥过期时间
	 */
	private Date expireDate;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 用户手机
	 */
	private String mobile;
	/**
	 * 授权令牌
	 */
	private String accessToken;
	/**
	 * 刷新授权令牌
	 */
	private String refreshToken;
	/**
	 * 有效时间
	 */
	private String expiresIn;
	/**
	 * 是否删除，1删除，0未删除
	 */
	private Integer isDelete = 0;
	/**
	 * 创建人
	 */
	private Long createId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人
	 */
	private Long updateId;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 微信头像URL
	 */
	private String headimgurl;
	public SdvUser() {
		// 默认无参构造方法
	}

	/**
	 * 获取用户名称 name
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置用户名称 name
	 *
	 * @param name
	 *            用户名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取微信账号 wechatAccount
	 *
	 * @return
	 */
	public String getWechatAccount() {
		return wechatAccount;
	}

	/**
	 * 设置微信账号 wechatAccount
	 *
	 * @param wechatAccount
	 *            微信账号
	 */
	public void setWechatAccount(String wechatAccount) {
		this.wechatAccount = wechatAccount;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getLocalSessionKey() {
		return localSessionKey;
	}

	public void setLocalSessionKey(String localSessionKey) {
		this.localSessionKey = localSessionKey;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * 获取是否删除，1删除，0未删除 isDelete
	 *
	 * @return
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * 设置是否删除，1删除，0未删除 isDelete
	 *
	 * @param isDelete
	 *            是否删除，1删除，0未删除
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * 获取创建人 createId
	 *
	 * @return
	 */
	public Long getCreateId() {
		return createId;
	}

	/**
	 * 设置创建人 createId
	 *
	 * @param createId
	 *            创建人
	 */
	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	/**
	 * 获取创建时间 createTime
	 *
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间 createTime
	 *
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取修改人 updateId
	 *
	 * @return
	 */
	public Long getUpdateId() {
		return updateId;
	}

	/**
	 * 设置修改人 updateId
	 *
	 * @param updateId
	 *            修改人
	 */
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	/**
	 * 获取修改时间 updateTime
	 *
	 * @return
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置修改时间 updateTime
	 *
	 * @param updateTime
	 *            修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	/** 系统生成结束,请勿修改,重新生成会覆盖 */

	/** 自定义开始start */

	/** 自定义结束end */
}