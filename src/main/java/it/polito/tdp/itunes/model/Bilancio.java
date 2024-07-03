package it.polito.tdp.itunes.model;

import java.util.Objects;

public class Bilancio implements Comparable<Bilancio> {
	private Album a;
	private int bilancio;
	
	public Bilancio(Album a, int bilancio) {
		super();
		this.a = a;
		this.bilancio = bilancio;
	}

	public Album getA() {
		return a;
	}

	public void setA(Album a) {
		this.a = a;
	}

	public int getBilancio() {
		return bilancio;
	}

	public void setBilancio(int bilancio) {
		this.bilancio = bilancio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, bilancio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bilancio other = (Bilancio) obj;
		return Objects.equals(a, other.a) && bilancio == other.bilancio;
	}

	@Override
	public int compareTo(Bilancio o) {
		// TODO Auto-generated method stub
		return o.bilancio-this.bilancio;
	}

	@Override
	public String toString() {
		return a + " - " + bilancio ;
	}
	
	
	
	
	
	

}
