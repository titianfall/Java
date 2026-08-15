package lang.Object.equals;

import java.util.Objects;

public class UserV2 {
    private String id;

    public UserV2(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
//        if(this == object) return true; // 자기 자신을 비교하는 경우가 거의 없기때문에 생략된다.
        if (object == null || getClass() != object.getClass()) return false;
        UserV2 user = (UserV2) object;
        return Objects.equals(id, user.id);
        // return id.equals(user.id);
    }
}
