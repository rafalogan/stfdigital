package br.jus.stf.pesquisa.interfaces;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.pesquisa.interfaces.dto.IndexarCommand;
import br.jus.stf.pesquisa.interfaces.facade.IndexadorApplicationFacade;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Controlador com API Rest para indexação
 * 
 * @author Lucas.Rodrigues
 *
 */
@RestController
@RequestMapping("/api/indexador")
public class IndexadorRestResource {
	
	@Autowired
	private IndexadorApplicationFacade indexadorApplicationFacade;

	@ApiOperation("Indexa objetos para pesquisa")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void indexar(@RequestBody @Valid IndexarCommand indexarCommand, BindingResult result) {
		
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		
		
		
	}

}
