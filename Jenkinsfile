pipeline {
    agent any

    environment {
        // Récupération des credentials définis dans Jenkins
        // Assurez-vous que l'ID dans Jenkins est bien 'Sonar_Token' et 'SONAR_HOST' (ou écrivez l'URL en dur)
        SONAR_TOKEN = credentials('Sonar_Token')
    }

    tools {
        // Ces noms doivent correspondre EXACTEMENT à "Manage Jenkins > Tools"
        maven 'Maven3'
        jdk 'JDK17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // Utilisation de 'bat' pour Windows au lieu de 'sh'
                bat 'mvn clean test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Coverage') {
            steps {
                // Génération du rapport JaCoCo
                bat 'mvn jacoco:report'
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/site/jacoco',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Coverage'
                ])
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Le nom 'sonar' ici doit correspondre au nom dans "Manage Jenkins > System"
                withSonarQubeEnv('sonar') {
                    // On passe le token via les arguments Maven pour être sûr
                    bat "mvn sonar:sonar -Dsonar.token=%SONAR_TOKEN%"
                }
            }
        }

        stage("Quality Gate") {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    // Attend que SonarQube renvoie le statut (Pass/Fail)
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}