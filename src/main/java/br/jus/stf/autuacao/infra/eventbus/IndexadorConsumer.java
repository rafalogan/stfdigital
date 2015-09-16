package br.jus.stf.autuacao.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.pesquisa.interfaces.IndexadorRestResource;
import br.jus.stf.pesquisa.interfaces.dto.IndexarCommand;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Consumidor de eventos de aplicação para enviar para indexação
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class IndexadorConsumer implements Consumer<Event<?>>, InitializingBean {

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private IndexadorRestResource indexadorRestResource;
	
	private ObjectMapper objectMapper;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indexadorEventBus"), this);
		objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}
	
	@Override
	public void accept(Event<?> event) {
		Object object = event.getData();
		IndexarCommand indexarCommand = createCommand(object);
		indexadorRestResource.indexar(indexarCommand, new BeanPropertyBindingResult(indexarCommand, "indexarCommand"));
	}

	private IndexarCommand createCommand(Object object) {
		IndexarCommand indexarCommand = new IndexarCommand();
		indexarCommand.setIndex(object.getClass().getSimpleName().toLowerCase());
		try {
			String json = objectMapper.writeValueAsString(object);
			indexarCommand.setObject(objectMapper.readTree(json));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return indexarCommand;
	}

}
