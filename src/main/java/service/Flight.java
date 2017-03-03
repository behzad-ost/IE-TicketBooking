package service;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by behzad on 2/10/17.
 */
public class Flight {
    public class Seat {
        private String className;
        private int available;
        private int adultPrice;
        private int childPrice;
        private int infantPrice;

        public Seat(String cName , int free){
            className = cName;
            available = free;
        }
        public String getClassName() {
            return className;
        }

        public int getAdultPrice() {
            return adultPrice;
        }

        public int getChildPrice() {
            return childPrice;
        }

        public int getInfantPrice() {
            return infantPrice;
        }

        public int getAvailable() {
            return available;
        }
    }
    private String airlineCode;
    private String number;
    private String date;
    private String origin;
    private String destination;
    private String dTime;
    private String aTime;
    private String planeModel;
    private ArrayList<Seat> seats;

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getdTime() {
        return dTime;
    }

    public String getaTime() {
        return aTime;
    }

    public String getPlaneModel() {
        return planeModel;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }


    public Flight(String line1 , String line2){
        String[] line1Args = line1.split("\\s+");

        airlineCode = line1Args[0];
        number = line1Args[1];
        date = line1Args[2];
        origin = line1Args[3];
        destination = line1Args[4];
        dTime = line1Args[5];
        aTime = line1Args[6];
        planeModel= line1Args[7];
        seats = new ArrayList<>();

        this.setClasses(line2);
    }

    public void setClasses(String line2){
        String[] line2Args = line2.split("\\s+");
        for(int i = 0 ; i < line2Args.length ; i++){
            int free;
            if(Objects.equals(line2Args[i].substring(1, 2), "C"))
                free = 0;
            else if(Objects.equals(line2Args[i].substring(1, 2), "A"))
                free = 9;
            else
                free = Integer.parseInt(line2Args[i].substring(1,2));
            Seat newSeat= new Seat(line2Args[i].substring(0,1),free);
//            System.out.println("f: "+newPair.getFirst()+ " s: "+newPair.getSecond());
            seats.add(newSeat);
        }
    }

    public void addPrice(Seat seat, String ps){
        String[] tokenPrices = ps.split("\\s+");
        seat.adultPrice = Integer.parseInt(tokenPrices[0]);
        seat.childPrice = Integer.parseInt(tokenPrices[0]);
        seat.infantPrice= Integer.parseInt(tokenPrices[0]);
    }

}
