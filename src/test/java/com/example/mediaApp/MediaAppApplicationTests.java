package com.example.mediaApp;

import com.example.mediaApp.model.entity.AddressEntity;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.FriendRequestEntity;
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

	private AppUserEntity user;
	private AddressEntity home;

	@BeforeEach
	void contextLoads() {
		home = new AddressEntity();
		home.setCountry("Hungary");
		home.setCity("Budapest");
		home.setStreet("Ördögmalom");
	}

	@Test
	void testUserSuccess(){
		// arrange
		user = new AppUserEntity();

		// act
		user.setFirstName("Vilmos");
		user.setLastName("Rideg");

		// assert
		assertEquals("AppUserEntity(Vilmos, Rideg)", user.toString());
	}

	@Test
	void testAddingAddressSuccess(){
		// arrange
		user = new AppUserEntity();
		user.setAddressList(new ArrayList<>());

		// act
		user.addAddress(home);
		home.setUser(user);

		// assert
		AddressEntity addressEntity = user.getAddressList().stream()
				.filter(addr -> addr.getUser().equals(user))
				.findAny()
				.orElse(null);
		assertNotNull(addressEntity);
		assertEquals("AddressEntity(Hungary, Budapest, Ördögmalom)", addressEntity.toString());
	}

	@Test
	void testReferenceDoesNotThrowException(){
		// arrange
		user = new AppUserEntity();
		user.setAddressList(new ArrayList<>());

		// act
		user.addAddress(home);
		home.setUser(user);

		// assert
		assertDoesNotThrow(() -> user.toString());
		assertDoesNotThrow(() -> home.toString());
	}

	@Test
	void testFriendRequest(){
		// arrange
		user = new AppUserEntity();
		user.setId(1L);
		user.setEmail("martin.gondocs@gmail.com");
		user.setFirstName("Martin");
		user.setLastName("Göndöcs");
		AppUserEntity friend = new AppUserEntity();
		friend.setId(2L);
		friend.setEmail("rideg.vili@gmail.com");
		friend.setFirstName("Vilmos");
		friend.setLastName("Rideg");
		FriendRequestEntity friendRequest = new FriendRequestEntity();

		friendRequest.setSenderUser(user);
		friendRequest.setReceiverUser(friend);
		user.setFriendRequest(List.of(friendRequest));
		friend.setFriendRequest(List.of(friendRequest));
		assertDoesNotThrow(user::toString);
		assertDoesNotThrow(friend::toString);

	}
}
