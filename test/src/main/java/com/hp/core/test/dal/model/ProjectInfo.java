package com.hp.core.test.dal.model;

import javax.persistence.Id;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018-05-31
 */
public class ProjectInfo extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	private Integer id;

	/**
	 * 项目名称
	 */
	private Integer projectName;

	/**
	 * 项目类型（1-环评；2-自查；3-应急预案；4-验收）
	 */
	private Integer projectType;

	/**
	 * 企业名称
	 */
	private Integer enterpriseName;

	/**
	 * 企业联系人
	 */
	private Integer enterpriseContacts;

	/**
	 * 企业联系人电话
	 */
	private Integer enterpriseMobile;

	/**
	 * 所在镇
	 */
	private Integer regionId;

	/**
	 * 所在地
	 */
	private Integer address;

	/**
	 * 合同签订时间
	 */
	private Integer contractSignDate;

	/**
	 * 合同签订人
	 */
	private Integer contractSignUserId;

	/**
	 * 项目来源（0-公司；其他值就是员工id）
	 */
	private Integer projectSource;

	/**
	 * 合同金额（元）
	 */
	private Integer contractMoney;

	/**
	 * 检测费用
	 */
	private Integer testingFee;

	/**
	 * 专家费用
	 */
	private Integer expertFee;

	/**
	 * 预付款
	 */
	private Integer advanceFee;

	/**
	 * 预付款时间
	 */
	private Integer advanceDate;

	/**
	 * 尾款
	 */
	private Integer retainageFee;

	/**
	 * 尾款支付时间
	 */
	private Integer retainageDate;

	/**
	 * 开发票时间
	 */
	private Integer invoiceDate;

	/**
	 * 发票快递单号
	 */
	private Integer invoiceExpressCode;

	/**
	 * 项目分配状态（0-未分配；1-已分配）
	 */
	private Integer projectAssignStatus;

	/**
	 * 项目进展状态（1-联系企业；2-现场踏勘；3-资料搜集；4-报告编制；5-项目检测；6-企业确认；7-项目审核；8-内部公示；9-项目送审；10-审批公示；11-审批完成；12-报告签收；13-项目延迟；14-项目完成）
	 */
	private Integer projectProgressStatus;

	/**
	 * 项目编制人
	 */
	private Integer projectDrawUserId;

	/**
	 * 项目分配时间
	 */
	private Integer projectAssignDate;

	/**
	 * 计划完成时间
	 */
	private Integer planCompleteDate;

	/**
	 * 项目完成时间
	 */
	private Integer projectCompleteDate;

	/**
	 * 合同备注
	 */
	private Integer agreementRemark;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 创建者
	 */
	private Integer createUserId;

	/**
	 * 创建时间
	 */
	private Integer createTime;

	/**
	 * 更新时间
	 */
	private Integer updateTime;

	/**
	 * 结算状态（0-未结算；1-已结算）
	 */
	private Integer settlementStatus;

	/**
	 * 结算时间
	 */
	private Integer settlementDate;

	/**
	 * 结算金额
	 */
	private Integer settlementFee;

	/**
	 * 检测方案
	 */
	private Integer checkPlan;

	/**
	 * 检测方案备注
	 */
	private Integer checkPlanRemark;

	/**
	 * 项目进度备注
	 */
	private Integer projectProgressRemark;

	/**
	 * 合同签订公司（1-南京国环；2-海通国环）
	 */
	private Integer contractSignCompany;

	/**
	 * 审核人员
	 */
	private Integer verifyUserId;

	/**
	 * 审核文档
	 */
	private Integer verifyFile;

	/**
	 * 归档文件
	 */
	private Integer completeFile;

	/**
	 * 归档文件编号
	 */
	private Integer completeFileCode;

	/**
	 * 原辅料
	 */
	private Integer accessories;

	/**
	 * 产品
	 */
	private Integer product;

	/**
	 * 是否资质发放（0-未发放；1-已发放）
	 */
	private Integer isQualificationsGrant;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectName() {
		return projectName;
	}

	public void setProjectName(Integer projectName) {
		this.projectName = projectName;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public Integer getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(Integer enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public Integer getEnterpriseContacts() {
		return enterpriseContacts;
	}

	public void setEnterpriseContacts(Integer enterpriseContacts) {
		this.enterpriseContacts = enterpriseContacts;
	}

	public Integer getEnterpriseMobile() {
		return enterpriseMobile;
	}

	public void setEnterpriseMobile(Integer enterpriseMobile) {
		this.enterpriseMobile = enterpriseMobile;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getAddress() {
		return address;
	}

	public void setAddress(Integer address) {
		this.address = address;
	}

	public Integer getContractSignDate() {
		return contractSignDate;
	}

	public void setContractSignDate(Integer contractSignDate) {
		this.contractSignDate = contractSignDate;
	}

	public Integer getContractSignUserId() {
		return contractSignUserId;
	}

	public void setContractSignUserId(Integer contractSignUserId) {
		this.contractSignUserId = contractSignUserId;
	}

	public Integer getProjectSource() {
		return projectSource;
	}

	public void setProjectSource(Integer projectSource) {
		this.projectSource = projectSource;
	}

	public Integer getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(Integer contractMoney) {
		this.contractMoney = contractMoney;
	}

	public Integer getTestingFee() {
		return testingFee;
	}

	public void setTestingFee(Integer testingFee) {
		this.testingFee = testingFee;
	}

	public Integer getExpertFee() {
		return expertFee;
	}

	public void setExpertFee(Integer expertFee) {
		this.expertFee = expertFee;
	}

	public Integer getAdvanceFee() {
		return advanceFee;
	}

	public void setAdvanceFee(Integer advanceFee) {
		this.advanceFee = advanceFee;
	}

	public Integer getAdvanceDate() {
		return advanceDate;
	}

	public void setAdvanceDate(Integer advanceDate) {
		this.advanceDate = advanceDate;
	}

	public Integer getRetainageFee() {
		return retainageFee;
	}

	public void setRetainageFee(Integer retainageFee) {
		this.retainageFee = retainageFee;
	}

	public Integer getRetainageDate() {
		return retainageDate;
	}

	public void setRetainageDate(Integer retainageDate) {
		this.retainageDate = retainageDate;
	}

	public Integer getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Integer invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Integer getInvoiceExpressCode() {
		return invoiceExpressCode;
	}

	public void setInvoiceExpressCode(Integer invoiceExpressCode) {
		this.invoiceExpressCode = invoiceExpressCode;
	}

	public Integer getProjectAssignStatus() {
		return projectAssignStatus;
	}

	public void setProjectAssignStatus(Integer projectAssignStatus) {
		this.projectAssignStatus = projectAssignStatus;
	}

	public Integer getProjectProgressStatus() {
		return projectProgressStatus;
	}

	public void setProjectProgressStatus(Integer projectProgressStatus) {
		this.projectProgressStatus = projectProgressStatus;
	}

	public Integer getProjectDrawUserId() {
		return projectDrawUserId;
	}

	public void setProjectDrawUserId(Integer projectDrawUserId) {
		this.projectDrawUserId = projectDrawUserId;
	}

	public Integer getProjectAssignDate() {
		return projectAssignDate;
	}

	public void setProjectAssignDate(Integer projectAssignDate) {
		this.projectAssignDate = projectAssignDate;
	}

	public Integer getPlanCompleteDate() {
		return planCompleteDate;
	}

	public void setPlanCompleteDate(Integer planCompleteDate) {
		this.planCompleteDate = planCompleteDate;
	}

	public Integer getProjectCompleteDate() {
		return projectCompleteDate;
	}

	public void setProjectCompleteDate(Integer projectCompleteDate) {
		this.projectCompleteDate = projectCompleteDate;
	}

	public Integer getAgreementRemark() {
		return agreementRemark;
	}

	public void setAgreementRemark(Integer agreementRemark) {
		this.agreementRemark = agreementRemark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
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

	public Integer getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(Integer settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public Integer getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Integer settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Integer getSettlementFee() {
		return settlementFee;
	}

	public void setSettlementFee(Integer settlementFee) {
		this.settlementFee = settlementFee;
	}

	public Integer getCheckPlan() {
		return checkPlan;
	}

	public void setCheckPlan(Integer checkPlan) {
		this.checkPlan = checkPlan;
	}

	public Integer getCheckPlanRemark() {
		return checkPlanRemark;
	}

	public void setCheckPlanRemark(Integer checkPlanRemark) {
		this.checkPlanRemark = checkPlanRemark;
	}

	public Integer getProjectProgressRemark() {
		return projectProgressRemark;
	}

	public void setProjectProgressRemark(Integer projectProgressRemark) {
		this.projectProgressRemark = projectProgressRemark;
	}

	public Integer getContractSignCompany() {
		return contractSignCompany;
	}

	public void setContractSignCompany(Integer contractSignCompany) {
		this.contractSignCompany = contractSignCompany;
	}

	public Integer getVerifyUserId() {
		return verifyUserId;
	}

	public void setVerifyUserId(Integer verifyUserId) {
		this.verifyUserId = verifyUserId;
	}

	public Integer getVerifyFile() {
		return verifyFile;
	}

	public void setVerifyFile(Integer verifyFile) {
		this.verifyFile = verifyFile;
	}

	public Integer getCompleteFile() {
		return completeFile;
	}

	public void setCompleteFile(Integer completeFile) {
		this.completeFile = completeFile;
	}

	public Integer getCompleteFileCode() {
		return completeFileCode;
	}

	public void setCompleteFileCode(Integer completeFileCode) {
		this.completeFileCode = completeFileCode;
	}

	public Integer getAccessories() {
		return accessories;
	}

	public void setAccessories(Integer accessories) {
		this.accessories = accessories;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	public Integer getIsQualificationsGrant() {
		return isQualificationsGrant;
	}

	public void setIsQualificationsGrant(Integer isQualificationsGrant) {
		this.isQualificationsGrant = isQualificationsGrant;
	}

}
