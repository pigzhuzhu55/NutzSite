package io.nutz.nutzsite.module.cms.util;

import com.alibaba.druid.util.StringUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.nutz.nutzsite.module.cms.bean.TagBean;
import io.nutz.nutzsite.module.cms.models.Tag;
import io.nutz.nutzsite.module.cms.models.TagSql;
import io.nutz.nutzsite.module.cms.services.SiteService;
import io.nutz.nutzsite.module.cms.services.TagService;
import io.nutz.nutzsite.module.cms.services.TagSqlService;
import io.nutz.nutzsite.module.cms.services.impl.SiteServiceImpl;
import io.nutz.nutzsite.module.cms.services.impl.TagServiceImpl;
import io.nutz.nutzsite.module.cms.services.impl.TagSqlServiceImpl;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagParser {
     private String content;
     private Map data;
     protected final Log LOG;
     private List<String> tagKeys;
     private int pageSize;
     private Map<String, TagBean> tags;
     private Dao dao;

    public TagParser(String content) {
        this(content, (Map)null);
    }

    public TagParser(String content, Map map) {
        this.data = new HashMap();
        this.LOG = Logs.get();
        this.tagKeys = new ArrayList();
        this.tags = new HashMap();
        this.content = content;
        this.dao = Mvcs.getIoc().get(Dao.class);
        if (map != null) {
            this.data.putAll(map);
        }
        this.parser();
    }

    public TagParser parser() {
        this.parserSingle().parserData();
        this.parserDoublue().parserData();
        this.content = this.parserFreemarker(this.content);
        this.rendering();
        return this;
    }


    private TagParser parserSingle() {
        Pattern pattern = Pattern.compile("\\{ms:+[\\S].[^\\{}]+?/}");
        Matcher matcher = pattern.matcher(this.content);

        while(matcher.find()) {
            String text = matcher.group(0);
            this.content = this.content.replace(text, matcher.group(0).replace("ms:", "").replace("{", "${").replace("/}", "!''}"));
            String key = text.split(":")[1].split("\\.")[0];
            this.tagKeys.add(key);
            this.tags.put(key, null);
        }
        return this;
    }

    private TagParser parserDoublue() {
        Pattern r = Pattern.compile("\\{ms.*?}");
        Matcher m = r.matcher(this.content);

        for(int index = 0; m.find(); ++index) {
            TagBean tagBean = new TagBean();
            String endTag = m.group(0);
            String tag = endTag.split(" ")[0].replace("{", "").replace("}", "");
            String p = tag + "_" + index;
            this.content = this.content.replace(endTag, endTag.replace(tag, p));
            tagBean.setBeginTag(endTag.replace(tag, p));
            tagBean.setEndTag("{/" + p.split(":")[1] + "}");
            String[] temp = endTag.replace("}", "").replace(p, "").split(" ");
            String[] var9 = temp;
            int var10 = temp.length;

            for(int var11 = 0; var11 < var10; ++var11) {
                String q = var9[var11];
                if (!StringUtils.isEmpty(q)) {
                    String[] _p = q.split("=");
                    if (_p.length == 2) {
                        tagBean.getParams().put(q.split("=")[0], q.split("=")[1]);
                    }
                }
            }

            tagBean.setContent(endTag.replace(tag, p));
            this.tags.put(p.split(":")[1], tagBean);
            this.tagKeys.add(p.split(":")[1]);
        }

        for(int i = this.tagKeys.size() - 1; i >= 0; --i) {
            String endTag = ((String)this.tagKeys.get(i)).split("_")[0];
            TagBean tag = (TagBean)this.tags.get(this.tagKeys.get(i));
            if (tag != null) {
                String p = tag.getContent().replace("{", "\\{") + "([\\w\\W]*?)\\{/ms:" + endTag + "}";
                Pattern pt = Pattern.compile(p);

                String temp;
                for(Matcher mt = pt.matcher(this.content); mt.find(); this.content = this.content.replace(mt.group(0), temp)) {
                    temp = mt.group(0).replace("/ms:" + endTag + "}", "/" + (String)this.tagKeys.get(i) + "}");
                    TagBean tagBean = (TagBean)this.tags.get(this.tagKeys.get(i));
                    tagBean.setContent(temp);
                }
            }
        }

        return this;
    }

    private TagParser parserData() {
        return this.parserData(this.data);
    }

    private TagParser parserData(Map root) {
        Iterator var3 = this.tagKeys.iterator();

        while(true) {
            while(true) {
                String tagName;
                TagBean tagBean;
                Tag tag;
                do {
                    do {
                        if (!var3.hasNext()) {
                            return this;
                        }

                        tagName = (String)var3.next();
                    } while(root.get(tagName) != null);

                    tagBean = (TagBean)this.tags.get(tagName);
                    String _tagName = tagName.split("_")[0].replace("ms:", "");
                    Cnd cnd = Cnd.NEW();
                    cnd.and("tag_name","=", _tagName);
                    tag = this.dao.fetch(Tag.class,cnd);
                } while(tag == null);

                List list;
                if (tagBean != null) {
                    Map tagParams = new HashMap();
                    tagParams.putAll(this.data);
                    tagParams.putAll(tagBean.getParams());
                    Map refs = new HashMap();
                    Object tagRefs = tagBean.getParams().get("refs");
                    if (tagRefs == null) {
                        Cnd cnd = Cnd.NEW();
                        cnd.and("tag_id","=", tag.getId());
                        TagSql tagSql = this.dao.fetch(TagSql.class,cnd);
                        String sql = this.rendering(tagParams,tagSql.getTagSql());

                        Sql sqls = Sqls.create(sql);
                        sqls.setCallback(Sqls.callback.maps());
                        sqls.setEntity(dao.getEntity(NutMap.class));
                        dao.execute(sqls);
                        list = sqls.getList(NutMap.class);
                        root.put(tagName, list);
                        if (tagBean.getParams().get("ref") != null) {
                            refs.put(tagBean.getParams().get("ref").toString(), list);
                            root.put(tagBean.getParams().get("ref"), refs);
                            TagBean child = tagBean.getChild();
                            String temp = tagBean.getContent().replace(child.getContent(), child.getBeginTag().split(":")[1].trim() + "${item.id}");
                            String ftl = this.parserFreemarker(temp);
                            sql = this.rendering(root, ftl);
                            this.content = this.content.replace(tagBean.getContent(), sql);
                        }
                    } else {
                        Object obj = root.get(tagRefs.toString().trim());
                        Map mapData = (Map)obj;
                        Iterator it = mapData.keySet().iterator();

                        while(it.hasNext()) {
                            list = (List)mapData.get(it.next());

                            for(int i = 0; i < list.size(); ++i) {
                                Map row = (Map)list.get(i);
                                tagParams.putAll(row);

                                Cnd cnd = Cnd.NEW();
                                cnd.and("tag_id","=", tag.getId());
                                TagSql tagSql = this.dao.fetch(TagSql.class,cnd);
                                String sql = this.rendering(tagParams,tagSql.getTagSql());

                                Sql sqls = Sqls.create(sql);
                                sqls.setCallback(Sqls.callback.maps());
                                sqls.setEntity(dao.getEntity(NutMap.class));
                                dao.execute(sqls);
                                List _list = sqls.getList(NutMap.class);

                                root.put(tagName + row.get("id"), list);
                                if (tagBean.getParams().get("ref") != null) {
                                    refs.put(tagBean.getParams().get("ref").toString() + row.get("id"), _list);
                                    root.put(tagBean.getParams().get("ref"), refs);
                                    TagBean child = tagBean.getChild();
                                    String ftl = "";
                                    String cont;
                                    if (child != null) {
                                        cont = tagBean.getContent().replace(child.getContent(), child.getBeginTag().split(":")[1].trim() + "${item.id}");
                                        ftl = this.parserFreemarker(cont);
                                    } else {
                                        ftl = this.parserFreemarker(tagBean.getContent());
                                    }

                                    cont = this.rendering(root, ftl.replace(tagName, tagName + row.get("id")));
                                    this.content = this.content.replace(tagName + row.get("id"), cont);
                                } else if (_list != null) {
                                    String ftl = this.parserFreemarker(tagBean.getContent());
                                    ftl = this.rendering(root, ftl.replace(tagName, tagName + row.get("id")));
                                    this.content = this.content.replace(tagName + row.get("id"), ftl);
                                }
                            }
                        }
                    }

                    if (tagBean.getParams().get("ispaging") != null) {
                        this.data.remove("ispaging");
                    }
                } else {
                    Cnd cnd = Cnd.NEW();
                    cnd.and("tag_id","=", tag.getId());
                    TagSql tagSql = this.dao.fetch(TagSql.class,cnd);
                    String sql = this.rendering(this.data,tagSql.getTagSql());

                    Sql sqls = Sqls.create(sql);
                    sqls.setCallback(Sqls.callback.maps());
                    sqls.setEntity(dao.getEntity(NutMap.class));
                    dao.execute(sqls);
                    list = sqls.getList(NutMap.class);
                    if (list.size() == 0) {
                        Map article = new HashMap();
                        list.add(article);
                    }

                    root.put(tagName, list.get(0));
                }
            }
        }
    }

    public String rendering() {
        return this.rendering(this.data, this.content);
    }

    private String rendering(Map root, String content) {
        Configuration cfg = new Configuration();
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("template", content);
        cfg.setNumberFormat("#");
        cfg.setTemplateLoader(stringLoader);

        try {
            Template template = cfg.getTemplate("template", "utf-8");
            StringWriter writer = new StringWriter();

            try {
                template.process(root, writer);
                content = writer.toString();
            } catch (TemplateException var8) {
                var8.printStackTrace();
                this.LOG.error("错误", var8);
            }
        } catch (IOException var9) {
            var9.printStackTrace();
            this.LOG.error("错误", var9);
        }

        return content;
    }

    private String parserFreemarker(String content) {
        Iterator var2 = this.tagKeys.iterator();

        String field;
        while(var2.hasNext()) {
            String str = (String)var2.next();
            if (str.indexOf("if") > -1) {
                field = ((TagBean)this.tags.get(str)).getBeginTag().replace("{ms:" + str, "<#if ").replace("}", ">");
                content = content.replace(((TagBean)this.tags.get(str)).getBeginTag(), field);
                content = content.replace(((TagBean)this.tags.get(str)).getEndTag(), "</#if>");
                content = content.replace("{/ms:if}", "</#if>");
            } else if (this.tags.get(str) != null) {
                content = content.replace(((TagBean)this.tags.get(str)).getBeginTag(), "<#list " + str.replace("ms:", "") + " as item>");
                content = content.replace(((TagBean)this.tags.get(str)).getEndTag(), "</#list>");
            }
        }

        content = content.replace("{ms:else}", "<#else>");
        Pattern p = Pattern.compile("\\[.*?/]");

        for(Matcher mt = p.matcher(content); mt.find(); content = content.replace(mt.group(0), field)) {
            field = mt.group(0);
            if (field.indexOf("field.") > 0) {
                field = mt.group(0).replace("[field.", "${item.").replace("/]", "!''}").replace("(!?\\d*)]", "$1!''}");
            } else if (field.indexOf("_root:") > 0) {
                field = mt.group(0).replace("[_root:", "${").replace("/]", "}").replace("]", "}");
            }
        }

        return content;
    }
}
