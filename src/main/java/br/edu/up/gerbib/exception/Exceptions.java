package br.edu.up.gerbib.exception;

import java.io.IOException;

public class Exceptions {

	public static void CreateDirException(IOException e) {
		System.err.println("Erro ao criar o diretório: " + e.getMessage());
		e.printStackTrace();
	}

	public static void CreateFileException(IOException e) {
		System.err.println("Erro ao criar o arquivo: " + e.getMessage());
		e.printStackTrace();
	}

	public static void WriteToFileException(IOException e) {
		System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
		e.printStackTrace();
	}

	public static void ReadFileException(IOException e) {
		System.err.println("Erro ao ler o arquivo: " + e.getMessage());
		e.printStackTrace();
	}

	public static void RemoveFromFileException(IOException e) {
		System.err.println("Erro ao remover do arquivo: " + e.getMessage());
		e.printStackTrace();
	}

	public static void GeneralIOException(IOException e) {
		System.err.println("Erro Geral: " + e.getMessage());
		e.printStackTrace();
	}
}
