package service;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by behzad on 3/5/17.
 */
public class ReserveRepo {
    private ArrayList<Reservation> reservations;

    public ReserveRepo(){
        reservations = new ArrayList<>();
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Reservation findByToken(String token) {
        for(int i = 0 ; i < reservations.size() ; i++){
            if(Objects.equals(reservations.get(i).getToken(), token)){
                return reservations.get(i);
            }
        }
        return null;
    }

    public void addReservation(Reservation newReserve) {
        reservations.add(newReserve);
    }
}
