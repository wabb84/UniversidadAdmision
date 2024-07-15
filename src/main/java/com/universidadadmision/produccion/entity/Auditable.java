package com.universidadadmision.produccion.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public abstract class Auditable<U> {
	
	@Column(name="created_by")
	protected String created_by;

	@Column(name="created_at")
	protected LocalDateTime created_at;
	
	@Column(name="modified_by")
	protected String modified_by;

	@Column(name="modified_at")
	protected LocalDateTime modified_at;
	
	@PrePersist
	public void prePersist() throws Exception {
		created_at = LocalDateTime.now();
        created_by = "ADMIN";
    }
	
	@PreUpdate
	public void preUpdate() throws Exception {
		modified_at = LocalDateTime.now();
		modified_by = "ADMIN";
	}
}