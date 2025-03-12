package model.grille;

public class Vide implements TypeCellule {
    protected final String SYMBOLE = " ";

    public Vide() {
    }
    @Override
    public String getSymbol() {
        return this.SYMBOLE;
    }

}
