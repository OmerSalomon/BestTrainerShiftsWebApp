package entities;

import javax.persistence.*;

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

    public WTrainer() {
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WUser getUser() {
        return user;
    }

    public void setUser(WUser user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsManager() {
        return isManager;
    }
    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }


    public String getAvailabilityString() {
        return availabilityString;
    }

    public void setAvailabilityString(String availabilityString) {
        this.availabilityString = availabilityString;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WTrainer trainer = (WTrainer) obj;
        return id == trainer.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", user=" + (user != null ? user.getUsername() : "null") +
                ", name=" + name + '\'' +
                ", isManager=" + isManager +
                '}';
    }
}
