package com.hp.core.webjars.dal.model;

import javax.persistence.Id;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018-08-06
 */
public class SysMenu extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@Id
	private Integer id;

	/**
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 菜单地址
	 */
	private String menuUrl;

	/**
	 * 父菜单ID
	 */
	private Integer parentMenuId;

	/**
	 * 排序号
	 */
	private Integer sortNumber;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 菜单类型（1-根节点；2-子结点；3-按钮）
	 */
	private Integer menuType;

	/**
	 * 按钮的ID名称
	 */
	private String buttonId;

	/**
	 * 菜单图标
	 */
	private String iconName;

	/**
	 * 菜单指向
	 */
	private String target;

	/**
	 * 额外参数
	 */
	private String extraUrl;

	/**
	 * 创建时间
	 */
	private Integer createTime;

	/**
	 * 更新时间
	 */
	private Integer updateTime;

	/**
	 * 创建者
	 */
	private Integer createUserId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Integer getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Integer parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getExtraUrl() {
		return extraUrl;
	}

	public void setExtraUrl(String extraUrl) {
		this.extraUrl = extraUrl;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

}
