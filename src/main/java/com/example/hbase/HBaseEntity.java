package com.example.hbase;

import lombok.Data;

/**
 * @author caohaifengx@163.com 2021-06-24 17:00
 */
@Data
public class HBaseEntity {

    /**
     * 表名
     */
    private String tableName;

    /**
     * rowKey
     */
    private String rowName;

    /**
     * 列族名
     */
    private String familyName;

    /**
     * 列名
     */
    private String qualifier;

    /**
     * 字节数组类型的数据
     */
    private byte[] data;
}
