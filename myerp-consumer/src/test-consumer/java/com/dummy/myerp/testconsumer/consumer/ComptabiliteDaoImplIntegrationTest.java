package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:com/dummy/myerp/testconsumer/consumer/bootstrapContext.xml")
public class ComptabiliteDaoImplIntegrationTest extends ConsumerTestCase {
    private ComptabiliteDao dao = getDaoProxy().getComptabiliteDao();
    private EcritureComptable vEcritureComptable;

    @Before
    public void setup() {
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");
    }

    @Test
    public void getListCompteComptableTest() {
        assertFalse(dao.getListCompteComptable().isEmpty());
    }

    @Test
    public void getListJournalComptableTest() {
        assertFalse(dao.getListCompteComptable().isEmpty());
    }
    @Test
    public void getListEcritureComptableTest() {
        assertFalse(dao.getListEcritureComptable().isEmpty());
    }

    @Test
    public void getEcritureComptableTest() throws NotFoundException {
        assertNotNull(dao.getEcritureComptable(-1));
    }

    @Test
    public void getEcritureComptableByRefTest() throws NotFoundException {
        assertNotNull(dao.getEcritureComptableByRef("AC-2016/00001"));
    }

    @Test(expected = NotFoundException.class)
    public void getEcritureComptableByRefNotExistsTest() throws NotFoundException {
        assertNotNull(dao.getEcritureComptableByRef("Mille millions de mille sabords !"));
    }

    @Test
    public void loadListLigneEcritureTest() throws NotFoundException {
        EcritureComptable ecritureComptable = dao.getEcritureComptable(-1);
        dao.loadListLigneEcriture(ecritureComptable);
        assertNotNull(ecritureComptable.getListLigneEcriture());
    }

    @Test
    public void insertEcritureComptableTest() {
        dao.insertEcritureComptable(vEcritureComptable);
        dao.deleteEcritureComptable(vEcritureComptable.getId());
    }

    @Test
    public void deleteEcritureComptableNotExist() {
        dao.deleteEcritureComptable(2);
    }

    @Test
    public void getListSequenceEcritureComptableTest() {
        assertEquals(4, dao.getListSequenceEcritureComptable(2016).size());
        assertEquals(0, dao.getListSequenceEcritureComptable(1050).size());
    }

    @Test
    public void updateSequenceEcritureComptable() {
        SequenceEcritureComptable initialSeq = findSequenceEcritureComptableVE2016(dao.getListSequenceEcritureComptable(2016));
        if (initialSeq == null)
            fail("Aucune SequenceEcritureComptable n'a été trouvé. La BDD a-t-elle été lancée avec les dumps fournis ?");

        initialSeq.setDerniereValeur(initialSeq.getDerniereValeur() + 1);
        dao.updateSequenceEcritureComptable(initialSeq);
        int lastValue = findSequenceEcritureComptableVE2016(dao.getListSequenceEcritureComptable(2016)).getDerniereValeur();
        assertEquals(42, lastValue);

        initialSeq.setDerniereValeur(initialSeq.getDerniereValeur() - 1);
        dao.updateSequenceEcritureComptable(initialSeq);
        lastValue = findSequenceEcritureComptableVE2016(dao.getListSequenceEcritureComptable(2016)).getDerniereValeur();
        assertEquals(41, lastValue);
    }

    @Test
    public void insertSequenceEcritureComptable() {
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(2019, 30, "VE");
        dao.insertSequenceEcritureComptable(sequenceEcritureComptable);
        assertEquals(1, dao.getListSequenceEcritureComptable(2019).size());
        dao.deleteSequenceEcritureComptable(sequenceEcritureComptable);
        assertEquals(0, dao.getListSequenceEcritureComptable(2019).size());
    }

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
