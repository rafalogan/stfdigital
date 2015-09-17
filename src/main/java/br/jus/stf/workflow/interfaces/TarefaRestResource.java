package br.jus.stf.workflow.interfaces;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.workflow.interfaces.commands.CompletarTarefaCommand;
import br.jus.stf.workflow.interfaces.commands.SinalizarCommand;
import br.jus.stf.workflow.interfaces.dto.TarefaDto;
import br.jus.stf.workflow.interfaces.facade.TarefaServiceFacade;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@RestController
@RequestMapping("/api/workflow/tarefas")
public class TarefaRestResource {
	
	@Autowired
	private TarefaServiceFacade tarefaServiceFacade;
    
	@Autowired
	private Validator validator;
    
    @ApiOperation(value = "Lista todas as tarefas associadas ao papel do usuário corrente")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<TarefaDto> tarefas(@RequestHeader("papel") String papel) {    	
        return tarefaServiceFacade.tarefas(papel);
	}
	
    @RequestMapping(value = "/tarefas/{id}/completar", method = RequestMethod.PUT)
	public void completar(@PathVariable("id") Long id, CompletarTarefaCommand command) {
		Set<ConstraintViolation<CompletarTarefaCommand>> result = validator.validate(command);
		if (!result.isEmpty()) {
			throw new IllegalArgumentException(result.toString());
		}
    	tarefaServiceFacade.completar(id, command.getStatus());
	}
    
    //TODO : Substituir validação pelo @Valid e injeção do BindingResult
    @RequestMapping(value = "/tarefas/{id}/sinalizar", method = RequestMethod.PUT)
	public void sinalizar(@PathVariable("id") Long id, @RequestBody SinalizarCommand command) {
		Set<ConstraintViolation<SinalizarCommand>> result = validator.validate(command);
		if (!result.isEmpty()) {
			throw new IllegalArgumentException(result.toString());
		}
		tarefaServiceFacade.sinalizar(id, command.getSinal(), command.getStatus());
	}
	
    @RequestMapping(value = "/tarefas/{id}", method = RequestMethod.GET)
	public TarefaDto consultar(@PathVariable("id") Long id){
		return tarefaServiceFacade.consultar(id);
	}

}
