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
*Name: cms_tag
*Author: Caicai
*Date: 2020-07-10 23:59:16
*Description: 标签
*/ 

@Table("cms_tag")
public class Tag extends Tag_ implements Serializable {
    private static final long serialVersionUID = 1L;

     
}
 
