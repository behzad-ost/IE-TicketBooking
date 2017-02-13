package ui;

import common.Transceiver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by ali on 2/8/17.
 */
public class ClientUI {
    private static String request;
    private static String response;
    private static String[] numbers;
    private static String[] dates;
    private static boolean buttonPressed;


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception evnt){};

        final String[] serachString = new String[1];
        numbers = new String[] {"1", "2", "3", "4", "5", "6", "7", "8"};
        dates = new String[] {"04Feb", "05Feb", "06Feb", "07Feb"};
        JFrame myFrame = new JFrame("سیستم رزرو بلیت هواپیمای اخوانی و اوسط");
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.setSize(500, 600);
        myFrame.setLocationRelativeTo(null);

//        Source Label and Text Field
        JLabel sourceLabel = new JLabel("مبدا:");
        sourceLabel.setBounds(400, 50, 200, 50);

        final JTextField sourceText = new JTextField();
        sourceText.setBounds(250, 50, 125, 50);

//      Destination Label and Text
        JLabel destLabel = new JLabel("مقصد:");
        destLabel.setBounds(400, 100, 200, 50);

        final JTextField destText = new JTextField();
        destText.setBounds(250, 100, 125, 50);

//        Number of Adults
        JLabel adultsLabel = new JLabel("بزرگ‌سال:");
        adultsLabel.setBounds(400, 150, 200, 50);

        final JComboBox<String> adultList = new JComboBox<>(numbers);
        String adultNum = (String) adultList.getSelectedItem();
        adultList.setBounds(250, 150, 125, 50);

//        Number of Childs
        JLabel childsLabel = new JLabel("کودک:");
        childsLabel.setBounds(400, 200, 200, 50);

        final JComboBox<String> childList = new JComboBox<>(numbers);
        String childNum = (String) childList.getSelectedItem();
        childList.setBounds(250, 200, 125, 50);

//        Number of Infants
        JLabel infantLabel = new JLabel("نوزاد:");
        infantLabel.setBounds(400, 250, 200, 50);

        final JComboBox<String> infantList = new JComboBox<>(numbers);
        String infantNum = (String) infantList.getSelectedItem();
        infantList.setBounds(250, 250, 125, 50);

//      Date Label
        JLabel dateLabel = new JLabel("تاریخ:");
        dateLabel.setBounds(400, 300, 200, 50);

        final JComboBox<String> dateList = new JComboBox<>(dates);
        String date = (String) dateList.getSelectedItem();
        dateList.setBounds(250, 300, 125, 50);


//        Submit Button
        JButton myButton = new JButton("جست‌وجو");
        myButton.setBounds(150, 450, 200, 50);

        myButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serachString[0] = "search " + sourceText.getText() + " " + destText.getText() + " " + dateList.getSelectedItem() +
                " " + adultList.getSelectedItem() + " " + childList.getSelectedItem() + " " + infantList.getSelectedItem() + "\n";
                request = serachString[0];
                buttonPressed = true;

                try {
                    Transceiver sender = new Transceiver("localhost", 8083);
                    sender.send(request);
                    response = sender.receive();
                    System.out.println(response);
                    sender.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        myFrame.add(myButton);
        myFrame.add(sourceText);
        myFrame.add(sourceLabel);
        myFrame.add(destLabel);
        myFrame.add(destText);
        myFrame.add(dateLabel);
        myFrame.add(adultsLabel);
        myFrame.add(childsLabel);
        myFrame.add(infantLabel);

        myFrame.setLayout(null);
        myFrame.setVisible(true);
        myFrame.add(adultList);
        myFrame.add(childList);
        myFrame.add(infantList);
        myFrame.add(dateList);
    }

//    public static
}
