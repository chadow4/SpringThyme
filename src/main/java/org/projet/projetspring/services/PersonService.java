package org.projet.projetspring.services;

import org.projet.projetspring.dtos.PersonDto;
import org.projet.projetspring.models.Person;
import org.projet.projetspring.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private PersonDto convertToDto(Person person) {
        return new PersonDto(person.getId(), person.getFirstName(), person.getLastName(), person.getBirthDate());
    }

    public List<PersonDto> findAll() {
        return personRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Person findById(Long id) {
        return  personRepository.findById(id).orElse(null);
    }

    public Person createUser(PersonDto personDto) {
        Person user = new Person(personDto.getFirstName(), personDto.getLastName(), personDto.getBirthDate());
        return personRepository.save(user);
    }
}