package yumefusaka.qqcrawler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import yumefusaka.qqcrawler.pojo.Entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
