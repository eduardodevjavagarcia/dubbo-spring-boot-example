package br.com.dubbo.server.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dubbo.server.example.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
