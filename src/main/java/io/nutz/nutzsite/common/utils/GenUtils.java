package io.nutz.nutzsite.common.utils;


import io.nutz.nutzsite.common.base.Globals;
import io.nutz.nutzsite.common.config.GenConfig;
import io.nutz.nutzsite.common.constant.CommonMap;
import io.nutz.nutzsite.module.tool.gen.models.ColumnInfo;
import io.nutz.nutzsite.module.tool.gen.models.TableInfo;
import org.apache.velocity.VelocityContext;
import org.nutz.lang.Lang;
import org.nutz.lang.random.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
/**
 * 代码生成器 工具类
 *
 * @author haiming
 */
public class GenUtils {
    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = getProjectPath();

    /**
     * 项目空间路径open
     */
    private static final String PROJECT_PATH_OPEN = getProjectPathOpen();


    /**
     * html空间路径
     */
    private static final String TEMPLATES_PATH = "main/resources/views";

    /**
     * 大小写范围
     */
    private static Pattern p = Pattern.compile("[A-Z]");

    /**
     * 设置列信息
     */
    public static List<ColumnInfo> transColums(List<ColumnInfo> columns) {
        // 列信息
        List<ColumnInfo> columsList = new ArrayList<>();
        for (ColumnInfo column : columns) {
            // 列名转换成Java属性名
            String attrName = StringUtils.convertToCamelCase(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrname(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = CommonMap.javaTypeMap.get(column.getDataType());
            column.setAttrType(attrType);

            columsList.add(column);
        }
        return columsList;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static VelocityContext getVelocityContext(TableInfo table) {
        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        String packageName = GenConfig.getPackageName();
        String packageNameOpen = GenConfig.getPackageNameOpen();
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("tableComment", replaceKeyword(table.getTableComment()));
        velocityContext.put("primaryKey", table.getPrimaryKey());
        velocityContext.put("className", table.getClassName());
        velocityContext.put("classname", table.getClassname());
        velocityContext.put("moduleName", getModuleName(packageName));
        velocityContext.put("moduleNameOpen", getModuleName(packageNameOpen));
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("package", packageName);
        velocityContext.put("packageOpen", packageNameOpen);
        velocityContext.put("author", GenConfig.getAuthor());
        velocityContext.put("datetime", DateUtils.getDate());
        velocityContext.put("uuid1", R.UU32().toLowerCase());
        velocityContext.put("uuid2", R.UU32().toLowerCase());
        velocityContext.put("uuid3", R.UU32().toLowerCase());
        velocityContext.put("uuid4", R.UU32().toLowerCase());
        velocityContext.put("uuid5", R.UU32().toLowerCase());
        //args[0].
        velocityContext.put("array2str", "${array2str(args[0])}");
        velocityContext.put("args", "${args[0].");
        return velocityContext;
    }


    /**
     * 获取 列表模板
     *
     * @return
     */
    public static List<String> getListTemplates(String tplType) {
        List<String> templates = new ArrayList<String>();
        templates.add("views/vm/java/Models.java.vm");
        templates.add("views/vm/java/ApiController.java.vm");
        if(Globals.TPL_CRUD.equals(tplType)){
            templates.add("views/vm/java/list/Service.java.vm");
            templates.add("views/vm/java/list/ServiceImpl.java.vm");
            templates.add("views/vm/java/list/Controller.java.vm");
            templates.add("views/vm/html/list/list.html.vm");
            templates.add("views/vm/html/list/add.html.vm");
            templates.add("views/vm/html/list/edit.html.vm");
            templates.add("views/vm/html/list/detail.html.vm");
        }else  if(Globals.TPL_TREE.equals(tplType)){
            templates.add("views/vm/java/tree/Service.java.vm");
            templates.add("views/vm/java/tree/ServiceImpl.java.vm");
            templates.add("views/vm/java/tree/Controller.java.vm");
            templates.add("views/vm/html/tree/list.html.vm");
            templates.add("views/vm/html/tree/add.html.vm");
            templates.add("views/vm/html/tree/edit.html.vm");
            templates.add("views/vm/html/tree/tree.html.vm");
        }
        templates.add("views/vm/sql/sql.vm");
        return templates;
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName) {
        if (Boolean.valueOf(GenConfig.getAutoRemovePre())) {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (Lang.isNotEmpty(GenConfig.getTablePrefix())) {
            tableName = tableName.replace(GenConfig.getTablePrefix(), "");
        }
        return StringUtils.convertToCamelCase(tableName);
    }

    /**
     * 获取列表 文件名
     *
     * @param template
     * @param table
     * @param moduleName
     * @return
     */
    public static String getFileName(String template, TableInfo table, String moduleName) {
        // 小写类名
        String classname = table.getClassname();
        // 大写类名
        String className = table.getClassName();
        String javaPath = PROJECT_PATH;
        String javaPathOpen = PROJECT_PATH_OPEN;
        String htmlPath = TEMPLATES_PATH + "/" + moduleName + "/" + classname;

        if (Lang.isNotEmpty(classname)) {
            javaPath = javaPath.replace(".", "/") + "/";
        }

        if (template.contains("Models.java.vm")) {
            return javaPath + "models" + "/" + className + ".java";
        }

        if (template.contains("Service.java.vm")) {
            return javaPath + "services" + "/" + className + "Service.java";
        }
        if (template.contains("ServiceImpl.java.vm")) {
            return javaPath + "services/impl" + "/" + className + "ServiceImpl.java";
        }
        if (template.contains("ApiController.java.vm")) {
            return javaPathOpen + "/Api" + className + "Controller.java";
        }
        if (template.contains("Controller.java.vm")) {
            return javaPath + "controller" + "/" + className + "Controller.java";
        }
        if (template.contains("list.html.vm")) {
            return htmlPath + "/" + classname + ".html";
        }
        if (template.contains("add.html.vm")) {
            return htmlPath + "/" + "add.html";
        }
        if (template.contains("edit.html.vm")) {
            return htmlPath + "/" + "edit.html";
        }
        if (template.contains("detail.html.vm")) {
            return htmlPath + "/" + "detail.html";
        }
        if (template.contains("tree.html.vm")) {
            return htmlPath + "/" + "tree.html";
        }
        if (template.contains("sql.vm")) {
            return classname + "Menu.sql";
        }
        return null;
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
        return moduleName;
    }

    public static String getProjectPath() {
        String packageName = GenConfig.getPackageName();
        StringBuffer projectPath = new StringBuffer();
        projectPath.append("main/java/");
        projectPath.append(packageName.replace(".", "/"));
        projectPath.append("/");
        return projectPath.toString();
    }

    public static String getProjectPathOpen() {
        String packageNameOpen = GenConfig.getPackageNameOpen();
        StringBuffer projectPath = new StringBuffer();
        projectPath.append("main/java/");
        projectPath.append(packageNameOpen.replace(".", "/"));
        projectPath.append("/");
        return projectPath.toString();
    }


    public static String replaceKeyword(String keyword) {
        String keyName = keyword.replaceAll("(?:表|信息)", "");
        return keyName;
    }
}
