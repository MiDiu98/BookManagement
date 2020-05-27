package com.ungmydieu.BookManagement;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookManagementApplicationTests {

	@Value("${spring.datasource.url}")
	private String dataSourceUrl;

	@Test
	void contextLoads() {
	}

	@Test
	public void test_dataSourceUrl() {
		Assert.assertEquals(dataSourceUrl, "jdbc:mysql://localhost:3306/book_management_test");
	}

}
