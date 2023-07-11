package by.shop.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_profile")
@Data
public class UserProfile extends AbstractModel {
    @Column(name = "id", nullable = false)
    Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_credentials_id", nullable = false, unique = true)
    UserCredentials userCredentials;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bucket_id", nullable = false, unique = true)
    Bucket bucket;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    Role role;
    @Column(name = "fullName", nullable = false)
    String fullName;
    @Column(name = "address")
    String address;
    @Column(name = "email")
    String email;
    @Column(name = "phone")
    String phone;
}
