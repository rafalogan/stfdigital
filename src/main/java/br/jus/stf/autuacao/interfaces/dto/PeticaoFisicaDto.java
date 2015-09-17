package br.jus.stf.autuacao.interfaces.dto;

import java.util.List;
import java.util.Map;

import com.wordnik.swagger.annotations.ApiModel;

/**
 * @author Lucas.Rodrigues
 * 
 */
@ApiModel(value = "Representa a petição física registrada")
public class PeticaoFisicaDto extends PeticaoDto {
	
	private Integer volumes;
	private Integer apensos;
	private String formaRecebimento;
	private String numeroSedex;

	public PeticaoFisicaDto(Long id, Long numero, Short ano, String classe, Map<String, List<Long>> partes, List<Long> documentos) {
		super(id, numero, ano, classe, partes, documentos);
	}
	
	/**
	 * @return the volumes
	 */
	public Integer getVolumes() {
		return volumes;
	}

	/**
	 * @param volumes the volumes to set
	 */
	public void setVolumes(Integer volumes) {
		this.volumes = volumes;
	}

	/**
	 * @return the apensos
	 */
	public Integer getApensos() {
		return apensos;
	}

	/**
	 * @param apensos the apensos to set
	 */
	public void setApensos(Integer apensos) {
		this.apensos = apensos;
	}

	/**
	 * @return the formaRecebimento
	 */
	public String getFormaRecebimento() {
		return formaRecebimento;
	}

	/**
	 * @param formaRecebimento the formaRecebimento to set
	 */
	public void setFormaRecebimento(String formaRecebimento) {
		this.formaRecebimento = formaRecebimento;
	}

	/**
	 * @return the numeroSedex
	 */
	public String getNumeroSedex() {
		return numeroSedex;
	}

	/**
	 * @param numeroSedex the numeroSedex to set
	 */
	public void setNumeroSedex(String numeroSedex) {
		this.numeroSedex = numeroSedex;
	}
	
}
