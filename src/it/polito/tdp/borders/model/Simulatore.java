package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {

	//parametri
	private static final int MIGRANTI = 1000;
    private Graph<Country, DefaultEdge> grafo;
    
    //variabili interne
    private int passo;
 	private PriorityQueue<Evento> queue;  
    //statistiche
    private Map<Country, Stats> stats; // da aggiornare riguardo la presenza di migranti
    
	public void init(Graph<Country, DefaultEdge> grafo, Country partenza) {
		// TODO Auto-generated method stub

		this.grafo = grafo;
		this.passo = 0;
		this.stats = new HashMap<Country, Stats>();
		
		for(Country c : grafo.vertexSet())
			stats.put(c, new Stats(c, 0));
		
		this.queue = new PriorityQueue<Evento>();
		queue.add(new Evento(passo, partenza, MIGRANTI));
		
	}

	public void run() {
		
		while(!queue.isEmpty()) {
			
			//abbiamo un evento
			Evento ev = queue.poll(); 
			
			Country stato = ev.getStato();
			
			if(ev.getPasso()>passo)
				passo = ev.getPasso();
			
			// se possiamo mandarli negli stati vicini
			List<Country> vicini = new ArrayList<Country>(Graphs.neighborListOf(grafo, stato));
			   
			  //migranti per neighboor
			int mpn = (int)(ev.getNumero()/2)/vicini.size();
			
			if(mpn > 0) {
				
				//un po' sono stanzianti
				int ms = ev.getNumero()-mpn*vicini.size();
				stats.get(stato).addMigranti(ms);
				//per ogni vicino creo un evento
				for(Country c : vicini)
                  queue.add(new Evento(ev.getPasso()+1, c, mpn));				
			}
			else {
				
				int ms = ev.getNumero();
				stats.get(stato).addMigranti(ms);
				
			}
			//se lo stato è nuovo
			
		}
		
	}

	public String stats() {
		
		String ris ="";
		
		List<Stats> risultato = new LinkedList<>(stats.values()); 
		Collections.sort(risultato);
		for(Stats sta : risultato) {
			if(sta.getMigranti()>0)
               ris+=sta.getStato().getStateAbb()+" - "+sta.getStato().toString()+" - "+sta.getMigranti()+"\n";
	}
		return ris;
	}

}
