package io.nutz.nutzsite.module.cms.models._;

import io.nutz.nutzsite.common.base.BaseModel;
import io.nutz.nutzsite.module.sys.models.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;
import org.nutz.dao.interceptor.annotation.PrevInsert;

import java.io.Serializable;
import java.util.Date;
 
/**
*Name: cms_tag_sql
*Author: Caicai
*Date: 2020-07-10 23:59:12
*Description: 标签对应多个sql语句
*/ 

public class TagSql_ extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    //#region private fields

    @Id
    @Column("id")
    @Comment("")
    private Integer id;

    /**
    *自定义标签编号
    */
    @Column("tag_id")
    @Comment("自定义标签编号")
    private Integer tagId;

    /**
    *自定义sql支持ftl写法
    */
    @Column("tag_sql")
    @Comment("自定义sql支持ftl写法")
    private String tagSql;

    /**
    *排序升序
    */
    @Column("sort")
    @Comment("排序升序")
    private Integer sort;
    
    //#endregion
    
    
    //#region getter and setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getTagId() {
        return tagId;
    }
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
    public String getTagSql() {
        return tagSql;
    }
    public void setTagSql(String tagSql) {
        this.tagSql = tagSql;
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    //#endregion
    
    //#region others
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("tag_id", getTagId())
                .append("tag_sql", getTagSql())
                .append("sort", getSort())
                .toString();
    }
    
    public static class ${
        public static String id="id";
        public static String tagId="tag_id";
        public static String tagSql="tag_sql";
        public static String sort="sort";
    }
    //#endregion
}
 
