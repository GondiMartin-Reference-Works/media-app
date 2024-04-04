package com.example.mediaApp;

import com.example.mediaApp.model.entity.AddressEntity;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.FriendRequestEntity;
import com.example.mediaApp.model.entity.GroupEntity;
import com.example.mediaApp.model.entity.GroupRequestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MediaAppApplicationTests {

	private AppUserEntity user;
	private AddressEntity home;

	@BeforeEach
	void contextLoads() {
		// arrange (home)
		home = new AddressEntity();
		home.setCountry("Hungary");
		home.setCity("Budapest");
		home.setStreet("Ördögmalom");
		// arrange (user)
		user = new AppUserEntity();
		user.setId(1L);
		user.setEmail("martin.gondocs@gmail.com");
		user.setFirstName("Martin");
		user.setLastName("Göndöcs");
	}

	@Test
	void testUserSuccess(){
		assertEquals("AppUserEntity(Martin, Göndöcs)", user.toString());
	}

	@Test
	void testAddingAddressSuccess(){
		// act
		user.setAddresses(List.of(home));

		// assert
		AddressEntity addressEntity = user.getAddresses().stream()
				.findAny()
				.orElse(null);
		assertNotNull(addressEntity);
		assertEquals("AddressEntity(Hungary, Budapest, Ördögmalom)", addressEntity.toString());
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
		AppUserEntity friend = new AppUserEntity();
		friend.setId(2L);
		friend.setEmail("rideg.vili@gmail.com");
		friend.setFirstName("Vilmos");
		friend.setLastName("Rideg");
		FriendRequestEntity friendRequestEntity = new FriendRequestEntity();

		// act
		friendRequestEntity.setSenderUser(user);
		friendRequestEntity.setReceiverUser(friend);
		user.setFriendRequests(List.of(friendRequestEntity));
		friend.setFriendRequests(List.of(friendRequestEntity));

		// assert
		assertDoesNotThrow(user::toString);
		assertDoesNotThrow(friend::toString);
	}

	@Test
	void testGroupRequestApproveSuccess(){
		// arrange
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setName("Media app group");
		GroupRequestEntity request = new GroupRequestEntity();
		request.setGroup(groupEntity);
		request.setSenderUser(user);

		// act
		groupEntity.setGroupRequests(List.of(request));

		// assert
		AppUserEntity groupRequestSender = (groupEntity.getGroupRequests().stream()
					.filter(gRequest -> gRequest.getSenderUser().equals(user))
					.findFirst()
					.orElse(new GroupRequestEntity()))
					.getSenderUser();
		assertEquals(groupRequestSender, user);
	}

	@Test
	void testGroupParticipantAddingSuccess(){
		// arrange
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setName("Media app group");

		// act
		groupEntity.setParticipantUsers(List.of(user));
		user.setJoinedGroups(List.of(groupEntity));

		// assert
		assertTrue(groupEntity.getParticipantUsers().contains(user));
		assertTrue(user.getJoinedGroups().contains(groupEntity));
	}
}
