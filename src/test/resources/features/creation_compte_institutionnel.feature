Feature: Creation de compte Campus France Institutionnel

  Scenario: Remplir le formulaire de creation de compte institutionnel
    Given je suis sur la page Campus France pour creer un compte institutionnel
    When je charge les donnees du profil institutionnel depuis le fichier JSON
    And je renseigne les informations personnelles du compte institutionnel
    And je renseigne les informations specifiques au profil institutionnel
    And j accepte les conditions pour le compte institutionnel
    Then le formulaire institutionnel doit etre correctement rempli