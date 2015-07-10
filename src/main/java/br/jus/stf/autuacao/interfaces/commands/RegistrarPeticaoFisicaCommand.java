package br.jus.stf.autuacao.interfaces.commands;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
public class RegistrarPeticaoFisicaCommand {

	private String tipoRecebimento;

	public RegistrarPeticaoFisicaCommand() {
	}

	public RegistrarPeticaoFisicaCommand(String tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}

	public void setTipoRecebimento(String tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}

	public String getTipoRecebimento() {
		return tipoRecebimento;
	}

}
