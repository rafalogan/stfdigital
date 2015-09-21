package br.jus.stf.autuacao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.autuacao.domain.model.PeticaoFisica;



/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public interface ProcessoWorkflowAdapter {

	/**
	 * Iniciar uma nova instância do processo de autuação de petição eletrônica
	 * @param peticaoEletronica
	 */
	void iniciarProcessoWorkflow(PeticaoEletronica peticaoEletronica);
	
	/**
	 * Iniciar uma nova instância do processo de autuação de petição eletrônica
	 * @param peticaoFisica
	 */
	void iniciarProcessoWorkflow(PeticaoFisica peticaoFisica);
	
	/**
	 * Emite um sinal para o processo que rejeita a autução da petição
	 * 
	 * @param peticao
	 */
	void rejeitarAutuacao(Peticao peticao);
	
}
