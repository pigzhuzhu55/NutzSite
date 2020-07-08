package io.nutz.nutzsite.module.cms.services.impl;

import io.nutz.nutzsite.common.service.BaseServiceImpl;
import io.nutz.nutzsite.module.cms.models.TagSql;
import io.nutz.nutzsite.module.cms.services.TagSqlService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
/**
 * 标签对应多个sql语句 服务层实现
 *
 * @author haiming
 * @date 2020-07-08
 */
@IocBean(args = {"refer:dao"})
public class TagSqlServiceImpl extends BaseServiceImpl<TagSql> implements TagSqlService{
    public TagSqlServiceImpl(Dao dao) {
        super(dao);
    }
}
