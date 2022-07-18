package br.org.pavlov.simple.codec;

import static br.org.pavlov.infra.FileHandlingIO.createFile;
import static br.org.pavlov.infra.FileHandlingIO.readFile;
import static br.org.pavlov.infra.FileHandlingIO.saveFile;
import static br.org.pavlov.tools.Utils.isBlank;
import static br.org.pavlov.tools.Utils.isNotBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.pavlov.simple.model.Usuario;

public class UsuarioIO {

	public static final String FILE_PATH = "usuario.json";
	public static final String TEST_FILE_PATH = "usuario_test.json";

	private static UsuarioIO engine = new UsuarioIO(FILE_PATH);
	private static UsuarioIO testEngine = new UsuarioIO(TEST_FILE_PATH);

	public static UsuarioIO getInstance() {
		return engine;
	}

	public static UsuarioIO getTestInstance() {
		return testEngine;
	} 

	private UsuarioIO(String filePath){
		createFile(filePath);
	}

	private ObjectMapper om = new ObjectMapper();

	public List<Usuario> retrieveAsList() {
		String content = readFile(FILE_PATH);
		List<Usuario> lista = new ArrayList<>();
		if(isFileEmpty(content)) {
			return lista;
		}
		try {
			for (String line : content.split("\n")) {
				if(isBlank(line)) {
					continue;
				}
				lista.add(om.readValue(line, Usuario.class));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return lista;
	}

	private boolean isFileEmpty(String content) {
		return "{}".equals(content);
	}

	public Map<Integer, Usuario> retrieveAsIdMap() {
		String content = readFile(FILE_PATH);
		Map<Integer, Usuario> mapa = new HashMap<>();
		if(isFileEmpty(content)) {
			return mapa;
		}
		try {
			Usuario item;
			for (String line : content.split("\n")) {
				if(isBlank(line)) {
					continue;
				}
				item = om.readValue(line, Usuario.class);
				mapa.put(item.getId(), item);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return mapa;
	}

	public void save(Collection<Usuario> lista) throws JsonProcessingException {
		StringBuffer content = new StringBuffer();
		for(Usuario usu : lista) {
			content.append(om.writeValueAsString(usu)).append("\n");
		}
		if(isNotBlank(lista)) {
			content.replace(content.length()-1, content.length(), "");
		}
		saveFile(FILE_PATH, content.toString());
	}

	/* public Usuario findById(Integer id) {
		String content = readFile(FILE_PATH);
		return new Usuario();
	}

	public void save(Usuario usuario) {

	}

	public void delete(Integer id) {

	} */
}
