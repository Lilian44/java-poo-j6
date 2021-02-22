package fr.diginamic.recensement.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;

/**
 * Recherche et affichage de la population d'une ville
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationVilleService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("driver non trouvé");
		}

		ResourceBundle props = ResourceBundle.getBundle("database");
		String url = props.getString("database.url");
		String root = props.getString("database.user");
		String pass = props.getString("database.password");

		System.out.println("Quel est le nom de la ville recherchée ? ");
		String choix = scanner.nextLine();

		try {
			Connection connection = DriverManager.getConnection(url, root, pass);

			Statement monStatement = connection.createStatement();
			ResultSet curseur = monStatement.executeQuery("SELECT * FROM villes Where nom_commune= '"+choix+"'");

			while(curseur.next()) {
				Integer pop = curseur.getInt("population");
				System.out.println(choix +": " + pop +" habitants");
			}
			
			curseur.close();
			monStatement.close();
			connection.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		List<Ville> villes = rec.getVilles();
//		for (Ville ville : villes) {
//			if (ville.getNom().equalsIgnoreCase(choix)
//					|| ville.getNom().toLowerCase().startsWith(choix.toLowerCase())) {
//				System.out.println(ville);
//			}
//		}
	}
}
