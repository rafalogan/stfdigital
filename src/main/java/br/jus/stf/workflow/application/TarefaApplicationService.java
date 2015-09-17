package br.jus.stf.workflow.application;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.shared.domain.model.TarefaId;
import br.jus.stf.workflow.domain.model.ProcessoWorkflow;
import br.jus.stf.workflow.domain.model.ProcessoRepository;
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

	public List<Tarefa> listar(String papel) {
		return tarefaRepository.listar(papel);
	}

	public void completar(Tarefa tarefa) {
		tarefaRepository.completar(tarefa);
		ProcessoWorkflow processo = processoRepository.consultar(tarefa.processo());	
		processo.atualizarStatus();
		processoRepository.salvar(processo);
	}
	
	public void sinalizar(Tarefa tarefa, String sinal){
		tarefaRepository.sinalizar(tarefa, sinal);
	}
	
}
