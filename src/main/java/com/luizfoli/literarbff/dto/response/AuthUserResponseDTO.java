package com.luizfoli.literarbff.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthUserResponseDTO extends ResponseDTO {

    private Long id;
    private String name;
    private String email;
    @JsonProperty("last_name") private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
