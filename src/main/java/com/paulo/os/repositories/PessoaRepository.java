package com.paulo.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paulo.os.domain.Pessoa;
import com.paulo.os.domain.Tecnico;


@Repository
public interface PessoaRepository  extends JpaRepository<Pessoa, Integer>{
	
	@Query("SELECT obj FROM Pessoa obj WHERE obj.cpf =:cpf")
	Tecnico findByCPF(@Param("cpf") String cpf);
}	
