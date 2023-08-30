package org.example.conf;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserSettings {
    private String userName;
    private String passWord;

}