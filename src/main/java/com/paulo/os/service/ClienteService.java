package com.paulo.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulo.os.domain.Cliente;
import com.paulo.os.domain.Pessoa;
import com.paulo.os.dto.ClienteDTO;
import com.paulo.os.repositories.ClienteRepository;
import com.paulo.os.repositories.PessoaRepository;
import com.paulo.os.service.exceptions.DataIntegratyViolationException;
import com.paulo.os.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrada ! id:" + id + ", tipo " + Cliente.class.getName()));
	}

	public List<Cliente> findALL() {

		return repository.findAll();
	}

	public Cliente create(ClienteDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("Cpf ja cadastrado na base de dados!");
		}
		return repository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));

	}

	public Cliente uptade(Integer id, @Valid ClienteDTO objDto) {
		Cliente oldObj = findById(id);

		if (findByCPF(objDto) != null && findByCPF(objDto).getId() != id) {
			throw new DataIntegratyViolationException("Cpf ja cadastrado na base de dados!");
		}
		oldObj.setNome(objDto.getNome());
		oldObj.setCpf(objDto.getCpf());
		oldObj.setTelefone(objDto.getTelefone());
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Cliente obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Pessoa possui ordens de serviço ");
		}

		repository.deleteById(id);
		{

		}

	}

	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}
