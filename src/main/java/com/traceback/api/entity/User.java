package com.traceback.api.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	
	@Column(name = "password_hash")
    private String passwordHash;
	
	@Column(name = "auth_provider")
    private String authProvider;
	
	@CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
	
////	@JsonManagedReference
//	@JsonIgnore
//	@OneToMany(mappedBy = "finder", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Item> reportedItems;
//
////	@JsonManagedReference
//	@JsonIgnore
//	@OneToMany(mappedBy = "loser", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Claim> myClaims;
	
	@Override
    public String toString() {
        return "User(id=" + id + ", name=" + name + ", email=" + email + ")";
    }
	
}
