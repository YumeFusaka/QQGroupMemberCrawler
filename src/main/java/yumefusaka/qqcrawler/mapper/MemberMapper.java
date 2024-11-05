package yumefusaka.qqcrawler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import yumefusaka.qqcrawler.pojo.Entity.Member;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}