from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship

from database import Base


class Category(Base):
    __tablename__ = "category"

    id = Column(Integer, primary_key=True, autoincrement=True, index=True, name="category_id")
    level = Column(Integer, nullable=False, comment="카테고리 레벨")
    name = Column(String(40), nullable=False, comment="카테고리 이름")
    code = Column(String(10), nullable=False, comment="카테고리 코드")
    parent_id = Column(Integer, ForeignKey("category.category_id"), nullable=True, comment="부모 카테고리 ID")

    # 자기참조 관계 설정 (부모-자식 관계)
    parent = relationship("Category", remote_side=[id], backref="children")

    def __repr__(self):
        return f"<Category(id={self.id}, level={self.level}, name='{self.name}', code='{self.code}')>"
