package model.arme;

/**
 * L'interface {@code Equipement} définit les comportements de base 
 * pour les équipements utilisables dans le jeu. 
 * Chaque équipement peut être activé en fonction de sa logique propre.
 */
public interface Equipement {

    /**
     * Active l'équipement et applique ses effets. 
     * 
     * @return {@code true} si l'équipement a été activé avec succès, 
     *         {@code false} sinon.
     */
    public boolean activerEquipement();
}
