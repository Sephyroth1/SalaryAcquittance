package dev.sudhamshu.salaryacquittance.DTO;

import dev.sudhamshu.salaryacquittance.Utils.UsersEnum;
import lombok.Data;

@Data
public class RegisterEntity {
    private String name;
    private String username;
    private String email;
    private String password;
    private UsersEnum role;
}
