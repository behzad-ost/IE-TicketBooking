package service;

import java.util.ArrayList;

/**
 * Created by behzad on 3/5/17.
 */
public class ReserveRepo {
    private ArrayList<Reservation> reservations;

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }
}
