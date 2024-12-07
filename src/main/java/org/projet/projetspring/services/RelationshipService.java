package org.projet.projetspring.services;


import jakarta.transaction.Transactional;
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
import java.util.Set;

@Service
public class RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public Relationship createFriendship(RequestDto requestDto) {
        return this.createFriendship(requestDto.toUser(), requestDto.fromUser(), requestDto.requestStr());
    }

    @Transactional
    public Relationship createFriendship(Long toUser, Long fromUser, String requestStr){
        if (toUser.equals(fromUser)) {
            throw new IllegalArgumentException("Impossible de créer une amitié entre une personne et elle-même.");
        }
        Optional<Person> person1 = personRepository.findById(fromUser);
        Optional<Person> person2 = personRepository.findById(toUser);

        if (person1.isEmpty() || person2.isEmpty()) {
            throw new IllegalArgumentException("Personne introuvable pour les identifiants donnés.");
        }
        List<Relationship> relationships = person1.get().getAllFriendships();
        for (Relationship relationship : relationships) {
            if (Objects.equals(relationship.getRelationshipType(), requestStr) && (relationship.getFromUser().getId().equals(person2.get().getId()) || relationship.getToUser().getId().equals(person2.get().getId()))) {
                throw new IllegalArgumentException("Ces personnes sont déjà reliées.");
            }
        }

        Relationship relationship = new Relationship(requestStr, person1.get(), person2.get());
        return relationshipRepository.save(relationship);
    }


    public Set<String> getSortedRelationTypes(){
        return this.relationshipRepository.findSortedRelationTypes();
    }

}
