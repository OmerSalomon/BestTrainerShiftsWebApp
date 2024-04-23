package entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public WUser() {
        this.trainers = new ArrayList<>();
    }

    public WUser(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<WTrainer> getTrainers() {
        return trainers;
    }

    public void addTrainer(WTrainer trainer) {
        trainer.setUser(this); // Assuming Trainer has a setUser method to establish the relationship
        this.trainers.add(trainer);
    }

    public void removeTrainer(WTrainer trainer) {
        this.trainers.remove(trainer);
    }

    // equals, hashCode, toString, compareTo

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WUser user = (WUser) obj;
        return id == user.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int compareTo(WUser other) {
        return Integer.compare(this.id, other.id);
    }
}
