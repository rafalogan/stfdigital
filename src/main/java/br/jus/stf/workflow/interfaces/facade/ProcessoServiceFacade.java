package br.jus.stf.workflow.interfaces.facade;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.workflow.application.ProcessoApplicationService;
import br.jus.stf.workflow.domain.model.ProcessoRepository;
import br.jus.stf.workflow.interfaces.dto.ProcessoDto;
import br.jus.stf.workflow.interfaces.dto.ProcessoDtoAssembler;

/**
 * @author Lucas.Rodrigues
 */
@Component
public class ProcessoServiceFacade {
	
	@Autowired
	private ProcessoRepository processoRepository;

	@Autowired
	private ProcessoApplicationService processoApplicationService;
	
	@Autowired
	private ProcessoDtoAssembler processoDtoAssembler;

	public Long iniciar(String mensagem) {
		ProcessoWorkflowId id = processoApplicationService.iniciar(mensagem);
		return id.toLong();
	}
	
	public ProcessoDto consultar(Long id) {
		ProcessoWorkflowId processoId = new ProcessoWorkflowId(id);
		return Optional.ofNullable(processoRepository.consultar(processoId))
				.map(processoDtoAssembler::toDto)
				.orElseThrow(IllegalArgumentException::new);
	}
	
}
