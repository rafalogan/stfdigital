package br.jus.stf.workflow.interfaces;

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

import br.jus.stf.workflow.application.ProcessoApplicationService;
import br.jus.stf.workflow.interfaces.commands.IniciarProcessoCommand;
import br.jus.stf.workflow.interfaces.dto.ProcessoDto;
import br.jus.stf.workflow.interfaces.dto.ProcessoDtoAssembler;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@RestController
@RequestMapping("/api/workflow/processos")
public class ProcessoRestResource {

	@Autowired
	private ProcessoApplicationService processoApplicationService;
	
	@Autowired
	private ProcessoDtoAssembler processoDtoAssembler;
	
	@Autowired
	private Validator validator;

	//TODO : Substituir validação pelo @Valid e injeção do BindingResult
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String iniciar(@RequestBody IniciarProcessoCommand command) {
		validate(command);
		return processoApplicationService.iniciar(command.getMensagem());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ProcessoDto consultar(@PathVariable("id") String id) { 
		return processoDtoAssembler.toDto(processoApplicationService.consultar(id));
	}
	
	private void validate(Object object) {
		Set<ConstraintViolation<Object>> result = validator.validate(object);
		if (!result.isEmpty()) {
			throw new IllegalArgumentException(result.toString());
		}
	}
}
