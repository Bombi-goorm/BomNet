//package com.bombi.core.domain.interest.service;
//
//import static org.assertj.core.api.Assertions.*;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bombi.core.application.service.NotificationService;
//import com.bombi.core.domain.member.model.Member;
//import com.bombi.core.domain.member.model.Role;
//import com.bombi.core.domain.notification.model.Notification;
//import com.bombi.core.domain.notification.model.NotificationType;
//import com.bombi.core.domain.notification.repository.NotificationRepository;
//import com.bombi.core.presentation.dto.notification.ReadNotificationRequestDto;
//
//import jakarta.persistence.EntityManager;
//
//@Disabled
//@SpringBootTest
//@Transactional
//@ActiveProfiles("test")
//public class NotificationServiceTest {
//
//	@Autowired
//	private NotificationService notificationService;
//
//	@Autowired
//	EntityManager em;
//	@Autowired
//	private NotificationRepository notificationRepository;
//
//	@Test
//	void readOneNoti() {
//		//given
//		Role role = new Role("role");
//		Member member = Member.of("p", "e", role);
//		em.persist(member);
//		em.persist(role);
//
//		Notification notification1 = createNoti(member, "F");
//
//		em.persist(notification1);
//
//		em.flush();
//		em.clear();
//
//		//when
//		ReadNotificationRequestDto requestDto = new ReadNotificationRequestDto(notification1.getId());
//		notificationService.markAsRead(requestDto);
//
//		//then
//		Notification readNotification = notificationRepository.findById(notification1.getId())
//			.orElseThrow();
//		assertThat(readNotification).extracting(Notification::getIsRead).isEqualTo("T");
//	}
//
//	@Test
//	void readAllNoti() {
//		//given
//		Role role = new Role("role");
//		Member member = Member.of("p", "e", role);
//		em.persist(member);
//		em.persist(role);
//
//		Notification notification1 = createNoti(member, "F");
//		Notification notification2 = createNoti(member, "F");
//		Notification notification3 = createNoti(member, "F");
//
//		em.persist(notification1);
//		em.persist(notification2);
//		em.persist(notification3);
//
//		em.flush();
//		em.clear();
//
//		//when
//		notificationService.markAsReadAllNotification(member.getId().toString());
//
//		//then
//		List<Notification> readNotifications = notificationRepository.findAll();
//		assertThat(readNotifications).extracting(Notification::getIsRead).containsOnly("T");
//	}
//
//	private static Notification createNoti(Member member, String isRead) {
//		return Notification.builder()
//			.member(member)
//			.notificationType(NotificationType.WEATHER)
//			.title("title")
//			.message("message")
//			.isRead(isRead)
//			.build();
//	}
//}
