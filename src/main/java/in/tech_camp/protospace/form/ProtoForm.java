package in.tech_camp.protospace.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProtoForm {

    @NotBlank(message = "プロトタイプ名は必須です")
    private String name;

    @NotBlank(message = "キャッチコピーは必須です")
    private String catchCopy;  // camelCaseでOK。ProtoEntity側とのマッピングはコントローラ等で調整。

    @NotBlank(message = "コンセプトは必須です")
    private String concept;

    private MultipartFile image; // 画像アップロード用
}
