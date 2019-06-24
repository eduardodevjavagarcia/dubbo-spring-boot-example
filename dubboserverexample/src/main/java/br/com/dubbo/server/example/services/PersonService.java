package br.com.dubbo.server.example.services;

import java.util.List;

import br.com.dubbo.server.example.domain.Person;

public interface PersonService {

	List<Person> findAll();

	Person save(Person personRequest);

	Person updatePartial(Long id, Person personRequest);

	Person delete(Long id);

	Person delete(Person person);

	Person findById(Long id);

	Person update(Long id, Person personRequest);

}
