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
 *Name: cms_tag
 *Author: Caicai
 *Date: 2020-07-10 22:05:27
 *Description: 标签
 */

public class Tag_ extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    //#region private fields

    @Id
    @Column("id")
    @Comment("")
    private Integer id;

    /**
     *标签名称
     */
    @Column("tag_name")
    @Comment("标签名称")
    private String tagName;

    /**
     *标签类型
     */
    @Column("tag_type")
    @Comment("标签类型")
    private Integer tagType;

    /**
     *描述
     */
    @Column("tag_description")
    @Comment("描述")
    private String tagDescription;

    //#endregion


    //#region getter and setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    public Integer getTagType() {
        return tagType;
    }
    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }
    public String getTagDescription() {
        return tagDescription;
    }
    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }
    //#endregion

    //#region others
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("tag_name", getTagName())
                .append("tag_type", getTagType())
                .append("tag_description", getTagDescription())
                .toString();
    }

    public static class ${
        public static String id="id";
        public static String tagName="tag_name";
        public static String tagType="tag_type";
        public static String tagDescription="tag_description";
    }
    //#endregion
}

