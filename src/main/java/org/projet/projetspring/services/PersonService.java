package org.projet.projetspring.services;

import org.projet.projetspring.dtos.FilterDto;
import org.projet.projetspring.dtos.PersonDto;
import org.projet.projetspring.models.Person;
import org.projet.projetspring.repositories.PersonRepository;
import org.projet.projetspring.repositories.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    public List<PersonDto> toDto(List<Person> list){
        return list.stream().map(person -> new PersonDto(
                        person.getId(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getBirthDate(),
                        person.getProfil()))
                .collect(Collectors.toList());
    }


    public List<PersonDto> findAll() {
        return this.toDto(personRepository.findAll());
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person createUser(PersonDto personDto) {
        Person person = new Person(personDto.firstName(), personDto.lastName(), personDto.birthDate(), personDto.profil());
        return personRepository.save(person);
    }

    public Person createUser(String firstName, String lastName, LocalDate birthDate, String profil) {
        Person person = new Person(firstName, lastName, birthDate, profil);
        return personRepository.save(person);
    }

    public List<PersonDto> findSorted(FilterDto filterDto) {
        if(Objects.equals(filterDto.condition(), "norelation")){
            return this.toDto(this.personRepository.findAllByRequestedRelationshipsEmptyAndAskedRelationshipsEmpty());
        }
        if(Objects.equals(filterDto.condition(), "manyrelation")){
            return this.toDto(this.personRepository.findAllByDifferentRelations());
        }
        if(Objects.equals(filterDto.condition(), "nbrelation") && filterDto.nbrelation() != null){
            return this.toDto(this.personRepository.findAllWithListSizeLessThanEqual(filterDto.nbrelation()));
        }
        if(Objects.equals(filterDto.condition(), "keyword") && !filterDto.keyword().isEmpty()){
            return this.toDto(this.personRepository.findAllByProfilContaining(filterDto.keyword()));
        }

        if(Objects.equals(filterDto.condition(), "friendwith") && filterDto.user_id() != null){
            return this.toDto(this.relationshipRepository.findAllByFriendWith(filterDto.user_id()));
        }
        return this.findAll();
    }
}
