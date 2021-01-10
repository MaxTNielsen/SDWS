package org.acme;

import java.util.List;

public interface ISimpleDTUPayBL {
    boolean makeTransaction(Transaction t);

    String getCustomer(int id);

    void registerCustomer(Customer c);

    void registerMerchant(Merchant m);

    List<Transaction> getAllTransactions(String cid);
}
