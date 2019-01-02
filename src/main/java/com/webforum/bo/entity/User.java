package com.webforum.bo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sebastian Löfstrand (selo@kth.se), Jonas Lundvall (jonlundv@kth.se)
 *
 * Represents Users in the community
 */
@Entity
@Table(name = "user")
@JsonIgnoreProperties("inspection")
public class User implements Serializable {
    // Constants ----------------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", updatable=false)
    private Long id;

    @Column(name="user_username")
    private String username;

    @Column(name="user_password")
    @JsonIgnore
    private String password;

    @Column(name="user_email")
    private String email;

    @Column(name="user_role")
    @JsonIgnore
    private Role role;

    @Column(name="user_blocked", nullable = false, columnDefinition = "boolean default false")
    @JsonIgnore
    private Boolean blocked;

    @OneToMany(mappedBy="owner")
    @JsonIgnore
    private Set<BlogPost> blogPosts = new HashSet<BlogPost>();

    // Getters/setters ----------------------------------------------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id= id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Boolean isBlocked() { return blocked; }
    public void setBlocked(Boolean blocked) { this.blocked = blocked; }

    public Set<BlogPost> getBlogPosts() { return blogPosts; }
    public void setBlogPosts(Set<BlogPost> blogPosts) { this.blogPosts = blogPosts; }


    // Constructors -------------------------------------------------------------------------------
    public User() { }

    public User(Long id) {
        this.id = id;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.blocked = false;
        this.role = Role.MEMBER;
    }

    public User(String username, String password, String email, Role role) {
        this(username, password, email);
        this.role = role;
    }

    // Actions ------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", blocked=" + blocked +
                '}';
    }
}