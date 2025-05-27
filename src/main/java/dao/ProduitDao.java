package dao;

import model.Produit;

public class ProduitDao extends GenericDao<Produit, Long> {
    public ProduitDao() {
        super(Produit.class);
    }
    // Ajoute ici d'autres méthodes spécifiques à Produit si nécessaire
}
