package com.example.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caohaifengx@163.com 2021-06-24 15:50
 */
@Slf4j
@Component
@ConditionalOnBean(HBaseConfig.class)
public class HBaseTemplate implements HBaseOperations {

    // 操作表结构
    @Autowired
    private HBaseAdmin hBaseAdmin;

    @Autowired
    private Connection connection;


    @Override
    public boolean tableExists(String tableName) {
        Assert.hasLength(tableName, "表名不能为空");
        boolean flag = false;
        try {
            flag = hBaseAdmin.tableExists(TableName.valueOf(tableName));
        } catch (IOException e) {
            log.error("HABSE-IOException:{}", e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 通过表名和rowKey获取数据,获取一条数据
     *
     * @param tableName 表名
     * @param rowKeyVar rowKey 泛型 可支持多种类型{String,Long,Double}
     * @return Result 类型
     */
    @Override
    public <T> Result queryByTableNameAndRowKey(String tableName, T rowKeyVar) {

        Assert.hasLength(tableName, "表名不能为空");
        Assert.notNull(rowKeyVar, "rowKeyVar不能为null");

        boolean tableExists = tableExists(tableName);
        Assert.isTrue(tableExists, String.format("表：%s 不存在", tableName));

        // 依据类型转换rowKeyVar
        byte[] rowKey = checkType(rowKeyVar);
        Result result = null;
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            // 根据rowKey查询
            result = table.get(new Get(rowKey));
        } catch (IOException e) {
            log.error("IOException : {}", e.getMessage(), e);
        } finally {
            closeTable(table);
        }
        return result;
    }

    /**
     * 自定义查询
     *
     * @param tableName 表名
     * @param getList   请求体
     * @return Result类型
     */
    @Override
    public Result[] query(String tableName, List<Get> getList) {

        Assert.hasLength(tableName, "表名不能为空");
        Assert.notEmpty(getList, "getList不能为空");

        Assert.isTrue(tableExists(tableName), String.format("表：%s 不存在", tableName));

        Table table = null;
        Result[] result = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            result = table.get(getList);
        } catch (IOException e) {
            log.error("query error , message:{}", e.getMessage(), e);
        } finally {
            closeTable(table);
        }
        return result;
    }


    /**
     * 创建一张表
     *
     * @param tableName   表名
     * @param familyNames 列族名
     */
    @Override
    public void createTable(String tableName, String... familyNames) {

        Assert.hasLength(tableName, "表名不能为空");

        // 表已存在
        Assert.isTrue(!tableExists(tableName), String.format("表：%s 已存在", tableName));

        TableName tableNameVar = TableName.valueOf(tableName);

        List<ColumnFamilyDescriptor> familyDescriptors = new ArrayList<>();
        for (String familyName : familyNames) {
            // 构建列簇
            ColumnFamilyDescriptor descriptor = ColumnFamilyDescriptorBuilder.newBuilder(familyName.getBytes())
                    .build();
            familyDescriptors.add(descriptor);
        }

        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(tableNameVar)
                .setColumnFamilies(familyDescriptors).build();

        try {
            hBaseAdmin.createTable(tableDescriptor);
        } catch (IOException e) {
            log.error("create failed , Exception: {}", e.getMessage(), e);
        }
    }

    /**
     * 新增一条数据
     *
     * @param entity 插入数据实体
     */
    @Override
    public void put(HBaseEntity entity) {

        // todo 对entity做详细校验？？？
        Assert.notNull(entity, "HBase数据实体不能为null");
        Assert.isTrue(tableExists(entity.getTableName()), String.format("表：%s 不存在", entity.getTableName()));

        Table table = null;

        try {
            table = connection.getTable(TableName.valueOf(entity.getTableName()));
            Put put = new Put(Bytes.toBytes(entity.getRowName()));
            put.addColumn(Bytes.toBytes(entity.getFamilyName()), Bytes.toBytes(entity.getQualifier()), entity.getData());
            table.put(put);
        } catch (IOException e) {
            log.error("data put error,message: {}", e.getMessage(), e);
        } finally {
            closeTable(table);
        }

    }

    /**
     * 批量插入数据
     *
     * @param tableName 表名
     * @param putList   put集合
     */
    @Override
    public void putBatch(String tableName, List<Put> putList) {

        Assert.notEmpty(putList, "批量数据不能为空");

        Assert.isTrue(tableExists(tableName), String.format("表：%s 不存在", tableName));

        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            table.put(putList);
        } catch (IOException e) {
            log.error("data put error, message:{}", e.getMessage(), e);
        } finally {
            closeTable(table);
        }
    }


