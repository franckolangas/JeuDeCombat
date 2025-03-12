package model.joueur;

import java.util.*;
import javax.swing.Timer;

import controller.AbstractModeleEcoutable;
import model.grille.*;
import model.arme.*;

/**
 * Classe représentant la configuration du jeu. Cette classe gère le déroulement de la partie,
 * l'initialisation de la grille, la gestion des tours, et la coordination des actions entre les joueurs.
 */
public class ConfigDuJeu extends AbstractModeleEcoutable {
    private static final int DELAI_TOUR_MS = 800; // Délai entre chaque tour (en millisecondes)
    protected GrilleSetupStrategy setupStrategy; // Stratégie de configuration de la grille
    protected Grille grille; // Grille de jeu
    protected int nombreDeJoueur; // Nombre de joueurs
    protected int nombreDeMur; // Nombre de murs dans la grille
    protected int nombreDePastille; // Nombre de pastilles à collecter
    protected List<Bombe> bombesActives; // Liste des bombes actives sur la grille
    protected List<Bouclier> bouclierActif; // Liste des boucliers actifs
    protected int compteurTour; // Compteur du nombre de tours écoulés
    protected Map<Joueur, GrilleProxy> proxies; // Proxies associés à chaque joueur
    private Timer timer; // Timer pour gérer la boucle de jeu
    private List<Joueur> joueurs; // Liste des joueurs
    private int index; // Index du joueur actuel
    protected GrilleProxy proxyJoueurCourant; // Proxy du joueur courant
    protected Joueur joueurCourant; // Joueur courant

    /**
     * Constructeur de la configuration du jeu.
     * 
     * @param grille La grille de jeu.
     * @param nombreDeJoueur Le nombre de joueurs dans la partie.
     * @param nombreDeMur Le nombre de murs à placer dans la grille.
     * @param nombreDePastille Le nombre de pastilles à collecter par les joueurs.
     */
    public ConfigDuJeu(Grille grille, int nombreDeJoueur, int nombreDeMur, int nombreDePastille) {
        this.grille = grille;
        this.nombreDeJoueur = nombreDeJoueur;
        this.nombreDePastille = nombreDePastille;
        this.nombreDeMur = nombreDeMur;
        this.compteurTour = 0;
        this.bombesActives = new ArrayList<>();
        this.proxies = new HashMap<>();
        this.joueurs = new ArrayList<>();
        this.index = 0; // Initialisation de l'index du joueur
        this.joueurCourant = null;
        this.proxyJoueurCourant = null;
    }

    /**
     * Retourne la grille de jeu.
     * 
     * @return La grille de jeu.
     */
    public Grille getGrille() {
        return grille;
    }

    /**
     * Retourne le compteur du nombre de tours écoulés.
     * 
     * @return Le compteur de tours.
     */
    public int getCompteurTour() {
        return this.compteurTour;
    }

    /**
     * Retourne le joueur courant.
     * 
     * @return Le joueur courant.
     */
    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    /**
     * Retourne le proxy du joueur courant.
     * 
     * @return Le proxy du joueur courant.
     */
    public GrilleProxy getProxyJoueurCourant() {
        return proxyJoueurCourant;
    }

    /**
     * Retourne les proxies associés aux joueurs.
     * 
     * @return Le map des proxies par joueur.
     */
    public Map<Joueur, GrilleProxy> getProxies() {
        return proxies;
    }

    /**
     * Définit la stratégie de configuration de la grille.
     * 
     * @param strategy La stratégie de configuration à utiliser.
     */
    public void setSetupStrategy(GrilleSetupStrategy strategy) {
        this.setupStrategy = strategy;
    }

    /**
     * Initialise la grille en utilisant la stratégie de configuration.
     * 
     * @throws IllegalStateException Si aucune stratégie de configuration n'est définie.
     */
    public void initialiserGrille() {
        if (setupStrategy != null) {
            setupStrategy.configurerGrille();
        } else {
            throw new IllegalStateException("Aucune stratégie de configuration définie.");
        }
    }

    /**
     * Démarre la partie. Le jeu commence à jouer en boucle en utilisant un timer.
     */
    public void jouerPartie() {
        joueurs = new ArrayList<>(grille.getJoueurs()); // Copie de la liste des joueurs
        if (joueurs.isEmpty()) {
            System.out.println("Aucun joueur présent sur la grille. La partie ne peut pas commencer.");
            return;
        }

        timer = new Timer(DELAI_TOUR_MS, e -> {
            if (!joueursRestants()) {
                timer.stop();
                System.out.println("Fin de la partie !");
                return;
            }

            jouerUnTour(); // Joue un tour à chaque itération du timer
        });
        timer.start();
    }

    /**
     * Vérifie si des joueurs sont encore en lice.
     * 
     * @return true si il reste plus d'un joueur, false sinon.
     */
    private boolean joueursRestants() {
        return joueurs.size() > 1;
    }

    /**
     * Définit la liste des bombes actives.
     * 
     * @param bombesActives Liste des bombes actives à configurer.
     */
    public void setBombesActives(List<Bombe> bombesActives) {
        this.bombesActives = bombesActives;
    }

    /**
     * Joue un tour pour le joueur courant, vérifie l'état de la grille et met à jour les informations du jeu.
     */
    public void jouerUnTour() {
        this.joueurCourant = joueurs.get(index);
        this.proxyJoueurCourant = this.joueurCourant.getGrilleProxy();

        // Afficher la grille dans le terminal avant chaque tour
        System.out.println("##### Affichage Grille Generale #####");
        this.grille.afficherGrille(); // Affiche la grille dans le terminal

        // Vérifier les bombes
        proxyJoueurCourant.verifierBombe(joueurCourant);
        fireChange();

        for (GrilleProxy proxy : this.proxies.values()) {
            proxy.setCompteur(this.compteurTour); // Synchroniser le compteur
        }

        // affichage de la grille proxy du joueur courant
        System.out.println("##### Grille pour " + joueurCourant.getNom() + " #####");
        proxyJoueurCourant.afficherGrille();
        // Afficher l’état du joueur actuel
        System.out.println(joueurCourant.toString());

        if (joueurCourant.getEnergie() <= 0) {
            System.out.println(joueurCourant.getNom() + " n’a plus d’énergie et est retiré du jeu.");
            proxies.remove(joueurCourant);
            joueurs.remove(index);
            grille.getCellule(joueurCourant.getPosition().getX(), joueurCourant.getPosition().getY())
                    .setType(new Vide());
            System.out.println("un joueur est mort ,mise à jour de la grille ");
            grille.afficherGrille();
            if(index >= joueurs.size()) index = 0;
        } else {
            joueurCourant.demanderAction();
            proxyJoueurCourant.verifierBouclierActif(joueurCourant);
            index = (index + 1) % joueurs.size(); // Passer au joueur suivant
        }

        compteurTour++;
        this.proxyJoueurCourant.setCompteur(compteurTour);
        System.out.println(compteurTour);
        System.out.println(this.proxyJoueurCourant.getCompteurTour());

        fireChange(); // Notifier les observateurs que l’état a changé
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la configuration du jeu.
     * 
     * @return Une chaîne représentant la configuration du jeu.
     */
    @Override
    public String toString() {
        return "nombre de joueurs:" + this.grille.getNbJoueur();
    }
}
