package br.jus.stf.workflow.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:14
 */
@javax.persistence.Entity
@Table(name = "PROCESSO_WORKFLOW", schema = "PLATAFORMA")
public class ProcessoWorkflow implements Entity<ProcessoWorkflow, ProcessoWorkflowId> {

	@EmbeddedId
	private ProcessoWorkflowId id;
	
	@Column(name = "DSC_STATUS_PROCESS_INSTANCE")
	private String status;
	
	@Transient
	private ProcessInstance processInstance;
	
	public ProcessoWorkflow(final ProcessInstance processInstance, final String status){
		Validate.notNull(processInstance, "processoWorkflow.processInstance.required");
		Validate.notBlank(status, "processoWorkflow.status.required");
		
		Long id = Long.parseLong(processInstance.getId());
		this.id = new ProcessoWorkflowId(id);
		this.processInstance = processInstance;
		this.status = status;
	}

	public ProcessoWorkflowId id() {
		return id;
	}

	public String status() {
		return status;
	}
	
	/**
	 * Atualiza o status do processo de acordo com o processInstance carregado
	 */
	public void atualizarStatus(String status) {
		Validate.notBlank(status, "processoWorkflow.status.required");
		
		this.status = status;
	}
	
	public ProcessInstance instance() {
		return processInstance;
	}
	
	public void setInstance(ProcessInstance processInstance) {
		Validate.notNull(processInstance, "processoWorkflow.processInstance.required");
		Validate.isTrue(id.toString().equals(processInstance.getId()));
		
		this.processInstance = processInstance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		ProcessoWorkflow other = (ProcessoWorkflow) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(final ProcessoWorkflow other){
		return other != null && this.id.sameValueAs(other.id);
	}
	
	//Hibernate
	
	ProcessoWorkflow() {
		
	}

}
