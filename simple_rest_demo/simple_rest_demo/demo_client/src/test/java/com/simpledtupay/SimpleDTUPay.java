package com.simpledtupay;

import org.acme.Customer;
import org.acme.Merchant;
import org.acme.Transaction;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class SimpleDTUPay {
    WebTarget baseUrl;

    public SimpleDTUPay() {
        Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/");
    }

    public void registerMerchant(String mid, String name) {
        Merchant m = new Merchant(mid, name);
        String res = baseUrl.path("accounts").path("merchant").request().post(Entity.entity(m, MediaType.APPLICATION_JSON_TYPE), String.class);
    }

    public void registerCustomer(String cid, String name) {
        Customer c = new Customer(cid, name);
        baseUrl.path("accounts").path("customer").request().post(Entity.entity(c, MediaType.APPLICATION_JSON_TYPE));
    }

    public boolean pay(String mid, String cid, int amount) throws WebApplicationException{
        Transaction t = new Transaction(mid, cid, amount);
            Response response = baseUrl.path("payment").request().post(Entity.entity(t, MediaType.APPLICATION_JSON_TYPE));
        if (response.getStatus() == 201) {
            response.close();
            t.setApproved(true);
            return t.isApproved();
        }
        else throw new WebApplicationException("Customer Not Know");
    }

    public List<Transaction> transactions(String cid) {
        return baseUrl.path("transactions").path(cid).request().accept(MediaType.APPLICATION_JSON).get(Customer.class).getTransactions();
    }
}