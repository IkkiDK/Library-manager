package br.edu.up.gerbib.view;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import br.edu.up.gerbib.controller.Adm;
import br.edu.up.gerbib.controller.AdmOperacoes;
import br.edu.up.gerbib.controller.FileManager;
import br.edu.up.gerbib.controller.User;
import br.edu.up.gerbib.controller.UserOperacoes;
import br.edu.up.gerbib.exception.Exceptions;
import br.edu.up.gerbib.model.Autor;
import br.edu.up.gerbib.model.Editora;
import br.edu.up.gerbib.model.Genero;
import br.edu.up.gerbib.model.Livro;

public class Main {
	
	 

	public static void main(String[] args) {
		
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		File dir = new File("C:\\Banco_de_dados");
		final String DEFAULT_FILE_NAME_ADM = "biblioteca_adm.txt";
		final String DEFAULT_FILE_NAME_USER = "biblioteca_user.txt";
		final String DEFAULT_FULL_PATH_ADM = "C:\\Banco_de_dados\\biblioteca_adm.txt";
		final String DEFAULT_FULL_PATH_USER = "C:\\Banco_de_dados\\biblioteca_user.txt";
        FileManager.createDir(dir);
        FileManager.createFileAdm(dir, DEFAULT_FILE_NAME_ADM);
        FileManager.createFileUser(dir, DEFAULT_FILE_NAME_USER);
		UserOperacoes userOperacoes = new UserOperacoes(DEFAULT_FULL_PATH_ADM, DEFAULT_FULL_PATH_USER);
		AdmOperacoes admOperacoes = new AdmOperacoes(DEFAULT_FULL_PATH_ADM, DEFAULT_FULL_PATH_USER);
		do {
			System.out.println("\n----- Faça seu login -----\n");
			System.out.println("Usuário - 1\n" + "Administrador - 2\n" + "Cadastrar novo usuário - 3\n"
					+ "Cadastrar novo administrador - 4");
			int op_1 = sc.nextInt();
			sc.nextLine();
			switch (op_1) {
				case 1:
					System.out.print("Login user: ");
					String user = sc.nextLine();
					System.out.print("Senha user: ");
					String senhaUser = sc.nextLine();
					User userLogin = new User();
					if (userLogin.autenticar(user, senhaUser)) {
						int op_2;
						do {
							System.out.println("\n----- Bem vindo à sua biblioteca virtual! -----");
							System.out.println("Escolha uma das opções: ");
							System.out.println("1 - Adicionar um livro");
							System.out.println("2 - remover um livro");
							System.out.println("3 - Pesquisar um livro");
							System.out.println("4 - Listar todos os seus livros");
							System.out.println("5 - Voltar para o menu incial");
							op_2 = sc.nextInt();
							sc.nextLine();

							switch (op_2) {
								case 1:
									char op_voltar_user;
									do {
										System.out.print("Digite o nome do livro que deseja adicionar: ");
										String nomeLivroAdicionar = sc.nextLine();
										try {
											userOperacoes.adicionarLivro(nomeLivroAdicionar);
										} catch (IOException e) {
											Exceptions.WriteToFileException(e); // Tratamento de exceção ao escrever em arquivo
										}
										System.out.println("Deseja adicionar mais um livro?(s/n)");
										op_voltar_user = sc.next().charAt(0);
										sc.nextLine();
									} while (op_voltar_user == 's');
									break;
								case 2:
									System.out.print("Digite o nome do livro que deseja remover: ");
									String nomeLivroRemover = sc.nextLine();
									try {
										userOperacoes.removerLivro(nomeLivroRemover);
									} catch (IOException e) {
										Exceptions.RemoveFromFileException(e); // Tratamento de exceção ao remover de arquivo
									}
									break;
								case 3:
									System.out.print("Digite o nome do livro que deseja pesquisar: ");
									String nomeLivroPesquisar = sc.nextLine();
									try {
										userOperacoes.buscarLivro(nomeLivroPesquisar);
									} catch (IOException e) {
										Exceptions.ReadFileException(e);
									}
									break;
								case 4:
                                    try {
                                        userOperacoes.listarLivros();
                                    } catch (IOException e) {
                                        Exceptions.ReadFileException(e);
                                    }
                                    break;
								default:
									break;
							}
						} while (op_2 != 5);
					} else {
						System.out.println("Falha na autenticação do usuário!");
					}
					break;
				case 2:
					System.out.println("Login adm: ");
					String adm = sc.nextLine();
					System.out.println("Senha adm: ");
					String senha_adm = sc.nextLine();

					Adm admLogin = new Adm();
					if (admLogin.autenticar(adm, senha_adm)) {
						int op_3;
						do {
							System.out.println("Gerenciamento do banco de dados da bibliota virtual");
							System.out.println("Escolha uma das opções: ");
							System.out.println("1 - Adicionar um livro");
							System.out.println("2 - remover um livro");
							System.out.println("3 - Pesquisar um livro");
							System.out.println("4 - Listar todos os livros cadastrados");
							System.out.println("5 - Voltar para o menu inicial");
							op_3 = sc.nextInt();
							sc.nextLine();

							switch (op_3) {
								case 1:
									char op_voltar;
									do {
										System.out.print("Digite o nome do Livro: \n");
										String nomeLivroAdm = sc.nextLine();
										System.out.print("Digite o Autor: \n");
										String autorAdm = sc.nextLine();
										System.out.print("Digite a editora: \n");
										String editoraAdm = sc.nextLine();
										System.out.print("Digite o gênero: \n");
										String generoAdm = sc.nextLine();
										Livro livro = new Livro(new Autor(autorAdm), new Genero(generoAdm),
												new Editora(editoraAdm), nomeLivroAdm);
                                        FileManager.writeToFile(DEFAULT_FULL_PATH_ADM, livro);
                                        System.out.println("Livro adicionado ao banco de dados");
										System.out.println("Deseja adicionar mais um livro?(s/n)");
										op_voltar = sc.next().charAt(0);
										sc.nextLine();
									} while (op_voltar == 's');
									break;
								case 2:
									System.out.println("Qual livro deseja remover?");
									String livroRemover = sc.nextLine();
									try {
										admOperacoes.removerLivro(livroRemover);
									} catch (IOException e) {
										Exceptions.RemoveFromFileException(e); // Tratamento de exceção ao remover de arquivo
									}
									break;
								case 3:
									System.out.println("Qual livro você deseja buscar?");
									String livroBuscar = sc.nextLine();
									try {
										admOperacoes.buscarLivro(livroBuscar);
									} catch (IOException e) {
										Exceptions.ReadFileException(e); // Tratamento de exceção ao ler arquivo
									}
									break;
								case 4:
                                    try {
                                        admOperacoes.listarLivros();
                                    } catch (IOException e) {
                                        Exceptions.ReadFileException(e);
                                    }
                                    break;
								default:
									break;
							}
						} while (op_3 != 5);
					} else {
						System.out.println("Falha na autenticação do administrador!");
					}
					break;
				case 3:
					System.out.print("Novo usuário - Digite o login: ");
					String novoUser = sc.nextLine();
					System.out.print("Novo usuário - Digite a senha: ");
					String novaSenhaUser = sc.nextLine();
					User novoUserObject = new User(novoUser, novaSenhaUser);
					novoUserObject.add();
					System.out.println("Novo usuário cadastrado com sucesso!");
					break;
				case 4:
					System.out.print("Novo administrador - Digite o login: ");
					String novoAdm = sc.nextLine();
					System.out.print("Novo administrador - Digite a senha: ");
					String novaSenhaAdm = sc.nextLine();
					Adm novoAdmObject = new Adm(novoAdm, novaSenhaAdm);
					System.out.print("Digite a senha mestra para criar um novo administrador: ");
					String senhaMestraDigitada = sc.nextLine();
					novoAdmObject.add_adm(senhaMestraDigitada);
					break;
				default:
					break;
			}
		} while (true);
	}
}
