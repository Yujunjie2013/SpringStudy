package com.example.cache.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@TableName(value = "student")
//从缓存反序列时,如果json中有未知字段在bean上没有对应的字段时，则忽略否则容易报错
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "SNO")
    @NotBlank
    private String sno;

    @TableField(value = "SNAME")
    @NotBlank
    private String sname;

    @TableField(value = "SSEX")
    @NotBlank
    private String ssex;

    @TableField(value = "DATASOURCE")
    @NotBlank
    private String datasource;

    @TableField(exist = false)
    private String age;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_SNO = "SNO";

    public static final String COL_SNAME = "SNAME";

    public static final String COL_SSEX = "SSEX";

    public static final String COL_DATASOURCE = "DATASOURCE";
}