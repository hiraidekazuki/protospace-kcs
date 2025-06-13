package in.tech_camp.protospace.controller;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import in.tech_camp.protospace.component.ImageUrl;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.entity.UserEntity;
import in.tech_camp.protospace.repository.ProtoRepository;


@WebMvcTest(controllers = ProtoController.class,
            excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class ProtoControllerIndexUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProtoRepository protoRepository;

    @MockBean
    private ImageUrl imageUrl;

    // 誰でも閲覧できることを確認
    @Test
    public void testShowIndex_isPublic() throws Exception {
        Mockito.when(protoRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("protos/index"))
            .andExpect(model().attributeExists("prototypes"));
    }

    // プロトタイプが空のときの表示確認
    @Test
    public void testShowIndex_emptyList() throws Exception {
        Mockito.when(protoRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("protos/index"))
            .andExpect(model().attribute("prototypes", hasSize(0)));
    }

    // プロトタイプが1件ある場合、詳細が表示されること
    @Test
    public void testShowIndex_withPrototypes_shouldShowDetails() throws Exception {
        // モックユーザーを作成
        UserEntity user = new UserEntity();
        user.setName("投稿者太郎");

        // モックプロトタイプを作成
        ProtoEntity proto = new ProtoEntity();
        proto.setId(1);
        proto.setName("テストプロトタイプ");
        proto.setCatchcopy("キャッチコピー");
        proto.setImage("test.png");
        proto.setUser(user);

        Mockito.when(protoRepository.findAll()).thenReturn(List.of(proto));

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("protos/index"))
            .andExpect(model().attribute("prototypes", hasSize(1)))
            .andExpect(model().attribute("prototypes", hasItem(
                allOf(
                    hasProperty("name", is("テストプロトタイプ")),
                    hasProperty("catchcopy", is("キャッチコピー")),
                    hasProperty("image", is("test.png")),
                    hasProperty("user", hasProperty("name", is("投稿者太郎")))
                )
            )));
    }
}