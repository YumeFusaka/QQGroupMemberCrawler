package yumefusaka.qqcrawler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yumefusaka.qqcrawler.pojo.Entity.User;

public interface IUserService extends IService<User> {
    void mySave(User user);
}
