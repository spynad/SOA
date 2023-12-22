package com.spynad.gateway;

import com.spynad.gateway.wsdl.GetAverageTicketDiscount;
import com.spynad.gateway.wsdl.GetAverageTicketDiscountResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class TestClient extends WebServiceGatewaySupport {
    public GetAverageTicketDiscountResponse getAverageTicketDiscount() {
        GetAverageTicketDiscount request = new GetAverageTicketDiscount();

        return (GetAverageTicketDiscountResponse)
                getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
