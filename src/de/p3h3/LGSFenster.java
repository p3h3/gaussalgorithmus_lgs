package de.p3h3;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Arrays;

public class LGSFenster{

    static JFrame f;

    static JLabel ergebnisLabel;

    private static double[][] koeffizientenMatrix;

    public LGSFenster(int gleichungen, int unbekannte){
        f = new JFrame("Gaussalgorithmus zum Lösen von LGS");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        koeffizientenMatrix = new double[gleichungen][unbekannte+1];

        JPanel[] subPanels = new JPanel[gleichungen];

        for (int i = 0; i < gleichungen; i++){
            subPanels[i] = new JPanel(new GridLayout(1,unbekannte+1));
            for (int j = 0; j < unbekannte+1; j++){
                JTextField koeffizientenFeld = new JTextField();
                int finalI = i;
                int finalJ = j;
                koeffizientenFeld.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        insertIntoMatrix();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        insertIntoMatrix();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        insertIntoMatrix();
                    }

                    public void insertIntoMatrix(){
                        String text = koeffizientenFeld.getText();
                        try{
                            koeffizientenMatrix[finalI][finalJ] = Double.parseDouble(text);
                        }catch (NumberFormatException e){
                            JOptionPane.showMessageDialog(f,
                                    "Bitte eine Zahl eingeben!",
                                    "Eingegebene Zeichen",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });
                subPanels[i].add(koeffizientenFeld);
            }
            panel.add(subPanels[i]);
        }

        JPanel subPanel1 = new JPanel(new GridLayout(1,2));
        ergebnisLabel = new JLabel("L={}");
        subPanel1.add(ergebnisLabel);
        JButton berechnenButton = new JButton("Berechnen!");
        berechnenButton.addActionListener((e) -> berechnen());
        subPanel1.add(berechnenButton);
        panel.add(subPanel1);

        // get screen size to make window size relative
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // stop process on close of main window
        f.setSize((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.5));
        f.getContentPane().add(panel);

    }

    private void berechnen(){
        // fucking wizardry - copy 2d array without reference
        double[][] km = Arrays.stream(koeffizientenMatrix).map(double[]::clone).toArray(double[][]::new);

        for(int i = 0; i < km.length; i++){
            for(int j = i+1; j < km.length; j++) {
                double[] hauptgleichung = km[i].clone();
                double[] vergleichsgleichung = km[j].clone();

                double vorfaktor = - vergleichsgleichung[i] / hauptgleichung[i];
                for (int k = 0; k < vergleichsgleichung.length; k++) {
                    double hauptgleichung_vorfaktor = hauptgleichung[k] *= vorfaktor;
                    km[j][k] = vergleichsgleichung[k] + hauptgleichung_vorfaktor;
                }
            }
        }

        double[] unbekannte = new double[km[0].length - 1];

        // erste unbekannte ausrechnen
        unbekannte[0] = km[km.length-1][km[km.length-1].length-1] / km[km.length-1][km[km.length-1].length-2];
        //System.out.println(unbekannte[0]);

        for(int i = 1; i < unbekannte.length; i++){
            double[] aktuellegleichung = km[km.length - i - 1].clone();
            double summand = 0;
            for(int j = aktuellegleichung.length - 2; j >= aktuellegleichung.length - i - 1; j--){
                summand += (aktuellegleichung[j] * unbekannte[aktuellegleichung.length - j - 2]);
            }
            unbekannte[i] = (aktuellegleichung[aktuellegleichung.length - 1] - summand) / aktuellegleichung[aktuellegleichung.length - i - 2];
        }

        StringBuilder loesungText = new StringBuilder("L = {(");
        for(int i = unbekannte.length-1; i > 0; i--){
            loesungText.append(unbekannte[i]).append(" | ");
        }
        loesungText.append(unbekannte[0]).append(")}");

        ergebnisLabel.setText(loesungText.toString());

        //printMatrix(km);
        //printMatrix(koeffizientenMatrix);
    }

    private void printMatrix(double[][] m){
        for (double[] doubles : m) {
            for (double aDouble : doubles) {
                System.out.print(aDouble + "|");
            }
            System.out.println();
        }
    }

    public static void show(boolean x){
        f.setVisible(x); // making the frame visible
    }
}
