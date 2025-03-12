package model.grille;

import java.util.*;

import model.arme.Bombe;
import model.joueur.*;

/**
 * La classe {@code Grille} représente une grille de jeu composée de cellules.
 * Elle gère les éléments du jeu comme les joueurs, les bombes, les murs et les pastilles d'énergie,
 * ainsi que les interactions entre eux.
 */
public class Grille {
    /**
     * Taille de la grille (nombre de cellules par ligne et par colonne).
     */
    protected final int taille;

    /**
     * Tableau 2D représentant les cellules de la grille.
     */
    protected Cellule[][] cellules;

    /**
     * Liste des joueurs présents sur la grille.
     */
    protected List<Joueur> mesJoueurs;

    /**
     * Constructeur de la classe {@code Grille}.
     * Initialise une grille de la taille donnée et ajoute des cellules vides.
     * 
     * @param taille la taille de la grille.
     */
    public Grille(int taille) {
        this.taille = taille;
        cellules = new Cellule[taille][taille];
        this.mesJoueurs = new ArrayList<>();
        initialiserGrille();
    }

    /**
     * Initialise la grille avec des cellules vides.
     */
    public void initialiserGrille() {
        for (int x = 0; x < taille; x++) {
            for (int y = 0; y < taille; y++) {
                cellules[x][y] = new Cellule(x, y, new Vide() {
                });
            }
        }
    }

    /**
     * Ajoute une bombe à une position spécifique sur la grille.
     * 
     * @param bombe la bombe à ajouter.
     */
    public void ajouterBombe(Bombe bombe) {
        if (estDansLesLimites(bombe.position.x, bombe.position.y)) {
            getCellule(bombe.position.x, bombe.position.y).setType(bombe);
        } else {
            System.out.println("Impossible d'ajouter une bombe hors limites.");
        }
    }

    /**
     * Vérifie si une position donnée est dans les limites de la grille.
     * 
     * @param x coordonnée X.
     * @param y coordonnée Y.
     * @return {@code true} si la position est dans les limites, {@code false} sinon.
     */
    public boolean estDansLesLimites(int x, int y) {
        return x >= 0 && x < taille && y >= 0 && y < taille;
    }

    /**
     * Retourne la cellule à une position donnée.
     * 
     * @param x coordonnée X.
     * @param y coordonnée Y.
     * @return la cellule correspondante ou {@code null} si la position est hors limites.
     */
    public Cellule getCellule(int x, int y) {
        if (estDansLesLimites(x, y)) {
            return cellules[x][y];
        }
        return null;
    }

    /**
     * Ajoute un joueur à une position donnée sur la grille.
     * 
     * @param joueur le joueur à ajouter.
     */
    public void ajouterJoueur(Joueur joueur) {
        Position pos = joueur.getPosition();
        if (estDansLesLimites(pos.getX(), pos.getY())) {
            cellules[pos.getX()][pos.getY()].setType(joueur);
        } else {
            System.out.println("Impossible d'ajouter le joueur à la position : " + pos);
        }
    }

    /**
     * Ajoute un mur à une position spécifique sur la grille.
     * 
     * @param mur le mur à ajouter.
     */
    public void ajouterMur(Mur mur) {
        if (estDansLesLimites(mur.position.x, mur.position.y)) {
            cellules[mur.position.x][mur.position.y].setType(mur);
        }
    }

    /**
     * Ajoute une pastille d'énergie à une position spécifique sur la grille.
     * 
     * @param pastille la pastille à ajouter.
     */
    public void ajouterPastilleEnergie(Pastille pastille) {
        if (estDansLesLimites(pastille.position.x, pastille.position.y)) {
            cellules[pastille.position.x][pastille.position.y].setType(pastille);
        }
    }

    /**
     * Affiche la grille dans la console pour le débogage.
     * Chaque cellule est représentée par son symbole.
     */
    public void afficherGrille() {
        for (int x = 0; x < taille; x++) {
            for (int y = 0; y < taille; y++) {
                System.out.print("|" + String.format("%-" + 2 + "s", cellules[x][y].toString()) + "|");
            }
            System.out.println();
        }
    }

    /**
     * Retourne la liste des joueurs présents sur la grille.
     * 
     * @return une liste d'objets {@code Joueur}.
     */
    public List<Joueur> getJoueurs() {
        return this.mesJoueurs;
    }

    /**
     * Retourne le nombre de joueurs présents sur la grille.
     * 
     * @return le nombre de joueurs.
     */
    public int getNbJoueur() {
        return this.mesJoueurs.size();
    }

    /**
     * Modifie la liste des joueurs présents sur la grille.
     * 
     * @param nouvJoueurs une nouvelle liste de joueurs.
     */
    public void setJoueurs(List<Joueur> nouvJoueurs) {
        this.mesJoueurs = nouvJoueurs;
    }

    /**
     * Retourne la taille de la grille.
     * 
     * @return la taille de la grille.
     */
    public int getTaille() {
        return taille;
    }
}
