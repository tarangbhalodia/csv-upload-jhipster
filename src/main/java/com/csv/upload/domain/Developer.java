package com.csv.upload.domain;



import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Developer.
 */
@Entity
@Table(name = "developer")
public class Developer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1)
    @Pattern(regexp = "[a-zA-Z]{2,}")
    @Column(name = "first_name", nullable = false)
    @CsvBindByPosition(position = 0)
    private String firstName;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]{2,}")
    @Column(name = "last_name", nullable = false)
    @CsvBindByPosition(position = 1)
    private String lastName;

    @Column(name = "top_skill")
    @CsvBindByPosition(position = 2)
    private String topSkill;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Developer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Developer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTopSkill() {
        return topSkill;
    }

    public Developer topSkill(String topSkill) {
        this.topSkill = topSkill;
        return this;
    }

    public void setTopSkill(String topSkill) {
        this.topSkill = topSkill;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Developer developer = (Developer) o;
        if (developer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), developer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Developer{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", topSkill='" + getTopSkill() + "'" +
            "}";
    }
}
