package br.edu.up.gerbib.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.edu.up.gerbib.exception.Exceptions;
import br.edu.up.gerbib.model.Autor;
import br.edu.up.gerbib.model.Editora;
import br.edu.up.gerbib.model.Genero;
import br.edu.up.gerbib.model.Livro;

public final class FileManager {

	public static void createDir(File dir) {
		if (!dir.exists()) {
			boolean created = dir.mkdir();
			if (!created) {
				System.err.println("Não foi possível criar o diretório: " + dir.getAbsolutePath());
			}
		}
	}

	public static void createFileAdm(File dir, String fileName) {
		try {
			File fileAdm = new File(dir, fileName);
			if (!fileAdm.exists()) {
				fileAdm.createNewFile();
			}
		} catch (IOException e) {
			Exceptions.CreateFileException(e);
		}
	}

	public static void createFileUser(File dir, String fileName) {
		try {
			File fileUser = new File(dir, fileName);
			if (!fileUser.exists()) {
				fileUser.createNewFile();
			}
		} catch (IOException e) {
			Exceptions.CreateFileException(e);
		}
	}

	public static void writeToFile(String path, Livro livro) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
			String line = livro.getNomeLivro() + "," + livro.getAutor().getNome() + "," + livro.getGenero().getGen()
					+ "," + livro.getEditora().getEdit() + ";";
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			Exceptions.WriteToFileException(e);
		}
	}

	public static void removeFromFile(String path, Livro livro) {
		try {
			List<String> linhasTemporarias = new ArrayList<>();
			try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
				String linha;
				while ((linha = reader.readLine()) != null) {
					linhasTemporarias.add(linha);
				}
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
				boolean livroRemovido = false;
				for (String linhaTemporaria : linhasTemporarias) {
					String[] partes = linhaTemporaria.split(",");
					if (!partes[0].trim().equals(livro.getNomeLivro())) {
						writer.write(linhaTemporaria);
						writer.newLine();
					} else {
						livroRemovido = true;
					}
				}
				if (livroRemovido) {
					System.out.println("Livro removido com sucesso!");
				} else {
					System.out.println("Livro não encontrado no banco de dados");
				}
			}
		} catch (IOException e) {
			Exceptions.RemoveFromFileException(e);
		}
	}

	public static void listarLivros(String path) {
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] partes = line.split(",");
				if (partes.length >= 4) {
					String nomeLivro = partes[0].trim();
					String autor = partes[1].trim();
					String genero = partes[2].trim();
					String editora = partes[3].trim();

					Livro livro = new Livro(new Autor(autor), new Genero(genero), new Editora(editora), nomeLivro);

					System.out.println("Nome do Livro: " + livro.getNomeLivro());
					System.out.println("Autor: " + livro.getAutor().getNome());
					System.out.println("Gênero: " + livro.getGenero().getGen());
					System.out.println("Editora: " + livro.getEditora().getEdit());
					System.out.println("---------------------------------------");
				}
			}
		} catch (IOException e) {
			Exceptions.ReadFileException(e);
		}
	}

	public static void buscarLivro(String path, String nomeLivro) {
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line;
			boolean livroEncontrado = false;

			while ((line = reader.readLine()) != null) {
				String[] partes = line.split(",");
				if (partes.length >= 4) {
					String nomeLivroEncontrado = partes[0].trim();
					String autor = partes[1].trim();
					String genero = partes[2].trim();
					String editora = partes[3].trim();

					if (nomeLivroEncontrado.equalsIgnoreCase(nomeLivro)) {
						Livro livro = new Livro(new Autor(autor), new Genero(genero), new Editora(editora),
								nomeLivroEncontrado);

						System.out.println("Livro encontrado:");
						System.out.println("Nome do Livro: " + livro.getNomeLivro());
						System.out.println("Autor: " + livro.getAutor().getNome());
						System.out.println("Gênero: " + livro.getGenero().getGen());
						System.out.println("Editora: " + livro.getEditora().getEdit());

						livroEncontrado = true;
						break;
					}
				}
			}

			if (!livroEncontrado) {
				System.out.println("Livro não encontrado.");
			}
		} catch (IOException e) {
			Exceptions.ReadFileException(e);
		}
	}

	public static void adicionarLivroUser(String pathAdm, String pathUser, String nomeLivro) {
		try {
			boolean livroEncontrado = verificarLivroExistente(pathAdm, nomeLivro);

			if (livroEncontrado) {
				copiarLivroParaUsuario(pathAdm, pathUser, nomeLivro);
				System.out.println("Livro adicionado com sucesso!");
			} else {
				System.out.println(
						"Livro não encontrado no banco de dados do administrador. Não foi possível adicionar.");
			}
		} catch (IOException e) {
			Exceptions.GeneralIOException(e);
		}
	}

	private static boolean verificarLivroExistente(String path, String nomeLivro) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] partes = line.split(",");
				if (partes.length >= 1) {
					String nomeLivroEncontrado = partes[0].trim();
					if (nomeLivroEncontrado.equalsIgnoreCase(nomeLivro)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static void copiarLivroParaUsuario(String pathAdm, String pathUser, String nomeLivro) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(pathAdm));
				BufferedWriter writer = new BufferedWriter(new FileWriter(pathUser, true))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] partes = line.split(",");
				if (partes.length >= 1) {
					String nomeLivroEncontrado = partes[0].trim();
					if (nomeLivroEncontrado.equalsIgnoreCase(nomeLivro)) {
						writer.write(line);
						writer.newLine();
					}
				}
			}
		}
	}

	public static void removerLivroUser(String pathUser, String nomeLivro) {
		try {
			boolean livroRemovido = removeFromFile(pathUser, nomeLivro);
			if (livroRemovido) {
				System.out.println("Livro removido com sucesso!");
			} else {
				System.out.println("Livro não encontrado na sua biblioteca. Nada foi removido.");
			}
		} catch (IOException e) {
			Exceptions.RemoveFromFileException(e);
		}
	}

	private static boolean removeFromFile(String path, String nomeLivro) throws IOException {
		List<String> linhasTemporarias = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String linha;
			boolean livroRemovido = false;
			while ((linha = reader.readLine()) != null) {
				String[] partes = linha.split(",");
				if (partes.length >= 1) {
					String nomeLivroEncontrado = partes[0].trim();
					if (!nomeLivroEncontrado.equalsIgnoreCase(nomeLivro)) {
						linhasTemporarias.add(linha);
					} else {
						livroRemovido = true;
					}
				}
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
				for (String linhaTemporaria : linhasTemporarias) {
					writer.write(linhaTemporaria);
					writer.newLine();
				}
			}
			return livroRemovido;
		}
	}

	public static void pesquisarLivroUser(String pathUser, String nomeLivro) {
		try (BufferedReader reader = new BufferedReader(new FileReader(pathUser))) {
			String line;
			boolean livroEncontrado = false;

			while ((line = reader.readLine()) != null) {
				String[] partes = line.split(",");
				if (partes.length >= 1) {
					String nomeLivroEncontrado = partes[0].trim();
					if (nomeLivroEncontrado.equalsIgnoreCase(nomeLivro)) {
						System.out.println("Livro encontrado na sua biblioteca:");
						System.out.println(line);
						livroEncontrado = true;
						break;
					}
				}
			}

			if (!livroEncontrado) {
				System.out.println("Livro não encontrado na sua biblioteca.");
			}
		} catch (IOException e) {
			Exceptions.ReadFileException(e);
		}
	}

	public static void listarLivrosUser(String pathUser) {
		try (BufferedReader reader = new BufferedReader(new FileReader(pathUser))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] partes = line.split(",");
				if (partes.length >= 4) {
					String nomeLivro = partes[0].trim();
					String autor = partes[1].trim();
					String genero = partes[2].trim();
					String editora = partes[3].trim();

					System.out.println("Nome do Livro: " + nomeLivro);
					System.out.println("Autor: " + autor);
					System.out.println("Gênero: " + genero);
					System.out.println("Editora: " + editora);
					System.out.println("---------------------------------------");
				}
			}
		} catch (IOException e) {
			Exceptions.ReadFileException(e);
		}
	}

	public static void listarLivroUser(String pathUser) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(pathUser));
		String line;
		boolean livrosEncontrados = false;

		while ((line = reader.readLine()) != null) {
			String[] partes = line.split(",");

			if (partes.length >= 4) {
				String nomeLivro = partes[0].trim();
				String autor = partes[1].trim();
				String genero = partes[2].trim();
				String editora = partes[3].trim();

				Livro livro = new Livro(new Autor(autor), new Genero(genero), new Editora(editora), nomeLivro);

				System.out.println("Nome do Livro: " + livro.getNomeLivro());
				System.out.println("Autor: " + livro.getAutor().getNome());
				System.out.println("Gênero: " + livro.getGenero().getGen());
				System.out.println("Editora: " + livro.getEditora().getEdit());
				System.out.println("---------------------------------------");

				livrosEncontrados = true;
			}
		}

		if (!livrosEncontrados) {
			System.out.println("Não existem livros na biblioteca.");
		}

		reader.close();
	}

}