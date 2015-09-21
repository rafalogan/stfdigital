package br.jus.stf.workflow.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.workflow.domain.model.ProcessoRepository;
import br.jus.stf.workflow.domain.model.ProcessoWorkflow;
import br.jus.stf.workflow.domain.model.Tarefa;
import br.jus.stf.workflow.domain.model.TarefaRepository;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Service
@Transactional
public class TarefaApplicationService {

	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private ProcessoRepository processoRepository;

	/**
	 * Completa uma tarefa com um status
	 * 
	 * @param tarefa
	 * @param status
	 */
	public void completar(Tarefa tarefa, String status) {
		tarefaRepository.completar(tarefa, status);
		ProcessoWorkflow processo = processoRepository.consultar(tarefa.processo());	
		processo.atualizarStatus(status);
		processoRepository.salvar(processo);
	}
	
}
