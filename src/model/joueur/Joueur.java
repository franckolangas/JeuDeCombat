package model.joueur;

import java.util.*;

import controller.AbstractModeleEcoutable;
import model.grille.*;
import model.arme.*;

/**
 * Classe représentant un joueur dans le jeu.
 * Un joueur possède des équipements, une position, de l'énergie et peut effectuer diverses actions dans le jeu.
 */
public class Joueur extends AbstractModeleEcoutable implements TypeCellule, StrategieDesJoueurs {
    protected String Symbole = ">";
    protected String nom;
    protected int energie;
    protected Position position;
    protected GrilleProxy grilleProxy;
    protected Grille grille;
    protected boolean bouclierActif;
    protected List<Equipement> equipements;
    protected ConfigDuJeu conf;

    /**
     * Constructeur d'un joueur.
     *
     * @param nom      Le nom du joueur.
     * @param energie L'énergie initiale du joueur.
     * @param position La position initiale du joueur sur la grille.
     * @param conf     La configuration du jeu.
     */
    public Joueur(String nom, int energie, Position position, ConfigDuJeu conf) {
        this.nom = nom;
        this.energie = energie;
        this.position = position;
        this.bouclierActif = false;
        this.conf = conf;
        this.grille = conf.getGrille();
        // Passez la grille initialisée à GrilleProxy
        this.grilleProxy = new GrilleProxy(this.grille, this);
        this.equipements = new ArrayList<>();
    }

    /**
     * Getter pour la grille proxy.
     *
     * @return La grille proxy.
     */
    public GrilleProxy getGrilleProxy() {
        return grilleProxy;
    }

    /**
     * Getter pour la configuration du jeu.
     *
     * @return La configuration du jeu.
     */
    public ConfigDuJeu getConfigDuJeu() {
        return conf;
    }

    /**
     * Setter pour la configuration du jeu.
     *
     * @param conf La nouvelle configuration du jeu.
     */
    public void setConfigDuJeu(ConfigDuJeu conf) {
        this.conf = conf;
    }

    /**
     * Setter pour la grille proxy.
     *
     * @param grilleProxy La nouvelle grille proxy.
     */
    public void setGrilleProxy(GrilleProxy grilleProxy) {
        this.grilleProxy = grilleProxy;
    }

    /**
     * Vérifie si le déplacement vers la position spécifiée est valide.
     *
     * @param x La coordonnée X de la position cible.
     * @param y La coordonnée Y de la position cible.
     * @return True si le déplacement est valide, false sinon.
     */
    private boolean estDeplacementValide(int x, int y) {
        if (grille.estDansLesLimites(x, y)) {
            Cellule celluleCible = grille.getCellule(x, y);
            if (celluleCible.getType() instanceof Mur || celluleCible.getType() instanceof Joueur) {
                System.out.println("Il y a un obstacle devant le joueur.");
                return false;
            } else if (celluleCible.getType() instanceof Bombe) {
                return true; // Déplacement vers une bombe possible
            } else if (celluleCible.getType() instanceof Pastille) {
                ajouterEnergie();
                return true;
            } else if (celluleCible.getType() instanceof Mine) {
                retireEnergie();
                return true;
            } else {
                return true; // Déplacement possible dans un espace libre
            }
        }
        System.out.println("Votre déplacement n'est pas dans les limites de la grille.");
        return false;
    }

    /**
     * Ajoute de l'énergie au joueur lorsqu'il passe sur une pastille.
     */
    public void ajouterEnergie() {
        if (this.energie >= 100)
            this.energie = 100;
        else {
            Pastille p1 = new Pastille(new Position(0, 0));
            this.energie += p1.getEnergie();
            System.out.println(this.energie);
        }
    }

    /**
     * Retire de l'énergie au joueur lorsqu'il passe sur une mine.
     */
    public void retireEnergie() {
        if (this.energie <= 0) {
            this.energie = 0;
        }
        this.energie -= 50;
    }

    /**
     * Demande une action à l'utilisateur (déplacement, utilisation d'équipement, etc.).
     */
    @Override
    public void demanderAction() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez choisir une action :");
        System.out.println("Déplacement (h = haut, b = bas, g = gauche, d = droite)");
        System.out.println("Utiliser un équipement (bouclier, bombe, mine, tirer)");

