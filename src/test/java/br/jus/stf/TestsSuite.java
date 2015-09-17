package br.jus.stf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.jus.stf.autuacao.application.PeticaoApplicationEventIntegrationTests;
import br.jus.stf.autuacao.application.PeticaoApplicationServiceUnitTests;
import br.jus.stf.autuacao.application.PeticaoIntegrationTests;
import br.jus.stf.autuacao.domain.ClasseProcessualServiceUnitTests;
import br.jus.stf.autuacao.domain.MinistroServiceUnitTests;
import br.jus.stf.generico.interfaces.DocumentoIntegrationTests;
import br.jus.stf.shared.infra.action.AcoesIntegrationTests;

@RunWith(Suite.class)
@SuiteClasses({ AcoesIntegrationTests.class,
	DocumentoIntegrationTests.class, PeticaoApplicationServiceUnitTests.class,
	PeticaoIntegrationTests.class, ClasseProcessualServiceUnitTests.class,
	MinistroServiceUnitTests.class, PeticaoApplicationEventIntegrationTests.class})
public class TestsSuite {

}
