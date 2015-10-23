package br.jus.stf.plataforma.pesquisas.infra.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.pesquisas.domain.model.command.Documento;
import br.jus.stf.plataforma.pesquisas.domain.model.command.DocumentoRepository;
import br.jus.stf.plataforma.pesquisas.domain.model.command.Indice;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository("documentoIndexadoRepository")
public class DocumentoRepositoryImpl implements DocumentoRepository {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public void criar(final Indice indice) {
		if (!elasticsearchTemplate.indexExists(indice.nome())) {
			elasticsearchTemplate.createIndex(indice.nome(), indice.configuracao());	
		}
		indice.mapeamentos().forEach((tipo, mapeamento) ->
			elasticsearchTemplate.putMapping(indice.nome(), tipo, mapeamento));
		elasticsearchTemplate.refresh(indice.nome(), true);
	}
	
	@Override
	public void salvar(final Documento documento) {
		IndexQuery query = new IndexQueryBuilder()
			.withIndexName(documento.indice().nome())
			.withType(documento.tipo())
			.withSource(documento.objeto())
			.build();
		Optional.ofNullable(documento.id()).ifPresent(query::setId);
		elasticsearchTemplate.index(query);
	}

}
