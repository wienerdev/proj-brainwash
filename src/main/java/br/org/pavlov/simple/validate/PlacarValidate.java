package br.org.pavlov.simple.validate;

import br.org.pavlov.constants.MessageRef;
import br.org.pavlov.simple.dao.UsuarioDAO;

public class PlacarValidate {

	UsuarioDAO usuarioDAO = new UsuarioDAO();

	public PlacarValidate validar(String tipoPonto, String nomeUsuario) throws Exception {
		return this.validarTipoPonto(tipoPonto).validarUsuario(nomeUsuario);
	}

	public PlacarValidate validar(String tipoPonto, Integer idUsuario) throws Exception {
		return this.validarTipoPonto(tipoPonto).validarUsuario(idUsuario);
	}

	public PlacarValidate validarTipoPonto(String tipoPonto) throws Exception {
		if(isTipoPontoInvalido(tipoPonto)) {
			throw new Exception(String.format(MessageRef.ERRO_TIPO_PONTO_NULO_OU_VAZIO.msg));
		}
		return this;
	}

	public PlacarValidate validarUsuario(String nomeUsuario) throws Exception {
		if(isUsuarioInvalido(nomeUsuario)) {
			throw new Exception(String.format(MessageRef.ERRO_NOME_USUARIO_INVALIDO.msg, nomeUsuario));
		}
		return this;
	}

	public PlacarValidate validarUsuario(Integer idUsuario) throws Exception {
		if(isUsuarioInvalido(idUsuario)) {
			throw new Exception(String.format(MessageRef.ERRO_ID_USUARIO_INVALIDO.msg, idUsuario));
		}
		return this;
	}

	private boolean isTipoPontoInvalido(String tipoPonto) {
		return tipoPonto == null || tipoPonto == "";
	}

	private boolean isUsuarioInvalido(String nomeUsuario) {
		return !usuarioDAO.listAll().stream().filter(usuario -> nomeUsuario.equals(usuario.getNome())).findFirst()
				.isPresent();
	}

    private boolean isUsuarioInvalido(Integer id) {
        return usuarioDAO.findByID(id) == null;
	}
}