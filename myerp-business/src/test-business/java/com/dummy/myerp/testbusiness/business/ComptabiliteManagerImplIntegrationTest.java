package com.dummy.myerp.testbusiness.business;


import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:com/dummy/myerp/testbusiness/business/bootstrapContext.xml")
public class ComptabiliteManagerImplIntegrationTest {
    private ComptabiliteManagerImpl manager;
    private EcritureComptable vEcritureComptable;

    @Before
    public void setup() {
        manager = new ComptabiliteManagerImpl();
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat1"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle1");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, new BigDecimal(123.5),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512),
                null, null,
                new BigDecimal(123.5)));
    }

    @Test
    public void getListCompteComptableTest() {
        assertFalse(manager.getListCompteComptable().isEmpty());
    }

    @Test
    public void getListJournalComptableTest() {
        assertFalse(manager.getListJournalComptable().isEmpty());
    }

    @Test
    public void getListEcritureComptableTest() {
        assertFalse(manager.getListEcritureComptable().isEmpty());
    }

    @Test
    public void insertAndDeleteEcritureComptableTest() throws FunctionalException {
        manager.insertEcritureComptable(vEcritureComptable);
        manager.deleteEcritureComptable(vEcritureComptable.getId());
    }

    @Test
    public void updateEcritureComptableTest() throws FunctionalException {
        List<EcritureComptable> list = manager.getListEcritureComptable();
        EcritureComptable initialEcritureComptable = findEcritureComptableById(list, -1);
        String initialReference = initialEcritureComptable.getReference();
        String testReference = "AC-2017/00001";
        initialEcritureComptable.setReference(testReference);

        manager.updateEcritureComptable(initialEcritureComptable);
        assertEquals(testReference, findEcritureComptableById(manager.getListEcritureComptable(), -1).getReference());
        initialEcritureComptable.setReference(initialReference);
        manager.updateEcritureComptable(initialEcritureComptable);
    }

    @Test
    public void addReferenceTest() {
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setLibelle("Achat");
        ecritureComptable.setDate(new Date());
        ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
    }

    @Test
    public void getListSequenceEcritureComptableTest () {
        assertFalse(manager.getListSequenceEcritureComptable(2016).isEmpty());
    }

    @Test
    public void updateSequenceEcritureComptableTest () {
        SequenceEcritureComptable initialSeq = findSequenceEcritureComptableVE2016(manager.getListSequenceEcritureComptable(2016));
        if (initialSeq == null)
            fail("Aucune SequenceEcritureComptable n'a été trouvé. La BDD a-t-elle été lancée avec les dumps fournis ?");

        initialSeq.setDerniereValeur(initialSeq.getDerniereValeur() + 1);
        manager.updateSequenceEcritureComptable(initialSeq);
        int lastValue = findSequenceEcritureComptableVE2016(manager.getListSequenceEcritureComptable(2016)).getDerniereValeur();
        assertEquals(42, lastValue);

        initialSeq.setDerniereValeur(initialSeq.getDerniereValeur() - 1);
        manager.updateSequenceEcritureComptable(initialSeq);
        lastValue = findSequenceEcritureComptableVE2016(manager.getListSequenceEcritureComptable(2016)).getDerniereValeur();
        assertEquals(41, lastValue);
    }

    @Test
    public void insertSequenceEcritureComptableTest () {
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(2019, 30, "VE");
        manager.insertSequenceEcritureComptable(sequenceEcritureComptable);
        assertEquals(1, manager.getListSequenceEcritureComptable(2019).size());
        manager.deleteSequenceEcritureComptable(sequenceEcritureComptable);
        assertEquals(0, manager.getListSequenceEcritureComptable(2019).size());
    }


    // =========== Trouve une ecriture comptable parmi du liste en fonction de l'id ===========
    public EcritureComptable findEcritureComptableById(List<EcritureComptable> ecritureComptables, int id) {
        for (EcritureComptable ecritureComptable : ecritureComptables) {
            if (ecritureComptable.getId() == id)
                return ecritureComptable;
        }
        return null;
    }

    // =========== Trouve une séquence ecriture comptable parmi du liste en fonction de l'année et du code ===========
    public SequenceEcritureComptable findSequenceEcritureComptableVE2016 (List<SequenceEcritureComptable> list) {
        SequenceEcritureComptable sequence = null;
        for (SequenceEcritureComptable seq :
                list) {
            if (seq.getJournalCode().equals("VE"))
                sequence = seq;
        }
        return sequence;
    }
}
