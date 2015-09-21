package br.jus.stf.workflow.domain.model;

import br.jus.stf.shared.domain.model.ProcessoWorkflowId;



/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public interface ProcessoRepository {
	
	/**
	 * Cria uma instância do processo.
	 * @param mensagem mensagem que iniciará o processo.
	 * @param status status inicial do processo
	 * @return Identificador da instância do processo de autuação criado.
	 */
	ProcessoWorkflowId criar(String mensagem, String status);

	/**
	 * Consulta um processo pelo id
	 * 
	 * @param id
	 * @return
	 */
	ProcessoWorkflow consultar(ProcessoWorkflowId id);
	
	/**
	 * Salva um processo workflow
	 * 
	 * @param processo
	 * @return
	 */
	ProcessoWorkflow salvar(ProcessoWorkflow processo);
	
	/**
	 * Emite um sinal para um processo
	 * 
	 * @param sinal
	 * @param status
	 */
	void sinalizar(String sinal, String status);
	
}
