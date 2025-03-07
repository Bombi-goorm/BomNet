from typing import Optional
from sqlalchemy import Integer, String, ForeignKey
from sqlalchemy.orm import relationship, Mapped, mapped_column
from app.database import Base

class NotificationCondition(Base):
    __tablename__ = "notification_condition"

    id: Mapped[int] = mapped_column("notification_condition_id", Integer, primary_key=True, autoincrement=True)
    target_price: Mapped[int] = mapped_column(String, nullable=False, comment="지정가")
    active: Mapped[str] = mapped_column(String(1), default="Y", comment="알림 조건 활성화 여부")
    member_id: Mapped[int] = mapped_column(Integer, ForeignKey("member.id"), nullable=False, comment="멤버 ID")

    # 가격알림용 임시 속성
    category: Mapped[Optional[str]] = mapped_column(String, nullable=True, comment="대분류")
    item: Mapped[Optional[str]] = mapped_column(String, nullable=True, comment="중분류")
    variety: Mapped[Optional[str]] = mapped_column(String, nullable=True, comment="하위 품종")
    region: Mapped[Optional[str]] = mapped_column(String, nullable=True, comment="지역")

    member = relationship("Member", back_populates="notification_conditions")

    def __repr__(self):
        return f"<NotificationCondition id={self.id} target_price={self.target_price}>"