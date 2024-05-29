package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	private List<Album> album;
	private Graph<Album, DefaultWeightedEdge> graph;
	private ItunesDAO dao;
	private List<Album> bestPath;
	private int best;
	
	public Model() {
		super();
		this.album = new ArrayList<>();
		this.graph = new SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge>( DefaultWeightedEdge.class);
		this.dao = new ItunesDAO();
	}
	
	public void creaGrafo(int n) {
		clearGraph();
		creaNodi(n);
		Graphs.addAllVertices(this.graph, this.album);
		
		
		for (Album a1 : this.album) {
			for (Album a2: this.album) {
				int peso = a1.getN()- a2.getN();
				
				if (peso>0) {
					Graphs.addEdgeWithVertices(this.graph, a2, a1, peso);
				}
			}
		}
		System.out.println(graph.vertexSet().size());
		System.out.println(graph.edgeSet().size());
	}
	
	private void clearGraph() {
		this.album = new ArrayList<>();
		this.graph = new SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge>( DefaultWeightedEdge.class);
	
	}

	public void creaNodi(int n) {
		if (album.isEmpty()) {
			this.album = dao.getFilteredAlbums(n);
		}
	}

	public int getNumVertici() {
		
		return this.graph.vertexSet().size();
	}

	public int getNumArchi() {
		// TODO Auto-generated method stub
		return this.graph.edgeSet().size();
	}
	
	private int getBilancio(Album a) {
		int bilancio =0;
		List <DefaultWeightedEdge> entranti = new ArrayList<>(graph.incomingEdgesOf(a));
		List <DefaultWeightedEdge> uscenti = new ArrayList<>(graph.outgoingEdgesOf(a));
		for (DefaultWeightedEdge edge: entranti) {
			bilancio+= this.graph.getEdgeWeight(edge);
		}
		for (DefaultWeightedEdge edge: uscenti) {
			bilancio-= this.graph.getEdgeWeight(edge);
		}
		return bilancio;
	}
	
	public List <Album> getAlbum(){
		List<Album> album = new ArrayList<>(this.graph.vertexSet());
		Collections.sort(album);
		return album;
	}
	
	public List<Bilancio> getAdiacenti(Album a ){
		List<Album> successori =Graphs.successorListOf(this.graph, a);
		List<Bilancio> bilanciSuccessori = new ArrayList<>();
		for (Album album: successori) {
			bilanciSuccessori.add(new Bilancio(album, getBilancio(album) ));
		}
		Collections.sort(bilanciSuccessori);
		return bilanciSuccessori;
	}
	
	public List<Album> getPercorso (Album a1, Album a2, int x){
		List<Album> parziale = new ArrayList<>();
		this.bestPath = new ArrayList<>();
		this.best =0;
		parziale.add(a1);
		ricorsione(parziale, a2, x );
		return this.bestPath;
	}

	private void ricorsione(List<Album> parziale, Album a2, int x) {
		Album corrente = parziale.get(parziale.size()-1);
		
		//condizione di uscita
		if (corrente.equals(a2)) {
			//controllo che la nuova condizione sia migliore di quella salvata
			if (getScore(parziale)> this.best) {
				this.best = getScore(parziale);
				this.bestPath = new ArrayList<>(parziale);
			}
			return;
		}
		//aggiungo gli elementi a parziale
		List<Album> successori =Graphs.successorListOf(this.graph, corrente);
		for (Album a : successori) {
			if (this.graph.getEdgeWeight(this.graph.getEdge(corrente, a))>= x) {
				parziale.add(a);
				ricorsione(parziale, a2, x);
				parziale.remove(a);
			}
			
		}
	}

	//score rappresenta in numero di vertici che hanno bilancio maggiore di quello di partenza
	private int getScore(List<Album> parziale) {
		Album a1 = parziale.get(0);
		int score =0;
		for (Album a : parziale.subList(1, parziale.size()-1)) {
			if (getBilancio(a) > getBilancio(a1)) {
				score +=1;
			}
		}
		return score;
	}
	
	
}
