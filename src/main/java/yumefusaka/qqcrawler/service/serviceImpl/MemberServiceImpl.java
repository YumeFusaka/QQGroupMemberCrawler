package yumefusaka.qqcrawler.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yumefusaka.qqcrawler.mapper.MemberMapper;
import yumefusaka.qqcrawler.pojo.Entity.Member;
import yumefusaka.qqcrawler.service.IMemberService;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Autowired
    private MemberMapper memberMapper;

}
