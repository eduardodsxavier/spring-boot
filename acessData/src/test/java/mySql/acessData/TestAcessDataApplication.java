package mySql.acessData;

import org.springframework.boot.SpringApplication;

public class TestAcessDataApplication {

	public static void main(String[] args) {
		SpringApplication.from(AcessDataApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
