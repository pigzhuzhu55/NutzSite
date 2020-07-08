package io.nutz.nutzsite.module.cms.models;

import io.nutz.nutzsite.common.base.BaseModel;
import io.nutz.nutzsite.module.sys.models.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;
/**
 * 标签表 cms_tag
 *
 * @author haiming
 * @date 2020-07-08
 */
@Table("cms_tag")
public class Tag extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Name
    @Column("id")
    @Comment("")
    @ColDefine(type = ColType.VARCHAR, width = 64)
    @Prev(els = {@EL("uuid()")})
    private Integer id;

    /** 标签名称 */
    @Column("tag_name")
    @Comment("标签名称")
    private String tagName;

    /** 标签类型 */
    @Column("tag_type")
    @Comment("标签类型")
    private Integer tagType;

    /** 描述 */
    @Column("tag_description")
    @Comment("描述")
    private String tagDescription;


    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }

    public String getTagName()
    {
        return tagName;
    }

    public void setTagType(Integer tagType)
    {
        this.tagType = tagType;
    }

    public Integer getTagType()
    {
        return tagType;
    }

    public void setTagDescription(String tagDescription)
    {
        this.tagDescription = tagDescription;
    }

    public String getTagDescription()
    {
        return tagDescription;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("tagName", getTagName())
                .append("tagType", getTagType())
                .append("tagDescription", getTagDescription())
                .toString();
    }
}
