package project.basketballgamegallery.model;

import javax.persistence.*;
@Entity
@Table(name = "accessrights")
public class AccessRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnumAccessRight name;
    public AccessRight() {
    }
    public AccessRight(EnumAccessRight name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public EnumAccessRight getName() {
        return name;
    }
    public void setName(EnumAccessRight name) {
        this.name = name;
    }
}