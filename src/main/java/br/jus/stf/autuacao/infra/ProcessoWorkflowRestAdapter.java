package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.ProcessoWorkflowAdapter;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.workflow.interfaces.ProcessoRestResource;
import br.jus.stf.workflow.interfaces.commands.IniciarProcessoCommand;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class ProcessoWorkflowRestAdapter implements ProcessoWorkflowAdapter {

	@Autowired
	private ProcessoRestResource processoRestService;

	@Override
	public ProcessoWorkflowId iniciar(String tipoRecebimento, PeticaoStatus status) {
		
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		command.setMensagem(tipoRecebimento);
		command.setStatus(status.toString());
		
		Long id = processoRestService.iniciar(command);
		return new ProcessoWorkflowId(id);
	}
	
}
