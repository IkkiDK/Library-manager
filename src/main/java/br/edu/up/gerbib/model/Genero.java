package br.edu.up.gerbib.model;

public class Genero {
	private String gen;

	public Genero(String gen) {
		this.gen = gen;
	}

	public String getGen() {
		return gen;
	}

	public void setGen(String gen) {
		this.gen = gen;
	}

	@Override
	public String toString() {
		return "Genero [gen=" + gen + "]";
	}
}
