package am.neovision.admin.toolkit;

import am.neovision.admin.toolkit.util.StringUtil;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author hakob.hakobyan created on 8/31/2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StringUtilTest {

    @Test
    @DisplayName("Should generate uuid")
    public void shouldGenerateUUID() {
        //given

        //when
        String actual = StringUtil.generateUUID();

        //then
        assertThat(actual)
        .isNotBlank();
    }

}
