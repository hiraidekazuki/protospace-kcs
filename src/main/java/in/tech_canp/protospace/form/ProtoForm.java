package in.tech_canp.protospace.form;

import lombok.Data;

@Data
public class ProtoForm {
    private String name;        // プロトタイプの名前
    private String catchcopy;   // キャッチコピー
    private String concept;     // コンセプト
    private String image;       // 画像のファイル名 or URL
}