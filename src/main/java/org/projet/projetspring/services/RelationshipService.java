package org.projet.projetspring.services;

import org.projet.projetspring.models.Relationship;
import org.projet.projetspring.repositories.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    public Relationship save(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }
}