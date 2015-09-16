package br.jus.stf.pesquisa.interfaces.dto;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comando para indexar objetos. Deve seguir a notação do Elasticsearch de mapeamentos
 * 
 * @author Lucas.Rodrigues
 *
 */
public class IndexarCommand {

	@NotBlank
	private String index;
	
	@NotNull
	private JsonNode object;
	
	public IndexarCommand() {
		
	}
	
	public IndexarCommand(final String index, final JsonNode object) {
		Validate.notBlank(index);
		Validate.notNull(object);
		
		this.index = index;
		this.object = object;
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the object
	 */
	public JsonNode getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(JsonNode object) {
		this.object = object;
	}
	
}
