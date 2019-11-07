package com.dummy.myerp.business.mock;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;

public class BusinessProxyMock implements BusinessProxy {
    private ComptabiliteManagerMock comptabiliteManagerMock;

    @Override
    public ComptabiliteManager getComptabiliteManager() {
        return this.comptabiliteManagerMock;
    }
}
