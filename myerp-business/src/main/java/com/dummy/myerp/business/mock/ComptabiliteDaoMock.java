package com.dummy.myerp.business.mock;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComptabiliteDaoMock implements ComptabiliteDao {
    private List<CompteComptable> listCompteComptable = new ArrayList<>();
    private List<JournalComptable> listJournalComptable = new ArrayList<>();
    private List<EcritureComptable> listEcritureComptable = new ArrayList<>();

    public ComptabiliteDaoMock() {

    }

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
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        int i = 0;
        for (EcritureComptable ecritureComptable : listEcritureComptable) {
            if (ecritureComptable.getId().equals(listEcritureComptable.get(i).getId()))
                return ecritureComptable;
            i++;
        }
        return null;
    }

    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        int i = 0;
        for (EcritureComptable ecritureComptable : listEcritureComptable) {
            if (ecritureComptable.getReference().equals(listEcritureComptable.get(i).getReference()))
                return ecritureComptable;
            i++;
        }
        return null;
    }

    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        //TODO Je comprends pas ce que ça doit faire cette affaire
    }

    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
        try {
            if (getEcritureComptable(pEcritureComptable.getId()) == null)
                listEcritureComptable.add(pEcritureComptable);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {
        int i = 0;
        for (EcritureComptable ecritureComptable : listEcritureComptable) {
            if (ecritureComptable.getId().equals(listEcritureComptable.get(i).getId()))
                listEcritureComptable.set(i, pEcritureComptable);
            i++;
        }
    }

    @Override
    public void deleteEcritureComptable(Integer pId) {
        try {
            EcritureComptable ecritureComptableToDelete = getEcritureComptable(pId);
            listEcritureComptable.remove(ecritureComptableToDelete);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
