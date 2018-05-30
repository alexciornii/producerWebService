package md.liberopay.producer.repository;

import md.liberopay.producer.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {
    private static final List<User> users = new ArrayList<>();

    @PostConstruct
    public void initData() {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Vasile");
        user1.setLastName("Osoianu");
        user1.setAge(27);
        user1.setAddress("mun.Chisinau");

        users.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Vasile");
        user2.setLastName("Pupkin");
        user2.setAge(35);
        user2.setAddress("mun.Chisinau");

        users.add(user2);

        User user3 = new User();
        user3.setId(3L);
        user3.setFirstName("Vasile");
        user3.setLastName("Vasile");
        user3.setAge(21);
        user3.setAddress("mun.Chisinau");

        users.add(user3);
    }

    public User findUserById(long id) {
        Assert.notNull(id, "The argument <id> is null");

        User result = null;

        for (User user : users) {
            if (id == user.getId()) {
                result = user;
            }
        }

        return result;
    }

    public List<User> getUsers() {
        return users;
    }
}