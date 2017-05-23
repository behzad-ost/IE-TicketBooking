package service.tickets;

import service.common.TicketInfo;

import java.util.ArrayList;

/**
 * Created by behzad on 5/21/17.
 */

public class TicketsResult {
    private boolean success;
    private String errorMessage;
    private ArrayList<TicketInfo> tickets;


    public TicketsResult() {
        this.success = false;
        this.errorMessage = "";
        this.tickets = new ArrayList<>();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ArrayList<TicketInfo> getTickets() {
        return tickets;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setTickets(ArrayList<TicketInfo> tickets) {
        this.tickets = tickets;
    }
}
