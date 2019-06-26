package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class BordersDAO {
	
	public List<Country> loadAllCountries() {
		
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				
				list.add(c) ;
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	public static void main(String[] args) {
		List<Country> list ;
		BordersDAO dao = new BordersDAO() ;
		list = dao.loadAllCountries() ;
		for(Country c: list) {
			System.out.println(c);
		}
	}

	public void loadGraphs(Graph<Country, DefaultEdge> grafo, int anno, Map<Integer, Country> idCountryMap) {
	
		String sql = "SELECT c.state1no, co1.StateAbb, co1.StateNme, c.state2no, co2.StateAbb, co2.StateNme " + 
				"FROM contiguity c, country co1, country co2 " + 
				"WHERE c.YEAR <= ? AND c.conttype = 1 AND co1.CCode = c.state1no AND co2.CCode = c.state2no " + 
				"AND c.state2no> c.state1no " + 
				"GROUP BY c.state1no, c.state2no ";
		
		try {
			
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery() ;
			
			
			
			while( rs.next() ) {
				
				int c1Id = rs.getInt("c.state1no");
				int c2Id = rs.getInt("c.state2no");
				

				if(!idCountryMap.containsKey(c1Id)) {
					Country c = new Country(
							c1Id,
							rs.getString("co1.StateAbb"), 
			                rs.getString("co1.StateNme")) ;
					
					idCountryMap.put(c.getcCode(), c);
					grafo.addVertex(c);
					
				}
				
				if(!idCountryMap.containsKey(c2Id)) {
					Country c = new Country(
							c2Id,
							rs.getString("co2.StateAbb"), 
			                rs.getString("co2.StateNme")) ;
					
					idCountryMap.put(c.getcCode(), c);
					grafo.addVertex(c);
					
				}
				
				grafo.addEdge(idCountryMap.get(c1Id), idCountryMap.get(c2Id));
				
			}
			
			conn.close() ;
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
