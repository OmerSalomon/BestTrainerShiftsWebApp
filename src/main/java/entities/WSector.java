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

    @Column(name = "morning_shift_size", nullable = false)

    private int morningShiftSize;
    @Column(name = "noon_shift_size", nullable = false)

    private int noonShiftSize;
    @Column(name = "evening_shift_size", nullable = false)

    private int eveningShiftSize;


    public WSector() {
        // Default constructor
    }

    public WSector(String name, int morningShiftSize, int noonShiftSize, int eveningShiftSize) {
        this.name = name;
        this.morningShiftSize = morningShiftSize;
        this.noonShiftSize = noonShiftSize;
        this.eveningShiftSize = eveningShiftSize;
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

    public int getMorningShiftSize() {
        return morningShiftSize;
    }

    public int getNoonShiftSize() {
        return noonShiftSize;
    }

    public int getEveningShiftSize() {
        return eveningShiftSize;
    }

    public void setMorningShiftSize(int morningShiftSize) {
        this.morningShiftSize = morningShiftSize;
    }

    public void setNoonShiftSize(int noonShiftSize) {
        this.noonShiftSize = noonShiftSize;
    }

    public void setEveningShiftSize(int eveningShiftSize) {
        this.eveningShiftSize = eveningShiftSize;
    }
}
