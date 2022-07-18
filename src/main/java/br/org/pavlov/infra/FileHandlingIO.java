package br.org.pavlov.infra;

import static br.org.pavlov.constants.MessageRef.INFRA_NAMELESS_FILE;
import static br.org.pavlov.tools.Utils.isBlank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandlingIO {
	
	static final ClassLoader CLASS_LOADER = FileHandlingIO.class.getClassLoader();

	public static void saveFile(String fileName, String content) {
		if(isBlank(content)) {
			return;
		}

		File file = retrieveFile(fileName);

		try (FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8)) {
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write(content);
			// writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFile(String fileName) {
		try {
			return Files.readString(Path.of(retrieveFile(fileName).getAbsolutePath()), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void createFile(String fileName) {
		File file = retrieveFile(fileName);

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void validateFileName(String fileName) {
		if(isBlank(fileName)) {
			throw new IllegalArgumentException(INFRA_NAMELESS_FILE.msg);
		}
	}

	private static File retrieveFile(String fileName) {
		validateFileName(fileName);

		String userDirectory = Paths.get("")
			.toAbsolutePath()
			.toString();
		
		File file = new File(userDirectory+"/"+fileName);
		return file;
	}

	/* private static InputStream getInputStreamFromInternalResource(String fileName) {

        // The class loader that loaded the class
        // ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = CLASS_LOADER.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

	private static InputStream getInputStreamFromExternalStructure(String fileName) {
		String userDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
		try (InputStream is = new FileInputStream(userDirectory)) {
			return is;
		} catch (IOException e) {
			throw new IllegalArgumentException("no file found: "+fileName, e);
		}
	} */
}
