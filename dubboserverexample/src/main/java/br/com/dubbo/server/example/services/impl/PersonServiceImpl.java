package br.com.dubbo.server.example.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import br.com.dubbo.server.example.domain.Person;
import br.com.dubbo.server.example.exceptions.PersonExistsException;
import br.com.dubbo.server.example.exceptions.PersonNotExistsException;
import br.com.dubbo.server.example.exceptions.PersonNotFoundException;
import br.com.dubbo.server.example.repositories.PersonRepository;
import br.com.dubbo.server.example.services.PersonService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(version = "1.0.0", executes = 200, loadbalance = "random")
@Transactional
public class PersonServiceImpl implements PersonService {

	private PersonRepository personRepository;

	public PersonServiceImpl(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Person> findAll() {
		log.info("Find all persons");
		return this.personRepository.findAll();
	}

	@Override
	public Person save(Person personRequest) {
		log.info("Save person");

		Optional.ofNullable(personRequest).filter(personReq -> !ObjectUtils.isEmpty(personReq.getId()))
				.flatMap(personReq -> this.personRepository.findById(personReq.getId())).ifPresent(
						personReq -> new PersonExistsException(personReq.getName() + " already exist in the system"));

		personRequest.setDateBirth(LocalDate.now());
		return this.personRepository.save(personRequest);
	}

	@Override
	public Person updatePartial(Long id, Person personRequest) {
		log.info("update");

		return Optional.ofNullable(id).map(this.personRepository::findById).filter(Optional::isPresent)
				.map(Optional::get)
				.map(person -> this.personRepository
						.save(Person.valueOf(person, personRequest.getName(), personRequest.getAge())))
				.orElseThrow(() -> new PersonNotExistsException("Person not exist in the system"));
	}

	@Override
	public Person delete(Long id) {
		log.info("delete");
		return Optional.ofNullable(id).map(this.personRepository::findById).filter(Optional::isPresent)
				.map(Optional::get).map(this::delete).orElse(null);
	}

	@Override
	public Person delete(Person person) {
		this.personRepository.delete(person);
		return person;
	}

	@Override
	public Person findById(Long id) {
		return Optional.ofNullable(this.personRepository.findById(id)).filter(Optional::isPresent).map(Optional::get)
				.orElseThrow(PersonNotFoundException::new);
	}

	@Override
	public Person update(Long id, Person personRequest) {
		log.info("Save person");

		Optional.ofNullable(id).flatMap(idPerson -> this.personRepository.findById(idPerson)).ifPresent(
				personReq -> new PersonExistsException(personReq.getName() + " already exist in the system"));

		return this.personRepository.save(Person.valueOf(id, personRequest));
	}

}