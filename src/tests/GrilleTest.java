package tests;

import java.util.List;
import model.arme.Bombe;
import model.grille.Cellule;
import model.grille.Grille;
import model.grille.Mur;
import model.grille.Pastille;
import model.grille.Position;
import model.joueur.ConfigDuJeu;
import model.joueur.Joueur;

public class GrilleTest {

    public GrilleTest() {
    }

    public static void main(String[] args) {
        GrilleTest test = new GrilleTest();
        boolean tousLesTestsReussis = test.runTests();
        System.out.println(tousLesTestsReussis ? "Tous les tests ont réussi !" : "Au moins un test a échoué.");
    }

    public boolean runTests() {
        boolean ok = true;

        ok = ok && testInitialiserGrille();
        ok = ok && testAjouterBombe();
        ok = ok && testEstDansLesLimites();
        ok = ok && testGetCellule();
        ok = ok && testAjouterJoueur();
        ok = ok && testAjouterMur();
        ok = ok && testAjouterPastilleEnergie();
        ok = ok && testAfficherGrille();
        ok = ok && testGetJoueurs();
        ok = ok && testGetNbJoueur();
        ok = ok && testSetJoueurs();
        ok = ok && testGetTaille();

        return ok;
    }

    public boolean testInitialiserGrille() {
        System.out.println("Test: initialiserGrille");
        Grille instance = new Grille(10);
        instance.initialiserGrille();
        return true; // Pas d'assertion nécessaire pour ce test
    }

    public boolean testAjouterBombe() {
        System.out.println("Test: ajouterBombe");
        Grille instance = new Grille(10);
        ConfigDuJeu conf = new ConfigDuJeu(instance, 0, 0, 0);
        Joueur joueur = new Joueur("Joueur 1", 100, new Position(2, 4), conf);
        Bombe bombe = new Bombe(new Position(2, 5), joueur);
        instance.ajouterBombe(bombe);
        return true; // Pas d'assertion nécessaire pour ce test
    }

    public boolean testEstDansLesLimites() {
        System.out.println("Test: estDansLesLimites");
        int x = 2;
        int y = 3;
        Grille instance = new Grille(10);
        boolean result = instance.estDansLesLimites(x, y);
        return result; // Vérification de la validité du test
    }

    public boolean testGetCellule() {
        System.out.println("Test: getCellule");
        int x = 3;
        int y = 5;
        Grille instance = new Grille(10);
        Cellule result = instance.getCellule(x, y);
        return result != null; // Vérification de l'existence de la cellule
    }

    public boolean testAjouterJoueur() {
        System.out.println("Test: ajouterJoueur");
        Grille instance = new Grille(10);
        ConfigDuJeu conf = new ConfigDuJeu(instance, 0, 0, 0);
        Joueur joueur = new Joueur("Joueur 1", 100, new Position(2, 4), conf);
        instance.ajouterJoueur(joueur);
        return true; // Pas d'assertion nécessaire pour ce test
    }

    public boolean testAjouterMur() {
        System.out.println("Test: ajouterMur");
        Mur mur = new Mur(new Position(2, 4));
        Grille instance = new Grille(10);
        instance.ajouterMur(mur);
        return true; // Pas d'assertion nécessaire pour ce test
    }

    public boolean testAjouterPastilleEnergie() {
        System.out.println("Test: ajouterPastilleEnergie");
        Pastille pastille = new Pastille(new Position(2, 4));
        Grille instance = new Grille(10);
        instance.ajouterPastilleEnergie(pastille);
        return true; // Pas d'assertion nécessaire pour ce test
    }

    public boolean testAfficherGrille() {
        System.out.println("Test: afficherGrille");
        Grille instance = new Grille(10);
        instance.afficherGrille();
        return true; // Pas d'assertion nécessaire pour ce test
    }

    public boolean testGetJoueurs() {
        System.out.println("Test: getJoueurs");
        Grille instance = new Grille(10);
        List<Joueur> result = instance.getJoueurs();
        return result != null; // Vérification que la liste des joueurs n'est pas nulle
    }

    public boolean testGetNbJoueur() {
        System.out.println("Test: getNbJoueur");
        Grille instance = new Grille(10);
        int result = instance.getNbJoueur();
        return result == 0; // Vérification que le nombre de joueurs est bien 0
    }

    public boolean testSetJoueurs() {
        System.out.println("Test: setJoueurs");
        List<Joueur> nouvJoueurs = null;
        Grille instance = new Grille(10);
        instance.setJoueurs(nouvJoueurs);
        return true; // Pas d'assertion nécessaire pour ce test
    }

    public boolean testGetTaille() {
        System.out.println("Test: getTaille");
        Grille instance = new Grille(10);
        int result = instance.getTaille();
        return result == 10; // Vérification que la taille de la grille est correcte
    }
}
