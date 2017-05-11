package data;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by behzad on 3/5/17.
 */
public class ReserveRepo {
    private ArrayList<Reservation> reservations;
    private static int lastId;
    public ReserveRepo(){
        reservations = new ArrayList<>();
        lastId = 1;
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
        newReserve.setId(lastId);
        lastId++;
        reservations.add(newReserve);
    }

    public ArrayList<Ticket> getTicketsOfReserve(Reservation reservation) {
        for(int i = 0 ; i < reservations.size() ; i++){
            if(Objects.equals(reservations.get(i).getToken(), reservation.getToken())){
                return reservations.get(i).getTickets();
            }
        }
        return null;
    }
}
