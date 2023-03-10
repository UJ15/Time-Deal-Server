package com.uj15.timedeal.util;

import com.uj15.timedeal.user.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@MockBeans({
        @MockBean(JpaMetamodelMappingContext.class),
        @MockBean(JdbcOperations.class),
        @MockBean(UserService.class)
})
@ExtendWith(SpringExtension.class)
public class ControllerSetUp {
}
