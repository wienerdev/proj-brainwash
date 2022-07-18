package br.org.pavlov.simple.service;

import br.org.pavlov.constants.MessageRef;
import br.org.pavlov.simple.dao.UsuarioDAO;
import br.org.pavlov.simple.model.Usuario;
import br.org.pavlov.validate.PlacarValidate;

public class PlacarService {
    
    UsuarioDAO dao = new UsuarioDAO();

    PlacarValidate validate = new PlacarValidate();

    public String receberPontos(String nomeUsuario, Integer quantidadePontos, String tipoPontos) throws Exception {
        validate.validar(tipoPontos, nomeUsuario);

        Usuario usuario = dao.findByName(nomeUsuario);

        if (usuario == null) {
            throw new Exception(MessageRef.ERRO_USUARIO_NULO.formatMsg(nomeUsuario));
        }

        usuario.add(tipoPontos, quantidadePontos);

        return MessageRef.SERV_REGISTRO_SUCESSO.formatMsg(usuario.getNome(), quantidadePontos, tipoPontos);
    }
}
