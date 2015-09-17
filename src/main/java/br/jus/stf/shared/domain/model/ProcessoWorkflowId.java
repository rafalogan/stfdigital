package br.jus.stf.shared.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:46
 */
@Embeddable
public class ProcessoWorkflowId implements ValueObject<ProcessoWorkflowId> {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "NUM_PROCESS_INSTANCE", nullable = false)
	private Long id;

	public ProcessoWorkflowId(final String id) {
		Validate.notNull(id, "processoWorkflowId.id.required");
		this.id = Long.valueOf(id);
	}
	
	public Long toLong(){
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
	
		ProcessoWorkflowId other = (ProcessoWorkflowId) obj;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final ProcessoWorkflowId other){
		return other != null && this.id.equals(other.id);
	}

	@Override
	public String toString(){
		return id.toString();
	}

	//Hibernate
	
	ProcessoWorkflowId() {
		
	}
	
}
