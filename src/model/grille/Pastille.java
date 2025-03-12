package model.grille;

/**
 * Représente une pastille d'énergie dans le jeu. Une pastille est une cellule spéciale 
 * qui peut être ramassée par un joueur pour regagner de l'énergie.
 */
public class Pastille implements TypeCellule {
    
    // Symbole représentant la pastille d'énergie dans l'affichage de la grille.
    protected final String SYMBOLE = "+";
    
    // Position de la pastille dans la grille.
    protected Position position;
    
    // Quantité d'énergie fournie par la pastille.
    protected int energie;

    /**
     * Constructeur de la classe Pastille.
     * Initialise la pastille à une position spécifiée dans la grille 
     * avec une quantité d'énergie par défaut de 10.
     *
     * @param position la position où placer la pastille dans la grille.
     */
    public Pastille(Position position) {
        this.energie = 10;  // La pastille donne 10 d'énergie par défaut
        this.position = position;
    }

    /**
     * Récupère la position de la pastille dans la grille.
     *
     * @return la position de la pastille.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Récupère la quantité d'énergie fournie par la pastille.
     *
     * @return la quantité d'énergie de la pastille.
     */
    public int getEnergie() {
        return energie;
    }

    /**
     * Récupère le symbole de la pastille.
     * Ce symbole est utilisé pour afficher la pastille dans la grille.
     *
     * @return le symbole représentant la pastille (ici "+").
     */
    @Override
    public String getSymbol() {
        return this.SYMBOLE;
    }
}
