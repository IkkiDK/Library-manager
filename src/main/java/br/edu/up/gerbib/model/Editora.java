package br.edu.up.gerbib.model;

public class Editora {
	private String edit;

	public Editora(String edit) {
		this.edit = edit;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	@Override
	public String toString() {
		return "Editora [edit=" + edit + "]";
	}
}