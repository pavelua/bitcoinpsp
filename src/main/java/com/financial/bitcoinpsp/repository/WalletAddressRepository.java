package com.financial.bitcoinpsp.repository;

import com.financial.bitcoinpsp.model.WalletAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("walletAddressRepository")
public interface WalletAddressRepository extends JpaRepository<WalletAddress, Long> {
}
