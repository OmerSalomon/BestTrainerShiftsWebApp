package entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a user in the system.
 * This class is mapped to the "users" table in the database and includes details such as the user's username,
 * password, and associated trainers.
 */
@Entity
@Table(name = "users")
public class WUser implements Serializable, Comparable<WUser> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cascade({CascadeType.ALL})
    private List<WTrainer> trainers;

    /**
     * Default constructor which initializes the list of trainers associated with the user.
     */
    public WUser() {
        this.trainers = new ArrayList<>();
    }

    /**
     * Constructs a new WUser with the specified username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     */
    public WUser(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the ID of the user.
     *
     * @return the ID of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the new ID of the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the list of trainers associated with the user.
     *
     * @return the list of trainers
     */
    public List<WTrainer> getTrainers() {
        return trainers;
    }

    /**
     * Adds a trainer to the list of trainers associated with the user.
     *
     * @param trainer the trainer to be added
     */
    public void addTrainer(WTrainer trainer) {
        trainer.setUser(this); // Set the user reference in the trainer object
        this.trainers.add(trainer);
    }

    /**
     * Removes a trainer from the list of trainers associated with the user.
     *
     * @param trainer the trainer to be removed
     */
    public void removeTrainer(WTrainer trainer) {
        this.trainers.remove(trainer);
    }

    /**
     * Compares this user with another user for order. Comparison is based on user ID.
     *
     * @param other another user to be compared
     * @return a negative integer, zero, or a positive integer as this user is less than, equal to,
     *         or greater than the specified user.
     */
    @Override
    public int compareTo(WUser other) {
        return Integer.compare(this.id, other.id);
    }

    /**
     * Generates a hash code for this user based primarily on its ID.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Compares this user to another object for equality.
     *
     * @param obj the object to compare with
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WUser user = (WUser) obj;
        return id == user.getId();
    }

    /**
     * Returns a string representation of the user including their ID, username, and password.
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}
