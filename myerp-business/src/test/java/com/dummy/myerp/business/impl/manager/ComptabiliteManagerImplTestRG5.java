package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/*
    ===== RG5 =====
La référence d'une écriture comptable est composée du code du journal dans lequel figure
l'écriture suivi de l'année et d'un numéro de séquence (propre à chaque journal) sur 5 chiffres
incrémenté automatiquement à chaque écriture. Le formatage de la référence est : XX-AAAA/#####.
Ex : Journal de banque (BQ), écriture au 31/12/2016 --> BQ-2016/00001
 */

public class ComptabiliteManagerImplTestRG5 {
    private ComptabiliteManagerImpl manager = null;

    @Before
    public void setUp() {
        this.manager = new ComptabiliteManagerImpl();
    }

    // Référence datée de cette année
    @Test
    public void checkEcritureComptableUnitWithGoodDateReference() throws FunctionalException {
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-" + year + "/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal("123"),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal("123")));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    // Référence datée d'une année future
    @Test (expected = FunctionalException.class)
    public void checkEcritureComptableUnitWithBadDateUpReference() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-3030/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal("123"),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal("123")));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    // Référence datée d'une année passée
    @Test (expected = FunctionalException.class)
    public void checkEcritureComptableUnitWithBadDateDownReference() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2010/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal("123"),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal("123")));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    // Référence nulle
    @Test (expected = FunctionalException.class)
    public void checkEcritureComptableUnitWithNullReference() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference(null);
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal("123"),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal("123")));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }


}
