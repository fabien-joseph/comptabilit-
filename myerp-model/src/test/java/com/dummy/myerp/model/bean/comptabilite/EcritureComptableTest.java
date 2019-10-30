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
        return new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                vLibelle,
                vDebit, vCredit);
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
    public void testEcritureComptable () {
        JournalComptable journalComptable = new JournalComptable("CodeComptable", "LibelleComptable");
        Date date = new Date(1220227200L * 1000);
        String libelle = "Libelle";
        String reference = "Reference";
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(
                new CompteComptable(1, "LibelleCompteComptable"),
                "LibelleLigneEcritureComptable",
                new BigDecimal(55555),
                new BigDecimal(55555)
        );
        List<LigneEcritureComptable> ligneEcritureComptables = new ArrayList<>();
        ligneEcritureComptables.add(ligneEcritureComptable);

        EcritureComptable eComptable = new EcritureComptable();
        eComptable.setId(1);
        eComptable.setLibelle(libelle);
        eComptable.setDate(date);
        eComptable.setJournal(journalComptable);
        eComptable.setReference(reference);
        eComptable.getListLigneEcriture().add(ligneEcritureComptable);

        Assert.assertEquals(new Integer(1), eComptable.getId());
        Assert.assertEquals(libelle, eComptable.getLibelle());
        Assert.assertEquals(date, eComptable.getDate());
        Assert.assertEquals(journalComptable, eComptable.getJournal());
        Assert.assertEquals(reference, eComptable.getReference());
        Assert.assertEquals(ligneEcritureComptables, eComptable.getListLigneEcriture());
    }

    @Test
    public void testSequenceEcritureComptable() {
        SequenceEcritureComptable seqEcComptable2 = new SequenceEcritureComptable(2015, 99);
        SequenceEcritureComptable seqEcComptable = new SequenceEcritureComptable();
        seqEcComptable.setAnnee(2015);
        seqEcComptable.setDerniereValeur(99);
        String toString = "SequenceEcritureComptable{annee=2015, derniereValeur=99}";
        Assert.assertEquals(new Integer(2015), seqEcComptable.getAnnee());
        Assert.assertEquals(new Integer(99), seqEcComptable.getDerniereValeur());
        Assert.assertEquals(toString, seqEcComptable.toString());

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
        Assert.assertEquals("Libelle", ligneEcritureComptable.getLibelle());
    }

    @Test
    public void testJournalComptable() {
        JournalComptable jComptable = new JournalComptable();
        jComptable.setCode("AC50");
        jComptable.setLibelle("Libelle");
        String toString = "JournalComptable{code='AC50', libelle='Libelle'}";

        Assert.assertEquals("AC50", jComptable.getCode());
        Assert.assertEquals("Libelle", jComptable.getLibelle());
        Assert.assertEquals(toString, jComptable.toString());
    }

    @Test
    public void testCompteComptable() {
        CompteComptable cComptable = new CompteComptable();
        cComptable.setNumero(1);
        cComptable.setLibelle("Libelle");
        String toString = "CompteComptable{numero=1, libelle='Libelle'}";

        Assert.assertEquals(new Integer(1), cComptable.getNumero());
        Assert.assertEquals("Libelle", cComptable.getLibelle());
        Assert.assertEquals(toString, cComptable.toString());
    }
}
