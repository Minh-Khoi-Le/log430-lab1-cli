package dao;

import model.Produit;

public class ProduitDao extends GenericDao<Produit, Long> {
    public ProduitDao() {
        super(Produit.class);
    }
    
}
