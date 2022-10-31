package corespringsecurity.corespringsecurity.domain;

public enum RoleType {

    ADMIN("ADMIN"),
    USER("USER"),
    MANAGER("MANAGER");

    private String role;

    RoleType(String role) {
        this.role = role;
    }
}
