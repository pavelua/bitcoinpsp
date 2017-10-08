package com.financial.bitcoinpsp.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "wallet_address")
public class WalletAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "address_id")
	private Long id;
	@Column(name = "merchant_id")
	@NotNull(message = "*Please provide an merchant ID")
	private Long merchantId;
	@Column(name = "address")
	@NotEmpty(message = "*Please provide an wallet address")
	private String address;
	@Column(name = "lifetime")
	@NotNull(message = "*Please provide lifetime")
	private Long lifeTime;
	@Column(name = "callback_url")
	@NotNull(message = "*Please provide callback_url")
	private String callbackUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(Long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
}
