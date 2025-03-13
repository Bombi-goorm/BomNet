package com.bombi.core.domain.interest.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.member.model.Role;
import com.bombi.core.domain.member.repository.MemberRepository;
import com.bombi.core.domain.notification.model.Notification;
import com.bombi.core.domain.notification.model.NotificationType;
import com.bombi.core.domain.notification.repository.NotificationRepository;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class NotificationRepoTest {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	EntityManager em;

	@Test
	void findUnreadNotifications() {
		Role role = new Role("role");
		Member member = Member.of("p", "e", role);
		memberRepository.save(member);
		em.persist(role);

		Notification notification1 = createNoti(member, "F");
		Notification notification2 = createNoti(member, "F");
		Notification notification3 = createNoti(member, "F");

		notificationRepository.saveAllAndFlush(List.of(notification1, notification2, notification3));

		em.flush();
		em.clear();

		//when
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<Notification> notificationPage = notificationRepository.findAllByMember_IdAndIsReadOrderByCreatedDateDesc(member.getId(),
			"F", pageRequest);

		//then
		Assertions.assertThat(notificationPage).hasSize(3);
	}

	@Test
	void readTwoUnreadNotification() {
		Role role = new Role("role");
		Member member = Member.of("p", "e", role);
		memberRepository.save(member);
		em.persist(role);

		Notification notification1 = createNoti(member, "F");
		Notification notification2 = createNoti(member, "F");
		Notification notification3 = createNoti(member, "T");

		notificationRepository.saveAllAndFlush(List.of(notification1, notification2, notification3));

		em.flush();
		em.clear();

		//when
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<Notification> notificationPage = notificationRepository.findAllByMember_IdAndIsReadOrderByCreatedDateDesc(member.getId(),
			"F", pageRequest);

		//then
		Assertions.assertThat(notificationPage).hasSize(2);
	}

	@Test
	void readOneAndFindUnreadNotificationsByMember() {
		Role role = new Role("role");
		Member member = Member.of("p", "e", role);
		memberRepository.save(member);
		em.persist(role);

		Notification notification1 = createNoti(member, "F");
		Notification notification2 = createNoti(member, "F");
		Notification notification3 = createNoti(member, "F");

		notificationRepository.saveAllAndFlush(List.of(notification1, notification2, notification3));

		em.flush();
		em.clear();

		//when
		notificationRepository.updateAllNotificationsByMember_IdAndIsRead(member.getId(), "T");

		//then
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<Notification> notificationPage = notificationRepository.findAllByMember_IdAndIsReadOrderByCreatedDateDesc(member.getId(),
			"F", pageRequest);
		Assertions.assertThat(notificationPage).isEmpty();
	}

	private static Notification createNoti(Member member, String isRead) {
		return Notification.builder()
			.member(member)
			.notificationType(NotificationType.WEATHER)
			.title("title")
			.message("message")
			.isRead(isRead)
			.build();
	}

}
