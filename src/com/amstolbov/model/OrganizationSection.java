package com.amstolbov.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersonUID = 1L;

    private final Set<Organization> organizations = new HashSet<>();

    public void addOrganization(Organization org) {
        this.organizations.add(org);
    }

    public Set<Organization> getOrganization() {
        return organizations;
    }

    @Override
    public String toString() {
        return organizations.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }
}
