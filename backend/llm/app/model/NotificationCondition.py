from __future__ import annotations

from typing import Optional
from sqlalchemy import Integer, String, ForeignKey
from sqlalchemy.orm import relationship, Mapped, mapped_column
from app.database import Base

class NotificationCondition(Base):
    __tablename__ = "notification_condition"

    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)
    target_price: Mapped[Optional[int]] = mapped_column(Integer, nullable=True, comment="지정가")
    active: Mapped[str] = mapped_column(String(1), default="Y", comment="알림 조건 활성화 여부")
    # Member의 __tablename__은 "member"이므로 외래키는 "member.id"
    member_id: Mapped[int] = mapped_column(Integer, ForeignKey("member.id"), nullable=False, comment="멤버 ID")
    # Product의 PK는 DB에 'product_id'로 생성되므로 외래키는 "product.product_id"
    product_id: Mapped[int] = mapped_column(Integer, ForeignKey("product.product_id"), nullable=False, comment="작물 ID")

    member = relationship("Member", back_populates="notification_conditions")
    product = relationship("Product", back_populates="notification_conditions")

    def __repr__(self):
        return f"<NotificationCondition id={self.id} target_price={self.target_price}>"
