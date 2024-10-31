package org.projet.projetspring.services;


import org.projet.projetspring.models.Person;
import org.projet.projetspring.models.Relationship;
import org.projet.projetspring.repositories.PersonRepository;
import org.projet.projetspring.repositories.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private PersonRepository personRepository;

    public Relationship createFriendship(Long person1Id, Long person2Id) {
        Optional<Person> person1 = personRepository.findById(person1Id);
        Optional<Person> person2 = personRepository.findById(person2Id);

        if (person1Id.equals(person2Id)) {
            throw new IllegalArgumentException("Impossible de créer une amitié entre une personne et elle-même.");
        }
        List <Relationship> relationships = relationshipRepository.findFriendshipsByPersonId(person1Id, "ami");
        for (Relationship relationship : relationships) {
            if (relationship.getPerson1().getId().equals(person2Id) || relationship.getPerson2().getId().equals(person2Id)) {
                throw new IllegalArgumentException("Ces personnes sont déjà amies.");
            }
        }

        if (person1.isPresent() && person2.isPresent()) {
            Relationship relationship = new Relationship();
            relationship.setPerson1(person1.get());
            relationship.setPerson2(person2.get());
            relationship.setRelationshipType("ami");
            return relationshipRepository.save(relationship);
        } else {
            throw new IllegalArgumentException("Personne introuvable pour les identifiants donnés.");
        }
    }

    public List<Relationship> getFriendshipsByPersonId(Long personId) {
        return relationshipRepository.findFriendshipsByPersonId(personId, "ami");
    }
}
