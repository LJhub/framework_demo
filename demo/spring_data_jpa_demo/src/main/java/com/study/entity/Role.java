package com.itheima.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Role {
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long roleId;
    @Column
    private String roleName;
    @Column
    private String roleMemo;

    @ManyToMany(mappedBy = "roles")
    //@JoinTable(name="user_role",
    //        joinColumns = {@JoinColumn(name = "roleId",referencedColumnName = "roleId")},
    //        inverseJoinColumns = {@JoinColumn(name="userId",referencedColumnName = "userId")}
    //)
    private Set<User> users = new HashSet<>(0);

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleMemo() {
        return roleMemo;
    }

    public void setRoleMemo(String roleMemo) {
        this.roleMemo = roleMemo;
    }
}
