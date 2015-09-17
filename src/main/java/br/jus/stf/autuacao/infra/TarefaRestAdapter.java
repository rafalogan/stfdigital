package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.TarefaAdapter;
import br.jus.stf.shared.domain.model.TarefaId;
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
	public void completar(TarefaId id) {
		//CompletarTarefaCommand command = new CompletarTarefaCommand();

		///command.setIdTarefa(id);

		// [TODO] Rodrigo Barrerios: Substituir pelo Mecanismo de Integração
		tarefaRestService.completar(id.toLong());
	}

	@Override
	public void sinalizar(TarefaId id, String sinal) {
		SinalizarCommand command = new SinalizarCommand();
		command.setSinal(sinal);

		// [TODO] Rodrigo Barrerios: Substituir pelo Mecanismo de Integração
		tarefaRestService.sinalizar(id.toLong(), command);
	}
	
	@Override
	public TarefaDto consultar(TarefaId id){
		return this.tarefaRestService.consultar(id.toLong());
	}

}
