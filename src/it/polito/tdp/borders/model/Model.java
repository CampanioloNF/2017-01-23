package it.polito.tdp.borders.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO dao;
	private Graph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> idCountryMap;
	private Simulatore sim;
	public Model() {
		this.dao = new BordersDAO();
		this.sim = new Simulatore();
	}
	
	public List<Country> creaGrafo(int anno) {
		// TODO Auto-generated method stub
	   this.grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
	   this.idCountryMap = new HashMap<Integer, Country>();
	   
	   dao.loadGraphs(grafo, anno, idCountryMap);
	   
	   List<Country> vertici = new LinkedList<Country>(grafo.vertexSet());
	   Collections.sort(vertici);
	   return vertici;
	}
	
	public String simulate(Country partenza) {
		
		  sim.init(grafo, partenza);
		  sim.run();
		  return sim.stats();
		
	}

}
