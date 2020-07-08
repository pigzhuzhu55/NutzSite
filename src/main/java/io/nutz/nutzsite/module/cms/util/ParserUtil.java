package io.nutz.nutzsite.module.cms.util;

import cn.hutool.core.io.FileUtil;
import freemarker.cache.FileTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.*;
import io.nutz.nutzsite.module.cms.models.Site;
import io.nutz.nutzsite.module.cms.services.SiteService;
import org.apache.commons.lang3.StringUtils;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class ParserUtil {

    private static final Log LOG = Logs.get();
    public static final String TEMPLATES = "templets";
    public static final String HTML = "html";
    public static final String MOBILE = "m";
    public static final String INDEX = "index";
    public static final String HTML_SUFFIX = ".html";
    public static final String PAGE_LIST = "list-";
    public static final String HTM_SUFFIX = ".htm";
    public static final String IS_DO = "isDo";
    public static final String URL = "url";
    public static final String COLUMN = "column";
    public static final String ID = "id";
    public static final String TABLE_NAME = "tableName";
    public static final String MODEL_NAME = "modelName";
    public static final String DO_SUFFIX = ".do";
    public static final String PAGE = "pageTag";
    public static final String PAGE_NO = "pageNo";
    public static final String SIZE = "size";
    public static final String TYPE_ID = "typeid";
    public static final String SITE_ID = "siteId";
    public static boolean IS_SINGLE = true;
    public static Configuration cfg = new Configuration();
    public static FileTemplateLoader ftl = null;


    public static String buildTempletPath() {
        return buildTempletPath((String)null);
    }

    public static String buildTempletPath(String path) {
        return BasicUtil.getRealPath(TEMPLATES) + File.separator + BasicUtil.getSiteId() + File.separator + BasicUtil.getSite().getTheme() + (path != null ? File.separator + path : "");
    }

    public static String buildMobileHtmlPath(String path) {
        return BasicUtil.getRealPath("html") + File.separator + BasicUtil.getSiteId() + File.separator + "m" + File.separator + path + ".html";
    }

    public static String buildHtmlPath(String path) {
        return BasicUtil.getRealPath("html") + File.separator + BasicUtil.getSiteId() + File.separator + path + ".html";
    }

    public static boolean hasMobileFile(String path) {
        return FileUtil.exist(buildTempletPath("m" + File.separator + path));
    }

    public static String generate(String templatePath, Map params, boolean isMobile) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
        if (IS_SINGLE) {
            params.put("url", BasicUtil.getUrl());
        }

        params.put("html", "html");
        params.put("siteId", BasicUtil.getSiteId());
        if (ftl == null || !buildTempletPath().equals(ftl.baseDir.getPath())) {
            ftl = new FileTemplateLoader(new File(buildTempletPath()));
            cfg.setNumberFormat("#");
            cfg.setTemplateLoader(ftl);
        }

        Template template = cfg.getTemplate((isMobile ? BasicUtil.getSite().getMobileTheme() + File.separator : "") + templatePath, "utf-8");
        StringWriter writer = new StringWriter();
        TagParser tag = null;
        String content = null;

        try {
            template.process((Object)null, writer);
            tag = new TagParser(writer.toString(), params);
            content = tag.rendering();
            return content;
        } catch (TemplateException var8) {
            var8.printStackTrace();
            LOG.error("错误", var8);
            return null;
        }
    }

    public static String read(String templatePath, boolean isMobile) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
        if (ftl == null || !buildTempletPath().equals(ftl.baseDir.getPath())) {
            ftl = new FileTemplateLoader(new File(buildTempletPath()));
            cfg.setNumberFormat("#");
            cfg.setTemplateLoader(ftl);
        }

        Template template = cfg.getTemplate((isMobile ? BasicUtil.getSite().getMobileTheme() + File.separator : "") + templatePath, "utf-8");
        StringWriter writer = new StringWriter();
        TagParser tag = null;
        Object var5 = null;

        try {
            template.process((Object)null, writer);
            return writer.toString();
        } catch (TemplateException var7) {
            LOG.error("错误", var7);
            var7.printStackTrace();
            return null;
        }
    }
}
