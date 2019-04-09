package io.nutz.nutzsite.module.tool.gen.models;

import org.nutz.dao.entity.annotation.Column;

/**
 * 数据库表列信息
 */
public class ColumnInfo {
    /**
     * 字段名称
     */
    @Column("column_name")
    private String columnName;

    /**
     * 字段类型
     */
    @Column("data_type")
    private String dataType;

    /**
     * 列描述
     */
    @Column("column_comment")
    private String columnComment;

    /**
     * 列配置
     */
    private ColumnConfigInfo configInfo;

    /**
     * Java属性类型
     */
    private String attrType;

    /**
     * Java属性名称(第一个字母大写)，如：user_name => UserName
     */
    private String attrName;

    /**
     * Java属性名称(第一个字母小写)，如：user_name => userName
     */
    private String attrname;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public ColumnConfigInfo getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(ColumnConfigInfo configInfo) {
        this.configInfo = configInfo;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }
}