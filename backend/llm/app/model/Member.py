from __future__ import annotations

from sqlalchemy import Column, String, UUID, ForeignKey, Integer
from sqlalchemy.orm import relationship
from app.database import Base
import uuid


class Member(Base):
    __tablename__ = "member"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, comment="회원 ID")
    platform = Column(String(10), nullable=False, comment="소셜 로그인")
    auth_email = Column(String(100), nullable=False, unique=True, comment="이메일")
    is_enabled = Column(String(1), default="Y", comment="활성 여부")
    is_banned = Column(String(1), default="N", comment="차단 여부")
    role_id = Column(Integer, ForeignKey("role.id"), nullable=True, comment="권한 ID")

    role = relationship("Role", back_populates="members")
    notifications = relationship("Notification", back_populates="member")
    notification_conditions = relationship("NotificationCondition", back_populates="member")

    def __repr__(self):
        return f"<Member {self.auth_email} ({self.platform})>"
