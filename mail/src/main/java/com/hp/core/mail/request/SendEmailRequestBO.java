/**
 * 
 */
package com.hp.core.mail.request;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * @author huangping Apr 24, 2020
 */
public class SendEmailRequestBO {

	private String from;
	private String fromName;
	private String[] to;
	private String subject;
	private String text;
	private boolean html;
	
	private List<MailFileBO> mailFileList;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String[] getTo() {
		return to;
	}
	public void setTo(String... to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isHtml() {
		return html;
	}
	public void setHtml(boolean html) {
		this.html = html;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	public List<MailFileBO> getMailFileList() {
		return mailFileList;
	}
	public void setMailFileList(List<MailFileBO> mailFileList) {
		this.mailFileList = mailFileList;
	}
	public void addMailFile(MailFileBO mailFile) {
		if (this.mailFileList == null) {
			this.mailFileList = new ArrayList<>();
		}
		this.mailFileList.add(mailFile);
	}
	
}
