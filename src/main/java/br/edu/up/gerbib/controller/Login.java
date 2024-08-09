package br.edu.up.gerbib.controller;

public abstract class Login {
	private static String senhaMestra = "123456";
	private String login;
	private String senha;

	public Login() {
	}

	public Login(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public static String getSenhaMestra() {
		return senhaMestra;
	}

	public String getSenha() {
		return senha;
	}

	public static boolean autenticar_senha_mestra(String senha) {
		return senha.equals(getSenhaMestra());
	}

	public abstract boolean autenticar(String login, String senha);

	public abstract void add();
}
