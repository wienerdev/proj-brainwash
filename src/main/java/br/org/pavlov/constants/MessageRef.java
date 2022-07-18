package br.org.pavlov.constants;

public enum MessageRef {

	// MSG INFRA
	INFRA_FILE_NOT_FOUND("I001", "Arquivo não encontrado"),
	INFRA_NAMELESS_FILE("I002", "Arquivo não possui um nome válido"),

	// MSG SERVICE
	/**
	 * S001 - O usuário %s recebeu %d pontos do tipo %s
	 */
	SERV_REGISTRO_SUCESSO("S001", "O usuário %s recebeu %d pontos do tipo %s"),
	/**
	 * S002 - Pontos do tipo %s: %d
	 */
	SERV_RECUPERACAO_PONTOS("S002", "Pontos do tipo %s: %d"),

	// MSG ERR SERVICE
	ERRO_TIPO_PONTO_NULO_OU_VAZIO("E001", "Tipo de ponto está nulo ou vazio"),
	ERRO_NOME_USUARIO_INVALIDO("E002", "Usuário %s não encontrado"),
	ERRO_ID_USUARIO_INVALIDO("E003", "Usuário %d não encontrado"),
	ERRO_USUARIO_NULO("E004", "Usuário %s está nulo"),
	ERRO_TIPO_INVALIDO("E005", "Algo de errado aconteceu, possivelmente o tipo de ponto é inválido")

	// MSG DAO
	;

	private MessageRef(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public final String code;
	public final String msg;

	public String formatMsg(Object... args) {
		return String.format(this.msg, args); 
	}
}