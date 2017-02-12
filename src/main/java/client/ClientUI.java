package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 2/8/17.
 */
public class ClientUI {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame myFrame = new JFrame("سیستم رزرو بلیت هواپیمای اخوانی و اوسط");
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.setSize(500, 600);

//        Source Label and Text Field
        JLabel sourceLabel = new JLabel("مبدا:");
        sourceLabel.setBounds(400, 50, 200, 50);

        final JTextField sourceText = new JTextField();
        sourceText.setBounds(250, 50, 100, 50);

//      Destination Label and Text
        JLabel destLabel = new JLabel("مقصد:");
        destLabel.setBounds(400, 100, 200, 50);

        final JTextField destText = new JTextField();
        destText.setBounds(250, 100, 100, 50);

//        Number of Tickets
        JLabel numsLabel = new JLabel("تعداد بلیت:");
        numsLabel.setBounds(400, 150, 200, 50);

        final JTextField numsText = new JTextField();
        numsText.setBounds(250, 150, 100, 50);

//        Type of Ticket
        JLabel typeLabel = new JLabel("نوع بلیت:");
        typeLabel.setBounds(400, 200, 200, 50);


//      Date Label
        JLabel dateLabel = new JLabel("تاریخ:");
        dateLabel.setBounds(400, 250, 200, 50);

//        final JTextField destText = new JTextField();
//        destText.setBounds(250, 100, 100, 50);


//        Submit Button
        JButton myButton = new JButton("جست‌وجو");
        myButton.setBounds(150, 450, 200, 50);

        myButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sourceText.setText("شهیدی؟");
            }
        });

        myFrame.add(myButton);
        myFrame.add(sourceText);
        myFrame.add(sourceLabel);
        myFrame.add(destLabel);
        myFrame.add(destText);
        myFrame.add(dateLabel);
        myFrame.add(numsLabel);
        myFrame.add(numsText);
        myFrame.add(typeLabel);

        myFrame.setLayout(null);
        myFrame.setVisible(true);
    }
}
