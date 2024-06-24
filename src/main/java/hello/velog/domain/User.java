package hello.velog.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(fetch = FetchType.EAGER)
//    private Blog blog;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "registration_date")
    @CreatedDate
    private LocalDateTime registrationDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}