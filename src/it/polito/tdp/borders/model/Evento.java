package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{

	private int passo; //serve ad ordinare gli eventi 
	private Country stato;
	private int numero;
	
	

	public Evento(int passo, Country stato, int numero) {
		super();
		this.passo = passo;
		this.stato = stato;
		this.numero = numero;
	}
	public int getPasso() {
		return passo;
	}
	public Country getStato() {
		return stato;
	}
	public int getNumero() {
		return numero;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.passo-o.passo;
	}
	
	
	
	
}
