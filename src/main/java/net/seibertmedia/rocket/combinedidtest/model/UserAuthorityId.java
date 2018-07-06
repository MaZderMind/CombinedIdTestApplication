package net.seibertmedia.rocket.combinedidtest.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("WeakerAccess")
public class UserAuthorityId implements Serializable {

	private static final long serialVersionUID = -8162365460220637911L;

	User user;

	String authority;

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
