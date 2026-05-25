Feature: Creation de compte Campus France Chercheur

  Scenario: Remplir le formulaire de creation de compte chercheur
    Given je suis sur la page Campus France pour creer un compte chercheur
    When je charge les donnees du profil chercheur depuis le fichier JSON
    And je renseigne les informations personnelles du compte chercheur
    And je renseigne les informations specifiques au profil chercheur
    And j accepte les conditions pour le compte chercheur
    Then le formulaire chercheur doit etre correctement rempli