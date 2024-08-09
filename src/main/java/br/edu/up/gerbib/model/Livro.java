package br.edu.up.gerbib.model;

public class Livro {
	private String nomeLivro;
	private Autor autor;
	private Genero genero;
	private Editora editora;

	public Livro() {
	}

	public Livro(Autor autor, Genero genero, Editora editora, String nomeLivro) {
		this.autor = autor;
		this.genero = genero;
		this.editora = editora;
		this.nomeLivro = nomeLivro;
	}

	public String getNomeLivro() {
		return nomeLivro;
	}

	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	@Override
	public String toString() {
		return "Livro [nomeLivro=" + nomeLivro + ", autor=" + autor + ", genero=" + genero + ", editora=" + editora
				+ "]";
	}
}
