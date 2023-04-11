package com.tigger.health.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "health_files")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)
    private Integer id;

    private String username;

    private String name;

    private Integer age;

    private Integer sex;

    private String remark;

    private Integer height;

    private Double weight;

    private Double visionLeft;

    private Double visionRight;

    private Integer diastolic;

    private Integer systolic;

    private Double glu;

    private Integer heartRate;

    private String checkDate;

    private Integer hgb;

    private String image;

    private String colourSense;

    private String wbc;

    private String rbc;

    private String hct;

    private String mcv;

    private String mch;

    private String mchc;

    private String rdwCv;

    private String plt;

    private String mpv;

    private String pdw;

    private String lymphocytePercentage;

    private String intermediatePercentage;

    private String neutrophilsPercentage;

    private String lymphocytesAbsolute;

    private String intermediateAbsolute;

    private String neutrophilsAbsolute;

    private String rdwSd;

    private String pct;

    private String sg;

    private String ph;

    private String leu;

    private String nit;

    private String proteinuria;

    private String glycosuria;

    private String ket;

    private String urochologen;

    private String urobilinogen;

    private String bil;

    private String bld;

    private String whitecellsUroscopic;

    private String epithelialCells;

    private String inorganicSalts;

    private String urineProtein;

    private String alt;

    private String ast;

    private String ggt;

    private String urea;

    private String cr;

    private String ua;

    private String tcho;

    private String tg;

    private String hdlC;

    private String ldlC;

    private String ck;

    private String ckIsoenzyme;

    private String ld;

    private String t3;

    private String t4;

    private String tsh;

    private String tbil;

    private String dbil;

    private String ibil;

    private String bun;

}
