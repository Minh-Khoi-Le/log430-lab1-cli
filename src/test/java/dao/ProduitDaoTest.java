package dao;

import model.Produit;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProduitDaoTest {

    private ProduitDao produitDao;

    @BeforeEach
    void setUp() {
        produitDao = new ProduitDao();
    }

    @Test
    @Order(1)
    void testAjouterProduit() {
        Produit p = new Produit("Banane", 1.99, 30);
        produitDao.save(p);

        Produit fromDb = produitDao.findById(p.getId());
        Assertions.assertNotNull(fromDb, "Produit doit être en BD après l'ajout");
        Assertions.assertEquals("Banane", fromDb.getNom());
        Assertions.assertEquals(1.99, fromDb.getPrix());
        Assertions.assertEquals(30, fromDb.getQuantite());
    }

    @Test
    @Order(2)
    void testRechercherProduitParNom() {
        Produit p = new Produit("Pomme", 2.50, 15);
        produitDao.save(p);

        List<Produit> produits = produitDao.findAll();
        boolean trouve = produits.stream().anyMatch(pr -> pr.getNom().equalsIgnoreCase("Pomme"));
        Assertions.assertTrue(trouve, "Recherche par nom doit trouver le produit");
    }

    @Test
    @Order(3)
    void testConsulterStock() {
        produitDao.save(new Produit("Orange", 2.75, 10));
        List<Produit> produits = produitDao.findAll();
        Assertions.assertFalse(produits.isEmpty(), "Stock doit contenir au moins un produit");
    }

    @Test
    @Order(4)
    void testSupprimerProduit() {
        Produit p = new Produit("Cerise", 3.99, 5);
        produitDao.save(p);
        Long id = p.getId();

        Produit beforeDelete = produitDao.findById(id);
        Assertions.assertNotNull(beforeDelete, "Produit doit exister avant suppression");

        produitDao.delete(beforeDelete);

        Produit afterDelete = produitDao.findById(id);
        Assertions.assertNull(afterDelete, "Produit doit être supprimé");
    }

    @Test
    @Order(5)
    void testMettreAJourQuantite() {
        Produit p = new Produit("Kiwi", 0.99, 8);
        produitDao.save(p);

        // Simule une vente de 3
        p.setQuantite(5);
        produitDao.update(p);

        Produit fromDb = produitDao.findById(p.getId());
        Assertions.assertEquals(5, fromDb.getQuantite(), "Stock doit être mis à jour après la vente");
    }

    @Test
    void testProduitInexistant() {
        Produit p = produitDao.findById(99999L);
        Assertions.assertNull(p, "Un produit inexistant doit renvoyer null");
    }

}
