package vue;

import controller.EcouteurModele;
import model.arme.Bombe;
import model.arme.Mine;
import model.grille.Cellule;
import model.grille.Grille;
import model.grille.Mur;
import model.grille.Pastille;
import model.grille.GrilleProxy;
import model.joueur.Joueur;
import model.joueur.ConfigDuJeu;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * VueJeu est la classe représentant l'interface graphique du jeu, permettant d'afficher 
 * la grille du jeu, l'état des joueurs et les grilles proxy associées à chaque joueur.
 * Elle implémente l'interface EcouteurModele pour écouter les changements du modèle
 * et mettre à jour l'interface en conséquence.
 */
public class VueJeu extends JPanel implements EcouteurModele {
    private ConfigDuJeu modele; // Modèle principal
    private JPanel grillePanel; // Panel pour la grille principale
    private JPanel joueursPanel; // Panel pour les informations des joueurs
    private Map<Joueur, JFrame> proxyFrames; // Map pour gérer les fenêtres des grilles proxy

    /**
     * Constructeur de la VueJeu, initialise l'interface graphique et s'abonne aux 
     * changements du modèle.
     *
     * @param modele L'instance du modèle de jeu à afficher.
     */
    public VueJeu(ConfigDuJeu modele) {
        this.modele = modele;
        this.modele.addModelListener(this); // S'abonner aux changements du modèle
        this.proxyFrames = new java.util.HashMap<>();

        setLayout(new BorderLayout());

        // Panneau pour la grille principale
        grillePanel = new JPanel();

        // Panneau pour les informations des joueurs
        joueursPanel = new JPanel();
        joueursPanel.setLayout(new BoxLayout(joueursPanel, BoxLayout.Y_AXIS));
        joueursPanel.setBorder(BorderFactory.createTitledBorder("État des Joueurs"));

        // Ajouter les panneaux dans un JSplitPane pour partager l'espace
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(grillePanel),
                new JScrollPane(joueursPanel));
        splitPane.setResizeWeight(0.75); // Allouer 75% de l'espace à la grille
        add(splitPane, BorderLayout.CENTER);

