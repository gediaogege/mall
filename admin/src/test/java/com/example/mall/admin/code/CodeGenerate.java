package com.example.mall.admin.code;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import static com.baomidou.mybatisplus.enums.IdType.AUTO;

/**
 * 代码生成器
 */
public class CodeGenerate {
    @Test
    public void generateCod() throws Exception {
        String packageName = "com.example.mall";
        String moduleName = "admin";
        generateByTables(packageName, moduleName, "user_admin");
    }

    private void generateByTables(String packageName, String moduleName, String...tableNames) {
        AutoGenerator generator = new AutoGenerator();
        generator.setTemplateEngine(getTemplateEngine()) // 选择 freemarker 引擎，默认 Veloctiy
                .setPackageInfo(getPackageConfig(packageName, moduleName))
                .setDataSource(getDataSourceConfig())
                .setStrategy(getStrategyConfig(tableNames))
                .setGlobalConfig(getGlobalConfig());
        generator.execute();
    }

    //模板配置
    private FreemarkerTemplateEngine getTemplateEngine() {
        return new FreemarkerTemplateEngine();
    }

    //全局配置
    private GlobalConfig getGlobalConfig() {
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setAuthor("qmt")
                .setOutputDir("F:\\mybatis_plus_code")
                .setFileOverride(true)
                .setIdType(AUTO);
        config.setServiceName("%sService");
        return config;
    }

    //策略配置
    private StrategyConfig getStrategyConfig(String[] tableNames) {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)
                .setEntityLombokModel(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setEntityColumnConstant(true)
                .setInclude(tableNames);
        return strategyConfig;
    }

    //数据库配置
    private DataSourceConfig getDataSourceConfig() {
        String dbUrl = "jdbc:mysql://localhost:3306/mymall?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("admin")
                .setDriverName("com.mysql.jdbc.Driver");
        return dataSourceConfig;
    }

    //包配置
    private PackageConfig getPackageConfig(String packageName, String moduleName) {
        return new PackageConfig().setParent(packageName).setModuleName(moduleName);

    }



}
