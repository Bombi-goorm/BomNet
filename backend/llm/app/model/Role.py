from __future__ import annotations

from sqlalchemy import Column, String, Integer
from sqlalchemy.orm import relationship
from app.database import Base


class Role(Base):
    __tablename__ = "role"

    id = Column(Integer, primary_key=True, autoincrement=True, comment="권한 ID")
    name = Column(String(50), nullable=False, unique=True, comment="권한 명")

    # 만약 forward reference가 필요하다면 __future__ import annotations를 각 파일 상단에 추가하거나
    # 전체 경로("app.model.Member.Member")를 사용할 수 있습니다.
    members = relationship("Member", back_populates="role")

    def __repr__(self):
        return f"<Role {self.name}>"
