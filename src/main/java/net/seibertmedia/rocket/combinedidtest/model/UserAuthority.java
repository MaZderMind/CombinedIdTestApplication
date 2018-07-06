package net.seibertmedia.rocket.combinedidtest.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "user_authorities")
@IdClass(UserAuthorityId.class)
public class UserAuthority implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = -4523560968676777346L;

	/* Es wäre praktischer hier den "User" direkt zu mappen, allerdings ist es derzeit nicht
	 * möglich bei JPA, bei einem kombinierten Primary Key andere Entities zu verwenden.
	 */
	@Id
	@NotNull
	@ManyToOne
	private User user;

	@Id
	@NotNull
	private String authority;

	public User getUser() {
		return user;
	}

	public UserAuthority setUser(User user) {
		this.user = user;
		return this;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public UserAuthority setAuthority(String authority) {
		this.authority = authority;
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("user", user)
				.append("authority", authority)
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
