package com.gslab.parivahan.notification.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gslab.parivahan.notification.util.Buildable;
import com.gslab.parivahan.notification.util.EmailBuilder;

public class GslabEmail implements Buildable<EmailBuilder> {

	private String from;
	private String subject;
	private String body;
	private String template;
	private Map<String, Object> templateContent;
	private List<String> to = new ArrayList<>();
	private List<String> cc = new ArrayList<>();
	private List<String> bcc = new ArrayList<>();
	private Map<byte[], String> fileMap = new HashMap<>();
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public Map<String, Object> getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(Map<String, Object> templateContent) {
		this.templateContent = templateContent;
	}
	public List<String> getTo() {
		return to;
	}
	public void setTo(List<String> to) {
		this.to = to;
	}
	public List<String> getCc() {
		return cc;
	}
	public void setCc(List<String> cc) {
		this.cc = cc;
	}
	public List<String> getBcc() {
		return bcc;
	}
	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}
	public Map<byte[], String> getFileMap() {
		return fileMap;
	}
	public void setFileMap(Map<byte[], String> fileMap) {
		this.fileMap = fileMap;
	}
	@Override
	public String toString() {
		return "Email [from=" + from + ", subject=" + subject + ", body=" + body + ", template=" + template
				+ ", templateContent=" + templateContent + ", to=" + to + ", cc=" + cc + ", bcc=" + bcc + ", fileMap="
				+ fileMap + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bcc == null) ? 0 : bcc.hashCode());
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((cc == null) ? 0 : cc.hashCode());
		result = prime * result + ((fileMap == null) ? 0 : fileMap.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((template == null) ? 0 : template.hashCode());
		result = prime * result + ((templateContent == null) ? 0 : templateContent.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}
	@Override
	public EmailBuilder toBuilder() {
		return new EmailConcreteBuilder();
	}
	
	public static EmailBuilder builder() {
		return new EmailConcreteBuilder();
	}
	
	public static class EmailConcreteBuilder implements EmailBuilder{
		private GslabEmail email;
		
		
		EmailConcreteBuilder() {
			this(new GslabEmail());
		}
		
		public EmailConcreteBuilder(GslabEmail email) {
			this.email = email;
		}
		
		@Override
		public GslabEmail build() {
			return email;
		}

		@Override
		public EmailBuilder from(GslabEmail in) {
			email = (GslabEmail) in;
			return this;
		}

		@Override
		public EmailBuilder from(String from) {
			email.from = from;
			return this;
		}


		@Override
		public EmailBuilder subject(String subject) {
			email.subject = subject;
			return this;
		}

		@Override
		public EmailBuilder body(String body) {
			email.body = body;
			return this;
		}

		@Override
		public EmailBuilder template(String template) {
			email.template = template;
			return this;
		}

		@Override
		public EmailBuilder templateContent(Map<String, Object> content) {
			email.templateContent = content;
			return this;
		}

		@Override
		public EmailBuilder cc(List<String> cc) {
			email.cc = cc;			
			return this;
		}

		@Override
		public EmailBuilder bcc(List<String> bcc) {
			email.bcc = bcc;			
			return this;
		}

		@Override
		public EmailBuilder to(List<String> to) {
			email.to = to;
			return this;
		}

		@Override
		public EmailBuilder fileMap(Map<byte[], String> filemap) {
			email.fileMap = filemap;
			return this;
		}
	}
	
	
	
}