    /**
     * 删除某个列下的数据
     * entity.getQualifier() 为null，即未指定qualifier时会删除整个列簇下的数据
     */
    @Override
    public void delete(HBaseEntity entity) {

        //Assert.notNullBatch(tableName, rowName, familyName);
        //Assert.hasLengthBatch(tableName, rowName, familyName);
        Assert.notNull(entity, "实体不能为null");
        Assert.isTrue(tableExists(entity.getTableName()), String.format("表：%s 不存在", entity.getTableName()));

        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(entity.getTableName()));
            Delete delete = new Delete(entity.getRowName().getBytes());
            if (entity.getQualifier() != null) {
                delete.addColumn(entity.getFamilyName().getBytes(), entity.getQualifier().getBytes());
            }
            table.delete(delete);
        } catch (IOException e) {
            log.error("data delete error, message:{}", e.getMessage(), e);
        } finally {
            closeTable(table);
        }
    }

    /**
     * 批量删除数据
     *
     * @param tableName  表名
     * @param deleteList 需要删除的数据
     */
    @Override
    public void deleteBatch(String tableName, List<Delete> deleteList) {

        Assert.notNull(tableName, "不能为空");
        Assert.hasLength(tableName);

        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            table.delete(deleteList);
        } catch (IOException e) {
            log.error("data delete error, message:{}", e.getMessage(), e);
        } finally {
            closeTable(table);
        }
    }

    /**
     * 通过scan查询数据
     *
     * @param tableName 表名
     * @param scan      scan
     * @return 返回 ResultScanner
     */
    @Override
    public ResultScanner queryByScan(String tableName, Scan scan) {

        //Assert.notNullBatch(tableName, scan);
        Assert.hasLength(tableName);

        ResultScanner resultScanner = null;
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            resultScanner = table.getScanner(scan);
        } catch (IOException e) {
            log.error("query error, message:{}", e.getMessage(), e);
        } finally {
            closeTable(table);
        }
        return resultScanner;
    }

    /**
     * 删除表
     *
     * @param tableName 表名称
     */
    @Override
    public void dropTable(String tableName) {

        Assert.isTrue(tableExists(tableName), String.format("表：%s 不存在", tableName));

        try {
            hBaseAdmin.disableTable(TableName.valueOf(tableName));
            hBaseAdmin.deleteTable(TableName.valueOf(tableName));
            log.info("table {} delete successfully", tableName);
        } catch (IOException e) {
            log.error("table {} delete failed", e.getMessage(), e);
        }

    }


    /**
     * 传入一个泛型V 判断基本类型，返回对应的byte数组
     *
     * @param rowKeyVar 泛型rowKey
     * @param <V>       泛型
     * @return 返回格式化后的字节数组
     */
    private <V> byte[] checkType(V rowKeyVar) {
        byte[] rowKey = new byte[0];
        //判断T的类型
        String rowKeyType = rowKeyVar.getClass().getTypeName();
        log.info("rowKey Type is : {}", rowKeyType);
        if (rowKeyVar instanceof String) {
            rowKey = Bytes.toBytes((String) rowKeyVar);
        }
        if (rowKeyVar instanceof Long) {
            rowKey = Bytes.toBytes((Long) rowKeyVar);
        }
        if (rowKeyVar instanceof Double) {
            rowKey = Bytes.toBytes((Double) rowKeyVar);
        }
        if (rowKeyVar instanceof Integer) {
            rowKey = Bytes.toBytes((Integer) rowKeyVar);
        }

        return rowKey;
    }

    /**
     * 关闭连接
     *
     * @param table 表
     */
    public void closeTable(Table table) {
        if (table != null) {
            try {
                table.close();
            } catch (IOException e) {
                log.error("close table {} error {}", table.getName(), e.getMessage());
            }
        } else {
            log.info("closeTable : table is null");
        }
    }
}
