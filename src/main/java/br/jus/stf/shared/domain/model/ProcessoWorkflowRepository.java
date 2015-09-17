package br.jus.stf.shared.domain.model;

import java.util.List;


/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:14
 */
public interface ProcessoWorkflowRepository {

	/**
	 * 
	 * @param id
	 */
	public String status(ProcessoWorkflowId id);
	
	/**
	 * @param status
	 * @return
	 */
	public List<ProcessoWorkflowId> findByStatus(String status);

}