package yumefusaka.qqcrawler.pojo.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("member")
public class Member {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("uin")
    private String uin;

    @TableField("gender")
    private String gender;

    @TableField("nick")
    private String nick;

    @TableField("card")
    private String card;

    @TableField("join_time")
    private LocalDateTime joinTime;

    @TableField("generation")
    private Long generation;

    @TableField("role")
    private String role;
}
