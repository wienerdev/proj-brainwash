package br.org.pavlov.tools;

import java.util.Collection;

public final class Utils {
	private Utils(){}
	
	public static boolean isBlank(String valor) {
		return valor == null || valor.isBlank();
	}

	public static boolean isNotBlank(String valor) {
		return valor != null && !valor.isBlank();
	}

	public static boolean isBlank(Collection<?> valor) {
		return valor == null || valor.isEmpty();
	}

	public static boolean isNotBlank(Collection<?> valor) {
		return valor != null && !valor.isEmpty();
	}
}
