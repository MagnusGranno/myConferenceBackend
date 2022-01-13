package DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OwnerDTO {

    private long id;
    private String name;
    private String address;
    private long phone;
}
