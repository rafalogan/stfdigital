package br.jus.stf.pesquisa.domain.model;

import java.util.Map;


/**
 * @author Lucas.Rodrigues
 *
 */
public interface IndexadorRepository {
	
	/**
	 * Criar um índice com as configurações informadas
	 * 
	 * @param indice
	 * @param configuracao
	 */
	void criar(String indice, String configuracao);
	
	/**
	 * Verifica se um índice existe
	 * 
	 * @param indice
	 * @return
	 */
	boolean existe(String indice);

	/**
	 * Salva um objeto no índice
	 * 
	 * @param indice
	 * @param tipo
	 * @param id
	 * @param objeto
	 */
	void salvar(String indice, String tipo, String id, String objeto);	
}
