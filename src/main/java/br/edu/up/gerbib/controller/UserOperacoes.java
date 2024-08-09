package br.edu.up.gerbib.controller;

import java.io.IOException;

public class UserOperacoes implements BibliotecaOperacoes {
	private final String userFilePath;
	private final String admFilePath;

	public UserOperacoes(String admFilePath, String userFilePath) {
		this.admFilePath = admFilePath;
		this.userFilePath = userFilePath;
	}

	@Override
	public void adicionarLivro(String nomeLivro) throws IOException {
		FileManager.adicionarLivroUser(admFilePath, userFilePath, nomeLivro);
	}

	@Override
	public void removerLivro(String nomeLivro) throws IOException {
		FileManager.removerLivroUser(userFilePath, nomeLivro);
	}

	@Override
	public void buscarLivro(String nomeLivro) throws IOException {
		FileManager.pesquisarLivroUser(userFilePath, nomeLivro);
	}

	@Override
	public void listarLivros() throws IOException {
		FileManager.listarLivroUser(userFilePath);
	}
}