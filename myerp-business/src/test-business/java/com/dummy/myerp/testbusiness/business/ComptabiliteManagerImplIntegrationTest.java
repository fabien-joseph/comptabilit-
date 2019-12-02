package com.dummy.myerp.testbusiness.business;


import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    //TODO Pas encore implémenté, mais il faudra le faire
    @Test
    public void addReference() {
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



    // =========== Trouve une ecriture comptable parmi du liste en fonction de l'id ===========
    public EcritureComptable findEcritureComptableById(List<EcritureComptable> ecritureComptables, int id) {
        for (EcritureComptable ecritureComptable : ecritureComptables) {
            if (ecritureComptable.getId() == id)
                return ecritureComptable;
        }
        return null;
    }


}
