package com.kan.database.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.kan.database.greendao.entry.ShopTest;

import com.kan.database.greendao.gen.ShopTestDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig shopTestDaoConfig;

    private final ShopTestDao shopTestDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        shopTestDaoConfig = daoConfigMap.get(ShopTestDao.class).clone();
        shopTestDaoConfig.initIdentityScope(type);

        shopTestDao = new ShopTestDao(shopTestDaoConfig, this);

        registerDao(ShopTest.class, shopTestDao);
    }
    
    public void clear() {
        shopTestDaoConfig.clearIdentityScope();
    }

    public ShopTestDao getShopTestDao() {
        return shopTestDao;
    }

}
