package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

/*
    ===== RG6 =====
La référence d'une écriture comptable doit être unique, il n'est pas possible de créer plusieurs écritures ayant la même référence.
 */

@RunWith(MockitoJUnitRunner.class)
public class ComptabiliteManagerImplTestRG6 {

    private ComptabiliteManagerImpl manager = null;

    private List<EcritureComptable> listEcritureComptable = new ArrayList<>();

    @Mock
    private ComptabiliteDao comptabiliteDao;

    @Mock
    private DaoProxy daoProxy;

    @Mock
    private BusinessProxy businessProxy;

    @Before
    public void setUpEcritureComptable() throws NotFoundException {
        this.manager = new ComptabiliteManagerImpl();
        AbstractBusinessManager.configure(businessProxy, daoProxy, TransactionManager.getInstance());

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
        this.listEcritureComptable.add(vEcritureComptable);

        EcritureComptable vEcritureComptable2;
        vEcritureComptable2 = new EcritureComptable();
        vEcritureComptable2.setJournal(new JournalComptable("AC2", "Achat2"));
        vEcritureComptable2.setDate(new Date());
        vEcritureComptable2.setLibelle("Libelle2");
        vEcritureComptable2.setReference("AC-2019/00002");
        vEcritureComptable2.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(1234),
                null));
        vEcritureComptable2.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));
        this.listEcritureComptable.add(vEcritureComptable2);

        when(comptabiliteDao.getEcritureComptableByRef("AC-2019/00001")).thenReturn(listEcritureComptable.get(0));
        when(comptabiliteDao.getEcritureComptableByRef("AC-2019/00003")).thenReturn(listEcritureComptable.get(0));
        when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
    }

    // Ecritue comptable avec une même référence que celle d'une autre présente dans la liste
    @Test (expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG6WithOneSameReference() throws FunctionalException {
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

        manager.checkEcritureComptableContext(vEcritureComptable);
    }

    // Ecritue comptable avec une référence différente que celles présentes dans la liste
    @Test ()
    public void checkEcritureComptableUnitRG6WithOneDifferentReference() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC1", "Achat1"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle1");
        vEcritureComptable.setReference("AC-2019/00003");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        manager.checkEcritureComptableContext(vEcritureComptable);
    }

    // Ecritue comptable avec une référence différente que celles présentes dans la liste
    @Test ()
    public void checkEcritureComptableUnitRG6WithnULLReference() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC1", "Achat1"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle1");
        vEcritureComptable.setReference(null);
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        manager.checkEcritureComptableContext(vEcritureComptable);
    }
}
