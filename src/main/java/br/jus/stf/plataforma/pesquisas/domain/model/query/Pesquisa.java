package br.jus.stf.plataforma.pesquisas.domain.model.query;

import static org.springframework.data.elasticsearch.annotations.FieldIndex.no;
import static org.springframework.data.elasticsearch.annotations.FieldIndex.not_analyzed;
import static org.springframework.data.elasticsearch.annotations.FieldType.Nested;
import static org.springframework.data.elasticsearch.annotations.FieldType.Object;
import static org.springframework.data.elasticsearch.annotations.FieldType.String;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.Validate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Lucas.Rodrigues
 *
 */
@Document(indexName = "pesquisas", type = "Pesquisa")
public class Pesquisa implements Entity<Pesquisa, String> {

	@Id
	@Field(type = String, store = true, index = not_analyzed)
	private String id;
	
	@Field(type = String, store = true, index = not_analyzed)
	private String nome;
	
	@Field(type = Object, store = true, index = no)
	private String[] campos;
	
	@Field(type = Object, store = true, index = no)
	private String[] tipos;
	
	@Field(type = Object, store = true, index = no)
	private String[] indices;
	
	@Field(type = Nested, store = true, index = no)
	private Map<String, String> filtros = new HashMap<String, String>();
	
	@Field(type = Nested, store = true, index = no)
	private Map<String, String> ordenadores = new HashMap<String, String>(0);
	
	@Field(type = String, index = not_analyzed)
	private String usuario;
	
	@Field(type = String, index = not_analyzed)
	private String papel;
	
	public Pesquisa(final String[] indices, final Map<String, String> filtros) {
		Validate.notEmpty(indices, "pesquisa.indices.required");
		Validate.notEmpty(filtros, "pesquisa.filtros.required");
		
		this.indices = indices;
		this.filtros.putAll(filtros);
	}
	
	@Override
	public String id() {
		return id;
	}
	
	public String[] campos() {
		return campos;
	}
	
	public String[] tipos() {
		return tipos;
	}
	
	public String[] indices() {
		return indices;
	}
	
	public Map<String, String> filtros() {
		return filtros;
	}
	
	public Map<String, String> ordenadores() {
		return ordenadores;
	}
	
	public Pesquisa comCampos(final String[] campos) {
		this.campos = campos;
		return this;
	}
	
	public Pesquisa comTipos(final String[] tipos) {
		this.tipos = tipos;
		return this;
	}
	
	public Pesquisa comOrdenadores(final Map<String, String> ordenadores) {
		Optional.ofNullable(ordenadores).ifPresent(this.ordenadores::putAll);
		return this;
	}
	
	public void paraUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public void paraPapel(String papel) {
		this.papel = papel;
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
		
		Pesquisa other = (Pesquisa) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Pesquisa other) {
		return other != null && id.equals(other.id);
	}

}
