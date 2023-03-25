package com.github.psinalberth.user.infrastructure;

import com.github.psinalberth.common.application.Events;
import com.github.psinalberth.user.adapter.crypto.JasyptPasswordEncryptor;
import com.github.psinalberth.user.application.port.in.UserMapper;
import com.github.psinalberth.user.application.port.in.UserMapperImpl;
import com.github.psinalberth.user.application.port.out.PasswordEncryptorGateway;
import com.github.psinalberth.user.application.port.out.LoadUserPort;
import com.github.psinalberth.user.application.port.out.SaveUserPort;
import com.github.psinalberth.user.application.repository.UserDatabaseRepository;
import com.github.psinalberth.user.application.repository.UserRepository;
import com.github.psinalberth.user.application.service.BeFriendsUserService;
import com.github.psinalberth.user.application.service.FriendshipMaker;
import com.github.psinalberth.user.application.service.GetFriendsUserService;
import com.github.psinalberth.user.application.service.RegisterUserService;
import com.github.psinalberth.user.application.service.friendship.LoadFriendsPort;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.WithAnnotations;

public class UserConfig {

    @Produces
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Produces
    public UserRepository userRepository(UserDatabaseRepository repository) {
        return new UserRepository(repository);
    }

    @Produces
    public PasswordEncryptorGateway encryptPasswordPort() {
        return new JasyptPasswordEncryptor(new StrongPasswordEncryptor());
    }

    @Produces
    public GetFriendsUserService getFriendsUserService(LoadUserPort loadUserPort, LoadFriendsPort loadFriendsPort) {
        return new GetFriendsUserService(loadUserPort, loadFriendsPort);
    }

    @Produces
    public RegisterUserService registerUserService(LoadUserPort loadUserPort, SaveUserPort saveUserPort,
                                                   UserMapper masss, Events events,
                                                   PasswordEncryptorGateway passwordEncryptorGateway) {
        return new RegisterUserService(loadUserPort, saveUserPort, masss, passwordEncryptorGateway, events);
    }

    @Produces
    public BeFriendsUserService beFriendsUserService(LoadUserPort loadUserPort, FriendshipMaker friendshipMaker) {
        return new BeFriendsUserService(loadUserPort, friendshipMaker);
    }
}
