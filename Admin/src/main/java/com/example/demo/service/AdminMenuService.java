package com.example.demo.service;

import com.example.demo.model.model.front.AdminMenu;
import com.example.demo.model.model.front.AdminMenu;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminMenuService {

    public List<AdminMenu> getAdminMenu(){

        return Arrays.asList(
                AdminMenu.builder().title("고객 관리").url("/pages/user").code("user").build()
        );

    }

}