package br.jus.stf.generico.interfaces.facade;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.generico.application.GenericoApplicationService;
import br.jus.stf.generico.domain.model.ClasseRepository;
import br.jus.stf.generico.domain.model.DocumentoRepository;
import br.jus.stf.generico.domain.model.DocumentoTemporario;
import br.jus.stf.generico.domain.model.MinistroRepository;
import br.jus.stf.generico.domain.model.PessoaRepository;
import br.jus.stf.generico.interfaces.dto.ClasseDto;
import br.jus.stf.generico.interfaces.dto.ClasseDtoAssembler;
import br.jus.stf.generico.interfaces.dto.DocumentoDto;
import br.jus.stf.generico.interfaces.dto.DocumentoDtoAssembler;
import br.jus.stf.generico.interfaces.dto.MinistroDto;
import br.jus.stf.generico.interfaces.dto.MinistroDtoAssembler;
import br.jus.stf.generico.interfaces.dto.PessoaDto;
import br.jus.stf.generico.interfaces.dto.PessoaDtoAssembler;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.PessoaId;


/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class GenericoServiceFacade {

	@Autowired
	private GenericoApplicationService genericoApplicationService;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ClasseRepository classeRepository;
	
	@Autowired
	private MinistroRepository ministroRepository;
	
	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Autowired
	private ClasseDtoAssembler classeDtoAssembler;	
	
	@Autowired
	private MinistroDtoAssembler ministroDtoAssembler;
	
	@Autowired
	private PessoaDtoAssembler pessoaDtoAssembler;
	
	@Autowired
	private DocumentoDtoAssembler documentoDtoAssembler;
	
	/**
	 * Cadastra uma lista de pessoas
	 * 
	 * @param pessoasNovas
	 * @return lista de identificadores de pessoas cadastradas
	 */
	public List<PessoaDto> cadastrarPessoas(List<String> pessoas) {
		return genericoApplicationService.cadastrarPessoas(pessoas).stream()
				.map(pessoa -> pessoaDtoAssembler.toDto(pessoa))
				.collect(Collectors.toList());
	}
	
	/**
	 * Pesquisa uma pessoa
	 * 
	 * @param pessoaId
	 * @return a pessoa
	 */
	public PessoaDto pesquisarPessoa(Long pessoaId) {
		PessoaId id = new PessoaId(pessoaId); 
		return pessoaDtoAssembler.toDto(pessoaRepository.findOne(id));
	}	
	
	public List<DocumentoDto> salvarDocumentos(List<String> documentosTemporarios) {
		return genericoApplicationService.salvarDocumentos(documentosTemporarios).entrySet().stream()
				.map(entry -> documentoDtoAssembler.toDto(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}
	
	public String salvarDocumentoTemporario(MultipartFile file) {
		DocumentoTemporario documentoTemporario = new DocumentoTemporario(file);
		return genericoApplicationService.salvarDocumentoTemporario(documentoTemporario);
	}
	
	public InputStream pesquisaDocumento(Long documentoId) {
		return documentoRepository.loadStream(new DocumentoId(documentoId));
	}
	
	public List<ClasseDto> listarClasses() {
		return classeRepository.findAll().stream()
				.map(classe -> classeDtoAssembler.toDto(classe))
				.collect(Collectors.toList());
	}
	
	public List<MinistroDto> listarMinistros() {
		return ministroRepository.findAll().stream()
				.map(ministro -> ministroDtoAssembler.toDto(ministro))
				.collect(Collectors.toList());
	}
	
}
