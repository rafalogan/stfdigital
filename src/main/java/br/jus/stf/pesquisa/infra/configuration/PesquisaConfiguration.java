package br.jus.stf.pesquisa.infra.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
@EnableElasticsearchRepositories("br.jus.stf.pesquisa")
//@EnableSpringDataWebSupport //TODO: Lucas.Rodrigues Habilitar ao separar módulo
public class PesquisaConfiguration {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@PostConstruct
	public void configure() {
		//elasticsearchTemplate.
	}
	
}
