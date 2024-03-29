package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationEvent;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;

/**
 * Classe que implementa a publicação de eventos de petição
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PeticaoApplicationEventImpl implements PeticaoApplicationEvent {

	@Autowired
	private EventBus eventBus;
	
	/**
	 * Notifica interessados sobre o recebimento de uma petição.
	 * 
	 * @param peticao Dados da petição.
	 */
	@Override
	public void peticaoRecebida(Peticao peticao) {
		PeticaoRecebida evento = new PeticaoRecebida(peticao);
		eventBus.notify("indexadorEventBus", Event.wrap(evento));
		eventBus.notify("notificadorEventBus", Event.wrap(evento));
	}
	
	/**
	 * Notifica interessados sobre a remessa inválida de uma petição.
	 * 
	 * @param peticao Dados da petição.
	 */
	@Override
	public void remessaInvalida(Peticao peticao) {
		RemessaInvalida evento = new RemessaInvalida(peticao);
		eventBus.notify("indexadorEventBus", Event.wrap(evento));
		eventBus.notify("notificadorEventBus", Event.wrap(evento));
	}
	
	/**
	 * Notifica interessados sobre a rejeição de uma petição.
	 * 
	 * @param peticao Dados da petição.
	 */
	@Override
	public void peticaoRejeitada(Peticao peticao){
		PeticaoRejeitada evento = new PeticaoRejeitada(peticao);
		eventBus.notify("indexadorEventBus", Event.wrap(evento));
		eventBus.notify("notificadorEventBus", Event.wrap(evento));
	}

}
