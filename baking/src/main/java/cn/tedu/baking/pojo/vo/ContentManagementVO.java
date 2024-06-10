package cn.tedu.baking.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ContentManagementVO {

    private Long id;
    private String title;
    private String imgUrl;
    private String brief;
    private Long type;
    private String categoryName; //Query associated with classification table
    private Integer viewCount;
    private Integer commentCount;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
