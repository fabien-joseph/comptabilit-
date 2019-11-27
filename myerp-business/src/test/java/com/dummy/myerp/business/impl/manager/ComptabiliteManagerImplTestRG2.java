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
    ===== RG2 =====
Pour qu'une écriture comptable soit valide, elle doit être équilibrée :
la somme des montants au crédit des lignes d'écriture doit être égale à la somme des montants au débit.
 */

public class ComptabiliteManagerImplTestRG2 {
    private ComptabiliteManagerImpl manager = null;

    @Before
    public void setUp() {
        this.manager = new ComptabiliteManagerImpl();
    }

    // Ecriture comptable à 2 lignes équilibrées, censée fonctionner
    @Test()
    public void checkEcritureComptableUnitRG2With2LignesEcrituresBalanced() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal("1234"),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal("1234")));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    // Ecriture comptable à 2 lignes équilibrées, censée fonctionner
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2With2LignesEcrituresBalanced0() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal("0"),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal("0")));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    // Ecriture comptable à 2 lignes non équilibrées, censée rater
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2With2LignesEcrituresNotBalanced() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal("123"),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal("1234")));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    // Ecriture comptable à 4 lignes équilibrées, censée fonctionner
    @Test()
    public void checkEcritureComptableUnitRG2With4LignesEcrituresBalanced() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal("123"),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal("592")));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(3),
                null, new BigDecimal("592"),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(4),
                null, null,
                new BigDecimal("123")));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }




}
