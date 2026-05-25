package com.campusfrance.utils;

import com.campusfrance.models.Utilisateur;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class JsonReader {

    public static List<Utilisateur> lireUtilisateurs(String nomFichier) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream = JsonReader.class
                    .getClassLoader()
                    .getResourceAsStream("data/" + nomFichier);

            if (inputStream == null) {
                throw new RuntimeException("Fichier introuvable : data/" + nomFichier);
            }

            return mapper.readValue(inputStream, new TypeReference<List<Utilisateur>>() {});

        } catch (Exception e) {
            throw new RuntimeException("Erreur pendant la lecture du fichier JSON : " + nomFichier, e);
        }
    }
}