package org.acme;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/payment")
public class paymentResource {

    SimpleDTUPayBL dtuPay3 = SimpleDTUPayBL.getInstance_();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doTransaction(Transaction t) {
        if (dtuPay3.makeTransaction(t)) {
            return Response.status(201, "Transaction completed").build();
        } else return Response.status(400, "Transaction failed").build();
    }
}