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

public class ComptabiliteManagerImplTestRG6 {
    private ComptabiliteManagerImpl manager = null;

    @Before
    public void setUp() {
        this.manager = new ComptabiliteManagerImpl();
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG6() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC1", "Achat1"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle1");
        vEcritureComptable.setReference("Reference1");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        manager.checkEcritureComptableContext(vEcritureComptable);
    }
}
