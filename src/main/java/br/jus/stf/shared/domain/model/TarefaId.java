package br.jus.stf.shared.domain.model;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
public class TarefaId implements ValueObject<TarefaId> {

	private static final long serialVersionUID = 747808680221902331L;
	
	private Long id;
	
	public TarefaId(final Long id) {
		Validate.notNull(id, "tarefaid.id.required");
		
		this.id = id;
	}

	public Long toLong() {
		return id;
	}
	
	@Override
	public String toString() {
		return id.toString();
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
		
		TarefaId other = (TarefaId) obj;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(TarefaId other) {
		return other != null && id.equals(other.id);
	}
	
}
