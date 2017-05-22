package service.ticket;

import data.Ticket;
import service.common.FlightInfo;

import java.util.ArrayList;

/**
 * Created by ali on 5/22/17.
 */
public class TicketResult {
    private boolean success;
    private String errorMessage;
    private String refCode;
    private String reserveToken;

    public TicketResult() {
        success = true;
        errorMessage = "";
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getReserveToken() {
        return reserveToken;
    }

    public void setReserveToken(String personCode) {
        this.reserveToken = personCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
