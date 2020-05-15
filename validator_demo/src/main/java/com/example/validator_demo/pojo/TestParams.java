package com.example.validator_demo.pojo;

import com.example.validator_demo.annotation.IdentityCardNumber;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
public class TestParams {
    @NotBlank(message = "姓名不能为空")
    private String userName;
    @NotBlank(message = "手机号不能为空")
    private String userPhone;
    @Range(min = 18, max = 200, message = "本系统只适合成年人使用；不适合未成年及鬼神使用")
    private Integer age;
    @Max(value = 3)
    @Min(value = 0)
    @Digits(integer = 1, fraction = 2, message = "身高范围超过人类最大限制")
    private float height;
    @Email(message = "邮箱不合法")
    private String email;
    @NotNull(message = "集合不能为空")
    @Size(min = 1, max = 10, message = "集合大小超过限制")
    private List<String> infoList;
    @AssertFalse(message = "不能是学生")
    private Boolean isStudent;
    @AssertTrue(message = "只能是女孩")
    @NotNull(message = "对不起！男孩不能使用")
    private Boolean isGril;
    @Past(message = "生日不能是未来时间")
    private Date birthday;
    @Future(message = "预约时间只能是未来")
    @NotNull
    private Date subscribeTime;
    @NotNull(message = "身份证不能为空")
    @IdentityCardNumber
    private Long id;
}
