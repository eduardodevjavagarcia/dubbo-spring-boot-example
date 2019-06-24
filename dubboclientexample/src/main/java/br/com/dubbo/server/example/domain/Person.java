package br.com.dubbo.server.example.domain;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person implements Serializable {

	private Long id;

	private String name;

	private Integer age;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dateBirth;

	public static Person valueOf(Person person, String name, Integer age) {
		return Person.builder().id(person.getId()).name(name).age(age).dateBirth(person.getDateBirth()).build();
	}

	public static Person valueOf(Long id, Person person) {
		return Person.builder().id(id).name(person.getName()).age(person.getAge()).dateBirth(person.getDateBirth())
				.build();
	}

}