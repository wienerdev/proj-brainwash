package br.org.pavlov.simple.service;

import static br.org.pavlov.constants.MessageRef.SERV_RECUPERACAO_PONTOS;
import static br.org.pavlov.constants.MessageRef.SERV_REGISTRO_SUCESSO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.org.pavlov.constants.MessageRef;
import br.org.pavlov.simple.dao.UsuarioDAOTest;
import br.org.pavlov.simple.model.Usuario;

public class PlacarServiceTest {

	static UsuarioDAOTest daoTest;
	PlacarService placarService;

	Usuario param;

	static final String ESTRELA = "estrela";
	static final String MOEDA = "moeda";
	static final Integer QNT_PADRAO_10 = 10;
	static final Integer QNT_PADRAO_15 = 15;

	@BeforeAll
	public static void buildUp() {
		daoTest = new UsuarioDAOTest();
		Usuario starter = new Usuario(1, "ESVRENIO");
		Usuario starter2 = new Usuario(2, "AENDOLINA");
		starter.add(MOEDA, QNT_PADRAO_10);
		starter.add(ESTRELA, QNT_PADRAO_15);
		daoTest.save(starter);
		daoTest.save(starter2);
	}

	/* @AfterAll
	public static void destroy() {
		daoUsuario.deleteAll();
	} */

	@BeforeEach
	public void setup() {
		placarService = new PlacarService();
		placarService.dao = daoTest;

		param = new Usuario(1, "ESVRENIO");
	}

	/**
	 * Este teste precisa registrar os pontos de um unico usuario pelo nome
	 * @throws Exception
	 */
	@Test
	public void testRegistrarPontosPorNome() throws Exception {
		// Chamada do servico aqui
		String msg = placarService.receberPontos(param.getNome(), 10, "estrela");

		// MSG esperada
		String expMsg = SERV_REGISTRO_SUCESSO.formatMsg(param.getNome(), QNT_PADRAO_10, ESTRELA);
		// String expMsg = String.format(SERV_REGISTRO_SUCESSO.msg, param.getNome(), QNT_PADRAO, ESTRELA);

		assertEquals(expMsg, msg);
	}

	/**
	 * Este teste precisa registrar os pontos de um unico usuario por ID
	 * @throws Exception
	 */
	@Test
	public void testRegistrarPontosPorId() throws Exception {
		// Chamada do servico aqui
		String msg = placarService.receberPontos(param.getId(), 10, "estrela");

		// MSG esperada
		String expMsg = SERV_REGISTRO_SUCESSO.formatMsg(param.getNome(), QNT_PADRAO_10, ESTRELA);

		assertEquals(expMsg, msg);
	}

	@Test
	public void testRecuperarPontosUsuarioTipoMoeda() throws Exception {
		// Chamada do servico aqui
		String msg = placarService.recuperarPontos(param.getNome(), MOEDA);

		// MSG esperada
		String expMsg = SERV_RECUPERACAO_PONTOS.formatMsg(MOEDA, QNT_PADRAO_10);

		assertEquals(expMsg, msg);
	}

	@Test
	public void testRecuperarPontosUsuarioTipoEstrela() throws Exception {
		// Chamada do servico aqui
		String msg = placarService.recuperarPontos(param.getNome(), ESTRELA);

		// MSG esperada
		String expMsg = SERV_RECUPERACAO_PONTOS.formatMsg(ESTRELA, 15);

		assertEquals(expMsg, msg);
	}

	@Test
	public void testRecuperarPontosUsuarioTipoInvalido() throws Exception {
		// Chamada do servico aqui
		String msg = placarService.recuperarPontos(param.getNome(), "banana");

		// MSG esperada
		String expMsg = MessageRef.ERRO_TIPO_INVALIDO.msg;

		assertEquals(expMsg, msg);
	}

	@Test
	public void testRetornarUsuariosComPontos() {
		// Chamada do servico aqui
		placarService.recuperarUsuariosQuePossuemPontos();
		
		List<Usuario> listaUsuarioComPontos = daoTest.listAll().stream().filter(usuario -> usuario.getPontuacao().size() > 0).collect(Collectors.toList());
		
		assertEquals("ESVRENIO", listaUsuarioComPontos.get(0).getNome());
		assertEquals("ETIVALDA", listaUsuarioComPontos.get(1).getNome());
	}

	@Test
	public void testRetornarTiposDePontosJaRegistradosPorUsuario() {

		placarService.recuperarTipoPontosRegistrados();

		List<Usuario> listaUsuarioComPontos = daoTest.listAll().stream().filter(usuario -> usuario.getPontuacao().size() > 0).collect(Collectors.toList());

		assertEquals("[moeda, estrela]", listaUsuarioComPontos.get(0).getPontuacao().keySet().toString());
	}
	
}
