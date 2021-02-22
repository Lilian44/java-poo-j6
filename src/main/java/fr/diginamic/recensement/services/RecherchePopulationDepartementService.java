package fr.diginamic.recensement.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;

/** Recherche et affichage de la population d'un département
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationDepartementService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) {
		
		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();
		
		if (!NumberUtils.isDigits(choix)) {
			throw new RuntimeException("Le département doit être un entier.");
		}
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


		try {
			Connection connection = DriverManager.getConnection(url, root, pass);

			Statement monStatement = connection.createStatement();
			ResultSet curseur = monStatement.executeQuery("SELECT * FROM villes Where code_departement= '"+choix+"'");
			int popTotale=0;
			while(curseur.next()) {
				Integer pop = curseur.getInt("population");
				popTotale+=pop;
			}
			System.out.println(  popTotale +" habitants dans le département "+choix);
			
			curseur.close();
			monStatement.close();
			connection.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		List<Ville> villes = rec.getVilles();
//		int somme = 0;
//		for (Ville ville: villes){
//			if (ville.getCodeDepartement().equalsIgnoreCase(choix)){
//				somme+=ville.getPopulation();
//			}
//		}
//		if (somme>0){
//			System.out.println("Population du département "+choix+" : "+ somme);
//		}
//		else {
//			System.out.println("Département "+choix+ " non trouvé.");
//		}
	}
}
