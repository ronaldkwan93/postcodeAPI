package com.postcodeapi.postcodeapi.Suburb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.postcodeapi.postcodeapi.Postcode.Postcode;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "suburbs")
public class Suburb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String suburb;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "assignedSuburbs")
    private Set<Postcode> postcodeSet = new HashSet<>();

    public Set<Postcode> getPostcodeSet() {
        return postcodeSet;
    }

    public void setPostcodeSet(Set<Postcode> postcodeSet) {
        this.postcodeSet = postcodeSet;
    }
}
