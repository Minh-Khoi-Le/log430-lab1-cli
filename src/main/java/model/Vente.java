package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventes")
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;

    // Pour la simplicité, on ne gère pas encore la liste des produits vendus, à ajouter avec une entité "VenteProduit" plus tard.

    public Vente() {}

    public Vente(LocalDateTime date, Employe employe) {
        this.date = date;
        this.employe = employe;
    }

    // Getters et setters
    public Long getId() { return id; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
}
