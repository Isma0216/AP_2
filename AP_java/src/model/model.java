package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class model {

    private ArrayList<ADHERENT> listAdherent;
    private ArrayList<AUTEUR> listAuteur;
    private ArrayList<LIVRE> listLivre;

    private Connection conn;

    /* =======================
       CONSTRUCTEUR
       ======================= */
    public model() throws ClassNotFoundException, SQLException {

        listLivre = new ArrayList<>();
        listAdherent = new ArrayList<>();
        listAuteur = new ArrayList<>();

        String bdd = "2025_v2";
        String url = "jdbc:mysql://localhost:3306/" + bdd
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
        String user = "root";
        String mdp = "";

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, mdp);

        System.out.println("Connexion BDD OK");
    }

    /* =======================
       GETTERS
       ======================= */
    public ArrayList<ADHERENT> getListAdherent() {
        return listAdherent;
    }

    public ArrayList<AUTEUR> getListAuteur() {
        return listAuteur;
    }

    public ArrayList<LIVRE> getListLivre() {
        return listLivre;
    }

    /* =======================
       CHARGEMENT GLOBAL
       ======================= */
    public void getall() throws SQLException {

        listLivre.clear();
        listAuteur.clear();
        listAdherent.clear();

        Statement stmt = conn.createStatement();

        /* ---------- AUTEURS ---------- */
        ResultSet rs = stmt.executeQuery(
                "SELECT num, nom, prenom, date_naissance, description FROM auteur");

        while (rs.next()) {
            AUTEUR a = new AUTEUR(
                    rs.getString("num"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("date_naissance"),
                    rs.getString("description")
            );
            listAuteur.add(a);
        }

        /* ---------- ADHERENTS ---------- */
        rs = stmt.executeQuery("SELECT * FROM adherent");

        while (rs.next()) {
            ADHERENT ad = new ADHERENT(
                    rs.getString("num"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    new ArrayList<>()
            );
            listAdherent.add(ad);
        }

        /* ---------- LIVRES + LIENS ---------- */
        rs = stmt.executeQuery("SELECT * FROM livre");

        while (rs.next()) {

            AUTEUR auteur = findAuteur(rs.getString("auteur"));
            ADHERENT adherent = findAdherent(rs.getString("adherent"));

            LIVRE l = new LIVRE(
                    rs.getString("ISBN"),
                    rs.getString("titre"),
                    rs.getFloat("prix"),
                    auteur
            );

            if (adherent != null) {
                l.emprunter(adherent);
                adherent.getListLivre().add(l);
            }

            listLivre.add(l);
        }

        stmt.close();
    }

    /* =======================
       RECHERCHES
       ======================= */
    public AUTEUR findAuteur(String num) {
        for (AUTEUR a : listAuteur) {
            if (a.getNum().equals(num)) {
                return a;
            }
        }
        return null;
    }

    public ADHERENT findAdherent(String num) {
        for (ADHERENT ad : listAdherent) {
            if (ad.getNum().equals(num)) {
                return ad;
            }
        }
        return null;
    }

    public LIVRE findLivre(String isbn) {
        for (LIVRE l : listLivre) {
            if (l.getISBN().equals(isbn)) {
                return l;
            }
        }
        return null;
    }

    /* =======================
       MISE À JOUR EMAIL
       ======================= */
    public void majMail(String numAdherent, String mail) throws SQLException {

        String sql = "UPDATE adherent SET email = ? WHERE num = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, mail);
        stmt.setString(2, numAdherent);
        stmt.executeUpdate();

        stmt.close();
    }

    /* =======================
       EMPRUNTER LIVRE
       ======================= */
    public boolean emprunterLivre(String isbn, String numAdherent) throws SQLException {

        String sql = "UPDATE livre SET adherent = ? WHERE ISBN = ? AND adherent IS NULL";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, numAdherent);
        stmt.setString(2, isbn);

        int lignes = stmt.executeUpdate();
        stmt.close();

        return lignes > 0; // true si emprunt réussi
    }

    /* =======================
       RENDRE LIVRE
       ======================= */
    public boolean rendreLivre(String isbn) throws SQLException {

        String sql = "UPDATE livre SET adherent = NULL WHERE ISBN = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, isbn);
        int lignes = stmt.executeUpdate();

        stmt.close();
        return lignes > 0;
    }
}
