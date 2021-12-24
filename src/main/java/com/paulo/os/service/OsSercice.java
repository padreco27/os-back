package com.paulo.os.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulo.os.domain.Cliente;
import com.paulo.os.domain.OS;
import com.paulo.os.domain.Tecnico;
import com.paulo.os.domain.enums.Prioridade;
import com.paulo.os.domain.enums.Status;
import com.paulo.os.dto.OSDTO;
import com.paulo.os.repositories.OSRepository;
import com.paulo.os.service.exceptions.ObjectNotFoundException;

@Service
public class OsSercice {

	@Autowired
	private OSRepository repository;

	@Autowired
	private TecnicoService tecnicoservice;

	@Autowired
	private ClienteService clienteservice;

	public OS findById(Integer id) {
		Optional<OS> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrada ! id:" + id + ", tipo " + OS.class.getName()));
	}

	public List<OS> findALL() {

		return repository.findAll();
	}

	public OS create(@Valid OSDTO objDTO) {

		return fromDTO(objDTO);
	}

	

	public OS uptade(@Valid OSDTO objDto) {
		findById(objDto.getId());
		return fromDTO(objDto);
	}

	private OS fromDTO(OSDTO objDTO) {
		OS newObj = new OS();
		newObj.setId(objDTO.getId());
		newObj.setObservacoes(objDTO.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(objDTO.getPrioridade()));
		newObj.setStatus(Status.toEnum(objDTO.getStatus()));

		Tecnico tec = tecnicoservice.findById(objDTO.getTecnico());
		Cliente cli = clienteservice.findById(objDTO.getCliente());

		newObj.setCliente(cli);
		newObj.setTecnico(tec);

		if (newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}

		return repository.save(newObj);
	}

}
