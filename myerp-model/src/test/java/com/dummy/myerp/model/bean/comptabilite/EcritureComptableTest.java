package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        return new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                vLibelle,
                vDebit, vCredit);
    }

    @Test
    public void isEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée avec valeurs positives");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33.00"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301.00"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7.00"));
        Assert.assertTrue(vEcriture.toString()
                + " result = " + vEcriture.isEquilibree()
                + " debit = " + vEcriture.getTotalDebit()
                + ", crédit = " + vEcriture.getTotalCredit(), vEcriture.isEquilibree());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Equilibrée avec valeurs négatives");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "-200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "-100.50", "-33.00"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "-301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "-40", "-7"));
        Assert.assertTrue(vEcriture.toString()
                + " result = " + vEcriture.isEquilibree()
                + " debit = " + vEcriture.getTotalDebit()
                + ", crédit = " + vEcriture.getTotalCredit(), vEcriture.isEquilibree());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assert.assertFalse(vEcriture.toString()
                + " result = " + vEcriture.isEquilibree()
                + " debit = " + vEcriture.getTotalDebit()
                + ", crédit = " + vEcriture.getTotalCredit(), vEcriture.isEquilibree());

    }

    @Test
    public void checkTotalCredit() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(1234)));

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, new BigDecimal(1234),
                new BigDecimal(587)));

        int result = 1821;
        if (!vEcritureComptable.getTotalCredit().equals(BigDecimal.valueOf(1821))) {
            fail("La valeur totale du crédit n'est pas bonne, elle devrait valoir " + result + " mais vaut " + vEcritureComptable.getTotalCredit());
        }
    }

    @Test
    public void checkTotalDebit() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        BigDecimal debit = new BigDecimal(5654);
        BigDecimal credit = new BigDecimal(1564);

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(1234),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, new BigDecimal(587),
                new BigDecimal(1234)));

        int result = 1821;
        if (!vEcritureComptable.getTotalDebit().equals(BigDecimal.valueOf(1821))) {
            fail("La valeur totale du débit n'est pas bonne, elle devrait valoir " + result + " mais vaut " + vEcritureComptable.getTotalDebit());
        }
    }

    @Test
    public void testGetSetEcritureComptable() {
        BeanTester.testBean(EcritureComptable.class);

        CompteComptable compteComptable = new CompteComptable(2, "LibelleCompte2");
        List<CompteComptable> compteComptables = new ArrayList<>();
        compteComptables.add(new CompteComptable(1, "LibelleCompte1"));
        compteComptables.add(compteComptable);
        compteComptables.add(new CompteComptable(3, "LibelleCompte3"));
        compteComptables.add(null);
        Assert.assertEquals(compteComptable, CompteComptable.getByNumero(compteComptables, 2));
    }

    @Test
    public void testGetSetSequenceEcritureComptable() {
        BeanTester.testBean((SequenceEcritureComptable.class));
    }

    @Test
    public void testGetSetLigneEcritureComptable() {
        BeanTester.testBean(LigneEcritureComptable.class);
    }

    @Test
    public void testGetSetJournalComptable() {
        BeanTester.testBean(JournalComptable.class);

        JournalComptable journalComptable = new JournalComptable("EDE58", "LibelleJournalEDE58");
        List<JournalComptable> journalComptables = new ArrayList<>();
        journalComptables.add(new JournalComptable("OKIU6", "LibelleJournalOKIU6"));
        journalComptables.add(new JournalComptable("BV658", "LibelleJournalBV658"));
        journalComptables.add(null);
        journalComptables.add(journalComptable);
        Assert.assertEquals(journalComptable, JournalComptable.getByCode(journalComptables, "EDE58"));
    }

    @Test
    public void testGetSetCompteComptable() {
        BeanTester.testBean(CompteComptable.class);
    }
}
