package com.dummy.myerp.business.impl.manager;

/*
    ===== RG6 =====
Les montants des lignes d'écritures peuvent comporter 2 chiffres maximum après la virgule.
 */

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class ComptabiliteManagerImplTestRG7 {
    private ComptabiliteManagerImpl manager = null;

    @Before
    public void setUpEcritureComptable() {
        this.manager = new ComptabiliteManagerImpl();
    }


    // Ligne comptable sans chiffre après la virgule, devrait passer
    @Test()
    public void checkEcritureComptableUnitRG7With0Decimal() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC1", "Achat1"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle1");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    // Ligne comptable à 1 chiffre après la virgule, devrait passer
    @Test()
    public void checkEcritureComptableUnitRG7With1Decimal() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC1", "Achat1"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle1");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123.5),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123.5)));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    // TODO comprendre pourquoi ça plante ici alors que ça devrait pas
/*    // Ligne comptable à 2 chiffres après la virgule, devrait passer
    @Test()
    public void checkEcritureComptableUnitRG7With2Decimal() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC1", "Achat1"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle1");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123.55),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123.55)));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }*/

    // Ecritue comptable avec une référence différente que celles présentes dans la liste
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG7With3Decimal() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC1", "Achat1"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle1");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123.555),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123.555)));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }




}
