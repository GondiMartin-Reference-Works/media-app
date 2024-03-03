package com.example.mediaApp;

import com.example.mediaApp.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MediaAppApplicationTests {

	private AppUser user;
	private Address home;

	@BeforeEach
	void contextLoads() {
		// arrange (home)
		home = new Address();
		home.setCountry("Hungary");
		home.setCity("Budapest");
		home.setStreet("Ördögmalom");
		// arrange (user)
		user = new AppUser();
		user.setId(1L);
		user.setEmail("martin.gondocs@gmail.com");
		user.setFirstName("Martin");
		user.setLastName("Göndöcs");
	}

	@Test
	void testUserSuccess(){
		assertEquals("AppUser(Martin, Göndöcs)", user.toString());
	}

	@Test
	void testAddingAddressSuccess(){
		// act
		user.setAddresses(List.of(home));

		// assert
		Address address = user.getAddresses().stream()
				.findAny()
				.orElse(null);
		assertNotNull(address);
		assertEquals("Address(Hungary, Budapest, Ördögmalom)", address.toString());
	}

	@Test
	void testReferenceDoesNotThrowException(){
		// act
		user.setAddresses(List.of(home));

		// assert
		assertDoesNotThrow(() -> user.toString());
		assertDoesNotThrow(() -> home.toString());
	}

	@Test
	void testFriendRequest(){
		// arrange
		AppUser friend = new AppUser();
		friend.setId(2L);
		friend.setEmail("rideg.vili@gmail.com");
		friend.setFirstName("Vilmos");
		friend.setLastName("Rideg");
		FriendRequest friendRequest = new FriendRequest();

		// act
		friendRequest.setSenderUser(user);
		friendRequest.setReceiverUser(friend);
		user.setFriendRequests(List.of(friendRequest));
		friend.setFriendRequests(List.of(friendRequest));

		// assert
		assertDoesNotThrow(user::toString);
		assertDoesNotThrow(friend::toString);
	}

	@Test
	void testGroupRequestApproveSuccess(){
		// arrange
		Group group = new Group();
		group.setName("Media app group");
		GroupRequest request = new GroupRequest();
		request.setGroup(group);
		request.setSenderUser(user);

		// act
		group.setGroupRequests(List.of(request));

		// assert
		AppUser groupRequestSender = (group.getGroupRequests().stream()
					.filter(gRequest -> gRequest.getSenderUser().equals(user))
					.findFirst()
					.orElse(null))
					.getSenderUser();
		assertEquals(groupRequestSender, user);
	}

	@Test
	void testGroupParticipantAddingSuccess(){
		// arrange
		Group group = new Group();
		group.setName("Media app group");

		// act
		group.setParticipantUsers(List.of(user));
		user.setJoinedGroups(List.of(group));

		// assert
		assertTrue(group.getParticipantUsers().contains(user));
		assertTrue(user.getJoinedGroups().contains(group));
	}
}
