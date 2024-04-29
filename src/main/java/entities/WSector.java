package entities;

import javax.persistence.*;

/**
 * Entity class representing a sector within the system.
 * This class is mapped to the "sectors" table in the database and includes details about sector names and the size of shifts.
 */
@Entity
@Table(name = "sectors")
public class WSector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private WUser user;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "morning_shift_size", nullable = false)
    private int morningShiftSize;
    @Column(name = "noon_shift_size", nullable = false)
    private int noonShiftSize;
    @Column(name = "evening_shift_size", nullable = false)
    private int eveningShiftSize;

    /**
     * Default constructor for JPA.
     */
    public WSector() {
        // Default constructor for JPA
    }

    /**
     * Constructs a new WSector with the specified name and shift sizes for morning, noon, and evening shifts.
     *
     * @param name the name of the sector
     * @param morningShiftSize the size of the morning shift
     * @param noonShiftSize the size of the noon shift
     * @param eveningShiftSize the size of the evening shift
     */
    public WSector(String name, int morningShiftSize, int noonShiftSize, int eveningShiftSize) {
        this.name = name;
        this.morningShiftSize = morningShiftSize;
        this.noonShiftSize = noonShiftSize;
        this.eveningShiftSize = eveningShiftSize;
    }

    // Getter and setter methods

    /**
     * Returns the database ID of the sector.
     *
     * @return the ID of the sector
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the database ID of the sector.
     *
     * @param id the new ID of the sector
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user associated with this sector.
     *
     * @return the associated user
     */
    public WUser getUser() {
        return user;
    }

    /**
     * Sets the user associated with this sector.
     *
     * @param user the user to be associated with this sector
     */
    public void setUser(WUser user) {
        this.user = user;
    }

    /**
     * Returns the name of the sector.
     *
     * @return the name of the sector
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the sector.
     *
     * @param name the new name of the sector
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the size of the morning shift.
     *
     * @return the size of the morning shift
     */
    public int getMorningShiftSize() {
        return morningShiftSize;
    }

    /**
     * Sets the size of the morning shift.
     *
     * @param morningShiftSize the new size of the morning shift
     */
    public void setMorningShiftSize(int morningShiftSize) {
        this.morningShiftSize = morningShiftSize;
    }

    /**
     * Returns the size of the noon shift.
     *
     * @return the size of the noon shift
     */
    public int getNoonShiftSize() {
        return noonShiftSize;
    }

    /**
     * Sets the size of the noon shift.
     *
     * @param noonShiftSize the new size of the noon shift
     */
    public void setNoonShiftSize(int noonShiftSize) {
        this.noonShiftSize = noonShiftSize;
    }

    /**
     * Returns the size of the evening shift.
     *
     * @return the size of the evening shift
     */
    public int getEveningShiftSize() {
        return eveningShiftSize;
    }

    /**
     * Sets the size of the evening shift.
     *
     * @param eveningShiftSize the new size of the evening shift
     */
    public void setEveningShiftSize(int eveningShiftSize) {
        this.eveningShiftSize = eveningShiftSize;
    }

    /**
     * Provides a string representation of the sector including its name and shift sizes.
     *
     * @return a string representation of the sector
     */
    @Override
    public String toString() {
        return name + " {" + morningShiftSize + ", " + noonShiftSize + ", " + eveningShiftSize + "}";
    }
}
