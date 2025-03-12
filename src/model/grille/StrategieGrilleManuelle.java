package model.grille;

import model.joueur.ConfigDuJeu;
import model.joueur.Joueur;

public class StrategieGrilleManuelle extends ConfigDuJeu implements GrilleSetupStrategy {

    public StrategieGrilleManuelle(Grille grille, int nbj, int nbMur, int pastille) {
        super(grille, nbj, nbMur, pastille);
    };

    @Override
    public void configurerGrille() {
        ajouterMur(new Mur(new Position(1, 1)));
        ajouterPastilleEnergie(new Pastille(new Position(2, 2)));
        ajouterJoueur(new Joueur("Joueur1", 100, new Position(3, 3), this));
    }

    public void ajouterMur(Mur mur) {
        if (this.grille.estDansLesLimites(mur.position.x, mur.position.y)) {
            this.grille.cellules[mur.position.x][mur.position.y].setType(mur);
        }
    }

    // Ajoute une pastille d'énergie à la grille
    public void ajouterPastilleEnergie(Pastille pastille) {
        if (this.grille.estDansLesLimites(pastille.position.x, pastille.position.y)) {
            this.grille.cellules[pastille.position.x][pastille.position.y].setType(pastille);
        }
    }

    public void ajouterJoueur(Joueur joueur) {
        if (this.grille.estDansLesLimites(joueur.getPosition().getX(), joueur.getPosition().getY())) {
            this.grille.cellules[joueur.getPosition().getX()][joueur.getPosition().getY()].setType(joueur);
        }
    }

}
