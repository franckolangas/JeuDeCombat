package model.grille;

/**
 * Représente un mur dans le jeu. Un mur est une cellule qui bloque le mouvement 
 * et peut être utilisée pour délimiter des zones dans la grille.
 */
public class Mur implements TypeCellule {
    
    // Symbole représentant le mur dans l'affichage de la grille.
    protected final String SYMBOLE = "#";
    
    // Position du mur sur la grille.
    protected Position position;

    /**
     * Constructeur de la classe Mur.
     * Initialise un mur à une position spécifiée dans la grille.
     *
     * @param position la position où placer le mur dans la grille.
     */
    public Mur(Position position) {
        this.position = position;
    }

    /**
     * Récupère le symbole du mur.
     * Ce symbole est utilisé pour afficher le mur dans la grille.
     *
     * @return le symbole représentant le mur (ici "#").
     */
    @Override
    public String getSymbol() {
        return this.SYMBOLE;
    }

    /**
     * Récupère la position du mur dans la grille.
     *
     * @return la position du mur.
     */
    public Position getPosition() {
        return this.position;
    }
}
