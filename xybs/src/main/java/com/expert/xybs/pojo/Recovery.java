package com.expert.xybs.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
public class Recovery {
    private Integer id;

    private String title;

    private String ioc;

    private String video;

    private String content;

    private Date ctime;

    private Integer num;


}