package br.org.pavlov.simple.dao;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.pavlov.simple.codec.UsuarioIO;
import br.org.pavlov.simple.model.Usuario;

public class UsuarioDAO {
	
	private UsuarioIO codec;

	private static final Map<Integer,Usuario> content = UsuarioIO.getInstance().retrieveAsIdMap();
	private static Integer lastId = content.keySet().stream()
		.mapToInt(key -> key)
		.max().orElse(0);

	public UsuarioDAO() {
		codec = UsuarioIO.getInstance();
	}

	public Collection<Usuario> listAll() {
		return content.values();
	}

	public Usuario findByID(Integer id) {
		return content.get(id);
	}

	public Usuario findByName(String nomeUsuario) {
		return content.values().stream()
		.filter(usuario -> nomeUsuario.equals(usuario.getNome()))
		.findFirst().orElse(null);
	}

	public void save(Usuario usuario) {
		if(usuario == null) {
			return;
		}
		boolean isNew = usuario.getId() == null;
		if(isNew) {
			usuario.setId(++lastId);
		}
		content.put(usuario.getId(), usuario);
		try {
			codec.save(content.values());
		} catch (JsonProcessingException e) {
			if(isNew) {
				lastId --;
				content.remove(usuario.getId());
			}
			e.printStackTrace();
		}
	}

	public void delete(Integer id) {
		Usuario usu = content.remove(id);
		if(usu != null) {
			try {
				codec.save(content.values());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
}
