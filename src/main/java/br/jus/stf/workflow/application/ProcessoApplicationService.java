package br.jus.stf.workflow.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.workflow.domain.model.ProcessoRepository;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Service
@Transactional
public class ProcessoApplicationService {

	@Autowired
	private ProcessoRepository processoRepository;

	/**
	 * Inicia uma nova instância do processo
	 * @param mensagem Mensagem que inicia um processo
	 * @param status Status inicial do processo
	 * @return Identificador da instância do processo.
	 */
	public ProcessoWorkflowId iniciar(String mensagem, String status) {
		return processoRepository.criar(mensagem, status);
	}
	
}
