package com.military.coordination.component;

/**
 * Represents a person's name as a component, consisting of a surname and a
 * first name.
 * <p>
 * Both the surname and first name must be non-null and non-empty.
 * </p>
 *
 * @param surname   the surname of the person; must not be null or empty
 * @param firstName the first name of the person; must not be null or empty
 * @param nickName  the nickname of the person; can be null or empty
 *
 * @throws IllegalArgumentException if either {@code surname} or
 *                                  {@code firstName} is null or empty
 */
public record NameComponent(String surname, String firstName, String nickName) {

    /**
     * Compact constructor with validation.
     *
     * @param surname   the surname (must not be null or empty)
     * @param firstName the first name (must not be null or empty)
     */
    public NameComponent {
        if (surname == null || surname.isEmpty()) {
            throw new IllegalArgumentException("Surname cannot be null or empty");
        }
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
    }
}
