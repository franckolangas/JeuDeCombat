package tests;

import model.arme.Bombe;
import model.arme.Equipement;
import model.grille.Grille;
import model.grille.GrilleProxy;
import model.grille.Position;
import model.joueur.ConfigDuJeu;
import model.joueur.Joueur;

public class JoueurTest {

    private Joueur joueur;
    private ConfigDuJeu conf;
    private Grille instance;

    public JoueurTest() {
    }

    public static void main(String[] args) {
        JoueurTest test = new JoueurTest();
        boolean tousLesTestsReussis = test.runTests();
        System.out.println(tousLesTestsReussis ? "Tous les tests ont réussi !" : "Au moins un test a échoué.");
    }

    public boolean runTests() {
        System.out.println("Initialisation des tests pour la classe Joueur");

        // Test des différentes méthodes
        boolean ok = true;
        ok = ok && testGetGrilleProxy();
        ok = ok && testGetConfigDuJeu();
        ok = ok && testSetGrilleProxy();
        ok = ok && testDeplacer();
        ok = ok && testContientArme();
        // ok = ok && testAjouterEquipements();
        ok = ok && testGetNom();
        ok = ok && testGetPosition();
        ok = ok && testToString();

        System.out.println("Fin des tests pour la classe Joueur");

        return ok;
    }

    public void setUp() {
        instance = new Grille(10);
        conf = new ConfigDuJeu(instance, 2, 3, 1);
        joueur = new Joueur("Joueur 1", 100, new Position(2, 4), conf);
    }

    public void tearDown() {
        joueur = null;
        instance = null;
        conf = null;
    }

    public boolean testGetGrilleProxy() {
        setUp();
        System.out.println("Test: getGrilleProxy");
        GrilleProxy grilleProxy = new GrilleProxy(instance, joueur);
        joueur.setGrilleProxy(grilleProxy);
        boolean result = grilleProxy.equals(joueur.getGrilleProxy());
        tearDown();
        return result;
    }

    public boolean testGetConfigDuJeu() {
        setUp();
        System.out.println("Test: getConfigDuJeu");
        ConfigDuJeu configDuJeu = new ConfigDuJeu(null, 0, 0, 0);
        joueur.setConfigDuJeu(configDuJeu);
        boolean result = configDuJeu.equals(joueur.getConfigDuJeu());
        tearDown();
        return result;
    }

    public boolean testSetGrilleProxy() {
        setUp();
        System.out.println("Test: setGrilleProxy");
        GrilleProxy grilleProxy = new GrilleProxy(null, joueur);
        joueur.setGrilleProxy(grilleProxy);
        boolean result = grilleProxy.equals(joueur.getGrilleProxy());
        tearDown();
        return result;
    }

    public boolean testDeplacer() {
        setUp();
        System.out.println("Test: deplacer");
        joueur.setPosition(new Position(5, 5));
        joueur.deplacer(2, -1);
        boolean result = joueur.getPosition().equals(new Position(7, 4));
        tearDown();
        return result;
    }

    public boolean testContientArme() {
        setUp();
        System.out.println("Test: contientArme");
        Equipement arme = new Bombe(null, joueur); // Remplacez par une classespécifique
        joueur.getEquipements().add(arme);
        boolean result = joueur.contientArme(arme.getClass());
        tearDown();
        return result;
    }

    public boolean testAjouterEquipements() {
        setUp();
        System.out.println("Test: ajouterEquipements");
        joueur.ajouterEquipements(1, 1, 1, 1);
        boolean result = joueur.getEquipements().size() == 4;
        tearDown();
        return result;
    }

    public boolean testGetNom() {
        setUp();
        System.out.println("Test: getNom");
        joueur.setNom("TestNom");
        boolean result = "TestNom".equals(joueur.getNom());
        tearDown();
        return result;
    }

    public boolean testGetPosition() {
        setUp();
        System.out.println("Test: getPosition");
        Position position = new Position(3, 4);
        joueur.setPosition(position);
        boolean result = position.equals(joueur.getPosition());
        tearDown();
        return result;
    }

    public boolean testToString() {
        setUp();
        System.out.println("Test: toString");
        joueur.setNom("TestNom");
        boolean result = joueur.toString().contains("TestNom");
        tearDown();
        return result;
    }
}