        afficherGrille();
        afficherEtatJoueurs();
        afficherToutesGrillesProxy();
    }

    /**
     * Met à jour l'affichage de la grille principale du jeu.
     * Cette méthode redessine la grille en supprimant toutes les cellules existantes et en les réajoutant
     * selon les types des cellules (Joueur, Mur, Pastille, Bombe, Mine).
     * Chaque cellule est représentée par un JLabel avec une couleur et un texte spécifiques en fonction de son type.
     */
    public void afficherGrille() {
        Grille grille = modele.getGrille();
        grillePanel.removeAll();
        int taille = grille.getTaille();

        grillePanel.setLayout(new GridLayout(taille, taille));

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Cellule cellule = grille.getCellule(i, j);
                JLabel celluleLabel = new JLabel("", JLabel.CENTER);
                celluleLabel.setOpaque(true);
                celluleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if (cellule.getType() instanceof Joueur) {
                    celluleLabel.setText(cellule.getType().getSymbol());
                    celluleLabel.setBackground(Color.BLUE);
                } else if (cellule.getType() instanceof Mur) {
                    celluleLabel.setText("Mur");
                    celluleLabel.setBackground(Color.GRAY);
                } else if (cellule.getType() instanceof Pastille) {
                    celluleLabel.setText("Pastille");
                    celluleLabel.setBackground(Color.GREEN);
                } else if (cellule.getType() instanceof Bombe) {
                    celluleLabel.setText("Bombe");
                    celluleLabel.setBackground(Color.RED);
                } else if (cellule.getType() instanceof Mine) {
                    celluleLabel.setText("mine");
                    celluleLabel.setBackground(Color.ORANGE);
                }

                grillePanel.add(celluleLabel);
            }
        }

        grillePanel.revalidate();
        grillePanel.repaint();
    }
    /**
     * Met à jour l'affichage de l'état de chaque joueur.
     * Cette méthode parcourt les joueurs dans la grille et pour chaque joueur, crée un JPanel avec
     * les informations du joueur sous forme de texte HTML pour afficher les informations de manière lisible.
     * Les informations sont affichées dans un panneau de gauche avec des bordures.
     */
    public void afficherEtatJoueurs() {
        joueursPanel.removeAll();

        for (Joueur joueur : modele.getGrille().getJoueurs()) {
            JPanel joueurPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            joueurPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel joueurInfo = new JLabel("<html>" + joueur.toString().replace("\n", "<br>") + "</html>");
            joueurPanel.add(joueurInfo);
            joueursPanel.add(joueurPanel);
        }

        joueursPanel.revalidate();
        joueursPanel.repaint();
    }
    /**
     * Met à jour l'affichage des grilles proxy pour chaque joueur.
     * Cette méthode parcourt les joueurs et leurs grilles proxy et crée un JFrame pour chaque joueur.
     * Si un joueur est éliminé , un message "died" est affiché dans la fenêtre.
     * Sinon, la grille proxy du joueur est affichée dans un layout en grille, avec des labels
     * pour chaque cellule affichant son type et sa couleur respective.
     */
    public void afficherToutesGrillesProxy() {
        Map<Joueur, GrilleProxy> proxies = modele.getProxies();

        for (Map.Entry<Joueur, GrilleProxy> entry : proxies.entrySet()) {
            Joueur joueur = entry.getKey();
            GrilleProxy proxy = entry.getValue();

            JFrame proxyFrame = proxyFrames.get(joueur);
            if (proxyFrame == null) {
                proxyFrame = new JFrame("Grille Proxy de " + joueur.getNom());
                proxyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                proxyFrames.put(joueur, proxyFrame);
            }

            proxyFrame.getContentPane().removeAll();

            // Si le joueur n'a plus d'énergie, affiche "died"
            if (joueur.getEnergie() <= 0) {
                JLabel gameOverLabel = new JLabel("died", JLabel.CENTER);
                gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
                gameOverLabel.setForeground(Color.RED);
                proxyFrame.add(gameOverLabel);
            } else {
                // Sinon, afficher la grille proxy
                JPanel proxyPanel = new JPanel();
                proxyPanel.setLayout(new GridLayout(proxy.getTaille(), proxy.getTaille()));

                for (int i = 0; i < proxy.getTaille(); i++) {
                    for (int j = 0; j < proxy.getTaille(); j++) {
                        Cellule cellule = proxy.getCellule(i, j);
                        JLabel celluleLabel = new JLabel("", JLabel.CENTER);
                        celluleLabel.setOpaque(true);
                        celluleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                        if (cellule.getType() instanceof Joueur) {
                            celluleLabel.setText(cellule.getType().getSymbol());
                            celluleLabel.setBackground(Color.BLUE);
                        } else if (cellule.getType() instanceof Mur) {
                            celluleLabel.setText("Mur");
                            celluleLabel.setBackground(Color.GRAY);
                        } else if (cellule.getType() instanceof Pastille) {
                            celluleLabel.setText("Pastille");
                            celluleLabel.setBackground(Color.GREEN);
                        } else if (cellule.getType() instanceof Bombe) {
                            celluleLabel.setText("Bombe");
                            celluleLabel.setBackground(Color.RED);
                        } else if (cellule.getType() instanceof Mine) {
                            celluleLabel.setText("Mine");
                            celluleLabel.setBackground(Color.ORANGE);
                        }

                        proxyPanel.add(celluleLabel);
                    }
                }

                proxyFrame.add(proxyPanel);
            }

            proxyFrame.setSize(400, 400);
            proxyFrame.setVisible(true);
        }
    }

    @Override
    public void somethingHasChanged(Object source) {
        if (source instanceof ConfigDuJeu) {
            afficherGrille();
            afficherEtatJoueurs();
            afficherToutesGrillesProxy();
        }
    }
}