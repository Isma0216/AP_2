package view;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import controller.mainMVC;
import model.ADHERENT;
import model.LIVRE;

import java.awt.List;
import java.sql.SQLException;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class View_Info_Compte {

    private JFrame frame;
    private JTextField textCarte;
    private JTextField textNom;
    private JTextField textPrenom;
    private JTextField textEmail;

    /**
     * Create the application.
     * @throws SQLException 
     */
    public View_Info_Compte() throws SQLException {
        // Charger les données depuis le model
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
        
        
        textCarte = new JTextField();
        textCarte.setBounds(220, 66, 154, 20);
        frame.getContentPane().add(textCarte);
        textCarte.setColumns(10);
        
      
        
        JLabel Message = new JLabel("Entrer le numéro de carte");
        Message.setBounds(56, 69, 154, 14);
        frame.getContentPane().add(Message);
        
        JPanel panel = new JPanel();
        panel.setBounds(41, 121, 741, 325);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        panel.setVisible(false);
        
        JLabel lblNewLabel_1 = new JLabel("Nom");
        lblNewLabel_1.setBounds(49, 43, 63, 14);
        panel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("Prénom");
        lblNewLabel_1_1.setBounds(49, 82, 63, 14);
        panel.add(lblNewLabel_1_1);
        
        JLabel lblNewLabel_1_2 = new JLabel("Email");
        lblNewLabel_1_2.setBounds(49, 120, 63, 14);
        panel.add(lblNewLabel_1_2);
        
        textNom = new JTextField();
        textNom.setEnabled(false);
        textNom.setBounds(135, 40, 175, 20);
        panel.add(textNom);
        textNom.setColumns(10);
        
        textPrenom = new JTextField();
        textPrenom.setEnabled(false);
        textPrenom.setColumns(10);
        textPrenom.setBounds(135, 79, 175, 20);
        panel.add(textPrenom);
        
        textEmail = new JTextField();
        textEmail.setColumns(10);
        textEmail.setBounds(135, 117, 175, 20);
        panel.add(textEmail);
        
        JLabel lblNewLabel_2 = new JLabel("List emprunts");
        lblNewLabel_2.setBounds(49, 161, 82, 14);
        panel.add(lblNewLabel_2);
        
        List list = new List();
        list.setBounds(131, 161, 389, 154);
        panel.add(list);
        
        JLabel MajReussi = new JLabel("Email Changé !");
        MajReussi.setBounds(549, 120, 105, 14);
        MajReussi.setVisible(false);
        panel.add(MajReussi);
        
       

        JButton rechercher = new JButton("RECHERCHER");
        rechercher.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		String num = textCarte.getText();
        		ADHERENT pupuce = mainMVC.getM().findAdherent(num);
        		
        		if(pupuce != null) 
        		{
        			panel.setVisible(true);
        			textCarte.setEditable(false);
        			textNom.setText(pupuce.getNom());
        			textPrenom.setText(pupuce.getPrenom());
        			textEmail.setText(pupuce.getEmail());
        			for (LIVRE l : pupuce.getListLivre()) 
        			{
        				list.add(l.getTitre());
        			}
        			Message.setText("Utilisateur Trouvé");
        			Message.setForeground(Color.green);
        		}
        		
        		else {
        			Message.setText("Numéro non trouvé");
        			Message.setForeground(Color.red);
        		}
        	}
        }
        
    );
        
        JButton MAJ = new JButton("Mettre à jour le mail");
        MAJ.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String mail = textEmail.getText();
        		String num = textCarte.getText();
        		ADHERENT pupuce = mainMVC.getM().findAdherent(num);
        		try {
					mainMVC.getM().majMail(num, mail);
					MajReussi.setVisible(true);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        MAJ.setBounds(355, 116, 150, 23);
        panel.add(MAJ);
    
        
        rechercher.setBounds(395, 65, 130, 23);
        frame.getContentPane().add(rechercher);
        
        JLabel lblNewLabel_3 = new JLabel("COMPTE");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setBounds(296, 11, 137, 14);
        frame.getContentPane().add(lblNewLabel_3);
        
    }
}
