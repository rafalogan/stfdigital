package br.jus.stf.workflow.interfaces.dto;

import java.util.Map;

public class ProcessoDto {
	private Long id;
	private Map<String, Object> variaveis;
	
	public ProcessoDto(){
		
	}
	
	public ProcessoDto(Long id, Map<String, Object> variaveis){
		this.id = id;
		this.variaveis = variaveis;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Map<String, Object> getVariaveis() {
		return variaveis;
	}
	
	public void setVariaveis(Map<String, Object> variaveis) {
		this.variaveis = variaveis;
	}
	
}
