/**
 * 
 */
package com.hp.core.jsptags.ognl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Map;

import ognl.MemberAccess;

/**
 * @author huangping Jul 23, 2019
 */
public class DefaultMemberAccess implements MemberAccess {

	public boolean allowPrivateAccess = false;
	public boolean allowProtectedAccess = false;
	public boolean allowPackageProtectedAccess = false;

	/**
	 * 
	 * @param allowAllAccess
	 */
	public DefaultMemberAccess(boolean allowAllAccess) {
		this(allowAllAccess, allowAllAccess, allowAllAccess);
	}

	public DefaultMemberAccess(boolean allowPrivateAccess, boolean allowProtectedAccess, boolean allowPackageProtectedAccess) {
		super();
		this.allowPrivateAccess = allowPrivateAccess;
		this.allowProtectedAccess = allowProtectedAccess;
		this.allowPackageProtectedAccess = allowPackageProtectedAccess;
	}

	public boolean getAllowPrivateAccess() {
		return allowPrivateAccess;
	}

	public void setAllowPrivateAccess(boolean value) {
		allowPrivateAccess = value;
	}

	public boolean getAllowProtectedAccess() {
		return allowProtectedAccess;
	}

	public void setAllowProtectedAccess(boolean value) {
		allowProtectedAccess = value;
	}

	public boolean getAllowPackageProtectedAccess() {
		return allowPackageProtectedAccess;
	}

	public void setAllowPackageProtectedAccess(boolean value) {
		allowPackageProtectedAccess = value;
	}

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object setup(Map context, Object target, Member member, String propertyName) {
		Object result = null;

		if (isAccessible(context, target, member, propertyName)) {
			AccessibleObject accessible = (AccessibleObject) member;

			if (!accessible.isAccessible()) {
				result = Boolean.TRUE;
				accessible.setAccessible(true);
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void restore(Map context, Object target, Member member, String propertyName, Object state) {
		if (state != null) {
			((AccessibleObject) member).setAccessible(((Boolean) state).booleanValue());
		}
	}

	/**
	 * Returns true if the given member is accessible or can be made accessible by
	 * this object.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean isAccessible(Map context, Object target, Member member, String propertyName) {
		int modifiers = member.getModifiers();
		boolean result = Modifier.isPublic(modifiers);

		if (!result) {
			if (Modifier.isPrivate(modifiers)) {
				result = getAllowPrivateAccess();
			} else {
				if (Modifier.isProtected(modifiers)) {
					result = getAllowProtectedAccess();
				} else {
					result = getAllowPackageProtectedAccess();
				}
			}
		}
		return result;
	}

}
