# 🗳️ Voting CI - Projet d'Intégration Continue
Ce projet implémente un système de vote modulaire en Java. Il sert de démonstration pour l'application de Design Patterns (Factory, Strategy, Observer) et la mise en place d'un pipeline DevOps complet (Maven, Jenkins, SonarQube, JaCoCo).

## 📋 Prérequis Techniques
Pour construire et tester ce projet localement, vous avez besoin de :

Java 17 (Minimum requis pour le projet et SonarQube).

Maven 3.9+.

IntelliJ IDEA (recommandé).

Git.

## 🚀 Installation et Exécution (Local)
### 1. Cloner le projet

```Bash
git clone https://github.com/NadaMaliki/clean-voting-system.git
cd voting-ci-demo
```

### 2. Compilation et Tests
Pour compiler le projet et lancer les tests unitaires :

```Bash
mvn clean install
```

Les rapports de tests sont générés dans target/surefire-reports.

### 3. Lancer l'application (CLI)
L'application fonctionne en ligne de commande. Pour la lancer depuis le terminal :

```Bash
mvn exec:java -Dexec.mainClass="com.example.vote.App"
```

Commandes disponibles dans l'application :

vote : Enregistrer un nouveau vote (nom votant + candidat).

count : Afficher les résultats actuels (Stratégie Plurality).

stats : Afficher le nombre total de votes et le gagnant.

reset : Vider les votes en mémoire.

exit : Quitter.

## 📊 Analyse de Code et Qualité
### Couverture de Code (JaCoCo)
Pour générer le rapport de couverture de code localement :

```Bash
mvn jacoco:report
```

Ouvrez ensuite le fichier suivant dans votre navigateur pour voir les résultats (objectif > 60%) : 👉 target/site/jacoco/index.html

### Analyse SonarQube (Local)
Assurez-vous que le serveur SonarQube est démarré (nécessite JDK 17) :

Windows : StartSonar.bat

URL : http://localhost:9000

Login/Pass : admin / admin

Lancez l'analyse depuis le terminal :

```Bash
mvn sonar:sonar -Dsonar.login=VOTRE_TOKEN_SONARQUBE
```

## ⚙️ Configuration du Pipeline CI/CD (Jenkins)
Ce projet inclut un fichier Jenkinsfile configuré pour un environnement Windows.

### 1. Prérequis Jenkins
Assurez-vous que les plugins suivants sont installés :

Maven Integration

SonarQube Scanner

JaCoCo

Pipeline

### 2. Configuration des Outils (Global Tool Configuration)
Le Jenkinsfile fait référence à des outils spécifiques. Configurez-les dans Manage Jenkins > Tools :

JDK : Ajoutez une installation nommée exactement JDK17 (pointez vers votre dossier Java 17 local).

Maven : Ajoutez une installation nommée exactement Maven3.

### 3. Configuration Système (System Configuration)
Dans Manage Jenkins > System -> SonarQube servers :

Name : sonar (Attention : tout en minuscules).

Server URL : http://localhost:9000

Token : Sélectionnez votre credential (Secret Text) contenant le token SonarQube.

### 4. Configuration du Webhook (Quality Gate)
Pour que Jenkins attende le résultat de l'analyse SonarQube :

Allez dans SonarQube > Administration > Configuration > Webhooks.

Créez un Webhook :

Name : Jenkins

URL : http://localhost:8080/sonarqube-webhook/

Note : Si vous êtes en local, ajoutez sonar.validateWebhooks=false dans sonar.properties pour autoriser localhost.

### 5. Création du Job
Créez un job de type Pipeline.

Dans la section Pipeline, choisissez Pipeline script from SCM.

SCM : Git.

Repository URL : Votre lien GitHub.

Branch : */main.

Script Path : Jenkinsfile.

## 🏗️ Architecture et Design Patterns
Le projet suit une architecture modulaire respectant les principes SOLID :

Factory Method (RepositoryFactory) :

Permet d'instancier le dépôt de données (Mémoire, H2, Fichier) sans coupler le code client à l'implémentation concrète.

Strategy (CountingStrategy) :

Permet de changer l'algorithme de comptage des votes (Plurality, Weighted, etc.) dynamiquement à l'exécution.

Observer (VoteListener) :

Notifie les observateurs (ex: LoggingVoteListener) à chaque fois qu'un vote est ajouté, permettant de découpler la logique métier du logging ou de l'audit.
