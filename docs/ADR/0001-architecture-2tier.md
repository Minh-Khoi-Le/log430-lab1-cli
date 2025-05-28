# ADR-0001 : Architecture 2-Tiers pour la gestion de caisse

## Statut
Accepté

## Contexte
Le projet consiste à réaliser une application de gestion de caisse pouvant être utilisée simultanément par plusieurs utilisateurs (caisses) accédant à une base de données centralisée.

## Décision
L’architecture 2-tiers est retenue : chaque instance de client (console Java) communique directement avec une base PostgreSQL partagée, via Hibernate (ORM).

## Conséquences
- Déploiement simple grâce à Docker Compose (1 conteneur DB, 3 conteneurs client)
- Couplage fort entre client et BD, mais gain de simplicité
- Possibilité d’évolution vers une architecture 3-tiers si besoin
- Limite la scalabilité côté logique métier (pas de serveur d’application intermédiaire)

## Justification
L’architecture 2-tiers répond aux besoins fonctionnels et techniques du TP, tout en restant simple à déployer et à maintenir.
