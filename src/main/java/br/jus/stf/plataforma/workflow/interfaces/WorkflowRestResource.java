package br.jus.stf.plataforma.workflow.interfaces;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.workflow.interfaces.commands.IniciarProcessoCommand;
import br.jus.stf.plataforma.workflow.interfaces.commands.SinalizarCommand;
import br.jus.stf.plataforma.workflow.interfaces.dto.ProcessoDto;
import br.jus.stf.plataforma.workflow.interfaces.facade.WorkflowServiceFacade;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@RestController
@RequestMapping("/api/workflow/processos")
public class WorkflowRestResource {

	@Autowired
	private WorkflowServiceFacade processoServiceFacade;
	
	@Autowired
	private Validator validator;

	//TODO : Substituir validação pelo @Valid e injeção do BindingResult
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Long iniciar(@RequestBody IniciarProcessoCommand command) {
		validate(command);
		return processoServiceFacade.iniciar(command.getMensagem(), command.getInformacao(), command.getTipoInformacao(), command.getStatus(), command.getDescricao());
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Long iniciarPorMensagem(@RequestBody IniciarProcessoCommand command) {
		validate(command);
		return processoServiceFacade.iniciarPorMensagem(command.getMensagem(), command.getInformacao(), command.getTipoInformacao(), command.getStatus(), command.getDescricao());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ProcessoDto consultar(@PathVariable("id") Long id) { 
		return processoServiceFacade.consultar(id);
	}
	
    //TODO : Substituir validação pelo @Valid e injeção do BindingResult
    @RequestMapping(value = "/{id}/sinalizar", method = RequestMethod.PUT)
	public void sinalizar(@PathVariable("id") Long id, @RequestBody SinalizarCommand command) {
    	validate(command);
		processoServiceFacade.sinalizar(id, command.getSinal(), command.getStatus(), command.getDescricao());
	}
    
	private void validate(Object object) {
		Set<ConstraintViolation<Object>> result = validator.validate(object);
		if (!result.isEmpty()) {
			throw new IllegalArgumentException(result.toString());
		}
	}
	
}
