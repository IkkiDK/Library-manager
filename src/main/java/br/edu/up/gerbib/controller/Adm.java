package br.edu.up.gerbib.controller;

import java.util.HashMap;

public class Adm extends Login {
	private static HashMap<String, String> cadastro_adm;

	public Adm(String adm, String senha) {
		super(adm, senha);
		if (cadastro_adm == null) {
			cadastro_adm = new HashMap<>();
		}
	}

	public Adm() {
		if (cadastro_adm == null) {
			cadastro_adm = new HashMap<>();
		}
	}

	@Override
	public boolean autenticar(String adm, String senha) {
		if (cadastro_adm == null || !cadastro_adm.containsKey(adm)) {
			System.out.println("Administrador não existe.");
			return false;
		}

		String senhaCadastrada = cadastro_adm.get(adm);
		return senhaCadastrada.equals(senha);
	}

	@Override
	public void add() {
		cadastro_adm.put(super.getLogin(), super.getSenha());
	}

	public void add_adm(String senhaMestraDigitada) {
		if (senhaMestraDigitada.equals(super.getSenhaMestra())) {
			add();
			System.out.println("Novo administrador cadastrado com sucesso!");
		} else {
			System.out.println("Senha mestra incorreta! Não é possível adicionar um novo administrador.");
		}
	}
}
