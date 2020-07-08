package io.nutz.nutzsite.module.cms.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagBean {
    private Map params = new HashMap();
    private String content;
    private String beginTag;
    private String endTag;
    private TagBean child;

    public TagBean() {
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map getParams() {
        return this.params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public String getBeginTag() {
        return this.beginTag;
    }

    public void setBeginTag(String beginTag) {
        this.beginTag = beginTag;
    }

    public String getEndTag() {
        return this.endTag;
    }

    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    public TagBean getChild() {
        if (this.child == null) {
            String p = "\\{ms:.*? ";
            Pattern r = Pattern.compile(p);
            Matcher m = r.matcher(this.content);
            int i = 0;

            String child;
            for(child = null; m.find(); ++i) {
                if (i == 1) {
                    child = m.group();
                }
            }

            if (child != null) {
                this.child = new TagBean();
                this.child.setBeginTag(child);
                p = "\\" + child.trim() + "([\\s\\S]*)\\" + child.replace("{ms:", "{/").trim() + "}";
                r = Pattern.compile(p);
                m = r.matcher(this.content);

                while(m.find()) {
                    this.child.setContent(m.group());
                }
            }
        }

        return this.child;
    }

    public void setChild(TagBean child) {
        this.child = child;
    }
}
