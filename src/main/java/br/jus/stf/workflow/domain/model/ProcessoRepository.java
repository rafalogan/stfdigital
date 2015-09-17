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

	ProcessoWorkflow consultar(ProcessoWorkflowId id);
	
	ProcessoWorkflow salvar(ProcessoWorkflow processo);
	
}
