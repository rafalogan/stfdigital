package br.jus.stf.plataforma.pesquisas.infra.persistence;

import static org.elasticsearch.action.search.SearchType.DFS_QUERY_THEN_FETCH;
import static org.elasticsearch.search.sort.SortBuilders.fieldSort;
import static org.elasticsearch.search.sort.SortOrder.ASC;
import static org.elasticsearch.search.sort.SortOrder.DESC;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.pesquisas.domain.model.query.Pesquisa;
import br.jus.stf.plataforma.pesquisas.domain.model.query.PesquisaRepository;
import br.jus.stf.plataforma.pesquisas.domain.model.query.Resultado;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class PesquisaRepositoryImpl implements PesquisaRepository {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Override
	public List<Resultado> sugerir(Pesquisa pesquisa) {
		NativeSearchQueryBuilder builder = buildQuery(pesquisa);
		builder.withQuery(buildSuggestionFiltros(pesquisa.filtros()));
		return executeQuery(builder.build());
	}
	
	@Override
	public List<Resultado> pesquisar(Pesquisa pesquisa, Pageable pageable) {
		NativeSearchQueryBuilder builder = buildQuery(pesquisa);
		builder.withQuery(buildFiltros(pesquisa.filtros()));
		Optional.ofNullable(pageable).ifPresent(builder::withPageable);
		return executeQuery(builder.build());
	}

	/**
	 * Monta um query com base na pesquisa
	 * 
	 * @param pesquisa
	 * @return uma query builder
	 */
	private NativeSearchQueryBuilder buildQuery(Pesquisa pesquisa) {
		NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
		builder.withFields(pesquisa.campos());
		builder.withTypes(pesquisa.tipos());
		builder.withIndices(pesquisa.indices());
		builder.withSearchType(DFS_QUERY_THEN_FETCH);
		Map<String, String> ordenadores = pesquisa.ordenadores();
		ordenadores.keySet().forEach(ordenador -> {
			SortOrder sentido = (DESC.toString().equalsIgnoreCase(ordenadores.get(ordenador))) ? DESC : ASC;
			builder.withSort(fieldSort(ordenador).order(sentido));
		});
		return builder;
	}

	/**
	 * Constrói uma query com base nos filtros
	 * 
	 * @param filtros
	 * @return uma query baseada nos filtros
	 */
	private BoolQueryBuilder buildFiltros(Map<String, String> filtros) {
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		filtros.keySet().forEach(campo -> 
			boolQuery.must(QueryBuilders.matchQuery(campo, filtros.get(campo)).operator(Operator.AND)));
		return boolQuery;
	}
	
	/**
	 * Constrói uma query por prefixos com base nos filtros
	 * 
	 * @param filtros
	 * @return uma query baseada nos filtros
	 */
	private BoolQueryBuilder buildSuggestionFiltros(Map<String, String> filtros) {
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		filtros.keySet().forEach(campo -> 
			boolQuery.must(QueryBuilders.prefixQuery(campo, filtros.get(campo))));
		return boolQuery;
	}
	
	/**
	 * Executa uma query
	 * 
	 * @param query
	 * @return o resultado
	 */
	private List<Resultado> executeQuery(NativeSearchQuery query) {
		return elasticsearchTemplate.query(query, new ResultadoPesquisa());
	}
	
	/**
	 * Extrai o resultado da pesquisa
	 */
	private final class ResultadoPesquisa implements ResultsExtractor<List<Resultado>> {
		@Override
		public List<Resultado> extract(SearchResponse response) {
			List<Resultado> documentos = new ArrayList<Resultado>();
			try {
				for (SearchHit hit : response.getHits()) {
					documentos.add(coletarResultado(hit));
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return documentos;
		}
	}
	
	/**
	 * Coleta um resultado a partir de um item encontrado na pesquisa
	 * 
	 * @param hit
	 * @return
	 * @throws JsonProcessingException 
	 */
	private Resultado coletarResultado(SearchHit hit) throws JsonProcessingException {
		Map<String, Object> source = hit.isSourceEmpty() ? coletarCampos(hit) : hit.getSource();
		return new Resultado(hit.getId(), hit.getType(), source);
	}
	
	/**
	 * Coleta um mapa com nome dos campos como chave
	 * 
	 * @param hit
	 * @return um mapa dos campos
	 */
	private Map<String, Object> coletarCampos(final SearchHit hit) {
		Map<String, Object> campos = new LinkedHashMap<String, Object>();
		hit.fields().keySet()
			.forEach(campo -> campos.put(campo, hit.field(campo).getValue()));
		return campos;
	}
	
}
