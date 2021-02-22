package fr.diginamic.recensement.entites;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class IntegrationRecensement {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

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

			Path path = Paths.get("C:/workspace/java-poo-j6/src/main/resources/recensement.csv");

			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			Statement monStatement = connection.createStatement();

			for (int i = 1; i < lines.size(); i++) {
				String[] arr = lines.get(i).split(";");
				String codeRgn = arr[0];
				String nomRgn = arr[1];
				String codeDprtmt = arr[2];
				String codeCmne = arr[5];
				String nomCmne = arr[6];
				int pop = Integer.parseInt(arr[9].replaceAll(" ", ""));

				PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO "
						+ "villes(code_region, nom_region,code_departement,code_commune,nom_commune,population) "
						+ "VALUES (?, ?, ?, ?, ?, ?)");

				prepStatement.setString(1, codeRgn);
				prepStatement.setString(2, nomRgn);
				prepStatement.setString(3, codeDprtmt);
				prepStatement.setString(4, codeCmne);
				prepStatement.setString(5, nomCmne);
				prepStatement.setInt(6, pop);

				prepStatement.executeUpdate();
//				monStatement.executeUpdate(
//						"INSERT INTO villes (code_region,nom_region,code_departement,code_commune,nom_commune,population) VALUES ("
//								+'codeRgn'","'+nomRgn+'","'+codeDprtmt+'","'+codeCmne+'","'+nomCmne'",pop)" );
//								
//				arr[0], arr[1], arr[2], arr[5], arr[6],
//						Integer.parseInt(arr[9].replaceAll(" ", ""));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
