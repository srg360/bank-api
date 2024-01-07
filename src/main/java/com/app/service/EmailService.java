package com.app.service;

import com.app.dto.EmailDetails;

public interface EmailService {

	void sendEmailAlert(EmailDetails emailDetails);
	
	void sendEmailWithAttachment(EmailDetails emailDetails);
}
