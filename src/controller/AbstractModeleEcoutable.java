/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.*;

/**
 *
 * @author mathet
 */
public class AbstractModeleEcoutable implements ModeleEcoutable {

    private List<EcouteurModele> listeners;

    public AbstractModeleEcoutable() {
        listeners = new ArrayList<>();
    }

    @Override
    public void addModelListener(EcouteurModele l) {
        listeners.add(l);
    }

    @Override
    public void removeModelListener(EcouteurModele l) {
        listeners.remove(l);
    }

    public void fireChange() {
        for (EcouteurModele l : listeners)
            l.somethingHasChanged(this);
    }

}
