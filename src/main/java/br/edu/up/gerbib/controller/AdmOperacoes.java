package br.edu.up.gerbib.controller;

import java.io.IOException;

import br.edu.up.gerbib.model.Autor;
import br.edu.up.gerbib.model.Editora;
import br.edu.up.gerbib.model.Genero;
import br.edu.up.gerbib.model.Livro;

public class AdmOperacoes implements BibliotecaOperacoes {
	private final String admFilePath;
	private final String userFilePath;

	public AdmOperacoes(String admFilePath, String userFilePath) {
		this.admFilePath = admFilePath;
		this.userFilePath = userFilePath;
	}

	@Override
	public void adicionarLivro(String nomeLivro) throws IOException {
		Livro livro = new Livro(new Autor("Autor"), new Genero("Genero"), new Editora("Editora"), nomeLivro);
		FileManager.writeToFile(admFilePath, livro);
	}

	@Override
	public void removerLivro(String nomeLivro) throws IOException {
		Livro livroParaRemover = new Livro(null, null, null, nomeLivro);
		FileManager.removeFromFile(admFilePath, livroParaRemover);
		FileManager.removerLivroUser(userFilePath, nomeLivro);
	}

	@Override
	public void buscarLivro(String nomeLivro) throws IOException {
		FileManager.buscarLivro(admFilePath, nomeLivro);
	}

	@Override
	public void listarLivros() throws IOException {
		FileManager.listarLivros(admFilePath);
	}
}
