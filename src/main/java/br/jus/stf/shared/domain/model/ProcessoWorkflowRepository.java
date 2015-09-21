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
	String statusById(ProcessoWorkflowId id);
	
	/**
	 * @param status
	 * @return
	 */
	List<ProcessoWorkflowId> findByStatus(String status);

}