from __future__ import annotations

from sqlalchemy import Column, String, Integer, Enum, ForeignKey
from sqlalchemy.orm import relationship
from app.database import Base
from enum import Enum as PyEnum


class NotificationType(PyEnum):
    WEATHER = "특보 알림"
    TARGET_PRICE = "가격 알림"


class Notification(Base):
    __tablename__ = "notification"

    id = Column(Integer, primary_key=True, autoincrement=True)
    member_id = Column(Integer, ForeignKey("member.id"), nullable=False, comment="멤버 ID")
    # Product의 PK 컬럼은 실제 DB에서는 'product_id'이므로 외래키도 이 이름으로 지정합니다.
    product_id = Column(Integer, ForeignKey("product.product_id"), nullable=False, comment="작물 ID")
    notification_type = Column(Enum(NotificationType), nullable=False, comment="알림 종류")
    message = Column(String(255), nullable=True, comment="알림 내용")
    is_read = Column(String(1), default="F", comment="알림 확인 여부")

    member = relationship("Member", back_populates="notifications")
    product = relationship("Product", back_populates="notifications")

    def __repr__(self):
        return f"<Notification id={self.id} type={self.notification_type}>"
