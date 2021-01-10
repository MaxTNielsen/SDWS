package com.simpledtupay;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.acme.Transaction;
import javax.ws.rs.WebApplicationException;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleDTUPaySteps {
    String cid, mid, errorMessage;
    int amount;
    List<Transaction> t;
    SimpleDTUPay dtuPay = new SimpleDTUPay();
    boolean successful, unsuccessful;

    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) {
        this.cid = cid;
        dtuPay.registerCustomer(cid, "customer1");
    }

    @Given("a merchant with id {string}")
    public void aMerchantWithId(String mid) {
        this.mid = mid;
        dtuPay.registerMerchant(mid, "merchant1");
    }

    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
        this.amount = amount;
        this.successful = dtuPay.pay(mid, cid, amount);
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(successful);
    }

    @Given("a successful payment of {int} kr from customer {string} to merchant {string}")
    public void aSuccessfulPaymentOfKrFromCustomerToMerchant(int amount, String cid, String mid) {
        this.cid = cid;

    }

    @When("the manager asks for a list of transactions")
    public void theManagerAsksForAListOfTransactions() {
        this.t = dtuPay.transactions(cid);
    }

    @Then("the list contains a transaction where customer {string} paid {int} kr to merchant {string}")
    public void theListContainsATransactionWhereCustomerPaidKrToMerchant(String cid, int amount, String mid) {
        assertNotNull(t);
    }

    @Given("a new customer with id {string}")
    public void aNewCustomerWithId(String cid) {
        this.cid = cid;
    }

    @Given("the same merchant with id {string}")
    public void theSameMerchantWithId(String mid) {
        this.mid = mid;
    }

    @When("the merchant initiates a new payment for {int} kr by the customer")
    public void theMerchantMakesANewTransaction(int amount) {
        this.amount = amount;
        try {
            this.unsuccessful = dtuPay.pay(mid, cid, amount);
        }
        catch (WebApplicationException e){
             this.errorMessage = e.getMessage();
        }
    }

    @Then("the payment is not successful")
    public void thePaymentIsNotSuccessful() {
        assertFalse(successful);
    }

    @Then("a error message is returned saying customer not know")
    public void aErrorMessageIsReturnedSayingCustomerNotKnow() {
        assertEquals("Customer Not Know", errorMessage);
    }

}