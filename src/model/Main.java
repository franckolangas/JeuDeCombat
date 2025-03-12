package model;

// import model.joueur.*;
import model.grille.*;

public class Main {
    public static void main(String[] args) {
        Grille grille = new Grille(5);
        // ConfigDuJeu conf1 = new ConfigDuJeu(grille, 1, 4, 3);

        StrategyGrilleAleatoire config = new StrategyGrilleAleatoire(grille, 2, 4, 1, 1, 1);
        config.setSetupStrategy(config); // Utiliser une stratégie aléatoire
        config.initialiserGrille();
        System.out.println(config.toString());
        // config.jouerTour();

    }
    // il me faut une fonction qui prend en paramettre un joueur et et qui change la
    // case à laquelle le joueur est par "-"
}
