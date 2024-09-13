package com.jmsc.app.entity;

import java.util.Date;

import com.jmsc.app.common.enums.EBankGuaranteeCreationType;
import com.jmsc.app.common.enums.EBankGuaranteeSecurityType;
import com.jmsc.app.common.enums.EBankGuaranteeStatus;
import com.jmsc.app.common.enums.EBankGuaranteeType;


public interface BankGuaranteeInterface {

	public Long getClientId();

	public Long getId();

	public EBankGuaranteeCreationType getCreationType();

	public EBankGuaranteeType getType();

	public String getBgNumber();

	public Long getBgAmount();

	public Date getValidFrom();

	public Date getValidTo();

	public String getInFavourOf();

	public String getWorkName();

	public String getBank();

	public EBankGuaranteeSecurityType getSecurity();

	public EBankGuaranteeStatus getStatus();

	public Long getCharge();

	public Date getChargedOn();

	public String getChargedFromAccount();

	public boolean isFileAttached();

	public String getFileName();

	public String getContentType();

	public Date getCreatedTimestamp();

	public Date getUpdatedTimestamp();
}
