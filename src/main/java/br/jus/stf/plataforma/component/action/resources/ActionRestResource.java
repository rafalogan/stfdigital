package br.jus.stf.plataforma.component.action.resources;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.component.action.resources.commands.ResourcesCommand;
import br.jus.stf.plataforma.component.action.resources.commands.VerifyActionsCommand;
import br.jus.stf.plataforma.component.action.service.ActionService;
import br.jus.stf.plataforma.component.action.support.ActionMappingInfo;
import br.jus.stf.plataforma.component.action.support.ActionView;

import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Controlador rest que expõem os serviços de listagem, verificação e execução de ações
 * @author Lucas.Rodrigues
 *
 */
@RestController
@RequestMapping("/api/actions")
public class ActionRestResource {
	
	@Autowired
	private ActionService actionService;
	
    /**
     * Lista todas as ações registradas pelo módulo
     * @return as ações
     */
    @ApiOperation(value = "Lista as ações de um determinado contexto.")
    @JsonView(ActionView.class)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Collection<ActionMappingInfo> list() {

    	return actionService.listActions()
    			.stream()
				.sorted((a1, a2) -> a1.getDescription().compareTo(a2.getDescription()))
				.collect(Collectors.toList());
	}
    
    /**
     * Verifica se uma ação pode ser executada ou listada.
     * 
     * @param actionId
     * @param command
     * @return true se permitido, caso contrário false
     */
    @ApiOperation(value = "Verifica se uma ação pode ser executada ou listada.")
	@RequestMapping(value = "/{actionId}/isallowed", method = RequestMethod.POST)
	public boolean isAllowed(@PathVariable("actionId") String actionId, @RequestBody ResourcesCommand command) {
    	return actionService.isAllowed(actionId, command.getResources());
	}
    
    /**
     * Verifica se várias ações podem ser executadas ou listadas.
     * 
     * @param command
     * @param result
     * @return os ids das ações permitidas
     */
    @ApiOperation(value = "Verifica se as ações podem ser executadas ou listadas.")
	@RequestMapping(value = "/isallowed", method = RequestMethod.POST)
	public Collection<String> verifyAllowed(@RequestBody @Valid VerifyActionsCommand command, BindingResult result) {
    	
    	if (result.hasErrors()) {
    		throw new IllegalArgumentException("Verificação inválida: " + result.getAllErrors());
    	}
    	return command.getIds()
    		.stream()
    		.filter(id -> actionService.isAllowed(id, command.getResources()))
    		.collect(Collectors.toList());
	}
    
    /**
     * Executa a ação
     * 
     * @param actionId
     * @param command
     * @return o resultado da execução
     */
    @ApiOperation(value = "Executa a ação de um determinado contexto.")
	@RequestMapping("/{actionId}/execute")
	public Object execute(@PathVariable("actionId") String actionId, @RequestBody ResourcesCommand command) {
    	return actionService.executeAction(actionId, command.getResources());
	}
	
}
