package io.nutz.nutzsite.module.cms.models;

import io.nutz.nutzsite.common.base.BaseModel;
import io.nutz.nutzsite.module.cms.models._.*;
import io.nutz.nutzsite.module.sys.models.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;
import org.nutz.dao.interceptor.annotation.PrevInsert;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章表 cms_article
 *
 * @author haiming
 * @date 2019-06-11
 */
@Table("cms_article")
public class Article extends Article_ implements Serializable {
    private static final long serialVersionUID = 1L;

    @One(field = "categoryId")
    private Category category;


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
