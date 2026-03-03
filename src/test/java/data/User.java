package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;
    private String password;
    private String name;

    public static User random() {
        String timestamp = System.currentTimeMillis() + "";
        return new User(
                "user" + timestamp + "@yandex.ru",
                "password123",
                "Test name"
        );
    }
}
