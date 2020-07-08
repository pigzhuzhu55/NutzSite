package io.nutz.nutzsite.module.cms.services.impl;

import io.nutz.nutzsite.common.service.BaseServiceImpl;
import io.nutz.nutzsite.module.cms.models.Tag;
import io.nutz.nutzsite.module.cms.services.TagService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
/**
 * 标签 服务层实现
 *
 * @author haiming
 * @date 2020-07-08
 */
@IocBean(args = {"refer:dao"})
public class TagServiceImpl extends BaseServiceImpl<Tag> implements TagService {
    public TagServiceImpl(Dao dao) {
        super(dao);
    }
}
