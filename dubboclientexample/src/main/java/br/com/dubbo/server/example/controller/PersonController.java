package br.com.dubbo.server.example.controller;

import java.util.List;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dubbo.server.example.domain.Person;
import br.com.dubbo.server.example.services.PersonService;

@RequestMapping("/persons")
@RestController
public class PersonController {

	@Reference(cache = "lru", version = "1.0.0", timeout = 300, actives = 0, retries = 2, loadbalance = "random")
	private PersonService personService;

	@GetMapping
	public ResponseEntity<List<Person>> findAll() {
		return ResponseEntity.ok(this.personService.findAll());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Person> findById(@PathVariable Long id) {
		return new ResponseEntity<>(this.personService.findById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Person> create(@RequestBody Person person) {
		return new ResponseEntity<>(this.personService.save(person), HttpStatus.CREATED);
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Person person) {
		return ResponseEntity.ok(this.personService.updatePartial(id, person));
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person person) {
		return ResponseEntity.ok(this.personService.update(id, person));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return ResponseEntity.ok(this.personService.delete(id));
	}

}
