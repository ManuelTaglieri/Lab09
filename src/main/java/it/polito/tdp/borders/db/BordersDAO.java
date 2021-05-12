package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public Map<Integer, Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		Map<Integer, Country> result = new HashMap<Integer, Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.put(rs.getInt("ccode"),new Country(rs.getInt("ccode"), rs.getString("StateNme")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {
		
		String sql = "SELECT state1no, state2no "
				+ "FROM contiguity cg "
				+ "WHERE conttype=1 AND cg.year<=? AND state1no>state2no";
		
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Border(rs.getInt("state1no"), rs.getInt("state2no")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
}
