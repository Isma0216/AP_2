package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controller.mainMVC;
import model.LIVRE;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class View_Rendre {

    private JFrame frame;
    private JTextField textNum;

    /**
     * Create the application.
     * @throws SQLException
     */
    public View_Rendre() throws SQLException {
        mainMVC.getM().getall();
        initialize();
        frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frame = new JFrame();
        frame.setBounds(100, 100, 876, 220);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel titre = new JLabel("RENDRE UN LIVRE");
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setBounds(300, 11, 250, 20);
        frame.getContentPane().add(titre);

        JLabel lblScan = new JLabel("Scanner le livre (numéro)");
        lblScan.setBounds(165, 80, 200, 14);
        frame.getContentPane().add(lblScan);

        textNum = new JTextField();
        textNum.setBounds(365, 77, 199, 20);
        frame.getContentPane().add(textNum);
        textNum.setColumns(10);

        JLabel message = new JLabel("");
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setBounds(140, 123, 488, 14);
        frame.getContentPane().add(message);

        JButton valider = new JButton("VALIDER LE RETOUR");
        valider.setBounds(591, 76, 170, 23);
        frame.getContentPane().add(valider);

        /* ===== ACTION DU BOUTON ===== */
        valider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String numLivre = textNum.getText();

                if (numLivre.isEmpty()) {
                    message.setText("Veuillez scanner un livre");
                    message.setForeground(Color.RED);
                    message.setVisible(true);
                    return;
                }

                LIVRE livre = mainMVC.getM().findLivre(numLivre);

                if (livre == null) {
                    message.setText("Livre introuvable");
                    message.setForeground(Color.RED);
                    message.setVisible(true);
                    return;
                }

                try {
                    mainMVC.getM().rendreLivre(numLivre);
                    message.setText("Livre rendu avec succès");
                    message.setForeground(Color.GREEN);
                    message.setVisible(true);
                    textNum.setText("");

                } catch (SQLException ex) {
                    message.setText("Erreur lors du rendu du livre");
                    message.setForeground(Color.RED);
                    message.setVisible(true);
                    ex.printStackTrace();
                }
            }
        });
    }
}
