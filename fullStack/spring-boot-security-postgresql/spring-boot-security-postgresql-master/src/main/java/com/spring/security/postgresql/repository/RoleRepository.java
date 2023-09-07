package com.spring.security.postgresql.repository;

import java.util.List;
import java.util.Optional;

import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.security.postgresql.models.ERole;
import com.spring.security.postgresql.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
  @Query("select r from Role as r where r.name not in :nameList")
  List<Role> getRoleNotAdmin(@PathParam("nameList")List <ERole> nameList);

}
