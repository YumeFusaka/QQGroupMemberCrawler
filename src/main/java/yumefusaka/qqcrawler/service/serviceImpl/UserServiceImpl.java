package yumefusaka.qqcrawler.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yumefusaka.qqcrawler.mapper.UserMapper;
import yumefusaka.qqcrawler.pojo.Entity.User;
import yumefusaka.qqcrawler.service.IUserService;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void mySave(User user) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("qq", user.getQq());
        List<User> users = userMapper.selectList(queryWrapper);
        if (users.isEmpty())
            userMapper.insert(user);
        else {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("qq", user.getQq());
            userMapper.update(user, updateWrapper);
        }
    }
}
