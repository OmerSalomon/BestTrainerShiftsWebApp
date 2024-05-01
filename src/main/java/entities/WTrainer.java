package entities;

import javax.persistence.*;

/**
 * Entity class representing a trainer.
 * This class is mapped to the "trainers" table in the database and includes details about trainers such as their name,
 * managerial status, and availability.
 */
@Entity
@Table(name = "trainers")
public class WTrainer {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private WUser user;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "is_manager", nullable = false)
    private boolean isManager;

    @Column(name = "availability", length = 21, nullable = false)
    private String availabilityString;

    /**
     * Default constructor for JPA.
     */
    public WTrainer() {
    }

    /**
     * Gets the ID of the trainer.
     *
     * @return the ID of the trainer
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the trainer.
     *
     * @param id the new ID of the trainer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user associated with this trainer.
     *
     * @return the associated user
     */
    public WUser getUser() {
        return user;
    }

    /**
     * Sets the user associated with this trainer.
     *
     * @param user the user to be associated with this trainer
     */
    public void setUser(WUser user) {
        this.user = user;
    }

    /**
     * Returns the name of the trainer.
     *
     * @return the name of the trainer
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the trainer.
     *
     * @param name the new name of the trainer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the trainer is a manager.
     *
     * @return true if the trainer is a manager, otherwise false
     */
    public boolean getIsManager() {
        return isManager;
    }

    /**
     * Sets the managerial status of the trainer.
     *
     * @param isManager the managerial status to set
     */
    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    /**
     * Returns the availability of the trainer as a string.
     * This is typically used to store availability in a compact form, such as a sequence of bits or characters.
     *
     * @return the availability of the trainer as a string
     */
    public String getAvailabilityString() {
        return availabilityString;
    }

    /**
     * Parses the availability string into a boolean array.
     * '1' in the string indicates availability (true), and '0' indicates non-availability (false).
     *
     * @return a boolean array representing the availability
     */
    public boolean[] getAvailabilityArray() {
        boolean[] availabilityArray = new boolean[availabilityString.length()];
        for (int i = 0; i < availabilityString.length(); i++) {
            availabilityArray[i] = availabilityString.charAt(i) == '1';
        }
        return availabilityArray;
    }


    /**
     * Sets the availability of the trainer.
     *
     * @param availabilityString the string representing the trainer's availability
     */
    public void setAvailabilityString(String availabilityString) {
        this.availabilityString = availabilityString;
    }

    /**
     * Provides a hash code for the trainer based primarily on its ID.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Compares this trainer to another object for equality.
     *
     * @param obj the object to compare with
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WTrainer trainer = (WTrainer) obj;
        return id == trainer.id;
    }

    /**
     * Returns a string representation of the trainer including its ID, associated user, name, and managerial status.
     *
     * @return a string representation of the trainer
     */
    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", user=" + (user != null ? user.getUsername() : "null") +
                ", name='" + name + '\'' +
                ", isManager=" + isManager +
                '}';
    }
}
