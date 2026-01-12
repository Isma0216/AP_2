package view;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import model.model;

public class View_Accueil {

    private JFrame frame;
    private model m;

    // Constructeur reçoit l'instance du model
    public View_Accueil(model m) {
        this.m = m;
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Bouton Catalogue
        JButton btnCatalogue = new JButton("CATALOGUE");
        btnCatalogue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new View_List();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnCatalogue.setBounds(126, 11, 171, 23);
        frame.getContentPane().add(btnCatalogue);

        // Bouton Infos Compte
        JButton btnInfos = new JButton("INFOS DU COMPTE");
        btnInfos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new View_Info_Compte();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnInfos.setBounds(126, 62, 171, 23);
        frame.getContentPane().add(btnInfos);

        // Bouton Rendre Livre
        JButton btnRendre = new JButton("RENDRE LIVRE");
        btnRendre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new View_Rendre();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnRendre.setBounds(126, 110, 171, 23);
        frame.getContentPane().add(btnRendre);

        // Bouton Emprunter Livre
        JButton btnEmprunter = new JButton("EMPRUNTER LIVRE");
        btnEmprunter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new View_Emprunt(m);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnEmprunter.setBounds(126, 161, 171, 23);
        frame.getContentPane().add(btnEmprunter);
    }
}
