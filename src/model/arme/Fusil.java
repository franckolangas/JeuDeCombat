package model.arme;

/**
 * La classe {@code Fusil} représente une arme avec une grande portée et des dégâts élevés.
 * Elle implémente l'interface {@code Equipement} et peut être activée et utilisée pour tirer.
 */
public class Fusil implements Equipement {
    /**
     * La portée maximale du fusil, exprimée en unités.
     */
    protected int portee = 8;

    /**
     * Les dégâts infligés par un tir de fusil.
     */
    protected int degat = 100;

    /**
     * La balle associée au fusil, utilisée pour représenter son projectile.
     */
    protected Bale bale;

    /**
     * Constructeur par défaut du fusil. Initialise la balle associée.
     */
    public Fusil() {
        this.bale = new Bale();
    }

    /**
     * Obtient la portée maximale du fusil.
     * 
     * @return la portée maximale en unités.
     */
    public int getPortee() {
        return portee;
    }

    /**
     * Obtient les dégâts infligés par un tir de fusil.
     * 
     * @return les dégâts infligés.
     */
    public int getDegat() {
        return degat;
    }

    /**
     * Active l'équipement (le fusil) pour indiquer qu'il est prêt à être utilisé.
     * 
     * @return {@code true} pour indiquer que l'équipement a été activé avec succès.
     */
    @Override
    public boolean activerEquipement() {
        return true;
    }

    /**
     * Simule l'action de tirer avec le fusil.
     * 
     * @return {@code true} si le tir a été effectué avec succès.
     */
    public boolean tirer() {
        // Logique pour gérer les munitions peut être réactivée ici.
        return true;
    }

    /**
     * Retourne une représentation textuelle du fusil.
     * 
     * @return une chaîne décrivant les caractéristiques du fusil.
     */
    @Override
    public String toString() {
        return "Fusil (Portée: " + portee + ", degat: " + degat + ")";
    }
}
