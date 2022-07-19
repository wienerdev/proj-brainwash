package br.org.pavlov.simple.service;

import br.org.pavlov.constants.MessageRef;
import br.org.pavlov.simple.dao.UsuarioDAO;
import br.org.pavlov.simple.model.Usuario;
import br.org.pavlov.simple.validate.PlacarValidate;

public class PlacarService {
    
    UsuarioDAO dao = new UsuarioDAO();

    PlacarValidate validate = new PlacarValidate();

    public String receberPontos(String nomeUsuario, Integer quantidadePontos, String tipoPonto) throws Exception {
        validate.validar(tipoPonto, nomeUsuario);

        Usuario usuario = dao.findByName(nomeUsuario);

        if (usuario == null) {
            throw new Exception(MessageRef.ERRO_USUARIO_NULO.formatMsg(nomeUsuario));
        }

        usuario.add(tipoPonto, quantidadePontos);

        return MessageRef.SERV_REGISTRO_SUCESSO.formatMsg(usuario.getNome(), quantidadePontos, tipoPonto);
    }

    public String receberPontos(Integer idUsuario, Integer quantidadePontos, String tipoPonto) throws Exception {
        validate.validar(tipoPonto, idUsuario);

        Usuario usuario = dao.findByID(idUsuario);

        if (usuario == null) {
            throw new Exception(MessageRef.ERRO_USUARIO_NULO.formatMsg(idUsuario));
        }

        usuario.add(tipoPonto, quantidadePontos);

        dao.save(usuario);

        return MessageRef.SERV_REGISTRO_SUCESSO.formatMsg(usuario.getNome(), quantidadePontos, tipoPonto);
    }

    public String recuperarPontos(String nomeUsuario, String tipoPonto) throws Exception {
        validate.validar(tipoPonto, nomeUsuario);

        Usuario usuario = dao.findByName(nomeUsuario);

        if (usuario == null) {
            throw new Exception(MessageRef.ERRO_USUARIO_NULO.formatMsg(nomeUsuario));
        }

        if ("moeda".equalsIgnoreCase(tipoPonto)) {
            return MessageRef.SERV_RECUPERACAO_PONTOS.formatMsg(tipoPonto, usuario.getPontuacao().get("moeda").intValue());
        }

        if ("estrela".equalsIgnoreCase(tipoPonto)) {
            return MessageRef.SERV_RECUPERACAO_PONTOS.formatMsg(tipoPonto, usuario.getPontuacao().get("estrela").intValue());
        }

        return MessageRef.ERRO_TIPO_INVALIDO.msg;
    }

    public void recuperarUsuariosQuePossuemPontos() {

        dao.listAll().forEach(usuario -> {
            usuario.getPontuacao().forEach((tipo, quantidade) -> {
                if (quantidade > 0) {
                    System.out.println("- " + usuario.getNome() + " | " + quantidade + " pontos do tipo " + tipo);
                }
            });
        });
    }

    public void recuperarTipoPontosRegistrados() {

        dao.listAll().forEach(usuario -> {
            usuario.getPontuacao().forEach((tipo, quantidade) -> {
                if (quantidade > 0) {
                    System.out.println("- "+tipo);
                }
            });
        });
    }
}
