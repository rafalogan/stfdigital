package br.jus.stf.workflow.interfaces.facade;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.shared.domain.model.TarefaId;
import br.jus.stf.workflow.application.TarefaApplicationService;
import br.jus.stf.workflow.domain.model.Tarefa;
import br.jus.stf.workflow.domain.model.TarefaRepository;
import br.jus.stf.workflow.interfaces.dto.TarefaDto;
import br.jus.stf.workflow.interfaces.dto.TarefaDtoAssembler;

/**
 * @author Lucas.Rodrigues
 * 
 */
@Component
public class TarefaServiceFacade {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private TarefaDtoAssembler tarefaDtoAssembler;
	
    @Autowired 
    private TarefaApplicationService tarefaApplicationService;
    
	public List<TarefaDto> tarefas(String papel) {    	
        return tarefaApplicationService.listar(papel).stream()
        		.map(tarefa -> tarefaDtoAssembler.toDto(tarefa))
        		.collect(Collectors.toList());
	}
	
	public void completar(Long id, String status) {
		Tarefa tarefa = carregarTarefa(id);
        tarefaApplicationService.completar(tarefa, status);
	}
    
	public void sinalizar(Long id, String sinal, String status) {
		Tarefa tarefa = carregarTarefa(id);
		tarefaApplicationService.sinalizar(tarefa, sinal, status);
	}
	
	public TarefaDto consultar(Long id) {
		Tarefa tarefa = carregarTarefa(id);
		return tarefaDtoAssembler.toDto(tarefa);
	}
	
	private Tarefa carregarTarefa(Long id) {
		TarefaId tarefaId = new TarefaId(id);
		return Optional.ofNullable(tarefaRepository.consultar(tarefaId))
				.orElseThrow(IllegalArgumentException::new);
	}

}
