package org.projet.projetspring.repositories;

import org.projet.projetspring.models.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    @Query("SELECT r FROM Relationship r WHERE (r.person1.id = :personId OR r.person2.id = :personId) AND r.relationshipType = :relationshipType")
    List<Relationship> findFriendshipsByPersonId(
            @Param("personId") Long personId,
            @Param("relationshipType") String relationshipType
    );
}