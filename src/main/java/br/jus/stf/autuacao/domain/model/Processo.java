package br.jus.stf.autuacao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.infra.persistence.GerarNumeroProcesso;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.PeticaoId;
import br.jus.stf.shared.domain.model.ProcessoId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
@javax.persistence.Entity
@Table(name = "PROCESSO", schema = "AUTUACAO",
	uniqueConstraints = @UniqueConstraint(columnNames = {"SIG_CLASSE", "NUM_PROCESSO"}))
public class Processo implements Entity<Processo> {

	@Embedded
	@AttributeOverride(name = "id",
		column = @Column(name = "SEQ_PROCESSO", insertable = false, updatable = false))
	private ProcessoId processoId;
	
	@Embedded
	private ClasseId classe;
	
	@GerarNumeroProcesso
	@Column(name = "NUM_PROCESSO", nullable = false)
	private Long numero;
	
	@Embedded
	@AttributeOverride(name = "id",
		column = @Column(name = "SEQ_MINISTRO_RELATOR"))
	private MinistroId relator;

	
	@Embedded
	private PeticaoId peticao;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, 
			targetEntity = ParteProcesso.class)
	@JoinColumn(name = "SEQ_PROCESSO")
	private Set<Parte> partes = new HashSet<Parte>(0);
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PROCESSO_DOCUMENTO", schema = "AUTUACAO",
			joinColumns = @JoinColumn(name = "SEQ_PROCESSO"))
	private Set<DocumentoId> pecas = new TreeSet<DocumentoId>(
			(p1, p2) -> p1.toLong().compareTo(p2.toLong()));

	/**
	 * @param classe
	 * @param numero
	 * @param relator
	 * @param peticao
	 * @param partes
	 * @param documentos
	 */
	public Processo(final ClasseId classe, final MinistroId relator,
			final PeticaoId peticao, final Set<ParteProcesso> partes, final Set<DocumentoId> pecas) {
		Validate.notNull(classe, "processo.classe.required");
		Validate.notNull(relator, "processo.relator.required");
		Validate.notNull(peticao, "processo.peticao.required");
		Validate.notEmpty(partes, "processo.partes.notEmpty");
		Validate.notNull(pecas, "processo.pecas.required");
		
		this.classe = classe;
		this.relator = relator;
		this.peticao = peticao;
		this.partes.addAll(partes);
		this.pecas.addAll(pecas);
	}

	public ProcessoId id() {
		return this.processoId;
	}
	
	public ClasseId classe() {
		return classe;
	}
	
	public Long numero() {
		return numero;
	}

	public MinistroId relator() {
		return this.relator;
	}

	public PeticaoId peticao() {
		return this.peticao;
	}

	public Set<Parte> partesPoloAtivo() {
		return Collections.unmodifiableSet(partes.stream()
		  .filter(p -> p.polo() == TipoPolo.POLO_ATIVO)
		  .collect(Collectors.toSet()));
	}

	public Set<Parte> partesPoloPassivo() {
		return Collections.unmodifiableSet(partes.stream()
		  .filter(p -> p.polo() == TipoPolo.POLO_PASSIVO)
		  .collect(Collectors.toSet()));
	}
	
	/**
	 * 
	 * @param parte
	 */
	public boolean adicionarParte(final Parte parte){
		Validate.notNull(parte, "peticao.parte.required");
		
		return this.partes.add(parte);
	}
	
	/**
	 * 
	 * @param parte
	 */
	public boolean removerParte(final Parte parte){
		Validate.notNull(parte, "peticao.parte.required");
		
		return this.partes.remove(parte);
	}

	/**
	 * 
	 * @param peca
	 */
	public boolean adicionarPeca(final DocumentoId peca){
		Validate.notNull(peca, "peticao.peca.required");
	
		return this.pecas.add(peca);
	}
	
	/**
	 * 
	 * @param peca
	 */
	public boolean removerPeca(final DocumentoId peca){
		Validate.notNull(peca, "peticao.peca.required");
	
		return this.pecas.remove(peca);
	}

	public Set<DocumentoId> pecas() {
		return Collections.unmodifiableSet(pecas);
	}
	
	public String identificacao() {
		return new StringBuilder()
				.append(classe.toString())
				.append(" ")
				.append(numero)
				.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processoId == null) ? 0 : processoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Processo other = (Processo) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Processo other) {
		return other != null && this.processoId.sameValueAs(other.processoId);
	}
	
	// Hibernate
	
	@Id
	@Column(name = "SEQ_PROCESSO")
	@SequenceGenerator(name = "PROCESSOID", sequenceName = "AUTUACAO.SEQ_PROCESSO", allocationSize = 1)
	@GeneratedValue(generator = "PROCESSOID", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	Processo() {

	}

}