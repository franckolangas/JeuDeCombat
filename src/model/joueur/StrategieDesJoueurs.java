package model.joueur;

/**
 * Interface représentant la stratégie d'un joueur.
 * Chaque implémentation de cette interface définit une stratégie spécifique 
 * pour déterminer l'action que le joueur doit entreprendre dans le jeu.
 */
public interface StrategieDesJoueurs {
    
    /**
     * Méthode permettant au joueur de demander une action à réaliser.
     * Cette méthode est appelée pour récupérer la prochaine action à effectuer
     * en fonction de la stratégie définie.
     */
    void demanderAction();
}
