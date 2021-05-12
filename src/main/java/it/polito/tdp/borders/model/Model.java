package it.polito.tdp.borders.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country, DefaultEdge> grafo;
	private BordersDAO dao;
	private Map<Integer, Country> paesi;

	public Model() {
		
		this.dao = new BordersDAO();
		this.paesi = new HashMap<>();
		this.paesi.putAll(dao.loadAllCountries());
		
	}

	public Graph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}

	public Map<Integer, Country> getPaesi() {
		return paesi;
	}

	public void createGraph(int anno) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		List<Border> confini = dao.getCountryPairs(anno);
		
		for (Border b : confini) {
			this.grafo.addVertex(paesi.get(b.getCod1()));
			this.grafo.addVertex(paesi.get(b.getCod2()));
			
			this.grafo.addEdge(paesi.get(b.getCod1()), paesi.get(b.getCod2()));
			
		}
		
		System.out.println("Numero di vertici: "+this.grafo.vertexSet().size()+", numero di archi: "+this.grafo.edgeSet().size());
	}

	public int getConnectedComponents() {
		
		ConnectivityInspector<Country, DefaultEdge> ispettore = new ConnectivityInspector<>(this.grafo);
		
		return ispettore.connectedSets().size();	
		
	}

	public Collection<Country> getCountries() {
		return paesi.values();
	}
}
