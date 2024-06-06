package hello.login.web.item.form;

import lombok.Data;


@Data
public class ItemUpdateForm {

    private Long id;

    private String itemName;

    private Integer price;

    //수정에서는 수량은 자유롭게 변경할 수 있다.
    private Integer quantity;

}