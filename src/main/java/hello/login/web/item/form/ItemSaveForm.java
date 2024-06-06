package hello.login.web.item.form;

import lombok.Data;


@Data
public class ItemSaveForm {

    private String itemName;

    private Integer price;

    private Integer quantity;
}