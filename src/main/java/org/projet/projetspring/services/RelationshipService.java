package org.projet.projetspring.services;


import org.projet.projetspring.dtos.RequestDto;
import org.projet.projetspring.models.Person;
import org.projet.projetspring.models.Relationship;
import org.projet.projetspring.repositories.PersonRepository;
import org.projet.projetspring.repositories.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private PersonRepository personRepository;

    public void createFriendship(RequestDto requestDto) {
        if (requestDto.getToUser().equals(requestDto.getFromUser())) {
            throw new IllegalArgumentException("Impossible de créer une amitié entre une personne et elle-même.");
        }
        Optional<Person> person1 = personRepository.findById(requestDto.getFromUser());
        Optional<Person> person2 = personRepository.findById(requestDto.getToUser());

        if (person1.isEmpty() || person2.isEmpty()) {
            throw new IllegalArgumentException("Personne introuvable pour les identifiants donnés.");
        }
        List<Relationship> relationships = person1.get().getAllFriendships();
        for (Relationship relationship : relationships) {
            if (Objects.equals(relationship.getRelationshipType(), requestDto.getRequestStr()) && (relationship.getFromUser().getId().equals(person2.get().getId()) || relationship.getToUser().getId().equals(person2.get().getId()))) {
                throw new IllegalArgumentException("Ces personnes sont déjà reliées.");
            }
        }

        Relationship relationship = new Relationship(requestDto.getRequestStr(), person1.get(), person2.get());
        relationshipRepository.save(relationship);

    }

}
