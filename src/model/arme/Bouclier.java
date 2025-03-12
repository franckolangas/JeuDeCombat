package model.arme;

import model.joueur.*;

/**
 * La classe {@code Bouclier} représente un équipement défensif utilisé par un joueur. 
 * Lorsqu'il est activé, le bouclier protège le joueur pour une durée déterminée.
 */
public class Bouclier implements Equipement {

    /** Le joueur qui utilise ou possède ce bouclier. */
    protected Joueur joueur;

    /** Le délai avant la désactivation automatique du bouclier. */
    protected int delaisDesactivation = 4;

    /**
     * Constructeur par défaut de la classe {@code Bouclier}.
     */
    public Bouclier() {
    }

    /**
     * Récupère le délai restant avant la désactivation du bouclier.
     * 
     * @return Le délai avant désactivation.
     */
    public int getDelaisDesactivation() {
        return delaisDesactivation;
    }

    /**
     * Récupère le joueur associé à ce bouclier.
     * 
     * @return Le joueur utilisant ce bouclier.
     */
    public Joueur getJoueur() {
        return joueur;
    }

    /**
     * Définit le délai avant la désactivation automatique du bouclier.
     * 
     * @param delaisDesactivation Le nouveau délai de désactivation en nombre de tours.
     */
    public void setDelaisDesactivation(int delaisDesactivation) {
        this.delaisDesactivation = delaisDesactivation;
    }

    /**
     * Active le bouclier pour le joueur associé.
     * Implémenté depuis l'interface {@code Equipement}.
     * 
     * @return {@code true} si l'activation du bouclier a réussi.
     */
    @Override
    public boolean activerEquipement() {
        joueur.activerBouclier();
        return true;
    }
}
