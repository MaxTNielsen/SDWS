package org.acme;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/accounts")
public class MerchantResource {
    SimpleDTUPayBL dtuPay2 = SimpleDTUPayBL.getInstance_();

    @Path("merchant")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String registerMerchant(Merchant m) {
        dtuPay2.registerMerchant(m);
        return "Merchant added with id: " + m.getId();
    }
}