package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.TarefaAdapter;
import br.jus.stf.workflow.interfaces.TarefaRestResource;
import br.jus.stf.workflow.interfaces.commands.SinalizarCommand;
import br.jus.stf.workflow.interfaces.dto.TarefaDto;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class TarefaRestAdapter implements TarefaAdapter {

	@Autowired
	private TarefaRestResource tarefaRestService;

	@Override
	public void completar(String id) {
		//CompletarTarefaCommand command = new CompletarTarefaCommand();

		///command.setIdTarefa(id);

		// [TODO] Rodrigo Barrerios: Substituir pelo Mecanismo de Integração
		tarefaRestService.completar(id);
	}

	@Override
	public void sinalizar(String sinal, String taskId) {
		SinalizarCommand command = new SinalizarCommand();
		command.setSinal(sinal);

		// [TODO] Rodrigo Barrerios: Substituir pelo Mecanismo de Integração
		tarefaRestService.sinalizar(taskId, command);
	}
	
	@Override
	public TarefaDto consultar(String id){
		return this.tarefaRestService.consultar(id);
	}

}
