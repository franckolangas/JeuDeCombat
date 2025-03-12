package model.grille;

/**
 * La classe {@code Cellule} représente une case individuelle sur une grille.
 * Chaque cellule possède une position (coordonnées X et Y) et un type spécifique
 * déterminé par l'interface {@code TypeCellule}.
 */
public class Cellule {
    /**
     * Coordonnée X de la cellule sur la grille.
     */
    protected int x;

    /**
     * Coordonnée Y de la cellule sur la grille.
     */
    protected int y;

    /**
     * Type de la cellule, déterminé par une implémentation de {@code TypeCellule}.
     */
    protected TypeCellule type;

    /**
     * Constructeur de la classe {@code Cellule}.
     * 
     * @param x    la coordonnée X de la cellule.
     * @param y    la coordonnée Y de la cellule.
     * @param type le type de la cellule.
     */
    public Cellule(int x, int y, TypeCellule type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * Retourne la coordonnée X de la cellule.
     * 
     * @return la valeur de la coordonnée X.
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la coordonnée Y de la cellule.
     * 
     * @return la valeur de la coordonnée Y.
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne le type de la cellule.
     * 
     * @return une instance de {@code TypeCellule} représentant le type de la cellule.
     */
    public TypeCellule getType() {
        return type;
    }

    /**
     * Modifie le type de la cellule.
     * 
     * @param type une nouvelle instance de {@code TypeCellule}.
     */
    public void setType(TypeCellule type) {
        this.type = type;
    }

    /**
     * Retourne une représentation textuelle de la cellule.
     * Cette représentation correspond au symbole défini par le type de la cellule.
     * 
     * @return une chaîne contenant le symbole du type de la cellule.
     */
    @Override
    public String toString() {
        return type.getSymbol();
    }
}
