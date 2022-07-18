package br.org.pavlov.simple.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import br.org.pavlov.tools.Utils;

public class Usuario {
	
	private Integer id;
	private String nome;

	private final Map<String, Integer> pontuacao = new HashMap<>();

	private Integer saldoPontos = 0;

	public Usuario(Integer id, String nome, Integer saldoPontos) {
		this.id = id;
		this.nome = nome;
		this.saldoPontos = saldoPontos;
	}

	public Usuario(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Usuario(String nome) {
		this.nome = nome;
	}
	
	public Usuario() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void add(String tipo, Integer qntPontos) {
		if(Utils.isBlank(tipo) || qntPontos == null) {
			return;
		}

		this.pontuacao.put(tipo, 
			Optional.ofNullable(this.pontuacao.get(tipo))
				.orElse(0)
			+ qntPontos
		);

		this.saldoPontos += qntPontos;
	}

	public Map<String, Integer> getPontuacao() {
		return pontuacao;
	}

	public void limparPontuacao() {
		this.pontuacao.clear();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

}
