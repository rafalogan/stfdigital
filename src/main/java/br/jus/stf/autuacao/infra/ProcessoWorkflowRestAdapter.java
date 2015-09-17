package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.ProcessoAdapter;
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
public class ProcessoWorkflowRestAdapter implements ProcessoAdapter {

	@Autowired
	private ProcessoRestResource processoRestService;

	@Override
	public ProcessoWorkflowId iniciar(String tipoRecebimento) {
		
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		command.setMensagem(tipoRecebimento);
		
		Long id = processoRestService.iniciar(command);
		return new ProcessoWorkflowId(id);
	}
	
}
