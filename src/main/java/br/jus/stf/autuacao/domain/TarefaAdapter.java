package br.jus.stf.autuacao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.shared.domain.model.TarefaId;
import br.jus.stf.workflow.interfaces.dto.TarefaDto;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public interface TarefaAdapter {

	public void completar(TarefaId id);

	public void sinalizar(TarefaId id, String sinal);
	
	public TarefaDto consultar(TarefaId id);

}
