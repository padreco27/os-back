package com.paulo.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulo.os.domain.Pessoa;
import com.paulo.os.domain.Tecnico;
import com.paulo.os.dto.TecnicoDTO;
import com.paulo.os.repositories.PessoaRepository;
import com.paulo.os.repositories.TecnicoRepository;
import com.paulo.os.service.exceptions.DataIntegratyViolationException;
import com.paulo.os.service.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj= repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrada ! id:" + id + ", tipo " + Tecnico.class.getName()));
	}

	public List<Tecnico> findALL() {
		
		return repository.findAll();
	}

	public  Tecnico create( TecnicoDTO objDTO) {
		if(findByCPF(objDTO)!= null) {
			throw new DataIntegratyViolationException("Cpf ja cadastrado na base de dados!");
		}
		return repository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
		
	}
	
	
	public Tecnico uptade( Integer id,@Valid TecnicoDTO objDto) {
		Tecnico oldObj=findById(id);
		
		if(findByCPF(objDto)!= null && findByCPF(objDto).getId() != id ) {
			throw new DataIntegratyViolationException("Cpf ja cadastrado na base de dados!");
		}
		oldObj.setNome(objDto.getNome());
		oldObj.setCpf(objDto.getCpf());
		oldObj.setTelefone(objDto.getTelefone());
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
			if(obj.getList().size() >0) {
				throw new DataIntegratyViolationException("Tecnico possui ordens de serviço ");
			}
		
		
			repository.deleteById(id);{
				
			}
		
		
	}
	
	
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
	}

	

	

	
}
