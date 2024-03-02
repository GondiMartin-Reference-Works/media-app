package com.example.mediaApp;

import com.example.mediaApp.model.entity.Address;
import com.example.mediaApp.model.entity.AppUser;
import com.example.mediaApp.model.entity.FriendRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
		assertEquals("AppUserEntity(Vilmos, Rideg)", user.toString());
	}

	@Test
	void testAddingAddressSuccess(){
		// arrange
		user = new AppUser();
		user.setAddressList(new ArrayList<>());

		// act
		user.addAddress(home);

		// assert
		Address address = user.getAddressList().stream()
				.findAny()
				.orElse(null);
		assertNotNull(address);
		assertEquals("AddressEntity(Hungary, Budapest, Ördögmalom)", address.toString());
	}

	@Test
	void testReferenceDoesNotThrowException(){
		// arrange
		user = new AppUser();
		user.setAddressList(new ArrayList<>());

		// act
		user.addAddress(home);

		// assert
		assertDoesNotThrow(() -> user.toString());
		assertDoesNotThrow(() -> home.toString());
	}

	@Test
	void testFriendRequest(){
		// arrange
		user = new AppUser();
		user.setId(1L);
		user.setEmail("martin.gondocs@gmail.com");
		user.setFirstName("Martin");
		user.setLastName("Göndöcs");
		AppUser friend = new AppUser();
		friend.setId(2L);
		friend.setEmail("rideg.vili@gmail.com");
		friend.setFirstName("Vilmos");
		friend.setLastName("Rideg");
		FriendRequest friendRequest = new FriendRequest();

		friendRequest.setSenderUser(user);
		friendRequest.setReceiverUser(friend);
		user.setFriendRequest(List.of(friendRequest));
		friend.setFriendRequest(List.of(friendRequest));
		assertDoesNotThrow(user::toString);
		assertDoesNotThrow(friend::toString);

	}
}
