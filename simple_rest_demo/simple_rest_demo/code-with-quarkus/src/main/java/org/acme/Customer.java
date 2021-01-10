package org.acme;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Clients {
    private List<Transaction> transactions = new ArrayList<>();

    public Customer() {

    }

    public Customer(String id, String name) {
        super(id, name);

    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction t) {
        this.transactions.add(t);
    }
}
