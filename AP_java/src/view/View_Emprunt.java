package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.model;
import model.LIVRE;
import model.ADHERENT;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class View_Emprunt {

    private JFrame frame;
    private model m;

    /**
     * Constructeur
     */
    public View_Emprunt(model m) throws SQLException {
        this.m = m;
        m.getall(); // charge les données depuis la BDD
        initialize();
        frame.setVisible(true);
    }

    /**
     * Initialise la fenêtre
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Label et champ pour ISBN
        JLabel lblISBN = new JLabel("ISBN du livre :");
        lblISBN.setBounds(50, 50, 120, 25);
        frame.getContentPane().add(lblISBN);

        JTextField txtISBN = new JTextField();
        txtISBN.setBounds(180, 50, 200, 25);
        frame.getContentPane().add(txtISBN);

        // Label et champ pour numéro adhérent
        JLabel lblNumAdherent = new JLabel("Numéro adhérent :");
        lblNumAdherent.setBounds(50, 100, 120, 25);
        frame.getContentPane().add(lblNumAdherent);

        JTextField txtNumAdherent = new JTextField();
        txtNumAdherent.setBounds(180, 100, 200, 25);
        frame.getContentPane().add(txtNumAdherent);

        // Bouton Emprunter
        JButton btnEmprunter = new JButton("EMPRUNTER");
        btnEmprunter.setBounds(180, 150, 120, 30);
        frame.getContentPane().add(btnEmprunter);

        // Action du bouton
        btnEmprunter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String isbn = txtISBN.getText().trim();
                String numAdherent = txtNumAdherent.getText().trim();

                if (isbn.isEmpty() || numAdherent.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "❌ Veuillez remplir les deux champs.");
                    return;
                }

                try {
                    // Vérifie si le livre existe
                    LIVRE livre = m.findLivre(isbn);
                    if (livre == null) {
                        JOptionPane.showMessageDialog(frame, "❌ ISBN introuvable !");
                        return;
                    }

                    // Vérifie si l'adhérent existe
                    ADHERENT adherent = m.findAdherent(numAdherent);
                    if (adherent == null) {
                        JOptionPane.showMessageDialog(frame, "❌ Numéro adhérent introuvable !");
                        return;
                    }

                    // Vérifie si le livre est déjà emprunté
                    if (livre.getEmprunteur() != null) {
                        JOptionPane.showMessageDialog(frame, "❌ Livre déjà emprunté par " 
                                                        + livre.getEmprunteur().getNom());
                        return;
                    }

                    // Emprunt en BDD
                    boolean ok = m.emprunterLivre(isbn, numAdherent);

                    if (ok) {
                        // Met à jour l'objet en mémoire
                        livre.emprunter(adherent);
                        adherent.getListLivre().add(livre);

                        JOptionPane.showMessageDialog(frame, "✅ Livre emprunté !");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "❌ Erreur : impossible d'emprunter le livre.");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "❌ Erreur lors de l'emprunt.");
                }
            }
        });
    }
}
