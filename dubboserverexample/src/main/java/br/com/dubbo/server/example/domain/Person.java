package br.com.dubbo.server.example.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "person")
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "age")
	private Integer age;

	@JsonFormat(timezone = "GMT")
	@Column(name = "date_birth")
	private LocalDate dateBirth;

	public static Person valueOf(Person person, String name, Integer age) {
		return Person.builder().id(person.getId()).name(name).age(age).dateBirth(person.getDateBirth()).build();
	}

	public static Person valueOf(Long id, Person person) {
		return Person.builder().id(id).name(person.getName()).age(person.getAge()).dateBirth(person.getDateBirth())
				.build();
	}

}