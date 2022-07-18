package br.org.pavlov.simple.dao;

import br.org.pavlov.simple.codec.UsuarioIO;

public class UsuarioDAOTest extends UsuarioDAO {

	public UsuarioDAOTest() {
		this.codec = UsuarioIO.getTestInstance();
	}
}