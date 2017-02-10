package common;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by behzad on 2/10/17.
 */
public class Flight {
    private String code;
    private String number;
    private String date;
    private String origin;
    private String destination;
    private String dTime;
    private String aTime;
    private String planeModel;
    private ArrayList<Pair<String,Integer>> classes;

    public Flight(String line1 , String line2){
        String[] line1Args = line1.split("\\s+");
        String[] line2Args = line2.split("\\s+");

        code = line1Args[0];
        number = line1Args[1];
        date = line1Args[2];
        origin = line1Args[3];
        destination = line1Args[4];
        dTime = line1Args[5];
        aTime = line1Args[6];
        planeModel= line1Args[7];
        classes = new ArrayList<Pair<String,Integer>>();

        for(int i = 0 ; i < line2Args.length ; i++){
            int free;
            if(Objects.equals(line2Args[i].substring(1, 2), "C"))
                free = 0;
            else if(Objects.equals(line2Args[i].substring(1, 2), "A"))
                free = 9;
            else
                free = Integer.parseInt(line2Args[i].substring(1,2));

            Pair<String,Integer> newPair = new Pair<>(line2Args[i].substring(0,1),free);
            System.out.println("f: "+newPair.getFirst()+ " s: "+newPair.getSecond());
            classes.add(newPair);
        }
    }
}
