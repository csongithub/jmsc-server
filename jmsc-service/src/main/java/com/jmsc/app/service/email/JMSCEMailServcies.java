/**
 * 
 */
package com.jmsc.app.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.util.Strings;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Slf4j
@Service
public class JMSCEMailServcies {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public Boolean sendMail(Envelop envelop) throws MessagingException {
		
		if(Strings.isNullOrEmpty(envelop.getRecipients())) {
			throw new RuntimeException("Invalid Envelop, Missing Recipients");
		}
		
		String recipients[] = envelop.getRecipients().split(",");

		for(String recipient: recipients) {
			if(recipient.endsWith(".com")) {
				log.info(String.format("Sending Mail To: %s", envelop.getRecipients()));
				MimeMessage message = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message
				helper.setSubject(envelop.getSubject());
				helper.setTo(recipient.trim());
				helper.setText(envelop.getBody().getMessage(), true); // true indicate html
				javaMailSender.send(message);
				log.info(String.format("Mail Successfully Sent To: %s", envelop.getRecipients()));
			}else {
				log.error("Invalid Recipient: " + recipient);
			}
		}
		return Boolean.TRUE;
	}

}
