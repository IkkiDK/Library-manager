package br.edu.up.gerbib.controller;

import java.util.HashMap;

public class User extends Login {
	private static HashMap<String, String> cadastro_user;

	public User(String user, String senha) {
		super(user, senha);
		if (cadastro_user == null) {
			cadastro_user = new HashMap<>();
		}
	}

	public User() {
		if (cadastro_user == null) {
			cadastro_user = new HashMap<>();
		}
	}

	@Override
	public boolean autenticar(String user, String senha) {
		if (cadastro_user == null || !cadastro_user.containsKey(user)) {
			System.out.println("Usuário não existe.");
			return false;
		}

		String senhaCadastrada = cadastro_user.get(user);
		return senhaCadastrada.equals(senha);
	}

	@Override
	public void add() {
		cadastro_user.put(super.getLogin(), super.getSenha());
	}
}

