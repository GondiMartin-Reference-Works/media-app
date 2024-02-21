package com.example.mediaApp;

import com.example.mediaApp.model.entity.Address;
import com.example.mediaApp.model.entity.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.stream.Stream;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MediaAppApplicationTests {

	private AppUser user;
	private Address home;

	@BeforeEach
	void contextLoads() {
		home = new Address();
		home.setCountry("Hungary");
		home.setCity("Budapest");
		home.setStreet("Ördögmalom");
	}

	@Test
	void testUserSuccess(){
		// arrange
		user = new AppUser();

		// act
		user.setFirstName("Vilmos");
		user.setLastName("Rideg");

		// assert
		assertEquals("AppUser(Vilmos, Rideg)", user.toString());
	}

	@Test
	void testAddingAddressSuccess(){
		// arrange
		user = new AppUser();
		user.setAddressList(new ArrayList<Address>());

		// act
		user.addAddress(home);

		// assert
		Address address = user.getAddressList().stream()
				.filter(addr -> addr.getUser().equals(user))
				.findAny()
				.orElse(null);
		assertEquals("Address(Hungary, Budapest, Ördögmalom)", address.toString());
	}

}
