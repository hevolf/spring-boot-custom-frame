package com.example.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author caohaifengx@163.com 2021-06-24 18:29
 */
@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "config.hbase", name = "enable", havingValue = "true")
public class HBaseConfig {

    @Value("${config.hbase.enableKerberos}")
    private boolean enableKerberos;

    @Value("${config.kerberos.user}")
    private String user;

    /**
     * krb5.conf 路径
     */
    @Value("${config.kerberos.krb5Path}")
    private String krb5Path;

    /**
     * xx.keytab 路径
     */
    @Value("${config.kerberos.keytabPath}")
    private String keytabPath;

    @Bean
    public HBaseAdmin hBaseAdmin(Connection connection){
        HBaseAdmin admin = null;
        try {
            admin = (HBaseAdmin) connection.getAdmin();
        } catch (IOException e) {
            log.error("实例化HBaseAdmin失败：{}", e.getMessage(), e);
        }
        return admin;
    }

    @Bean
    public Connection connection(org.apache.hadoop.conf.Configuration configuration){
        Connection connection = null;
        System.setProperty("java.security.krb5.conf", krb5Path);
        // 是否开启 kerberos 认证
        if (enableKerberos) {
            // 开启hadoop kerberos 认证
            configuration.set("hadoop.security.authentication", "kerberos");
            try {
                UserGroupInformation.setConfiguration(configuration);
                UserGroupInformation.loginUserFromKeytab(user, keytabPath);
            } catch (Exception e) {
                log.error("kerberos认证失败：{}", e.getMessage(), e);
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        try {
            connection = ConnectionFactory.createConnection(configuration());
        } catch (IOException e) {
            log.error("创建Connection失败：{}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return connection;
    }

    @Bean
    public org.apache.hadoop.conf.Configuration configuration(){
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set(HConstants.ZOOKEEPER_QUORUM, "192.168.68.11");
        conf.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181");
        conf.set(HConstants.ZOOKEEPER_ZNODE_PARENT, "/hbase");

        // ....
        return conf;
    }
}
