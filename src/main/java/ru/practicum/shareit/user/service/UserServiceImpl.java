package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Set<String> emails = new HashSet<>();


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
        if (emails.contains(userDto.getEmail())) {
            throw new IllegalArgumentException("User with email = " + userDto.getEmail() + " is exists");
        }
        User user = userRepository.save(UserMapper.toUser(userDto));
        emails.add(user.getEmail());

        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto update(long id, UserDto userDto) {
        User user = userRepository.findById(id);
        User newUser = UserMapper.toUser(userDto);
        if (newUser.getEmail() != null) {
            if (emails.contains(newUser.getEmail()) && !newUser.getEmail().equals(user.getEmail())) {
                throw new IllegalArgumentException("User with email = " + user.getEmail() + " is exists");
            } else {
                emails.remove(user.getEmail());
                user.setEmail(newUser.getEmail());
                emails.add(newUser.getEmail());
            }
        }
        if (newUser.getName() != null) {
            user.setName(newUser.getName());
        }
        if (user.getLogin() != null) {
            user.setLogin(newUser.getLogin());
        }
        return UserMapper.toUserDto(userRepository.update(id, user));
    }

    @Override
    public void delete(long id) {
        emails.remove(userRepository.findById(id).getEmail());
        userRepository.delete(id);
    }
}