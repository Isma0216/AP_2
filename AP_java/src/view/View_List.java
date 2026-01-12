package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import controller.mainMVC;
import model.ADHERENT;
import model.AUTEUR;
import model.LIVRE;

import java.awt.List;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class View_List {

	private JFrame frame;

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public View_List() throws SQLException {
		mainMVC.getM().getall();
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 847, 514);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		List list = new List();
		list.setBounds(28, 27, 767, 397);
		frame.getContentPane().add(list);
		for (LIVRE l : mainMVC.getM().getListLivre())
		{
			String dispo;
			if (l.getEmprunteur() == null)
			{
				dispo="disponible";
			}
			else
			{
				dispo="emprunté";
			}
			String auteur;
			if (l.getAuteur()==null)
			{
				auteur="inconnu";
			}
			else
			{
				auteur=l.getAuteur().getNom();
			}
			list.add("ISBN :"+l.getISBN()+" titre : "+l.getTitre()+" de : "+ auteur+ "("+dispo+")");
		
		}
	}
}


