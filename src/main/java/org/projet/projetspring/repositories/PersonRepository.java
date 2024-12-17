package org.projet.projetspring.repositories;

import org.projet.projetspring.models.Person;
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

    @Query(value = "SELECT p.id, p.birth_date, p.first_name, p.last_name, p.profil " +
            "FROM Person p " +
            "JOIN Relationship r ON p.id = r.FROM_USER OR p.id = r.TO_USER " +
            "GROUP BY p.id " +
            "HAVING COUNT(DISTINCT r.relationship_type) > 1",
            nativeQuery = true)
    List<Person> findPersonsWithMultipleRelationshipTypes();

}