        String action = scanner.nextLine().trim().toLowerCase();
        switch (action) {
            case "h":
                deplacerHaut();
                break;
            case "b":
                deplacerBas();
                break;
            case "g":
                deplacerGauche();
                break;
            case "d":
                deplacerDroite();
                break;
            case "bouclier":
                activerBouclier();
                break;
            case "bombe":
                placerBombe();
                break;
            case "mine":
                placerMine();
                break;
            case "tirer":
                System.out.print("Direction du tir (h, b, g, d) : ");
                String direction = scanner.nextLine().trim().toLowerCase();
                tirer(direction);
                break;
            default:
                System.out.println("Action invalide. Veuillez réessayer.");
        }

    }

    /**
     * Déplace le joueur d'une position donnée sur la grille.
     *
     * @param deltaX Le changement de position sur l'axe X.
     * @param deltaY Le changement de position sur l'axe Y.
     */
    public void deplacer(int deltaX, int deltaY) {
        int newX = this.position.getX() + deltaX;
        int newY = this.position.getY() + deltaY;

        if (estDeplacementValide(newX, newY)) {
            Position anciennePosition = new Position(this.position.getX(), this.position.getY());
            // Mise à jour de la grille
            grilleProxy.getCellule(anciennePosition.getX(), anciennePosition.getY()).setType(new Vide());

            this.position = new Position(newX, newY);
            grilleProxy.getCellule(newX, newY).setType(this);
            // grille.afficherGrille();

            // Mise à jour de la grille réelle
            grilleProxy.getGrille().getCellule(anciennePosition.getX(), anciennePosition.getY()).setType(new Vide());
            grilleProxy.getGrille().getCellule(newX, newY).setType(this);
        } else {
            System.out.println("Déplacement invalide.");
        }
    }

    /**
     * Déplace le joueur vers le haut.
     */
    public void deplacerHaut() {
        deplacer(-1, 0);
    }

    /**
     * Déplace le joueur vers le bas.
     */
    public void deplacerBas() {
        deplacer(1, 0);
    }

    /**
     * Déplace le joueur vers la gauche.
     */
    public void deplacerGauche() {
        deplacer(0, -1);
    }

    /**
     * Déplace le joueur vers la droite.
     */
    public void deplacerDroite() {
        deplacer(0, 1);
    }

    /**
     * Getter pour savoir si le bouclier est actif.
     *
     * @return True si le bouclier est actif, false sinon.
     */
    public boolean getBouclier() {
        return this.bouclierActif;
    }

    /**
     * Active le bouclier du joueur, s'il en possède un.
     */
    public void activerBouclier() {
        Bouclier arme = null;
        for (Equipement e : equipements) {
            if (e instanceof Bouclier) {
                arme = (Bouclier) e;
                break;
            }
        }

        // Vérifier si une bombe est disponible
        if (arme == null) {
            System.out.println("Vous n'avez pas de Bouclier disponible !");
            return;
        }
        setBouclierActif(true);
        equipements.remove(arme);
        Bouclier b1 = new Bouclier();
        b1.setDelaisDesactivation(b1.getDelaisDesactivation() + grilleProxy.getCompteurTour());
        grilleProxy.ajouterBouclierDelais(b1);
    }

    /**
     * Setter pour l'état du bouclier.
     *
     * @param bouclierActif L'état du bouclier (actif ou non).
     */
    public void setBouclierActif(boolean bouclierActif) {
        this.bouclierActif = bouclierActif;
    }

    /**
     * Place une bombe sur la grille, si le joueur en possède une.
     */
    public void placerBombe() {
        Bombe arme = null;
        for (Equipement e : equipements) {
            if (e instanceof Bombe) {
                arme = (Bombe) e;
                break;
            }
        }

        // Vérifier si une bombe est disponible
        if (arme == null) {
            System.out.println("Vous n'avez pas de bombe disponible !");
            return;
        }

        int x, y;
        Random random = new Random();
        do {
            x = random.nextInt(this.grilleProxy.getTaille());
            y = random.nextInt(this.grilleProxy.getTaille());
        } while (!(this.grilleProxy.getCellule(x, y).getType() instanceof Vide));
        equipements.remove(arme);

        Bombe bombe = new Bombe(new Position(x, y), this);
        System.out.println("Bombe ajoutée : " + bombe);
        bombe.delais = grilleProxy.getCompteurTour() + bombe.delais; // Définit le délai d’explosion
        grilleProxy.ajouterBombe(bombe); // Ajoute la bombe via ConfigDuJeu
        this.grille.afficherGrille();
    }

    /**
     * Place une mine sur la grille, si le joueur en possède une.
     */
    public void placerMine() {
        Mine arme = null;
        for (Equipement e : equipements) {
            if (e instanceof Mine) {
                arme = (Mine) e;
                break;
            }
        }

        // Vérifier si une mine est disponible
        if (arme == null) {
            System.out.println("Vous n'avez pas de mine disponible !");
            return;
        }

        int x, y;
        Random random = new Random();
        do {
            x = random.nextInt(this.grilleProxy.getTaille());
            y = random.nextInt(this.grilleProxy.getTaille());
        } while (!(this.grilleProxy.getCellule(x, y).getType() instanceof Vide)); // Vérifie que la cellule est vide
        equipements.remove(arme);

        Mine mine = new Mine(new Position(x, y), this);
        grilleProxy.ajouterMine(mine);
    }

        /**
     * Vérifie si le joueur possède un équipement spécifique du type donné.
     *
     * @param typeArme Le type d'équipement à vérifier.
     * @return True si l'équipement est présent dans la liste des équipements, sinon false.
     */
    public boolean contientArme(Class<? extends Equipement> typeArme) {
        for (Equipement equipement : equipements) {
            if (typeArme.isInstance(equipement)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Effectue un tir dans la direction spécifiée avec un fusil si disponible.
     *
     * @param direction La direction du tir. Utilisez 'h' pour haut, 'b' pour bas, 'g' pour gauche et 'd' pour droite.
     */
    public void tirer(String direction) {
        // Rechercher un fusil disponible dans les équipements
        Fusil fusil = null;
        for (Equipement e : equipements) {
            if (e instanceof Fusil) {
                fusil = (Fusil) e;
                break;
            }
        }

        // Vérifier si un fusil est disponible
        if (fusil == null) {
            System.out.println("Vous n'avez pas de fusil disponible !");
            return;
        }

        System.out.println("Vous tirez avec un fusil.");

        // Créer une balle à la position actuelle du joueur
        Bale balle = new Bale();
        balle.setPosition(new Position(position.getX(), position.getY()));

        int deltaX = 0, deltaY = 0;
        switch (direction.toLowerCase()) {
            case "h":
                deltaX = -1;
                break;
            case "b":
                deltaX = 1;
                break;
            case "g":
                deltaY = -1;
                break;
            case "d":
                deltaY = 1;
                break;
            default:
                System.out.println("Direction invalide !");
                return;
        }

        // Déplace la balle
        for (int i = 0; i < fusil.getPortee(); i++) {
            balle.deplacer(deltaX, deltaY);
            Position posActuelle = balle.getPosition();

            if (!grilleProxy.estDansLesLimites(posActuelle.getX(), posActuelle.getY())) {
                System.out.println("La balle sort des limites !");
                break;
            }

            Cellule cellule = grilleProxy.getCellule(posActuelle.getX(), posActuelle.getY());
            TypeCellule type = cellule.getType();

            if (type instanceof Joueur) {
                Joueur cible = (Joueur) type;
                int degats = fusil.getDegat();
                if (cible.bouclierActif) {
                    degats /= 2;
                }
                cible.setEnergie(cible.getEnergie() - degats);
                System.out.println(
                        "La balle a touché " + cible.getNom() + " et lui inflige " + degats + " points de dégâts !");
                break;
            } else if ((type instanceof Mur)) {
                System.out.println("La balle heurte un obstacle !");
                break;
            }
        }

        // Retirer le fusil utilisé de la liste des équipements
        equipements.remove(fusil);
        System.out.println("Le fusil a été utilisé et retiré de la liste des équipements.");
    }

    /**
     * Vérifie si un autre joueur est sur le même axe horizontal ou vertical que ce joueur.
     *
     * @return True si un autre joueur est sur le même axe horizontal ou vertical, sinon false.
     */
    public boolean autreJoueurSurMemeAxe() {
        int x = this.position.getX();
        int y = this.position.getY();

        // Tableau des directions : [deltaX, deltaY]
        int[][] directions = {
                { 1, 0 }, // Droite
                { -1, 0 }, // Gauche
                { 0, -1 }, // Haut
                { 0, 1 } // Bas
        };

        // Parcourir chaque direction
        for (int[] direction : directions) {
            int deltaX = direction[0];
            int deltaY = direction[1];

            // Vérifier jusqu'à 8 cases dans la direction donnée
            for (int i = 1; i <= 8; i++) {
                int nouveauX = x + i * deltaX;
                int nouveauY = y + i * deltaY;

                if (grille.estDansLesLimites(nouveauX, nouveauY)) {
                    TypeCellule type = grille.getCellule(nouveauX, nouveauY).getType();
                    if (type instanceof Joueur && type != this) {
                        return true;
                    }
                } else {
                    break; // Sortir si hors des limites
                }
            }
        }

        return false; // Aucun joueur trouvé
    }

    /**
     * Ajoute des équipements (mines, bombes, fusils, boucliers) au joueur.
     *
     * @param nombreMines   Le nombre de mines à ajouter.
     * @param nombreBombes  Le nombre de bombes à ajouter.
     * @param nombreFusils  Le nombre de fusils à ajouter.
     * @param nombreBoucliers Le nombre de boucliers à ajouter.
     */
    public void ajouterEquipements(int nombreMines, int nombreBombes, int nombreFusils, int nombreBoucliers) {
        // Ajouter les mines
        for (int i = 0; i < nombreMines; i++) {
            equipements.add(new Mine(new Position(0, 0), this));
        }
        // Ajouter les bombes
        for (int i = 0; i < nombreBombes; i++) {
            equipements.add(new Bombe(new Position(0, 0), this));
        }
        // Ajouter les fusils
        for (int i = 0; i < nombreFusils; i++) {
            equipements.add(new Fusil());
        }
        // Ajouter les boucliers
        for (int i = 0; i < nombreBoucliers; i++) {
            equipements.add(new Bouclier());
        }
    }

    /**
     * Getter pour le nom du joueur.
     *
     * @return Le nom du joueur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter pour l'énergie du joueur.
     *
     * @return L'énergie du joueur.
     */
    public int getEnergie() {
        return energie;
    }

    /**
     * Getter pour la position du joueur.
     *
     * @return La position du joueur.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setter pour le nom du joueur.
     *
     * @param nom Le nouveau nom du joueur.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Setter pour l'énergie du joueur.
     *
     * @param energie La nouvelle énergie du joueur.
     */
    public void setEnergie(int energie) {
        this.energie = energie;
    }

    /**
     * Setter pour la position du joueur.
     *
     * @param position La nouvelle position du joueur.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Getter pour la liste des équipements du joueur.
     *
     * @return La liste des équipements du joueur.
     */
    public List<Equipement> getEquipements() {
        return equipements;
    }

    /**
     * Setter pour la liste des équipements du joueur.
     *
     * @param equipements La nouvelle liste des équipements du joueur.
     */
    public void setEquipements(List<Equipement> equipements) {
        this.equipements = equipements;
    }

    /**
     * Setter pour le symbole du joueur.
     *
     * @param symbole Le nouveau symbole du joueur.
     */
    public void setSymbole(String symbole) {
        Symbole = symbole;
    }

    /**
     * Getter pour le symbole du joueur.
     *
     * @return Le symbole du joueur.
     */
    @Override
    public String getSymbol() {
        return this.Symbole;
    }

    /**
     * Retourne une chaîne de caractères représentant l'état du joueur.
     *
     * @return La représentation sous forme de chaîne du joueur (nom, énergie, position, bouclier, équipements).
     */
    @Override
    public String toString() {
        // Compter le nombre de chaque type d'équipement
        int nombreMines = 0;
        int nombreBombes = 0;
        int nombreFusils = 0;
        int nombreBoucliers = 0;

        for (Equipement equipement : equipements) {
            if (equipement instanceof Mine) {
                nombreMines++;
            } else if (equipement instanceof Bombe) {
                nombreBombes++;
            } else if (equipement instanceof Fusil) {
                nombreFusils++;
                // nbMunition += ((Fusil)equipement).getNbMunition();
            } else if (equipement instanceof Bouclier) {
                nombreBoucliers++;
            }

        }

        return "Nom : " + this.nom + "\n" +
                "Énergie : " + this.energie + "\n" +
                "Position : " + this.position.toString() + "\n" +
                "Bouclier Actif : " + this.bouclierActif + "\n" +
                "Équipements : " + this.equipements.size() + "\n" +
                " - Mines : " + nombreMines + "\n" +
                " - Bombes : " + nombreBombes + "\n" +
                " - Fusils : " + nombreFusils + "\n" +
                " - Bouclier : " + nombreBoucliers;
    }

}
