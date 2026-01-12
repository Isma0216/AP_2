package controller;

import java.sql.SQLException;
import model.model;
import view.View_Accueil;

public class mainMVC {

    private static model m;

    // Initialisation du model si ce n'est pas déjà fait
    public static void init() throws ClassNotFoundException, SQLException {
        if (m == null) {
            m = new model();
        }
    }

    // Getter pour le model
    public static model getM() {
        return m;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Début du programme");

            // Initialisation du model
            mainMVC.init();

            // Ouverture de la vue principale en passant le model
            new View_Accueil(getM());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
