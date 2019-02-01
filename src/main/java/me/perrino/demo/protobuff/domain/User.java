package me.perrino.demo.protobuff.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

    private Integer id;
    private String name;

}
