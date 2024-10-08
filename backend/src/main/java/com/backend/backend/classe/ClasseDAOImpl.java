package com.backend.backend.classe;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClasseDAOImpl implements ClasseDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Classe> getnomClasse() {
        // Query to fetch all classes
        return entityManager.createQuery("from Classe", Classe.class).getResultList();
    }

    @Override
    @Transactional
    public void save(Classe classe) {
        // Save or update a class
        entityManager.merge(classe);
    }

    @Override
    @Transactional
    public void delete(String nomClasse) {
        // Find the class by name and delete it
        Classe classe = findByNomClasse(nomClasse, null);  // Assuming niveauClasse is not required for deletion
        if (classe != null) {
            entityManager.remove(classe);
        }
    }

    @Override
    public Classe findByNomClasse(String nomClasse, String niveauClasse) {
        // Query to find class by nomClasse and niveauClasse
        try {
            return entityManager.createQuery(
                            "from Classe where nomClasse = :nomClasse and (:niveauClasse is null or niveauClasse = :niveauClasse)",
                            Classe.class
                    )
                    .setParameter("nomClasse", nomClasse)
                    .setParameter("niveauClasse", niveauClasse)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // No result found
        }
    }
}
