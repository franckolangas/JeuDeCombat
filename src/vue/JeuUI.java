package vue;

import model.grille.StrategyGrilleAleatoire;
import controller.JeuController;

import javax.swing.*;
import java.awt.*;

/**
 * Classe représentant l'interface utilisateur du jeu. Cette classe est responsable
 * de la gestion de l'affichage graphique du jeu et de la connexion avec le contrôleur
 * qui gère la logique du jeu.
 */
public class JeuUI {

    private StrategyGrilleAleatoire strategyGrille;
    private JeuController controller;
    private JFrame frame;

    /**
     * Constructeur pour initialiser l'interface utilisateur avec la configuration
     * du jeu.
     * 
     * @param strategyGrille La stratégie utilisée pour configurer la grille du jeu.
     *                       Cette stratégie permet de déterminer l'agencement initial
     *                       des éléments du jeu.
     */
    public JeuUI(StrategyGrilleAleatoire strategyGrille) {
        this.strategyGrille = strategyGrille;
        this.controller = new JeuController(strategyGrille);
    }

    /**
     * Configure et affiche l'interface graphique du jeu.
     * Cette méthode initialise la fenêtre principale du jeu et y ajoute les composants
     * nécessaires (comme la vue du jeu et le bouton pour démarrer un tour).
     * Elle est exécutée sur le thread de l'interface graphique via SwingUtilities.
     */
    public void afficher() {
        SwingUtilities.invokeLater(() -> {
            // Initialiser la vue du jeu
            VueJeu vueJeu = new VueJeu(strategyGrille);

            // Configurer la fenêtre principale
            frame = new JFrame("Jeu MVC");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());
            frame.add(vueJeu, BorderLayout.CENTER);

            // Ajouter un bouton pour démarrer le tour
            JButton startButton = new JButton("Démarrer le Jeu");
            startButton.addActionListener(e -> controller.startTour());
            frame.add(startButton, BorderLayout.SOUTH);

            // Rendre la fenêtre visible
            frame.setVisible(true);
        });
    }
}
