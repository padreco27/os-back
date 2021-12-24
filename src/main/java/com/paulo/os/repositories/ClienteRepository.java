package com.paulo.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paulo.os.domain.Cliente;



public interface ClienteRepository  extends JpaRepository<Cliente, Integer>{

}
