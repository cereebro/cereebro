package io.cereebro.core;

/**
 * Thrown when a System is unknown.
 * 
 * @author michaeltecourt
 */
public class UnknownSystemException extends RuntimeException {

    private static final long serialVersionUID = 3045000013370000360L;

    private final String name;

    /**
     * Thrown when a System is unknown.
     * 
     * @param name
     *            System name
     */
    public UnknownSystemException(String name) {
        super("Unknown system : " + name);
        this.name = name;
    }

    /**
     * System name.
     * 
     * @return System name
     */
    public String getName() {
        return name;
    }

}
