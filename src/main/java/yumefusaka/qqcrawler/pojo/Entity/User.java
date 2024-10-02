package yumefusaka.qqcrawler.pojo.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
@Builder
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("qq")
    private String qq;

    @TableField("q_year")
    private Integer qYear;

    @TableField("gender")
    private String gender;

    @TableField("nick_name")
    private String nickName;

    @TableField("group_name")
    private String groupName;

    @TableField("enter_time")
    private String enterTime;
}
