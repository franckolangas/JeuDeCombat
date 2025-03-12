package model.grille;

import java.util.List;
import java.util.Random;

import model.joueur.ConfigDuJeu;
import model.joueur.Joueur;
import model.joueur.StrategieJoueurAleatoire;
import model.joueur.StrategieJoueurIntelligent;

/**
 * Implémentation de la stratégie de configuration aléatoire de la grille du jeu.
 * Cette classe permet de configurer la grille avec des murs, des pastilles d'énergie et
 * différents types de joueurs (intelligents, aléatoires, humains).
 * Elle hérite de la classe {@link ConfigDuJeu} et implémente l'interface {@link GrilleSetupStrategy}.
 */
public class StrategyGrilleAleatoire extends ConfigDuJeu implements GrilleSetupStrategy {

    // Nombre de joueurs intelligents à créer
    protected int nbJoueursIntelligents;
    
    // Nombre de joueurs humains à créer
    protected int nbJoueursHumains;

    /**
     * Constructeur de la classe StrategyGrilleAleatoire.
     * Initialise la configuration de la grille en fonction des paramètres spécifiés.
     *
     * @param grille Grille à configurer.
     * @param nbJoueursAleatoires Nombre de joueurs aléatoires à créer.
     * @param nbMur Nombre de murs à ajouter à la grille.
     * @param pastille Nombre de pastilles d'énergie à ajouter à la grille.
     * @param nbJoueursIntelligents Nombre de joueurs intelligents à ajouter à la grille.
     * @param nbJoueursHumains Nombre de joueurs humains à ajouter à la grille.
     */
    public StrategyGrilleAleatoire(Grille grille, int nbJoueursAleatoires, int nbMur, int pastille,
                                    int nbJoueursIntelligents, int nbJoueursHumains) {
        super(grille, nbJoueursAleatoires, nbMur, pastille);
        this.nbJoueursIntelligents = nbJoueursIntelligents;
        this.nbJoueursHumains = nbJoueursHumains;
    }

    /**
     * Configure la grille en créant des murs, des pastilles d'énergie et des joueurs.
     */
    @Override
    public void configurerGrille() {
        creerMurAleatoire();
        creePastilleAleatoire();
        creerJoueurs();
    }

    /**
     * Crée les joueurs (intelligents, aléatoires et humains) et les ajoute à la grille.
     * Chaque joueur reçoit un équipement de départ et un proxy.
     */
    public void creerJoueurs() {
        List<Joueur> joueurs = super.grille.getJoueurs();
        Random random = new Random();

        // Création des joueurs intelligents
        for (int i = 1; i <= nbJoueursIntelligents; i++) {
            int x, y;
            do {
                x = random.nextInt(super.grille.getTaille());
                y = random.nextInt(super.grille.getTaille());
            } while (!(super.grille.getCellule(x, y).getType() instanceof Vide));

            StrategieJoueurIntelligent joueur = new StrategieJoueurIntelligent("Joueur Intelligent " + i, 100,
                    new Position(x, y), this);
            joueur.setSymbole("I" + String.valueOf(i));
            joueur.ajouterEquipements(4, 10, 7, 10);
            super.grille.ajouterJoueur(joueur);
            joueurs.add(joueur);

            GrilleProxy proxy = new GrilleProxy(super.grille, joueur);
            System.out.println("taille de la grille à l'init : " + grille.getTaille());
            super.proxies.put(joueur, proxy);
        }

        // Création des joueurs aléatoires
        for (int i = 1; i <= nombreDeJoueur; i++) {
            int x, y;
            do {
                x = random.nextInt(grille.getTaille());
                y = random.nextInt(grille.getTaille());
            } while (!(grille.getCellule(x, y).getType() instanceof Vide));

            StrategieJoueurAleatoire joueur = new StrategieJoueurAleatoire("Joueur Aleatoire " + i, 100,
                    new Position(x, y), this);
            joueur.setSymbole("A" + String.valueOf(i));
            joueur.ajouterEquipements(4, 10, 7, 10);
            grille.ajouterJoueur(joueur);
            joueurs.add(joueur);

            // Créer un proxy pour ce joueur
            GrilleProxy proxy = new GrilleProxy(grille, joueur);
            this.proxies.put(joueur, proxy);
        }

        // Création des joueurs humains
        for (int i = 1; i <= nbJoueursHumains; i++) {
            int x, y;
            do {
                x = random.nextInt(grille.getTaille());
                y = random.nextInt(grille.getTaille());
            } while (!(grille.getCellule(x, y).getType() instanceof Vide));

            Joueur joueur = new Joueur("Joueur Humain " + i, 100, new Position(x, y), this);
            joueur.setSymbole("H" + String.valueOf(i));
            joueur.ajouterEquipements(4, 10, 7, 10);
            grille.ajouterJoueur(joueur);
            joueurs.add(joueur);

            GrilleProxy proxy = new GrilleProxy(grille, joueur);
            this.proxies.put(joueur, proxy);
        }
    }

    /**
     * Crée et ajoute des murs à la grille de manière aléatoire.
     * Chaque mur est placé dans une cellule vide.
     */
    public void creerMurAleatoire() {
        Random random = new Random();
        for (int i = 1; i <= this.nombreDeMur; i++) {
            int x, y;
            // Générer une position aléatoire qui est vide sur la grille
            do {
                x = random.nextInt(this.grille.getTaille());
                y = random.nextInt(this.grille.getTaille());
            } while (!(this.grille.getCellule(x, y).getType() instanceof Vide)); // Vérifie que la cellule est vide

            Mur mur = new Mur(new Position(x, y));
            this.grille.ajouterMur(mur);
        }
    }

    /**
     * Crée et ajoute des pastilles d'énergie à la grille de manière aléatoire.
     * Chaque pastille est placée dans une cellule vide.
     */
    public void creePastilleAleatoire() {
        Random random = new Random();
        for (int i = 1; i <= this.nombreDePastille; i++) {
            int x, y;
            // Générer une position aléatoire qui est vide sur la grille
            do {
                x = random.nextInt(this.grille.getTaille());
                y = random.nextInt(this.grille.getTaille());
            } while (!(this.grille.getCellule(x, y).getType() instanceof Vide)); // Vérifie que la cellule est vide

            Pastille pastille = new Pastille(new Position(x, y));
            this.grille.ajouterPastilleEnergie(pastille);
        }
    }
}
