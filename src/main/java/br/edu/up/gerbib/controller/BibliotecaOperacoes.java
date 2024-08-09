package br.edu.up.gerbib.controller;

import java.io.IOException;

public interface BibliotecaOperacoes {
	void adicionarLivro(String nomeLivro) throws IOException;

	void removerLivro(String nomeLivro) throws IOException;

	void buscarLivro(String nomeLivro) throws IOException;

	void listarLivros() throws IOException;
}
