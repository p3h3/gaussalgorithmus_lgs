package de.p3h3;

import javax.swing.*;
import java.awt.*;

public class Main {

    static JFrame f;

    private static JTextField anzahlUnbekannteField;
    private static JTextField anzahlGleichungenField;

    public static void main(String[] args) {

        f = new JFrame("Gaussalgorithmus zum Lösen von LGS");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // eingabe anzahl unbekannte & label
        JPanel subPanel1 = new JPanel(new GridLayout(1,2));
        subPanel1.add(new JLabel("Anzahl Unbekannte:", SwingConstants.CENTER));
        anzahlUnbekannteField = new JTextField();
        subPanel1.add(anzahlUnbekannteField);

        // eingabe anzahl gleichungen & label
        JPanel subPanel2 = new JPanel(new GridLayout(1,2));
        subPanel2.add(new JLabel("Anzahl Gleichungen:", SwingConstants.CENTER));
        anzahlGleichungenField = new JTextField();
        subPanel2.add(anzahlGleichungenField);

        // eingabe anzahl gleichungen & label
        JPanel subPanel3 = new JPanel(new GridLayout(1,1));
        JButton fensterAnzeigenButton = new JButton("Fenster anzeigen");
        subPanel3.add(fensterAnzeigenButton);
        fensterAnzeigenButton.addActionListener(new fensterButtonListener());


        panel.add(subPanel1);
        panel.add(subPanel2);
        panel.add(subPanel3);

        // get screen size to make window size relative
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // stop process on close of main window
        f.setSize((int) (screenSize.width * 0.3), (int) (screenSize.height * 0.4));
        f.getContentPane().add(panel);
        f.setVisible(true); // making the frame visible

    }

    public static int getAnzahlUnbekannte(){
        try{
            String text = anzahlUnbekannteField.getText();
            return Integer.parseInt(text);
        }catch (NumberFormatException e){
            return -1;
        }
    }

    public static int getAnzahlGleichungen(){
        try{
            String text = anzahlGleichungenField.getText();
            return Integer.parseInt(text);
        }catch (NumberFormatException e){
            return -1;
        }
    }
}
