package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dummy.myerp.technical.exception.FunctionalException;
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
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                vLibelle,
                vDebit, vCredit);
        return vRetour;
    }

    @Test(expected = AssertionError.class)
    public void isEquilibree() throws FunctionalException {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
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
    public void testSequenceEcritureComptable() {
        SequenceEcritureComptable seqEcComptable = new SequenceEcritureComptable(2019, 58);
        seqEcComptable.setAnnee(2015);
        seqEcComptable.setDerniereValeur(99);

        if (seqEcComptable.getAnnee() != 2015 || seqEcComptable.getDerniereValeur() != 99) {
            fail("La création d'une SequenceEcritureComptable c'est mal déroulée");
        }
    }

    @Test
    public void testLigneEcritureComptable() {
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
        ligneEcritureComptable.setCompteComptable(new CompteComptable(1, "LibelleCompteComptable"));
        ligneEcritureComptable.setCredit(new BigDecimal(84848));
        ligneEcritureComptable.setDebit(new BigDecimal(84848));
        ligneEcritureComptable.setLibelle("Libelle");

        Assert.assertEquals(new Integer(1), ligneEcritureComptable.getCompteComptable().getNumero());
        Assert.assertEquals("LibelleCompteComptable", ligneEcritureComptable.getCompteComptable().getLibelle());
        Assert.assertEquals(new BigDecimal(84848), ligneEcritureComptable.getCredit());
        Assert.assertEquals(new BigDecimal(84848), ligneEcritureComptable.getDebit());
    }

    @Test
    public void testJournalComptable() {
        JournalComptable jComptable = new JournalComptable("AC50", "Libelle");

        Assert.assertEquals("AC50", jComptable.getCode());
        Assert.assertEquals("Libelle", jComptable.getLibelle());

    }
}
