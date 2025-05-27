package pos;

import dao.ProduitDao;
import model.Produit;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Système de Caisse CLI ===");

        ProduitDao produitDao = new ProduitDao();

        // Création et sauvegarde d’un produit de test
        Produit melon = new Produit("Melon", 3.99, 20);
        produitDao.save(melon);
        System.out.println("Produit ajouté en BD : " + melon.getNom());

        // Lecture des produits
        List<Produit> produits = produitDao.findAll();
        System.out.println("Produits en BD :");
        for (Produit p : produits) {
            System.out.println("- " + p.getId() + " | " + p.getNom() + " | Prix : " + p.getPrix() + " | Stock : " + p.getQuantite());
        }
    }
}
