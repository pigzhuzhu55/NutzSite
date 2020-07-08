package io.nutz.nutzsite.module.cms.models;

import io.nutz.nutzsite.common.base.BaseModel;
import io.nutz.nutzsite.module.sys.models.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;
/**
 * 标签对应多个sql语句表 cms_tag_sql
 *
 * @author haiming
 * @date 2020-07-08
 */
@Table("cms_tag_sql")
public class TagSql extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Name
    @Column("id")
    @Comment("")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    @Prev(els = {@EL("uuid()")})
    private Integer id;

    /** 自定义标签编号 */
    @Column("tag_id")
    @Comment("自定义标签编号")
    private Integer tagId;

    /** 自定义sql支持ftl写法 */
    @Column("tag_sql")
    @Comment("自定义sql支持ftl写法")
    private String tagSql;

    /** 排序升序 */
    @Column("sort")
    @Comment("排序升序")
    private Integer sort;


    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setTagId(Integer tagId)
    {
        this.tagId = tagId;
    }

    public Integer getTagId()
    {
        return tagId;
    }

    public void setTagSql(String tagSql)
    {
        this.tagSql = tagSql;
    }

    public String getTagSql()
    {
        return tagSql;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public Integer getSort()
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("tagId", getTagId())
                .append("tagSql", getTagSql())
                .append("sort", getSort())
                .toString();
    }
}
