package com.e_commerce.e_commerce.entity;

import com.e_commerce.e_commerce.configuration.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "addresses")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address extends BaseEntity {

    @Column(nullable = false)
    String recipientName;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String street;

    @Column(nullable = false)
    String ward;

    @Column(nullable = false)
    String district;

    @Column(nullable = false)
    String city;

    @Column
    String note;

    @Column(nullable = false)
    boolean isDefault = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
