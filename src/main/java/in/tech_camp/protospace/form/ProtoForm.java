package in.tech_camp.protospace.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProtoForm {
    @NotBlank(message = "プロトタイプ名は必須です")
    private String name;        // プロトタイプの名前
    
    @NotBlank(message = "キャッチコピーは必須です")
    private String catchcopy;   // キャッチコピー

    @NotBlank(message="コンセプトは必須です")
    private String concept;     // コンセプト

    @NotBlank(message="画像は1枚必須です")
    private String image;       // 画像のファイル名 or URL
}
