package org.acme;

import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;

import java.math.BigDecimal;
import java.util.List;

public interface ISimpleDTUPayBL {
    boolean makeTransaction(Transaction t);

    String getCustomer(int id);

    void registerCustomer(Customer c);

    void registerMerchant(Merchant m);

    List<Transaction> getAllTransactions(String cid);

    public void registerToBank(User u, BigDecimal d) throws BankServiceException_Exception;
}
