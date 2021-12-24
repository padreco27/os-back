package com.paulo.os.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.paulo.os.domain.OS;
import com.paulo.os.dto.OSDTO;
import com.paulo.os.service.OsSercice;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/os")
public class OsResource {

	@Autowired
	private OsSercice service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<OSDTO> findById(@PathVariable Integer id) {

		OSDTO objDTO = new OSDTO(service.findById(id));
		return ResponseEntity.ok().body(objDTO);

	}

	@GetMapping
	public ResponseEntity<List<OSDTO>> findALL() {
		List<OS> list = service.findALL();
		List<OSDTO> listDTO = list.stream().map(obj -> new OSDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping
	public ResponseEntity<OSDTO> create(@Valid @RequestBody OSDTO objDTO) {
		OS newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<OSDTO> uptade(@Valid @RequestBody OSDTO objDto) {
		objDto = new OSDTO(service.uptade(objDto));
		return ResponseEntity.ok().body(objDto);
	}

	

}
