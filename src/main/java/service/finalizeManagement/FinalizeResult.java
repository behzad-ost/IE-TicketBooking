package service.finalizeManagement;

import data.Ticket;

import java.util.ArrayList;

/**
 * Created by ali on 5/2/17.
 */
public class FinalizeResult {
    private ArrayList<Ticket> tickets;
    private boolean success;
    private String errorMessage;

    public FinalizeResult() {
        tickets = new ArrayList<>();
        this.success = true;
        this.errorMessage = "";
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
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

