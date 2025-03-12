package model.grille;

/**
 * Représente une position dans un espace 2D, spécifiée par des coordonnées x et y.
 * Cette classe permet de manipuler et de comparer des positions dans la grille du jeu.
 */
public class Position {
    
    // Coordonnée x de la position
    protected int x;
    
    // Coordonnée y de la position
    protected int y;

    /**
     * Constructeur de la classe Position.
     * Initialise la position avec les coordonnées spécifiées.
     *
     * @param abs la coordonnée x (abscisse) de la position.
     * @param ord la coordonnée y (ordonnée) de la position.
     */
    public Position(int abs, int ord) {
        this.x = abs;
        this.y = ord;
    }

    /**
     * Récupère la coordonnée x de la position.
     *
     * @return la coordonnée x de la position.
     */
    public int getX() {
        return x;
    }

    /**
     * Récupère la coordonnée y de la position.
     *
     * @return la coordonnée y de la position.
     */
    public int getY() {
        return y;
    }

    /**
     * Modifie la coordonnée x de la position.
     *
     * @param x la nouvelle valeur pour la coordonnée x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Modifie la coordonnée y de la position.
     *
     * @param y la nouvelle valeur pour la coordonnée y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la position.
     * 
     * @return une chaîne représentant la position.
     */
    @Override
    public String toString() {
        return "Position { x=" + getX() + ",y=" + getY() + " }";
    }

    /**
     * Vérifie si deux positions sont égales. Deux positions sont considérées égales si 
     * leurs coordonnées x et y sont identiques.
     * 
     * @param obj l'objet à comparer à cette position.
     * @return true si les deux positions ont les mêmes coordonnées, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        // Vérifie si l'objet passé est la même instance
        if (this == obj) {
            return true;
        }
        // Vérifie si l'objet est null ou d'une classe différente
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Cast l'objet en Position et compare les champs x et y
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }
}
