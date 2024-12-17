package org.projet.projetspring.services;

import jakarta.servlet.Filter;
import org.projet.projetspring.dtos.FilterDto;
import org.projet.projetspring.dtos.PersonDto;
import org.projet.projetspring.models.Person;
import org.projet.projetspring.models.Relationship;
import org.projet.projetspring.repositories.PersonRepository;
import org.projet.projetspring.repositories.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;
    private Filter filter;

    public List<PersonDto> toDto(List<Person> list) {
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
        if (Objects.equals(filterDto.condition(), "norelation")) {
            return this.toDto(this.personRepository.findAllByRequestedRelationshipsEmptyAndAskedRelationshipsEmpty());
        }
        if (Objects.equals(filterDto.condition(), "manyrelation")) {
            return this.toDto(this.personRepository.findPersonsWithMultipleRelationshipTypes());
        }
        if (Objects.equals(filterDto.condition(), "nbrelation") && filterDto.nbrelation() != null) {
            return this.toDto(this.personRepository.findAllWithListSizeLessThanEqual(filterDto.nbrelation()));
        }
        if (Objects.equals(filterDto.condition(), "keyword") && !filterDto.keyword().isEmpty()) {
            return this.toDto(this.personRepository.findAllByProfilContainingIgnoreCase(filterDto.keyword()));
        }

        if (Objects.equals(filterDto.condition(), "friendwith")) {
            return this.toDto(this.relationshipRepository.findAllByFriendWithFirstNameLastNameOfFriend(filterDto.user_firstname(), filterDto.user_lastname()));
        }
        return this.findAll();
    }


    public ArrayList<ArrayList<PersonDto>> findAllRelatedComponents(){
            ArrayList<ArrayList<Person>> personsCluster = new ArrayList<>();
            List<Person> persons = this.personRepository.findAll();
            Deque<Person> stack = new ArrayDeque<>();
            Set<Person> handledPersons = new HashSet<>();

            for (Person p : persons) {
                if (handledPersons.contains(p)) {
                    continue;
                }

                ArrayList<Person> cluster = new ArrayList<>();
                personsCluster.add(cluster);
                stack.push(p);

                while (!stack.isEmpty()) {
                    Person current = stack.pop();
                    if (handledPersons.contains(current)) {
                        continue;
                    }
                    handledPersons.add(current);
                    cluster.add(current);

                    for (Relationship relation : current.getAllFriendships()) {
                        Person neighbor = (current == relation.getFromUser()) ? relation.getToUser() : relation.getFromUser();
                        if (!handledPersons.contains(neighbor)) {
                            stack.push(neighbor);
                        }
                    }
                }
            }



        /*
        ArrayList<ArrayList<Person>> personsCluster = new ArrayList<>();
        List<Person> persons = this.personRepository.findAll();
        ArrayList<Person> stack = new ArrayList<>();
        ArrayList<Person> handledPersons = new ArrayList<>();
        int j = -1;
        for (Person p : persons) {
            if (handledPersons.contains(p)) {
                continue;
            }
            personsCluster.add(new ArrayList<Person>());
            j++;
            stack.add(p);
            while (!stack.isEmpty()) {
                Person current = stack.get(0);
                List<Relationship> friendships = current.getAllFriendships();
                for (Relationship relation : friendships) {
                    Person p1 = relation.getFromUser();
                    Person p2 = relation.getToUser();
                    Person toAdd;
                    if (current == p1) {
                        toAdd = p2;
                    } else {
                        toAdd = p1;
                    }
                    if(!handledPersons.contains(toAdd) && !stack.contains(toAdd)){
                        stack.add(toAdd);
                    }
                }
                handledPersons.add(current);
                stack.remove(current);
                personsCluster.get(j).add(current);
            }
        }
    */

        ArrayList<ArrayList<PersonDto>> result = new ArrayList<>();
        for(ArrayList<Person> personsArray : personsCluster){
            result.add((ArrayList<PersonDto>) this.toDto(personsArray));
        }
        return result;
    }
}
