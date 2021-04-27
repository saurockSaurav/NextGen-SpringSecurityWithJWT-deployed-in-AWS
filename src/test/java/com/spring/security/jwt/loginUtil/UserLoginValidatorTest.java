package com.spring.security.jwt.loginUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.security.jwt.config.YmlConfig;
import com.spring.security.jwt.util.UserLoginValidator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserLoginValidator.class, YmlConfig.class })
public class UserLoginValidatorTest {
	
	@Mock
	private YmlConfig		ymlConfig;
	
	@Autowired
	private UserLoginValidator userLoginValidator;
	
	@Before
	public void stetUp() {
		when(ymlConfig.getPropFilePath()).thenReturn("src/test/resources/UsersLog.properties");
	}

	@Test
	public void testWhen_UserLogin_All_UseCases() throws IOException {
		assertThat(userLoginValidator.login("Username1", "Password1")).isFalse();
		assertThat(userLoginValidator.login("Username2", "Password1")).isFalse();
	}
}
