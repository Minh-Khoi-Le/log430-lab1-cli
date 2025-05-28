# LOG430 - TP1 - Application de Caisse (2-Tier, Java/Hibernate/PostgreSQL)

## Description

Cette application simule un système de gestion de caisse en ligne de commande, développé en Java avec Hibernate (ORM) et PostgreSQL.  
L’architecture suit un modèle 2-Tiers : plusieurs clients (caisse) en conteneurs Docker accèdent directement à une base PostgreSQL (dans un conteneur dédié).

---

## Structure du projet

/src
/main/java : Code source principal (modèle, DAO, Main)
/test/java : Tests unitaires JUnit
/resources : Fichier de config hibernate.cfg.xml
/test/resources : Fichier de config hibernate.cfg.xml (tests)
Dockerfile : Image Java (build + run)
docker-compose.yml : Orchestration (DB + 3 caisses)
init-db.sql : Script auto-création BD test
README.md : Ce fichier
/docs


---

## Instructions d’exécution

### 1. **Prérequis**
- Docker & Docker Compose installés
- (Facultatif) Java 17+ et Maven pour build et test en local (variable local JAVA_HOME à ajouter.)

### 2. **Lancer l’application complète**
```bash
docker-compose up --build
```
Cela démarre :

- le conteneur PostgreSQL (db)

- trois clients caisses (caisse1, caisse2, caisse3)

- création auto de la base caisse_test via init-db.sql (pour les tests)
  

### 3. **Utilisation interactive (menu CLI)**

```bash
docker-compose run --rm caisse1
```
Chaque terminal CLI affiche le menu :

1. Rechercher un produit

2. Ajouter un produit

3. Consulter le stock

4. Supprimer un produit

5. Enregistrer une vente

6. Quitter

## **Instructions de test**

1. Vérifie que la DB tourne :

```bash
docker-compose up -d db
```
2. Lance les tests (local ou via Docker) :
```bash
mvn test
ou
docker run --rm -v "%cd%":/app -w /app maven:3.9.6-eclipse-temurin-21 mvn test
```
 
- Les tests s’exécutent avec la BD isolée caisse_test.


## Choix techniques

- Langage : Java 

- ORM : Hibernate 6 / JPA

- SGBD : PostgreSQL 16 (conteneur)

- Tests unitaires : JUnit 

- Déploiement : Docker 

- Gestion du schéma : Hibernate auto (hbm2ddl.auto)

- BD de test dédiée : caisse_test, isolée pour JUnit

Standard industriel pour la persistance, la conteneurisation et les tests reproductibles.
Séparation claire entre environnement de développement et test.


Auteur : Minh Khoi Le
Pour le cours de LOG430 - Architecture logicielle
 