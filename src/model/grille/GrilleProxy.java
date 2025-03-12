package model.grille;

import model.joueur.*;

import java.util.ArrayList;
import java.util.List;

import model.arme.*;

/**
 * La classe {@code GrilleProxy} agit comme un intermédiaire entre un joueur et la grille principale.
 * Elle gère les règles de visibilité des cellules et la logique des interactions, 
 * comme les bombes, les boucliers, et les mines.
 */
public class GrilleProxy {
    /**
     * Référence à la grille réelle contrôlée par ce proxy.
     */
    protected Grille grille;

    /**
     * Liste des bombes actuellement actives sur la grille.
     */
    protected List<Bombe> bombesActives;

    /**
     * Liste des boucliers actuellement actifs.
     */
    protected List<Bouclier> bouclierActif;

    /**
     * Compteur des tours pour gérer les délais des équipements (bombes, boucliers, etc.).
     */
    protected int compteurTour;

    /**
     * Joueur lié à ce proxy, utilisé pour appliquer les règles de visibilité.
     */
    private Joueur joueurCourant;

    /**
     * Constructeur de la classe {@code GrilleProxy}.
     * Initialise les listes d'équipements et associe un joueur au proxy.
     * 
     * @param grille la grille principale.
     * @param joueur le joueur lié au proxy.
     */
    public GrilleProxy(Grille grille, Joueur joueur) {
        this.grille = grille;
        this.bombesActives = new ArrayList<>();
        this.bouclierActif = new ArrayList<>();
        this.compteurTour = 0;
        this.joueurCourant = joueur;
    }

    /**
     * Retourne la grille principale associée à ce proxy.
     * 
     * @return la grille principale.
     */
    public Grille getGrille() {
        return grille;
    }

    /**
     * Récupère une cellule en respectant les règles de visibilité.
     * 
     * @param x la coordonnée X de la cellule.
     * @param y la coordonnée Y de la cellule.
     * @return une cellule visible par le joueur ou une cellule vide si masquée.
     */
    public Cellule getCellule(int x, int y) {
        Cellule cellule = grille.getCellule(x, y);
        TypeCellule type = cellule.getType();

        if (type instanceof Bombe) {
            Bombe bombe = (Bombe) type;
            if (!bombe.getJoueur().equals(joueurCourant)) {
                return new Cellule(x, y, new Vide());
            }
        } else if (type instanceof Mine) {
            Mine mine = (Mine) type;
            if (!mine.getJoueur().equals(joueurCourant)) {
                return new Cellule(x, y, new Vide());
            }
        }
        return cellule;
    }

    /**
     * Vérifie si une position est dans les limites de la grille.
     * 
     * @param x la coordonnée X.
     * @param y la coordonnée Y.
     * @return {@code true} si la position est valide, {@code false} sinon.
     */
    public boolean estDansLesLimites(int x, int y) {
        return grille.estDansLesLimites(x, y);
    }

    /**
     * Ajoute un mur à la grille.
     * 
     * @param mur le mur à ajouter.
     */
    public void ajouterMur(Mur mur) {
        grille.ajouterMur(mur);
    }

    /**
     * Ajoute une pastille d'énergie à la grille.
     * 
     * @param pastille la pastille à ajouter.
     */
    public void ajouterPastilleEnergie(Pastille pastille) {
        grille.ajouterPastilleEnergie(pastille);
    }

    /**
     * Ajoute une bombe à la grille et met à jour la liste des bombes actives.
     * 
     * @param bombe la bombe à ajouter.
     */
    public void ajouterBombe(Bombe bombe) {
        bombesActives.add(bombe);
        grille.ajouterBombe(bombe);
        joueurCourant.getConfigDuJeu().setBombesActives(bombesActives);
    }

    /**
     * Ajoute un bouclier actif avec un délai d'expiration.
     * 
     * @param bouclier le bouclier à ajouter.
     */
    public void ajouterBouclierDelais(Bouclier bouclier) {
        bouclierActif.add(bouclier);
    }

    /**
     * Vérifie et désactive les boucliers ayant atteint leur délai d'expiration.
     * 
     * @param joueurCourant le joueur dont les boucliers sont à vérifier.
     */
    public void verifierBouclierActif(Joueur joueurCourant) {
        List<Bouclier> bouclierASupprimer = new ArrayList<>();
        for (Bouclier b : bouclierActif) {
            if (compteurTour >= b.getDelaisDesactivation()) {
                joueurCourant.setBouclierActif(false);
                bouclierASupprimer.add(b);
            }
        }
        bouclierActif.removeAll(bouclierASupprimer);
    }

    /**
     * Vérifie les bombes actives et gère leur explosion si le délai est atteint.
     * 
     * @param joueurCourant le joueur à vérifier pour les dégâts de bombe.
     */
    public void verifierBombe(Joueur joueurCourant) {
        Position positionJoueur = joueurCourant.getPosition();
        List<Bombe> bombesASupprimer = new ArrayList<>();
        for (Bombe bombe : bombesActives) {
            if (compteurTour >= bombe.delais) {
                Position positionBombe = bombe.position;
                if (positionJoueur.equals(positionBombe)) {
                    if (bombe.activerEquipement()) {
                        joueurCourant.retireEnergie();
                    }
                }
                bombesASupprimer.add(bombe);
                grille.getCellule(positionBombe.getX(), positionBombe.getY()).setType(new Vide());
            }
        }
        bombesActives.removeAll(bombesASupprimer);
    }

    /**
     * Ajoute une mine à la grille.
     * 
     * @param mine la mine à ajouter.
     */
    public void ajouterMine(Mine mine) {
        Position pos = mine.getPosition();
        if (grille.estDansLesLimites(pos.x, pos.y)) {
            grille.getCellule(pos.x, pos.y).setType(mine);
        }
    }

    /**
     * Affiche la grille en respectant les règles de visibilité pour le joueur courant.
     */
    public void afficherGrille() {
        for (int x = 0; x < grille.taille; x++) {
            for (int y = 0; y < grille.taille; y++) {
                Cellule cellule = getCellule(x, y);
                System.out.print("|" + String.format("%-" + 2 + "s", cellule.getType().getSymbol()) + "|");
            }
            System.out.println();
        }
    }

    /**
     * Retourne la taille de la grille.
     * 
     * @return la taille de la grille.
     */
    public int getTaille() {
        return grille.taille;
    }

    /**
     * Retourne le compteur de tours.
     * 
     * @return le compteur de tours.
     */
    public int getCompteurTour() {
        return compteurTour;
    }

    /**
     * Définit le compteur de tours.
     * 
     * @param i la nouvelle valeur du compteur.
     */
    public void setCompteur(int i) {
        this.compteurTour = i;
    }

    /**
     * Retourne la liste des bombes actives.
     * 
     * @return la liste des bombes actives.
     */
    public List<Bombe> getBombesActives() {
        return this.bombesActives;
    }

    /**
     * Retourne la liste des boucliers actifs.
     * 
     * @return la liste des boucliers actifs.
     */
    public List<Bouclier> getBouclierActif() {
        return bouclierActif;
    }
}
