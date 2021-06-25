package com.example.hbase;

import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.List;


/**
 * @author caohaifengx@163.com 2021-06-24 15:43
 */
public interface HBaseOperations {

    boolean tableExists(String tableName) throws IOException;

    /**
     * 通过表名和rowKey获取数据,获取一条数据
     *
     * @param tableName 表名
     * @param rowKeyVar rowKey 泛型 可支持多种类型{String,Long,Double}
     * @return Result 类型
     */
    <T> Result queryByTableNameAndRowKey(String tableName, T rowKeyVar);

    /**
     * 自定义查询
     *
     * @param tableName 表名
     * @param getList   请求体
     * @return Result类型
     */
    Result[] query(String tableName, List<Get> getList);

    /**
     * 关闭连接
     *
     * @param table 表名
     */
    void closeTable(Table table);
    /**
     * 创建一张表
     *
     * @param tableName  表名
     * @param familyName 列族名
     */
    void createTable(String tableName, String... familyName);


    /**
     * 新增一条数据
     */
     void put(HBaseEntity entity);


    /**
     * 批量插入数据
     *
     * @param tableName 表名
     * @param putList   put集合
     */
    void putBatch(String tableName, List<Put> putList);


    /**
     * 删除一个列族下的数据(或列族下指定列)
     */
    void delete(HBaseEntity entity);


    /**
     * 批量删除数据
     *
     * @param tableName  表名
     * @param deleteList 需要删除的数据
     */
    void deleteBatch(String tableName, List<Delete> deleteList);

    /**
     * 通过scan查询数据
     *
     * @param tableName 表名
     * @param scan      scan
     * @return 返回 ResultScanner
     */
    ResultScanner queryByScan(String tableName, Scan scan);

    /**
     * 删除表
     *
     * @param tableName 表名称
     */
    void dropTable(String tableName);
}
