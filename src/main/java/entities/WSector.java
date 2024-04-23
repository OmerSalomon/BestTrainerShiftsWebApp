package entities;

import javax.persistence.*;

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

    @Column(name = "required_trainers", nullable = false)
    private int requiredNumberOfTrainers;

    public WSector() {
        // Default constructor
    }

    public WSector(String name, int requiredNumberOfTrainers, boolean isManager) {
        this.name = name;
        this.requiredNumberOfTrainers = requiredNumberOfTrainers;
    }

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

    public int getRequiredNumberOfTrainers() {
        return requiredNumberOfTrainers;
    }

    public void setRequiredNumberOfTrainers(int requiredNumberOfTrainers) {
        this.requiredNumberOfTrainers = requiredNumberOfTrainers;
    }



}
