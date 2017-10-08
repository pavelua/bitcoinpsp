package com.financial.bitcoinpsp.controller;

import com.financial.bitcoinpsp.client.RPCClient;
import com.financial.bitcoinpsp.model.WalletAddress;
import com.financial.bitcoinpsp.repository.WalletAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BitcoinController {
	@Autowired
	private WalletAddressRepository walletAddressRepository;
	@Autowired
	private RPCClient rpcClient;
	@Value("${aliveTimeoutMillis}")
	private long timeout;

	@RequestMapping(value = "/{merchantId}")
	public ModelAndView ping(@PathVariable Long merchantId, @RequestParam(value = "callback_url") String callbackUrl) {

		ModelAndView index = new ModelAndView("index");
		//TODO get rpc URL User's Name and Password from database (create table "merchant") to init
		String newAddress = rpcClient.getNewAddress("account-label");
		WalletAddress walletAddress = new WalletAddress();
		walletAddress.setMerchantId(merchantId);
		walletAddress.setAddress(newAddress);
		walletAddress.setLifeTime(System.currentTimeMillis() + timeout);
		walletAddress.setCallbackUrl(callbackUrl);
		walletAddressRepository.save(walletAddress);
		index.addObject("walletAddress", newAddress);
		return index;
	}
}
