package br.jus.stf.autuacao.application;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.ProcessoWorkflowAdapter;
import br.jus.stf.autuacao.domain.TarefaAdapter;
import br.jus.stf.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.infra.PeticaoStatus;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.shared.domain.model.TarefaId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
@Component
@Transactional
public class PeticaoApplicationService {
	
	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@Autowired
	private ProcessoWorkflowAdapter processoAdapter;
	
	@Autowired
	private TarefaAdapter tarefaAdapter;
	
	@Autowired
	private PeticaoFactory peticaoFactory;
	
	@Autowired
	private PeticaoApplicationEvent peticaoApplicationEvent;

	/**
	 * Registra uma nova petilçao.
	 * 
	 * @param peticaoEletronica Petição eletrônica recebida.
	 * 
	 * @return Id da petição eletrônica registrada.
	 */
	public PeticaoEletronica peticionar(ClasseId classeSugerida, List<String> poloAtivo, List<String> poloPassivo, List<String> documentos) {
		String tipoRecebimento = "remessaEletronica";
		
		PeticaoEletronica peticao = peticaoFactory.criarPeticaoEletronica(classeSugerida, poloAtivo, poloPassivo, documentos);
		
		ProcessoWorkflowId id = processoAdapter.iniciar(tipoRecebimento, PeticaoStatus.A_AUTUAR);
		peticao.associarProcessoWorkflow(id);
		peticaoRepository.save(peticao);
		
		peticaoApplicationEvent.peticaoRecebida(peticao);
		return peticao;
	}
	
	/**
	 * Registra o recebimento de uma petição física.
	 * 
	 * @param volumes Quantidade de volumes da petição física.
	 * 
	 * @return Id da petição física registrada.
	 */
	public PeticaoFisica registrar(Integer volumes, Integer apensos, FormaRecebimento formaRecebimento, String numeroSedex){
		String tipoRecebimento = "remessaFisica";

		PeticaoFisica peticao = peticaoFactory.criarPeticaoFisica(volumes, apensos, formaRecebimento, numeroSedex);
		
		ProcessoWorkflowId id = processoAdapter.iniciar(tipoRecebimento, PeticaoStatus.A_PREAUTUAR);
		peticao.associarProcessoWorkflow(id);
		peticaoRepository.save(peticao);
		
		peticaoApplicationEvent.peticaoRecebida(peticao);
		return peticao;
	}

	/**
	 * Realiza a preautuação de uma petição física.
	 * @param peticaoFisica Dados da petição física.
	 * @param classeSugerida
	 * @param tarefaId
	 */
	public void preautuar(PeticaoFisica peticao, ClasseId classeSugerida, TarefaId tarefaId) {
		peticao.preautuar(classeSugerida);
		peticaoRepository.save(peticao);
		tarefaAdapter.completar(tarefaId, PeticaoStatus.A_AUTUAR);
	}

	/**
	 * Realiza a atuação de uma petição.
	 * @param peticao Dados da petição.
	 * @param peticaoValida Indica se uma petição foi considerada válida.
	 */
	public void autuar(Peticao peticao, ClasseId classe, boolean peticaoValida, String motivoRejeicao, TarefaId tarefaId) {
		if (peticaoValida){
			peticao.aceitar(classe);
			tarefaAdapter.completar(tarefaId, PeticaoStatus.ACEITA);
		} else {
			peticao.rejeitar(motivoRejeicao);
			tarefaAdapter.sinalizar(tarefaId, "peticaoInvalida", PeticaoStatus.RECUSADA);
		}
		peticaoRepository.save(peticao);
	}

	/**
	 * Distribui um processo para um Ministro Relator.
	 * @param peticao Dados da petição.
	 * @param ministroRelator Dados do Ministro Relator do processo.
	 * @param tarefaId
	 * @return processo
	 */
	public Processo distribuir(Peticao peticao, MinistroId ministroRelator, TarefaId tarefaId) {
		tarefaAdapter.completar(tarefaId, PeticaoStatus.DISTRIBUIDA);
		return peticao.distribuir(ministroRelator);
	}

	/**
	 * Devolve uma petição.
	 * @param peticao Dados da petição.
	 * @param motivoRejeicao Motivo da rejeição da petição.
	 */
	public void devolver(Peticao peticao, String motivoRejeicao, TarefaId tarefaId) {
		peticao.rejeitar(motivoRejeicao);
		peticaoRepository.save(peticao);
		tarefaAdapter.completar(tarefaId, PeticaoStatus.DEVOLVIDA);
	}
	
}
