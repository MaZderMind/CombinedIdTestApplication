package net.seibertmedia.rocket.combinedidtest.model;

import static net.seibertmedia.rocket.combinedidtest.util.ModelUtil.joinAndShortenList;
import static net.seibertmedia.rocket.combinedidtest.util.ModelUtil.shorten;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.seibertmedia.rocket.combinedidtest.util.EqualsById;

@Entity
@Table(name = "users")
@DiscriminatorColumn(name = "type")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners({ AuditingEntityListener.class })
public class User implements Serializable, UserDetails, EqualsById {

	private static final long serialVersionUID = 162224965560968692L;

	@Id
	@Column(name = "id")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	private UUID id;

	// TODO darüber nachdenken von username als ID weg zu gehen um in Zukunft flexibler zu sein
	@NotNull
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@NotNull
	@Column(name = "password")
	private String hashedPassword;

	@NotNull
	@Column(name = "enabled", columnDefinition = "BOOLEAN NOT NULL DEFAULT true")
	private Boolean enabled = true;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	private List<UserAuthority> authorities = new ArrayList<>();

	// TODO DB migration, neu für alle user
	@CreatedDate
	@Column(name = "created", updatable = false)
	private LocalDateTime created;

	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@LastModifiedDate
	@Column(name = "modified")
	private LocalDateTime modified;

	@LastModifiedBy
	@Column(name = "modified_by")
	private String modifiedBy;

	public User() {

	}

	public User(String username, String hashedPassword, Boolean enabled) {
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.enabled = enabled;
	}

	public User(String username, String hashedPassword) {
		this(username, hashedPassword, true);
	}

	public User(UUID id, String username, String hashedPassword, boolean enabled) {
		this(username, hashedPassword, enabled);
		this.id = id;
	}

	public String getType() {
		return "User";
	}

	public UUID getId() {
		return id;
	}

	public User addAuthority(String authority) {
		return addAuthority(new UserAuthority().setAuthority(authority));
	}

	public User addAuthority(UserAuthority authority) {
		authorities.add(authority.setUser(this));
		return this;
	}

	@Override
	public Collection<UserAuthority> getAuthorities() {
		return authorities;
	}

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	@JsonIgnore
	public String getHashedPassword() {
		return hashedPassword;
	}

	public User setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
		return this;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return getHashedPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		// Funktion derzeit nicht verwendet – Achtung, umgekehrte Logik
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// Funktion derzeit nicht verwendet – Achtung, umgekehrte Logik
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// Funktion derzeit nicht verwendet – Achtung, umgekehrte Logik
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public User setEnabled(Boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public boolean equalsById(Object obj) {
		return obj != null &&
				obj instanceof User &&
				Objects.equals(username, ((User) obj).username);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("username", shorten(username))
				.append("enabled", enabled)
				.append("authorities", joinAndShortenList(authorities))
				.toString();
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
