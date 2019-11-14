package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

/*
    ===== RG4 =====
Les montants des lignes d'écriture sont signés et peuvent prendre des valeurs négatives (même si cela est peu fréquent).
 */

public class ComptabiliteManagerImplTestRG4 {
    private ComptabiliteManagerImpl manager = null;

    @Before
    public void setUp() {
        this.manager = new ComptabiliteManagerImpl();
    }

    // Deux négatives égaux
    @Test
    public void checkEcritureComptableWithLigneNegativeBalanced() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(-123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(-123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    // Deux négatives inégaux
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableWithLigneNegativeNotBalanced() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(-5),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(-123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    // Un négatif un positif inégaux
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableWithLigneNegativeNotBalancedAndBalanced() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(-5),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(5)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }


}
