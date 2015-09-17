package br.jus.stf.workflow.domain.model;

import java.util.List;

import br.jus.stf.shared.domain.model.TarefaId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public interface TarefaRepository {

	void completar(Tarefa tarefa, String status);

	List<Tarefa> listar(String papel);
	
	void sinalizar(Tarefa tarefa, String sinal, String status);
	
	Tarefa consultar(TarefaId id);
}
