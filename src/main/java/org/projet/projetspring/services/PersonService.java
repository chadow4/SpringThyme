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

    public List<PersonDto> findAll() {
        return personRepository.findAll().stream()
                .map(person -> new PersonDto(
                        person.getId(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getBirthDate()))
                .collect(Collectors.toList());
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person createUser(PersonDto personDto) {
        Person person = new Person(personDto.firstName(), personDto.lastName(), personDto.birthDate());
        return personRepository.save(person);
    }
}
