package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll() {
        return UserMapper.toUserDto(userRepository.findAll());
    }

    @Override
    public UserDto findById(long id) {
        return UserMapper.toUserDto(userRepository.findById(id));
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = userRepository.update(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);
    }
}
