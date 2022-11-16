package com.example.bookstoreapp.dao;

import com.example.bookstoreapp.ds.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesDao extends JpaRepository<Roles,Integer> {
    Optional<Roles> findRolesByRoleName(String roleName);
}
