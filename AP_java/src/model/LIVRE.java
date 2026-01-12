package model;

public class LIVRE {

    private String ISBN;
    private String titre;
    private float prix;
    private AUTEUR auteur;
    private ADHERENT emprunteur;

    /* =======================
       CONSTRUCTEUR
       ======================= */
    public LIVRE(String ISBN, String titre, float prix, AUTEUR auteur) {
        this.ISBN = ISBN;
        this.titre = titre;
        this.prix = prix;
        this.auteur = auteur;
        this.emprunteur = null; // livre disponible par défaut
    }

    /* =======================
       GETTERS & SETTERS
       ======================= */
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public AUTEUR getAuteur() {
        return auteur;
    }

    public void setAuteur(AUTEUR auteur) {
        this.auteur = auteur;
    }

    public ADHERENT getEmprunteur() {
        return emprunteur;
    }

    /* =======================
       MÉTHODES MÉTIER
       ======================= */

    /**
     * Emprunter le livre
     */
    public boolean emprunter(ADHERENT adherent) {
        if (emprunteur != null) {
            System.out.println("❌ Livre déjà emprunté.");
            return false;
        }
        this.emprunteur = adherent;
        System.out.println("✅ Livre emprunté par " + adherent.getNom());
        return true;
    }

    /**
     * Rendre le livre
     */
    public boolean rendre() {
        if (emprunteur == null) {
            System.out.println("❌ Ce livre n'est pas emprunté.");
            return false;
        }
        System.out.println("✅ Livre rendu par " + emprunteur.getNom());
        this.emprunteur = null;
        return true;
    }

    /**
     * Vérifier si le livre est disponible
     */
    public boolean estDisponible() {
        return emprunteur == null;
    }

    /* =======================
       AFFICHAGE
       ======================= */
    public void afficher() {
        System.out.print("ISBN : " + ISBN +
                " | Titre : " + titre +
                " | Prix : " + prix + " € | ");

        if (auteur != null) {
            System.out.print("Auteur : " + auteur.getNom() + " | ");
        } else {
            System.out.print("Auteur : Inconnu | ");
        }

        if (emprunteur != null) {
            System.out.print("Emprunté par : " + emprunteur.getNom());
        } else {
            System.out.print("Livre disponible");
        }

        System.out.println();
    }
}
