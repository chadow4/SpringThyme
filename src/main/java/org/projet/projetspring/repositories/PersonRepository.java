package org.projet.projetspring.repositories;

import org.projet.projetspring.models.Person;
import org.projet.projetspring.models.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByProfilContaining(String keyword);
    List<Person> findAllByRequestedRelationshipsEmptyAndAskedRelationshipsEmpty();
    @Query("SELECT p FROM Person p WHERE (size(p.askedRelationships)+size(p.requestedRelationships)) <= :n")
    List<Person> findAllWithListSizeLessThanEqual(@Param("n") int n);

    @Query("SELECT p FROM Person p JOIN p.requestedRelationships r GROUP BY p.id HAVING COUNT(DISTINCT r.relationshipType) > 1")
    List<Person> findAllByDifferentRelations();
}