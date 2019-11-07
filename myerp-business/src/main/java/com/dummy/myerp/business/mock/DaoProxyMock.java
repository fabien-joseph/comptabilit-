package com.dummy.myerp.business.mock;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;

public class DaoProxyMock implements DaoProxy {
    private ComptabiliteDaoMock comptabiliteDaoMock;

    @Override
    public ComptabiliteDao getComptabiliteDao() {
        return this.comptabiliteDaoMock;
    }
}
