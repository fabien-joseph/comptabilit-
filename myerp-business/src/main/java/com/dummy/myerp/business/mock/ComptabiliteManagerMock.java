package com.dummy.myerp.business.mock;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import java.util.ArrayList;
import java.util.List;

public class ComptabiliteManagerMock implements ComptabiliteManager {
    private List<CompteComptable> listCompteComptable = new ArrayList<>();
    private List<JournalComptable> listJournalComptable = new ArrayList<>();
    private List<EcritureComptable> listEcritureComptable = new ArrayList<>();

    @Override
    public List<CompteComptable> getListCompteComptable() {
        return listCompteComptable;
    }

    @Override
    public List<JournalComptable> getListJournalComptable() {
        return listJournalComptable;
    }

    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return listEcritureComptable;
    }

    @Override
    public void addReference(EcritureComptable pEcritureComptable) {

    }

    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {

    }

    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {

    }

    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {

    }

    @Override
    public void deleteEcritureComptable(Integer pId) {

    }
}
