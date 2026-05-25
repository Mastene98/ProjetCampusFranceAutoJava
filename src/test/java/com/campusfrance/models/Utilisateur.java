package com.campusfrance.models;

public class Utilisateur {

    public String email;
    public String motDePasse;
    public String confirmationMotDePasse;
    public String civilite;
    public String nom;
    public String prenom;
    public String paysResidence;
    public String paysNationalite;
    public String codePostal;
    public String ville;
    public String telephone;
    public String vousEtes;

    // Champs spécifiques au profil institutionnel
    public String fonction;
    public String typeOrganisme;
    public String nomOrganisme;

    // Champs spécifiques aux profils étudiant et chercheur
    public String domaineEtudes;
    public String niveauEtudes;
}