package com.dummy.myerp.business.impl.manager;

import org.junit.Before;

/*
    ===== RG1 =====
Le solde d'un compte comptable est égal à la somme des montants au débit des lignes
d'écriture diminuées de la somme des montants au crédit. Si le résultat est positif,
le solde est dit "débiteur", si le résultat est négatif le solde est dit "créditeur".
 */

public class ComptabiliteManagerImplTestRG1 {
    private ComptabiliteManagerImpl manager = null;

    @Before
    public void setUp() {
        this.manager = new ComptabiliteManagerImpl();
    }
}
