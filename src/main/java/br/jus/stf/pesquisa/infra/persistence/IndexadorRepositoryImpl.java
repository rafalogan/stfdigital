package br.jus.stf.pesquisa.infra.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Repository;

import br.jus.stf.pesquisa.domain.model.IndexadorRepository;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class IndexadorRepositoryImpl implements IndexadorRepository {

	@Autowired
	private ElasticsearchTemplate elasticSearchTemplate;
	
	@Override
	public void criar(String indice, String configuracao) {
		elasticSearchTemplate.createIndex(indice, configuracao);
	}

	@Override
	public boolean existe(String indice) {
		return elasticSearchTemplate.indexExists(indice);
	}
	
	@Override
	public void salvar(String indice, String tipo, String id, String objeto) {
		IndexQuery query = new IndexQueryBuilder()
			.withIndexName(indice)
			.withType(tipo)
			.withId(id)
			.withSource(objeto)
			.build();
		elasticSearchTemplate.index(query);
	}

}
