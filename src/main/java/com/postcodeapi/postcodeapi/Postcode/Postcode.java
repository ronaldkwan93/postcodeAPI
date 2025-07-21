package com.postcodeapi.postcodeapi.Postcode;

import com.postcodeapi.postcodeapi.Suburb.Suburb;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "postcodes")
public class Postcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;

    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToMany
    @JoinTable(
            name = "suburb_postcode",
            joinColumns = @JoinColumn(name = "postcode_id"),
            inverseJoinColumns = @JoinColumn(name = "suburb_id")
    )
    private Set<Suburb> assignedSuburbs = new HashSet<>();

    public Set<Suburb> getAssignedSuburbs() {
        return assignedSuburbs;
    }

    public void setAssignedSuburbs(Set<Suburb> assignedSuburbs) {
        this.assignedSuburbs = assignedSuburbs;
    }
}
