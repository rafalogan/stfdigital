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
public class PessoaId implements ValueObject<PessoaId> {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_PESSOA", nullable = false)
	private Long id;

	public PessoaId(final Long id){
		Validate.notNull(id, "pessoaId.id.required");
		
		this.id = id;
	}

	public Long toLong(){
		return id;
	}
	
	@Override
	public String toString(){
		return id.toString();
	}	
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		PessoaId other = (PessoaId) o;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final PessoaId other){
		return other != null && this.id.equals(other.id);
	}
	
	//Hibernate

	PessoaId() {
		
	}
	
}