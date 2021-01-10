package com.simpledtupay;

import dtu.ws.fastmoney.*;
import org.acme.*;
import org.acme.Transaction;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

public class SimpleDTUPay {
    WebTarget baseUrl;
    WebTarget baseUrlForBank;
    BankService b;

    public SimpleDTUPay() {
        b = new BankServiceService().getBankServicePort();
        Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/");
        Client clientBank = ClientBuilder.newClient();
        baseUrlForBank = clientBank.target("http://g-00.compute.dtu.dk:80/rest");
    }

    public void registerMerchant(String mid, String name) {
        Merchant m = new Merchant(mid, name);
        String res = baseUrl.path("accounts").path("merchant").request().post(Entity.entity(m, MediaType.APPLICATION_JSON_TYPE), String.class);
    }

    public void registerCustomer(String cid, String name) {
        Customer c = new Customer(cid, name);
        baseUrl.path("accounts").path("customer").request().post(Entity.entity(c, MediaType.APPLICATION_JSON_TYPE));
    }

    public boolean pay(String mid, String cid, int amount) throws WebApplicationException {
        Transaction t = new Transaction(mid, cid, amount);
        Response response = baseUrl.path("payment").request().post(Entity.entity(t, MediaType.APPLICATION_JSON_TYPE));
        if (response.getStatus() == 201) {
            response.close();
            t.setApproved(true);
            return t.isApproved();
        } else throw new WebApplicationException("Customer Not Know");
    }

    public List<Transaction> transactions(String cid) {
        return baseUrl.path("transactions").path(cid).request().accept(MediaType.APPLICATION_JSON).get(Customer.class).getTransactions();
    }

    public void registerBankAccount(String fname, String sname, String cpr) {
        User u = new User();
        u.setFirstName(fname);
        u.setLastName(sname);
        u.setCprNumber(cpr);
        BigDecimal bigDecimal = new BigDecimal("1000");
        try {
            b.retireAccount(cpr);
            b.createAccountWithBalance(u, bigDecimal);
        } catch (BankServiceException_Exception e){
            System.out.println(e.getMessage());
        }
       /* Response response = baseUrlForBank.path("accounts").request().post(Entity.entity(u, MediaType.TEXT_XML));
        if (response.getStatus() == 200) {
            response.close();
            return true;
        } else return false;*/
    }
}
