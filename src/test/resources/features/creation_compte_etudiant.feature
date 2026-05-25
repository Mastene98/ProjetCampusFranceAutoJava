Feature: Creation de compte Campus France Etudiant

  Scenario: Remplir le formulaire de creation de compte etudiant
    Given je suis sur la page Campus France pour creer un compte etudiant
    When je charge les donnees du profil etudiant depuis le fichier JSON
    And je renseigne les informations personnelles du compte etudiant
    And je renseigne les informations specifiques au profil etudiant
    And j accepte les conditions pour le compte etudiant
    Then le formulaire etudiant doit etre correctement rempli