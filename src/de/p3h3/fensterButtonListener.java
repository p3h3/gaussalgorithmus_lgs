package de.p3h3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fensterButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int anzahlUnbekannte = Main.getAnzahlUnbekannte();
        int anzahlGleichungen = Main.getAnzahlGleichungen();

        if(anzahlGleichungen < 0 || anzahlUnbekannte < 0){
            JOptionPane.showMessageDialog(Main.f,
                    "Bitte eine korrekte Zahl eingeben..",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(anzahlGleichungen < anzahlUnbekannte){
            JOptionPane.showMessageDialog(Main.f,
                    "Es gibt unendlich viele Lösungen für dieses Gleichungssystem!",
                    "Anzahl Lösungen",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        LGSFenster fenster = new LGSFenster(anzahlGleichungen, anzahlUnbekannte);
        LGSFenster.show(true);
    }
}
