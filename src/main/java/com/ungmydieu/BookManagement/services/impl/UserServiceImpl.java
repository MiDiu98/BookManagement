package com.ungmydieu.bookmanagement.services.impl;

import com.ungmydieu.bookmanagement.constants.RoleConstants;
import com.ungmydieu.bookmanagement.constants.SortOrderConstant;
import com.ungmydieu.bookmanagement.converters.users.UserDaoToUserDtoConverter;
import com.ungmydieu.bookmanagement.converters.users.UserDtoToUserDaoConverter;
import com.ungmydieu.bookmanagement.exceptions.BadRequestException;
import com.ungmydieu.bookmanagement.exceptions.NotFoundException;
import com.ungmydieu.bookmanagement.models.dao.Role;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.models.dto.UserPage;
import com.ungmydieu.bookmanagement.repositories.RoleRepository;
import com.ungmydieu.bookmanagement.repositories.UserRepository;
import com.ungmydieu.bookmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDaoToUserDtoConverter toUserDtoConverter;

    @Autowired
    private UserDtoToUserDaoConverter toUserDaoConverter;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserPage getAllUsers(Integer pageNo, Integer pageSize, String sortBy, String order) {
        Pageable paging = PageRequest.of(pageNo, pageSize, order.equals(SortOrderConstant.DESC)? Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        Page<User> pagedResult = userRepository.findAll(paging);
        UserPage userPage = new UserPage();

        if(pagedResult.hasContent()) {
            userPage.setUsersDto(toUserDtoConverter.convert(pagedResult.getContent()));
            userPage.setCurrentPage(pagedResult.getNumber());
            userPage.setTotalPages(pagedResult.getTotalPages());
        }
        return userPage;
    }

    @Override
    public UserPage getUserByAdmin(boolean enabled, Integer pageNo, Integer pageSize, String sortBy, String order) {
        Pageable paging = PageRequest.of(pageNo, pageSize, order.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        Page<User> pagedResult = userRepository.findAllByEnabled(enabled, paging);
        UserPage userPage = new UserPage();

        if(pagedResult.hasContent()) {
            userPage.setUsersDto(toUserDtoConverter.convert(pagedResult.getContent()));
            userPage.setCurrentPage(pagedResult.getNumber());
            userPage.setTotalPages(pagedResult.getTotalPages());
        }
        return userPage;
    }

    @Override
    public User getUserById(int id) {
        verifyUserIdExist(id);
        return userRepository.findById(id).get();
    }

    @Override
    public User update(Principal principal, int id, UserDTO userDTO) {
        verifyUserIdExist(id);
        verifyAuthor(principal, id);

        User user = toUserDaoConverter.convert(userDTO);

        return userRepository.save(user);
    }

    @Override
    public User updateByAdmin(int id, UserDTO userDTO) {
        verifyUserIdExist(id);

        User user = toUserDaoConverter.convert(userDTO);

        user.setEnabled(userDTO.isEnable());
        if (userDTO.getRoles().length > 0) {
            user.setRoles(new HashSet<>());
            for (String i : userDTO.getRoles()){
                Role role = roleRepository.findByName(i);
                if (role != null) user.getRoles().add(role);
                else throw new BadRequestException("Invalid role " + i);
            }
        }

        return userRepository.save(user);
    }

    @Override
    public void delete(String role, Principal principal, int id) {
        verifyUserIdExist(id);
        if (role.equals(RoleConstants.USER)) verifyAuthor(principal, id);

        userRepository.deleteById(id);
    }

    private void verifyUserIdExist(int id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(String.format("User id %d is not found", id));
        }
    }

    private void verifyAuthor(Principal principal, int id) {
        User currentUser = userRepository.findByEmail(principal.getName());
        if (!currentUser.equals(userRepository.findById(id).get())) {
            throw new BadRequestException(String.format("User id %d can not update user info", id));
        }
    }
}
