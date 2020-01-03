package Responses;

import java.io.Serializable;

public class BuyResponse implements Serializable {
    private Boolean okResponse;

    public BuyResponse(Boolean okResponse) {
        this.okResponse = okResponse;
    }

    public Boolean getOkResponse() {
        return okResponse;
    }

    public void setOkResponse(Boolean okResponse) {
        this.okResponse = okResponse;
    }
}
