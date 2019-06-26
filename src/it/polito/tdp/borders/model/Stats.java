package it.polito.tdp.borders.model;

public class Stats implements Comparable<Stats>{

	private Country stato;
	private int migranti;
	public Stats(Country stato, int migranti) {
		super();
		this.stato = stato;
		this.migranti = migranti;
	}
	public Country getStato() {
		return stato;
	}
	public int getMigranti() {
		return migranti;
	}
	public void addMigranti(int migr) {
		this.migranti+=migr;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stats other = (Stats) obj;
		if (stato == null) {
			if (other.stato != null)
				return false;
		} else if (!stato.equals(other.stato))
			return false;
		return true;
	}
	@Override
	public int compareTo(Stats o) {
		// TODO Auto-generated method stub
		return o.migranti-this.migranti;
	}
	
	
	
}
