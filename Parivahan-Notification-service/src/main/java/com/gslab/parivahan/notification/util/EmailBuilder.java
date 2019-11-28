package com.gslab.parivahan.notification.util;

import java.util.List;
import java.util.Map;

import com.gslab.parivahan.notification.model.GslabEmail;
import com.gslab.parivahan.notification.util.Buildable.Builder;


public interface EmailBuilder extends Builder<EmailBuilder,GslabEmail> {

	EmailBuilder from(String from);

	EmailBuilder to(List<String> to);

	EmailBuilder subject(String subject);

	EmailBuilder body(String body);

	EmailBuilder template(String template);
	
	EmailBuilder templateContent(Map<String, Object> content);
	
	EmailBuilder cc(List<String> cc);
	
	EmailBuilder bcc(List<String> bcc);
	
	EmailBuilder fileMap(Map<byte[], String> filemap);
}
