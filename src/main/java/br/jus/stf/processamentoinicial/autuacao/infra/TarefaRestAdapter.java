package br.jus.stf.processamentoinicial.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.interfaces.TarefaRestResource;
import br.jus.stf.plataforma.workflow.interfaces.commands.CompletarTarefaCommand;
import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDto;
import br.jus.stf.processamentoinicial.autuacao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.shared.ProcessoWorkflowId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class TarefaRestAdapter implements TarefaAdapter {

	@Autowired
	private TarefaRestResource tarefaRestResource;

	@Override
	public void completarDevolucao(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.DEVOLVIDA);		
	}

	@Override
	public void completarAutuacao(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.ACEITA);		
	}

	@Override
	public void completarPreautuacao(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.A_AUTUAR);
	}

	@Override
	public void completarDistribuicao(Peticao peticao) {
		completarTarefaPorProcesso(peticao, PeticaoStatus.DISTRIBUIDA);	
	}
	
	/**
	 * Completa uma tarefa do processo com um status
	 * 
	 * @param processoWorkflowId
	 */
	private void completarTarefaPorProcesso(Peticao peticao, PeticaoStatus status) {
		ProcessoWorkflowId id = peticao.processosWorkflow().iterator().next();
		CompletarTarefaCommand command = new CompletarTarefaCommand();
		command.setStatus(status.toString());
		TarefaDto dto = tarefaRestResource.consultarPorProcesso(id.toLong());
		tarefaRestResource.completar(dto.getId(), command);
	}

}
