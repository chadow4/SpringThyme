# Étape 1 : Image de base
FROM openjdk:17-jdk-alpine

# Étape 2 : Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Étape 3 : Copier le fichier JAR dans le conteneur
COPY target/projetspring-0.0.1-SNAPSHOT.jar app.jar

# Étape 4 : Exposer le port
EXPOSE 8080

# Étape 5 : Commande pour exécuter l’application
CMD ["java", "-jar", "app.jar"]
