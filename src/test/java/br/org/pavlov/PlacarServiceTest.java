package br.org.pavlov;

import static br.org.pavlov.constants.MessageRef.SERV_RECUPERACAO_PONTOS;
import static br.org.pavlov.constants.MessageRef.SERV_REGISTRO_SUCESSO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.org.pavlov.simple.dao.UsuarioDAO;
import br.org.pavlov.simple.model.Usuario;
import br.org.pavlov.simple.service.PlacarService;

public class PlacarServiceTest {

	UsuarioDAO daoUsuario;
	PlacarService placarService;

	Usuario param;

	static final String ESTRELA = "estrela";
	static final String MOEDA = "moeda";
	static final Integer QNT_PADRAO = 10;

	@BeforeEach
	public void setup() {
		daoUsuario = new UsuarioDAO();

		placarService = new PlacarService();

		param = new Usuario(1, "ESVRENIO");
		param.add(MOEDA, 30);
	}

	/**
	 * Este teste precisa registrar os pontos de um unico usuario
	 * @throws Exception
	 */
	@Test
	public void testRegistrarPontos() throws Exception {
		// Chamada do servico aqui
		String msg = placarService.receberPontos(param.getNome(), 10, "estrela");

		// MSG esperada
		String expMsg = SERV_REGISTRO_SUCESSO.formatMsg(SERV_REGISTRO_SUCESSO, param.getNome(), QNT_PADRAO, ESTRELA);

		assertEquals(expMsg, msg);
	}

	@Test
	public void testRecuperarPontosUsuario() {
		// Chamada do servico aqui
		String msg = "";

		// MSG esperada
		String expMsg = String.format(SERV_RECUPERACAO_PONTOS.msg, ESTRELA, QNT_PADRAO);

		assertEquals(expMsg, msg);
	}

	@Test
	public void testRetornarUsuariosComPontos() {
		// Chamada do servico aqui
		List<Usuario> usuarios = new ArrayList<>();

		assertEquals(1, usuarios.size());
		assertEquals(param.getNome(), usuarios.get(0).getNome());
	}

	@Test
	public void testRetornarTiposDePontosJaRegistradosPorUsuario() {
	}
	
}
