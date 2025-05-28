package pos;

import dao.ProduitDao;
import model.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.logging.*;

public class Main {
    public static void main(String[] args) {
        //Desactiver les logs de Hibernate
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.SEVERE);
        for (Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.SEVERE);
        }

        ProduitDao produitDao = new ProduitDao();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n==== MENU ====");
            System.out.println("1. Rechercher un produit");
            System.out.println("2. Ajouter un produit");
            System.out.println("3. Consulter le stock");
            System.out.println("4. Supprimer un produit");
            System.out.println("5. Enregistrer une vente");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");
            String choix = sc.nextLine();

            switch (choix) {
                case "1":
                    rechercherProduit(sc, produitDao);
                    break;
                case "2":
                    ajouterProduit(sc, produitDao);
                    break;
                case "3":
                    consulterStock(produitDao);
                    break;
                case "4":
                    supprimerProduit(sc, produitDao);
                    break;
                case "5":
                    enregistrerVente(sc, produitDao);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        }
        sc.close();
        System.out.println("Fermeture du système.");
    }

    private static void rechercherProduit(Scanner sc, ProduitDao produitDao) {
        System.out.print("Nom du produit à rechercher : ");
        String nomRecherche = sc.nextLine();
        List<Produit> resultats = produitDao.findAll();
        boolean trouve = false;
        for (Produit p : resultats) {
            if (p.getNom().equalsIgnoreCase(nomRecherche)) {
                System.out.println("-> " + p.getId() + " | " + p.getNom() + " | Prix : " + p.getPrix() + " | Stock : " + p.getQuantite());
                trouve = true;
            }
        }
        if (!trouve) System.out.println("Aucun produit trouvé.");
    }

    private static void ajouterProduit(Scanner sc, ProduitDao produitDao) {
        System.out.print("Nom : ");
        String nom = sc.nextLine();
        System.out.print("Prix : ");
        double prix = Double.parseDouble(sc.nextLine());
        System.out.print("Quantité en stock : ");
        int quantite = Integer.parseInt(sc.nextLine());
        Produit prod = new Produit(nom, prix, quantite);
        produitDao.save(prod);
        System.out.println("Produit ajouté !");
    }

    private static void consulterStock(ProduitDao produitDao) {
        List<Produit> produits = produitDao.findAll();
        System.out.println("Stock de produits :");
        for (Produit p : produits) {
            System.out.println("- " + p.getId() + " | " + p.getNom() + " | Prix : " + p.getPrix() + " | Stock : " + p.getQuantite());
        }
    }


    //Supprimer un produit
    private static void supprimerProduit(Scanner sc, ProduitDao produitDao) {
        List<Produit> produits = produitDao.findAll();
        if (produits.isEmpty()) {
            System.out.println("Aucun produit disponible.");
            return;
        }
        System.out.println("Produits disponibles :");
        for (Produit p : produits) {
            System.out.println("- ID : " + p.getId() + " | " + p.getNom() + " | Stock : " + p.getQuantite());
        }
        System.out.print("ID du produit à supprimer : ");
        Long id = Long.parseLong(sc.nextLine());
        Produit prod = produitDao.findById(id);
        if (prod != null) {
            produitDao.delete(prod);
            System.out.println("Produit supprimé !");
        } else {
            System.out.println("Produit introuvable.");
        }
    }

    // Enregistrer une vente
    private static void enregistrerVente(Scanner sc, ProduitDao produitDao) {
        List<Produit> produits = produitDao.findAll();
        if (produits.isEmpty()) {
            System.out.println("Aucun produit en stock !");
            return;
        }
        List<Produit> produitsAchetes = new ArrayList<>();
        List<Integer> quantites = new ArrayList<>();
        boolean ajouter = true;
        double total = 0;

        while (ajouter) {
            System.out.println("\nProduits disponibles :");
            for (Produit p : produits) {
                System.out.println("- ID : " + p.getId() + " | " + p.getNom() + " | Prix : " + p.getPrix() + " | Stock : " + p.getQuantite());
            }
            System.out.print("Entrez ID du produit ou terminer : ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("terminer")) break;
            Long id = Long.parseLong(input);
            Produit prod = produitDao.findById(id);
            if (prod == null) {
                System.out.println("Produit introuvable.");
                continue;
            }
            System.out.print("Quantité : ");
            int qte = Integer.parseInt(sc.nextLine());
            if (qte > prod.getQuantite()) {
                System.out.println("Stock insuffisant !");
                continue;
            }
            produitsAchetes.add(prod);
            quantites.add(qte);
            total += prod.getPrix() * qte;
            System.out.print("Continuer l'achat (c) ou quitter (q) ? ");
            String rep = sc.nextLine();
            ajouter = rep.equalsIgnoreCase("c");
        }

        // Mise à jour du stock
        for (int i = 0; i < produitsAchetes.size(); i++) {
            Produit prod = produitsAchetes.get(i);
            int newStock = prod.getQuantite() - quantites.get(i);
            prod.setQuantite(newStock);
            produitDao.update(prod);
        }

        // Impression facture
        System.out.println("\n=== Facture ===");
        System.out.println("Date : " + java.time.LocalDate.now());
        System.out.println("Produits achetés :");
        for (int i = 0; i < produitsAchetes.size(); i++) {
            Produit prod = produitsAchetes.get(i);
            int qte = quantites.get(i);
            double prix = prod.getPrix();
            System.out.println("- " + prod.getNom() + " x" + qte + " @ " + prix + " = " + String.format("%.2f", prix * qte) + "$");
        }
        System.out.println("Total : " + String.format("%.2f", total) + "$");
        System.out.println("=======================");
    }
}
