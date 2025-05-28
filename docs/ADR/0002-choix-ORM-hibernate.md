# ADR-0002 : Choix d’Hibernate pour la persistance ORM

## Statut
Accepté

## Contexte
L’application nécessite une persistance des entités (produits, ventes, etc) dans une base relationnelle, avec gestion des transactions et du mapping objet-relationnel.

## Décision
Hibernate (JPA) est choisi comme ORM pour :
- Faciliter le mapping des entités Java vers les tables PostgreSQL
- Simplifier la gestion des transactions et de la génération du schéma
- Utiliser un standard de l’industrie, bien intégré à Maven et Docker

## Conséquences
- Mapping et persistance des entités simplifiés
- Schéma généré automatiquement (hbm2ddl.auto)
- Possibilité de tester facilement la logique avec une base dédiée (caisse_test)
- Dépendance forte à l’ORM (apprentissage, configuration)

## Justification
Hibernate est robuste, documenté, et facilement utilisable.